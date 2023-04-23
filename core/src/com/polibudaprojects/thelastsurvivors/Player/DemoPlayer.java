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
import com.polibudaprojects.thelastsurvivors.weapons.FireWand;
import com.polibudaprojects.thelastsurvivors.weapons.Sword;
import com.polibudaprojects.thelastsurvivors.weapons.Weapon;

import java.util.ArrayList;

public class DemoPlayer {
    private final ArrayList<Weapon> weapons = new ArrayList<>();

    private float timer;

    private int level = 1;

    private float regenTimer;

    private final Animation<TextureRegion> playerDeath;
    private final Animation<TextureRegion> playerRunning;
    private final Animation<TextureRegion> playerStanding;

    private final Animation<TextureRegion> playerHpRegen;

    private final Animation<TextureRegion> playerHit;
    private boolean runningRight;
    private int lastInput;

    private int hpRegen = 20;

    private final Vector2 position;

    private final Sprite sprite;

    private int maxHealth = 100;
    private int currentHealth = 100;

    private final int score = 0;

    private float animationTime = 0f;

    private Animation<TextureRegion> animation;

    private boolean animationEnded;

    private boolean hit;

    private boolean gameOver;

    private boolean hpRestored;

    public DemoPlayer() {
        timer = 0.0f;
        Texture img = new Texture("player.png");
        TextureRegion playerStand = new TextureRegion(img, 0, 0, 144, 80);
        sprite = new Sprite(playerStand);
        sprite.setSize(180f, 100f);
        position = new Vector2(
                5500,
                5500
        );

        runningRight = true;

        Array<TextureRegion> frames = new Array<>();

        for (int i = 0; i < 8; i++) {
            frames.add(new TextureRegion(img, i * 144, 0, 144, 80));
        }

        this.playerStanding = new Animation<>(0.3f, frames, Animation.PlayMode.LOOP);
        frames.clear();

        for (int i = 0; i < 8; i++) {
            frames.add(new TextureRegion(img, i * 144, 80, 144, 80));
        }

        this.playerRunning = new Animation<>(0.2f, frames, Animation.PlayMode.LOOP);
        frames.clear();

        for (int i = 0; i < 5; i++) {
            frames.add(new TextureRegion(img, i * 144, 1840, 144, 80));
        }

        this.playerHit = new Animation<>(0.15f, frames, Animation.PlayMode.NORMAL);
        frames.clear();


        for (int i = 0; i < 12; i++) {
            frames.add(new TextureRegion(img, i * 144, 1920, 144, 80));
        }

        this.playerDeath = new Animation<>(0.4f, frames, Animation.PlayMode.NORMAL);
        frames.clear();

        for (int i = 0; i < 6; i++) {
            frames.add(new TextureRegion(img, i * 144, 1600, 144, 80));
        }

        this.playerHpRegen = new Animation<>(0.3f, frames, Animation.PlayMode.NORMAL);
        frames.clear();

        animation = this.playerStanding;

        this.weapons.add(new Sword(this));
        this.weapons.add(new FireWand(this));
    }

