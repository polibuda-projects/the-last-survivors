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
import java.util.List;

import static com.polibudaprojects.thelastsurvivors.monsters.phases.Phase.SPAWN_RADIUS_A_MAX;
import static com.polibudaprojects.thelastsurvivors.monsters.phases.Phase.SPAWN_RADIUS_B_MAX;

public class Monster {

    private static final float MAX_DISTANCE_TO_PLAYER = Math.max(SPAWN_RADIUS_A_MAX, SPAWN_RADIUS_B_MAX);
    private static final float VELOCITY_UPDATE_INTERVAL = 0.5f;
    private static final float PLAYER_ATTRACTION_FACTOR = 1.5f;
    public final HashMap<Weapon, Integer> wasHitBy = new HashMap<>();
    private final Sprite sprite;
    private final Type type;
    private final Vector2 position;
    private final Vector2 velocity = new Vector2();
    private final Vector2 velocityToPlayer = new Vector2();
    private float timeSinceLastVelocityUpdate = VELOCITY_UPDATE_INTERVAL;
    private Animation<TextureRegion> animation;
    private float animationTime = 0f;
    private long lastAttackTime;
    private int health;

    public Monster(Type type, Vector2 position) {
        this.type = type;
        this.position = position;
        this.sprite = type.getNewSprite();
        this.health = type.getMaxHealth();
        this.animation = type.getWalkAnimation();
    }

    public void update(List<Monster> others, Vector2 playerPosition, float deltaTime) {
        updateAnimation(deltaTime);
        if (!isDead()) {
            teleportIfShould(playerPosition); // Boss monster should teleport if is too far away
            updateVelocity(others, playerPosition, deltaTime);
            position.mulAdd(velocity, deltaTime);
        }
    }

    private void updateAnimation(float deltaTime) {
        animationTime += deltaTime;
        if (animation.isAnimationFinished(animationTime) && !isDead()) {
            animation = type.getWalkAnimation();
        }
        sprite.setRegion(animation.getKeyFrame(animationTime));
    }

    private void updateVelocity(List<Monster> others, Vector2 playerPosition, float deltaTime) {
        timeSinceLastVelocityUpdate += deltaTime;
        if (timeSinceLastVelocityUpdate >= VELOCITY_UPDATE_INTERVAL) {
            updateVelocityToPlayer(playerPosition);
            velocity.set(velocityToPlayer.cpy().add(getCollisionAvoidanceVelocity(others)));
            scaleVelocity();
            timeSinceLastVelocityUpdate = 0f;
        }
    }

    private void scaleVelocity() {
        if (velocity.len() > 1) {
            velocity.nor();
        }
        velocity.scl(type.getSpeed());
    }

    private void updateVelocityToPlayer(Vector2 playerPosition) {
        Vector2 direction = new Vector2(
                playerPosition.x - position.x - sprite.getWidth() / 2f,
                playerPosition.y - position.y - sprite.getHeight() / 2f
        );
        velocityToPlayer.set(direction.nor().scl(PLAYER_ATTRACTION_FACTOR));
    }

    private Vector2 getCollisionAvoidanceVelocity(List<Monster> others) {
        Vector2 avoidanceVelocity = new Vector2();
        for (Monster other : others) {
            float distance = position.dst(other.position);
            if (distance < sprite.getWidth() && distance != 0) {
                Vector2 distanceVector = position.cpy().sub(other.position);
                avoidanceVelocity.mulAdd(distanceVector, 1 / distance);
            }
        }
        return avoidanceVelocity.nor();
    }

    private void teleportIfShould(Vector2 playerPosition) {
        if (type.isBoss() && hasExceededMaxDistance(playerPosition)) {
            Vector2 distanceVector = playerPosition.cpy().sub(position);
            position.mulAdd(distanceVector, 2);
            timeSinceLastVelocityUpdate = VELOCITY_UPDATE_INTERVAL;
        }
    }

    public void draw(SpriteBatch batch) {
        sprite.setPosition(position.x, position.y);
        sprite.setFlip(isFlipX(), false);
        sprite.draw(batch);
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

    private boolean isFlipX() {
        return velocityToPlayer.x < 0;
    }

    public boolean shouldBeRemoved(Vector2 playerPosition) {
        return isDeathAnimationFinished() || (hasExceededMaxDistance(playerPosition) && !type.isBoss());
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
        return type.dropItem(new Vector2(
                position.x + sprite.getWidth() / 4,
                position.y + sprite.getHeight() / 4)
        );
    }

    public Rectangle getHitbox() {
        return type.getHitbox(position, isFlipX());
    }
}
