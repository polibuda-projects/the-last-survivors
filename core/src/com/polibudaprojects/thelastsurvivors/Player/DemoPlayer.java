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
        DEAD, ATTACKING_DEF,ATTACKING_SPEC;
    }

    public State currentState;
    public State previousState;

    public boolean defaultAttack = true;
    private Animation playerFirstAttack;
    private Animation playerSecondAttack;

    private Animation playerDeath;
    private float stateTimer;
    private boolean runningRight = true;

    public Vector2 position;
    public Sprite sprite;
    public float speed = 100f;

    private int health = 1000;

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
        currentState = State.ATTACKING_DEF;
        previousState = State.ATTACKING_DEF;
        stateTimer = 0;
        runningRight = true;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 1; i < 5; i++) {
            frames.add(new TextureRegion(img, i * 144, 720, 144, 80));
        }
        for (int i = 1; i < 4; i++) {
            frames.add(new TextureRegion(img, i * 144, 880, 144, 80));
        }

        for (int i = 1; i < 5; i++) {
            frames.add(new TextureRegion(img, i * 144, 960, 144, 80));
        }

        for (int i = 1; i < 5; i++) {
            frames.add(new TextureRegion(img, i * 144,1280 , 144, 80));
        }


        playerFirstAttack = new Animation(0.2f, frames);
        frames.clear();

        for (int i = 1; i < 16; i++) {
            frames.add(new TextureRegion(img, i * 144, 1520, 144, 80));
        }

        playerSecondAttack = new Animation(0.1f, frames);
        frames.clear();


        for (int i = 1; i < 12; i++) {
            frames.add(new TextureRegion(img, i * 144, 1920, 144, 80));
        }

        playerDeath = new Animation(0.4f, frames);
        frames.clear();

    }

    public void update(float deltaTime) {
        sprite.setRegion(getFrame(deltaTime));
        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
            position.y += deltaTime * speed;
            sprite.setRegion(getFrame(deltaTime));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            position.y -= deltaTime * speed;
            sprite.setRegion(getFrame(deltaTime));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            position.x -= deltaTime * speed;
            sprite.setRegion(getFrame(deltaTime));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            position.x += deltaTime * speed;
            sprite.setRegion(getFrame(deltaTime));
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            runningRight = false;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            runningRight = true;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.E)){
            defaultAttack = false;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            defaultAttack = true;
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

        switch(currentState){
            case DEAD:
                region = (TextureRegion) playerDeath.getKeyFrame(stateTimer, true);;
                break;
            case ATTACKING_SPEC:
                region = (TextureRegion) playerSecondAttack.getKeyFrame(stateTimer, true);
                break;
            default:
                region = (TextureRegion) playerFirstAttack.getKeyFrame(stateTimer, true);
                break;
        }

        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;

        return region;
    }

    public State getState(){
        if(isDead()){
            return State.DEAD;
        }
        else if(!defaultAttack){
            return State.ATTACKING_SPEC;
        }
        else{
            return State.ATTACKING_DEF;
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