    public void update(float deltaTime) {
        updateAnimation(deltaTime);

        timer += deltaTime;
        regenTimer += deltaTime;
        int seconds = (int) timer;
        int hours = seconds / 3600;
        int minutes = (seconds - (hours * 3600)) / 60;
        if (minutes > 5 && level == 1) {
            maxHealth = 300;
            hpRegen = 40;
            level = 2;
            currentHealth += 100;
            System.out.println("Max HP increased to 300");
            System.out.println("HP Regen increased to 40");
        }
        if (minutes > 10 && level == 2) {
            maxHealth = 700;
            hpRegen = 60;
            level = 3;
            currentHealth += 300;
            System.out.println("Max HP increased to 700");
            System.out.println("HP Regen increased to 60");
        }
        int regenSec = (int) regenTimer;
        regenSec = regenSec % 60;
        if (regenSec >= 40 && currentHealth < maxHealth) {
            regenTimer = 0.0f;
            if ((currentHealth + hpRegen) > maxHealth) {
                currentHealth = maxHealth;
            } else {
                currentHealth += hpRegen;
            }
            hpRestored = true;
            System.out.println("Regenerate " + hpRegen + " HP");
        }
        float speed = 150f;
        if (!isDead() && !isHiT()) {
            if ((Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP))) {
                position.y += deltaTime * speed;
                lastInput = 0;
            }
            if ((Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN))) {
                position.y -= deltaTime * speed;
                lastInput = 1;
            }
            if ((Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT))) {
                position.x -= deltaTime * speed;
                lastInput = 2;
            }
            if ((Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT))) {
                position.x += deltaTime * speed;
                lastInput = 3;
            }
            if ((Gdx.input.isKeyJustPressed(Input.Keys.A) && !Gdx.input.isKeyPressed(Input.Keys.D)) || (Gdx.input.isKeyPressed(Input.Keys.A) && !Gdx.input.isKeyPressed(Input.Keys.D)) || (Gdx.input.isKeyJustPressed(Input.Keys.LEFT) && !Gdx.input.isKeyPressed(Input.Keys.RIGHT)) || (Gdx.input.isKeyPressed(Input.Keys.LEFT) && !Gdx.input.isKeyPressed(Input.Keys.RIGHT))) {
                runningRight = false;
            }
            if ((Gdx.input.isKeyJustPressed(Input.Keys.D) && !Gdx.input.isKeyPressed(Input.Keys.A)) || (Gdx.input.isKeyPressed(Input.Keys.D) && !Gdx.input.isKeyPressed(Input.Keys.A)) || (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) && !Gdx.input.isKeyPressed(Input.Keys.LEFT)) || (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && !Gdx.input.isKeyPressed(Input.Keys.LEFT))) {
                runningRight = true;
            }
        }
        for (Weapon weapon : weapons) {
            weapon.update(deltaTime);
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

        for (Weapon weapon : weapons) {
            weapon.draw(batch);
        }
    }

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getCenterPosition() {
        float centerX = position.x + sprite.getWidth() / 2f;
        float centerY = position.y + sprite.getHeight() / 2f;
        //Todo fix memory leak
        return new Vector2(centerX, centerY);
    }

    public void takeDamage(int damage) {
        if ((currentHealth - damage) < 0) {
            currentHealth = 0;
        } else {
            hit = true;
            currentHealth -= damage;
        }
    }


    public boolean isDead() {
        return currentHealth <= 0;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean isHiT() {
        return hit;
    }

    public boolean isHpRestored() {
        return hpRestored;
    }


    private void updateAnimation(float deltaTime) {
        animationTime += deltaTime;
        if (animationEnded && animation == getPlayerDeath()) {
            if (animationTime >= 4.8f) {
                gameOver = true;
            }

        } else if (animationEnded && animation == getPlayerHit()) {
            if (animationTime >= 0.75f) {
                hit = false;
                animationEnded = false;
            }
        } else if (animationEnded && animation == getPlayerHpRegen()) {
            if (animationTime >= 2.0f) {
                hpRestored = false;
                animationEnded = false;
            }
        } else {
            if (((Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) || (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) || (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) || (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP))) && !isDead() && !isHiT() && !isHpRestored()) {
                animation = this.getPlayerRunning();
            } else if (isDead()) {
                animation = this.getPlayerDeath();
                animationTime = 0f;
                animationEnded = true;

            } else if (isHiT()) {
                animation = this.getPlayerHit();
                animationTime = 0f;
                animationEnded = true;
            } else if (isHpRestored()) {
                animation = this.getPlayerHpRegen();
                animationTime = 0f;
                animationEnded = true;
            } else {
                animation = this.getPlayerStanding();
            }
        }
        sprite.setRegion(animation.getKeyFrame(animationTime));
    }

    public Animation<TextureRegion> getPlayerDeath() {
        return playerDeath;
    }

    public Animation<TextureRegion> getPlayerRunning() {
        return playerRunning;
    }

    public Animation<TextureRegion> getPlayerStanding() {
        return playerStanding;
    }

    public Animation<TextureRegion> getPlayerHpRegen() {
        return playerHpRegen;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public Animation<TextureRegion> getPlayerHit() {
        return playerHit;
    }

    public ArrayList<Weapon> getWeapons() {
        return weapons;
    }

    public boolean isRunningRight() {
        return runningRight;
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public int getLastInput() {
        return lastInput;
    }

    public int getHpRegen() {
        return hpRegen;
    }
}
