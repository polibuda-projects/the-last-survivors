package com.polibudaprojects.thelastsurvivors.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.polibudaprojects.thelastsurvivors.Assets;
import com.polibudaprojects.thelastsurvivors.weapons.Weapon;

import java.util.ArrayList;

public abstract class Player {
    public static final int MAX_WEAPONS_COUNT = 3;
    public static final int REGEN_INTERVAL = 40;
    protected final ArrayList<Weapon> weapons = new ArrayList<>(MAX_WEAPONS_COUNT);
    private final int startX;
    private final int startY;
    private final int centerOffsetX;
    private final int centerOffsetY;
    private final Vector2 position = new Vector2();
    private final Vector2 velocity = new Vector2();
    private final Vector2 lastVelocity = Vector2.X;
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
    private boolean facingLeft;
    private int level;
    private int hpRegen;
    private int maxHealth;
    private int currentHealth;
    private int score;

    public Player(String textureFileName, int initialMaxHealth, int initialHpRegen, float initialSpeed, float spriteWidth, float spriteHeight, int startX, int startY, int centerOffsetX, int centerOffsetY) {
        Texture texture = Assets.get(textureFileName, Texture.class);

        this.initialMaxHealth = initialMaxHealth;
        this.initialHpRegen = initialHpRegen;
        this.speed = initialSpeed;

        this.startX = startX;
        this.startY = startY;
        this.centerOffsetX = centerOffsetX;
        this.centerOffsetY = centerOffsetY;

        playerRunning = loadRunningAnimation(texture);
        playerStanding = loadStandingAnimation(texture);
        playerDeath = loadDeathAnimation(texture);
        playerHpRegen = loadHpRegenAnimation(texture);
        playerHit = loadHitAnimation(texture);
        sprite = new Sprite(playerStanding.getKeyFrame(0));
        sprite.setSize(spriteWidth, spriteHeight);

        reset();
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
        position.set(startX, startY);

        level = 1;
        score = 0;
        hpRegen = initialHpRegen;
        maxHealth = initialMaxHealth;
        currentHealth = maxHealth;
        facingLeft = false;

        animationTime = 0f;
        animation = playerStanding;

        weapons.clear();
        addDefaultWeapon();
    }

    protected abstract void addDefaultWeapon();
    protected abstract void addAdditionalWeapon();

    protected abstract Animation<TextureRegion> loadRunningAnimation(Texture texture);

    protected abstract Animation<TextureRegion> loadStandingAnimation(Texture texture);

    protected abstract Animation<TextureRegion> loadDeathAnimation(Texture texture);

    protected abstract Animation<TextureRegion> loadHpRegenAnimation(Texture texture);

    protected abstract Animation<TextureRegion> loadHitAnimation(Texture texture);

    public void draw(SpriteBatch batch) {
        sprite.setFlip(facingLeft, false);
        sprite.setPosition(position.x, position.y);
        sprite.draw(batch);

        for (Weapon weapon : weapons) {
            weapon.draw(batch);
        }
    }

    public void update(float deltaTime) {
        if (!isDead()) {
            tryToLevelUp();
            tryToRegenerateHealth(deltaTime);
            move(deltaTime);
            updateWeapons(deltaTime);
        }
        updateAnimation(deltaTime);
    }

    private void updateAnimation(float deltaTime) {
        animationTime += deltaTime;
        if (shouldChangeAnimation()) {
            if (velocity.isZero()) {
                animation = playerStanding;
            } else {
                animation = playerRunning;
            }
        }
        sprite.setRegion(animation.getKeyFrame(animationTime));
    }

    private boolean shouldChangeAnimation() {
        return (animation.isAnimationFinished(animationTime) && animation != playerDeath) || (animation == playerRunning || animation == playerStanding);
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
            if (level < 99) {
                level += 1;
                score -=maxScore;
                System.out.println("Reached " + level + " Level!!!");
            }
            if (level == 2) {
                addAdditionalWeapon();
            }
        }
    }

    private void tryToRegenerateHealth(float deltaTime) {
        regenTimer += deltaTime;
        if (regenTimer >= REGEN_INTERVAL && currentHealth < maxHealth) {
            regenTimer = 0f;
            currentHealth = Math.min(currentHealth + hpRegen, maxHealth);
            replaceAnimation(playerHpRegen);
            System.out.println("Regenerate " + hpRegen + " HP");
        }
    }

    private void move(float deltaTime) {
        velocity.setZero();

        if (wantsToGoUp()) {
            velocity.add(0, 1);
        }
        if (wantsToGoDown()) {
            velocity.add(0, -1);
        }
        if (wantsToGoLeft()) {
            velocity.add(-1, 0);
            facingLeft = true;
        }
        if (wantsToGoRight()) {
            velocity.add(1, 0);
            facingLeft = false;
        }

        if (!velocity.isZero()) {
            lastVelocity.set(velocity);
            velocity.nor().scl(speed * deltaTime);
            position.add(velocity);
        }
    }

    private void updateWeapons(float deltaTime) {
        weapons.forEach(weapon -> weapon.update(deltaTime));
    }

    public void takeDamage(int damage) {
        if (!isDead()) {
            currentHealth = Math.max(currentHealth - damage, 0);
            if (isDead()) {
                replaceAnimation(playerDeath);
            } else {
                replaceAnimation(playerHit);
            }
        }
    }

    public ArrayList<Weapon> getWeapons() {
        return weapons;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getCenterPosition() {
        return new Vector2(position.x + centerOffsetX, position.y + centerOffsetY);
    }

    public Vector2 getLastVelocity() {
        return new Vector2(lastVelocity);
    }

    public boolean isDead() {
        return currentHealth <= 0;
    }

    public boolean isGameOver() {
        return isDead() && animation.isAnimationFinished(animationTime);
    }

    public boolean isFacingLeft() {
        return facingLeft;
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
