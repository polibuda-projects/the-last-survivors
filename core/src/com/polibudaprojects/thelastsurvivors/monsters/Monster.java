package com.polibudaprojects.thelastsurvivors.monsters;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.polibudaprojects.thelastsurvivors.Player.Player;
import com.polibudaprojects.thelastsurvivors.Player.Statistics;
import com.polibudaprojects.thelastsurvivors.items.Item;
import com.polibudaprojects.thelastsurvivors.monsters.types.Type;
import com.polibudaprojects.thelastsurvivors.weapons.Weapon;

import java.util.HashMap;

import static com.polibudaprojects.thelastsurvivors.monsters.phases.Phase.SPAWN_RADIUS_A_MAX;
import static com.polibudaprojects.thelastsurvivors.monsters.phases.Phase.SPAWN_RADIUS_B_MAX;

public class Monster {

    private static final float MAX_DISTANCE_TO_PLAYER = Math.max(SPAWN_RADIUS_A_MAX, SPAWN_RADIUS_B_MAX);
    private static final float VELOCITY_UPDATE_INTERVAL = 0.5f;
    public final HashMap<Weapon, Integer> wasHitBy = new HashMap<>();
    private final Sprite sprite;
    private final Type type;
    private final Vector2 position;
    private float timeSinceLastVelocityUpdate = VELOCITY_UPDATE_INTERVAL;
    private Vector2 velocity;
    private Animation<TextureRegion> animation;
    private float animationTime = 0f;
    private long lastAttackTime;
    private int health;

    public Monster(Type type, Vector2 position) {
        this.type = type;
        this.position = position;
        this.velocity = new Vector2();
        this.sprite = type.getNewSprite();
        this.health = type.getMaxHealth();
        this.animation = type.getWalkAnimation();
    }

    public void update(float deltaTime, Vector2 playerPosition) {
        updateAnimation(deltaTime);
        if (!isDead()) {
            timeSinceLastVelocityUpdate += deltaTime;
            if (timeSinceLastVelocityUpdate >= VELOCITY_UPDATE_INTERVAL) {
                updateVelocity(playerPosition);
                timeSinceLastVelocityUpdate = 0f;
            }
            position.mulAdd(velocity, deltaTime);
        }
    }

    private void updateVelocity(Vector2 playerPosition) {
        Vector2 direction = new Vector2(
                playerPosition.x - position.x - sprite.getWidth() / 2f,
                playerPosition.y - position.y - sprite.getHeight() / 2f
        );
        velocity = direction.nor().scl(type.getSpeed());
    }

    public void draw(SpriteBatch batch) {
        sprite.setPosition(position.x, position.y);
        sprite.setFlip(velocity.x < 0, false);
        sprite.draw(batch);
    }

    private void updateAnimation(float deltaTime) {
        animationTime += deltaTime;
        if (animation.isAnimationFinished(animationTime) && !isDead()) {
            animation = type.getWalkAnimation();
        }
        sprite.setRegion(animation.getKeyFrame(animationTime));
    }

    private void replaceAnimation(Animation<TextureRegion> type) {
        animation = type;
        animationTime = 0f;
    }

    public void attack(Player player) {
        System.out.println(type.getName() + " attacked player dealing " + type.getDamage() + " damage");
        player.takeDamage(type.getDamage());
        replaceAnimation(type.getAttackAnimation());
        lastAttackTime = TimeUtils.millis();
    }

    public boolean canAttack(Vector2 playerPosition) {
        return !isDead() &&
                TimeUtils.millis() - lastAttackTime > type.getAttackInterval() &&
                canReach(playerPosition);
    }

    private boolean canReach(Vector2 playerPosition) {
        return sprite.getBoundingRectangle().contains(playerPosition);
    }

    public void takeDamage(int damage) {
        if (!isDead()) {
            replaceAnimation(type.getHitAnimation());
            Statistics.getInstance().addToTotalDamage(damage);
            health -= damage;
            applyKnockback();
            if (isDead()) {
                Statistics.getInstance().addToKilledMonsters(1);
                replaceAnimation(type.getDieAnimation());
            }
        }
    }

    private void applyKnockback() {
        position.mulAdd(velocity, -0.8f);
    }

    public boolean isDead() {
        return health <= 0;
    }

    public boolean isDeathAnimationFinished() {
        return isDead() && animation.isAnimationFinished(animationTime);
    }

    public boolean hasExceededMaxDistance(Vector2 playerPosition) {
        return position.dst(playerPosition) > MAX_DISTANCE_TO_PLAYER;
    }

    public Item tryToDropItem() {
        if (!isDead()) {
            return null;
        }
        return type.dropItem(position);
    }

    public Rectangle getBoundingRectangle() {
        return sprite.getBoundingRectangle();
    }
}
