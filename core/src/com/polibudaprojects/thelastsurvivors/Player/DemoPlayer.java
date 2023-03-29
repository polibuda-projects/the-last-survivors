package com.polibudaprojects.thelastsurvivors.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class DemoPlayer {
    public enum State {
        DEAD, STANDING, RUNNING;
    }

    public State currentState;
    public State previousState;
    private final Animation playerRunning;

    private final Animation playerDeath;
    private float stateTimer;
    private boolean runningRight = true;

    public boolean dead;
    public Vector2 position;
    public Sprite sprite;
    public float speed = 150f;

    public int health = 60;

    private final int score = 0;

    private int counter = 0;

    Texture img = new Texture("player.png");

    TextureRegion playerStand;

    public DemoPlayer() {
        playerStand = new TextureRegion(img, 0, 0, 144, 80);
        sprite = new Sprite(playerStand);
        sprite.setSize(180f, 100f);
        position = new Vector2(
                (Gdx.graphics.getWidth() - sprite.getWidth()) / 2f,
                (Gdx.graphics.getHeight() - sprite.getHeight()) / 2f
        );
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;
        dead = false;

        Array<TextureRegion> frames = new Array<TextureRegion>();

        for (int i = 1; i < 8; i++) {
            frames.add(new TextureRegion(img, i * 144, 80, 144, 80));
        }

        playerRunning = new Animation(0.3f, frames);
        frames.clear();


        for (int i = 1; i < 12; i++) {
            frames.add(new TextureRegion(img, i * 144, 1920, 144, 80));
        }

        playerDeath = new Animation(0.4f, frames);
        frames.clear();

    }

    public void update(float deltaTime) {
        sprite.setRegion(getFrame(deltaTime));
        if ((Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) && !(currentState == State.DEAD)) {
            position.y += deltaTime * speed;
            sprite.setRegion(getFrame(deltaTime));
        }
        if ((Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) && !(currentState == State.DEAD)) {
            position.y -= deltaTime * speed;
            sprite.setRegion(getFrame(deltaTime));
        }
        if ((Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) && !(currentState == State.DEAD)) {
            position.x -= deltaTime * speed;
            sprite.setRegion(getFrame(deltaTime));
        }
        if ((Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) && !(currentState == State.DEAD)) {
            position.x += deltaTime * speed;
            sprite.setRegion(getFrame(deltaTime));
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            runningRight = false;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            runningRight = true;
        }

        if (isDead() && currentState == State.DEAD) {
            sprite.setRegion(getFrame(deltaTime));
            counter = counter + 1;
            if (counter == 128) {
                dead = true;
            }
        }
    }

    public TextureRegion getFrame(float dt) {
        if (runningRight && sprite.isFlipX()) {
            sprite.setFlip(false, false);
        } else if (!runningRight && !sprite.isFlipX()) {
            sprite.setFlip(true, false);
        }
        TextureRegion region;

        currentState = getState();

        switch (currentState) {
            case DEAD:
                region = (TextureRegion) playerDeath.getKeyFrame(stateTimer, true);
                break;
            case RUNNING:
                region = (TextureRegion) playerRunning.getKeyFrame(stateTimer, true);
                break;
            default:
                region = playerStand;
                break;
        }

        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;

        return region;
    }

    public State getState() {
        if (isDead()) {
            return State.DEAD;
        } else if ((Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) ||(Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT))  ||(Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) ||(Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) ) {
            return State.RUNNING;
        } else {
            return State.STANDING;
        }
    }

    public void draw(SpriteBatch batch) {
        update(Gdx.graphics.getDeltaTime());
        if (runningRight && sprite.isFlipX()) {
            sprite.setFlip(false, false);
        } else if (!runningRight && !sprite.isFlipX()) {
            sprite.setFlip(true, false);
        }
        sprite.setPosition(position.x, position.y);
        sprite.draw(batch);

    }

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getCenterPosition() {
        float centerX = position.x + sprite.getWidth() / 2f;
        float centerY = position.y + sprite.getHeight() / 2f;
        return new Vector2(centerX, centerY);
    }

    public void takeDamage(int damage) {
        health -= damage;
    }


    public boolean isDead() {
        return health <= 0;
    }
}
