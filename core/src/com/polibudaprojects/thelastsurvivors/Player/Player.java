package com.polibudaprojects.thelastsurvivors.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.polibudaprojects.thelastsurvivors.weapons.Weapon;

import java.util.ArrayList;

public abstract class Player {
    public static final float SPRITE_WIDTH = 180f;
    public static final float SPRITE_HEIGHT = 100f;
    public static final int REGEN_INTERVAL = 40;
    private static final int START_X = 5500;
    private static final int START_Y = 5500;
    protected final ArrayList<Weapon> weapons = new ArrayList<>();
    private final Texture texture;
    private final Vector2 position = new Vector2();
    private final Sprite sprite;
    private final int maxScore = 100;
    private final float speed;
    private final int initialHpRegen;
    private final int initialMaxHealth;
    private final Animation<TextureRegion> playerRunning;
    private final Animation<TextureRegion> playerStanding;
    private final Animation<TextureRegion> playerDeath;
    private final Animation<TextureRegion> playerHpRegen;
    private final Animation<TextureRegion> playerHit;
    private Animation<TextureRegion> animation;
    private float animationTime;
    private float regenTimer;
    private boolean runningRight;
    private int level;
    private int lastInput;
    private int hpRegen;
    private int maxHealth;
    private int currentHealth;
    private int score;

    public Player(Texture texture, int initialMaxHealth, int initialHpRegen, float initialSpeed) {
        this.texture = texture;
        this.initialMaxHealth = initialMaxHealth;
        this.initialHpRegen = initialHpRegen;
        this.speed = initialSpeed;

        playerRunning = loadRunningAnimation(texture);
        playerStanding = loadStandingAnimation(texture);
        playerDeath = loadDeathAnimation(texture);
        playerHpRegen = loadHpRegenAnimation(texture);
        playerHit = loadHitAnimation(texture);
        sprite = new Sprite(playerStanding.getKeyFrame(0));
        sprite.setSize(SPRITE_WIDTH, SPRITE_HEIGHT);

        reset();
        addDefaultWeapon();
    }

    private static boolean wantsToGoRight() {
        return Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT);
    }

    private static boolean wantsToGoLeft() {
        return Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT);
    }

    private static boolean wantsToGoDown() {
        return Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN);
    }

    private static boolean wantsToGoUp() {
        return Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP);
    }

    public void reset() {
        position.set(START_X, START_Y);

        level = 1;
        score = 0;
        hpRegen = initialHpRegen;
        maxHealth = initialMaxHealth;
        currentHealth = maxHealth;
        runningRight = true;

        animationTime = 0f;
        animation = playerStanding;
    }

    protected abstract void addDefaultWeapon();

    protected abstract Animation<TextureRegion> loadRunningAnimation(Texture texture);

    protected abstract Animation<TextureRegion> loadStandingAnimation(Texture texture);

    protected abstract Animation<TextureRegion> loadDeathAnimation(Texture texture);

    protected abstract Animation<TextureRegion> loadHpRegenAnimation(Texture texture);

    protected abstract Animation<TextureRegion> loadHitAnimation(Texture texture);

    public void draw(SpriteBatch batch) {
        sprite.setFlip(!runningRight, false);
        sprite.setPosition(position.x, position.y);
        sprite.draw(batch);

        for (Weapon weapon : weapons) {
            weapon.draw(batch);
        }
    }

    public void update(float deltaTime) {
        updateAnimation(deltaTime);
        if (!isDead()) {
            tryToLevelUp();
            tryToRegenerateHealth(deltaTime);
            move(deltaTime);
            updateWeapons(deltaTime);
        }
    }

    private void updateAnimation(float deltaTime) {
        animationTime += deltaTime;
        if ((animation.isAnimationFinished(animationTime) && animation != playerDeath) || (animation == playerRunning || animation == playerStanding)) {
            if (wantsToGoLeft() || wantsToGoRight() || wantsToGoDown() || wantsToGoUp()) {
                animation = playerRunning;
            } else {
                animation = playerStanding;
            }
        }
        sprite.setRegion(animation.getKeyFrame(animationTime));
    }

    private void replaceAnimation(Animation<TextureRegion> newAnimation) {
        animation = newAnimation;
        animationTime = 0f;
    }

    private void tryToLevelUp() {
        if (score >= maxScore) {
            if (level < 12) {
                maxHealth += 30;
                currentHealth += 10;
                hpRegen += 5;
            }
            level += 1;
            score = 0;
            System.out.println("Reached " + level + " Level!!!");
        }
    }

    private void tryToRegenerateHealth(float deltaTime) {
        regenTimer += deltaTime;
        int regenSec = (int) regenTimer;
        regenSec = regenSec % 60;
        if (regenSec >= REGEN_INTERVAL && currentHealth < maxHealth && currentHealth > 0) {
            regenTimer = 0f;
            if ((currentHealth + hpRegen) > maxHealth) {
                currentHealth = maxHealth;
            } else {
                currentHealth += hpRegen;
            }
            replaceAnimation(playerHpRegen);
            System.out.println("Regenerate " + hpRegen + " HP");
        }
    }

    private void move(float deltaTime) {
        if (wantsToGoUp()) {
            position.y += deltaTime * speed;
            lastInput = 0;
        }
        if (wantsToGoDown()) {
            position.y -= deltaTime * speed;
            lastInput = 1;
        }
        if (wantsToGoLeft()) {
            position.x -= deltaTime * speed;
            lastInput = 2;
        }
        if (wantsToGoRight()) {
            position.x += deltaTime * speed;
            lastInput = 3;
        }
        if (wantsToGoLeft() && !wantsToGoRight()) {
            runningRight = false;
        }
        if (wantsToGoRight() && !wantsToGoLeft()) {
            runningRight = true;
        }
    }

    private void updateWeapons(float deltaTime) {
        for (Weapon weapon : weapons) {
            if (level < 2) {
                weapon.update(deltaTime);
                break;
            } else {
                weapon.update(deltaTime);
            }
        }
    }

    public void takeDamage(int damage) {
        if (!isDead()) {
            currentHealth -= damage;
            if (isDead()) {
                replaceAnimation(playerDeath);
            } else {
                replaceAnimation(playerHit);
            }
        }
    }

    public void dispose() {
        texture.dispose();
    }

    public ArrayList<Weapon> getWeapons() {
        return weapons;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getCenterPosition() {
        return new Vector2(position.x + SPRITE_WIDTH * 0.5f, position.y + SPRITE_HEIGHT * 0.5f);
    }

    public boolean isDead() {
        return currentHealth <= 0;
    }

    public boolean isGameOver() {
        return isDead() && animation.isAnimationFinished(animationTime);
    }

    public boolean isRunningRight() {
        return runningRight;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public int getLastInput() {
        return lastInput;
    }

    public int getHpRegen() {
        return hpRegen;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public int getLevel() {
        return level;
    }

}
