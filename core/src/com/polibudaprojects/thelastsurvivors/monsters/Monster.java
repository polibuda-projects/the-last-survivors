package com.polibudaprojects.thelastsurvivors.monsters;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.polibudaprojects.thelastsurvivors.Player.DemoPlayer;
import com.polibudaprojects.thelastsurvivors.monsters.types.Type;
import com.polibudaprojects.thelastsurvivors.weapons.Weapon;

import java.util.HashMap;

import static com.polibudaprojects.thelastsurvivors.monsters.phases.Phase.SPAWN_RADIUS_MAX;

public class Monster {

    private static final float MAX_DISTANCE_TO_PLAYER = SPAWN_RADIUS_MAX;
    private static final float VELOCITY_UPDATE_INTERVAL = 0.5f;
    private float timeSinceLastVelocityUpdate = VELOCITY_UPDATE_INTERVAL;

    private final Sprite sprite;
    private final Type type;
    private final Vector2 position;
    private Vector2 velocity;
    private Animation<TextureRegion> animation;
    private float animationTime = 0f;
    private long lastAttackTime;
    private int health;

    public HashMap<Weapon, Long> wasHitBy = new HashMap<>();

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
        if (!isDead() && !canReach(playerPosition)) {
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

    public void attackIfPossible(DemoPlayer player) {
        if (canAttack(player)) {
            System.out.println(type.getName() + " attacked player dealing " + type.getDamage() + " damage");
            player.takeDamage(type.getDamage());
            replaceAnimation(type.getAttackAnimation());
            lastAttackTime = TimeUtils.millis();
        }
    }

    private boolean canAttack(DemoPlayer player) {
        return !isDead() &&
                TimeUtils.millis() - lastAttackTime > type.getAttackInterval() &&
                canReach(player.getCenterPosition());
    }

    private boolean canReach(Vector2 playerPosition) {
        return sprite.getBoundingRectangle().contains(playerPosition);
    }

    public void takeDamage(int damage, Weapon weapon) {
        // TODO może lepiej to częściowo przenieść do weapon.canAttack(Monster monster)? Tu powinno być tylko zadanie obrażeń
        if (!isDead()) {
            if (!wasHitBy.containsKey(weapon)) {
                wasHitBy.put(weapon, TimeUtils.millis());
                replaceAnimation(type.getHitAnimation());
                health -= damage;
                applyKnockback(-0.8f);
            } else if (wasHitBy.get(weapon) + weapon.getAttackInterval() < TimeUtils.millis()) {
                replaceAnimation(type.getHitAnimation());
                health -= damage;
                wasHitBy.replace(weapon, TimeUtils.millis());
                applyKnockback(-0.8f);
            }
            if (isDead()) {
                replaceAnimation(type.getDieAnimation());
            }
        }
    }

    private void applyKnockback(float knockback) {
        position.mulAdd(velocity, knockback);
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

    public Rectangle getBoundingRectangle() {
        return sprite.getBoundingRectangle();
    }
}
