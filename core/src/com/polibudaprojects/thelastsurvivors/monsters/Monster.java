package com.polibudaprojects.thelastsurvivors.monsters;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.polibudaprojects.thelastsurvivors.Player.DemoPlayer;
import com.polibudaprojects.thelastsurvivors.monsters.types.Type;
import com.polibudaprojects.thelastsurvivors.weapons.Weapon;

import java.util.HashMap;

public class Monster {

    private final Type type;
    private final Vector2 position;
    public final Sprite sprite;
    private Animation<TextureRegion> animation;
    private float animationTime = 0f;
    private long lastAttackTime;
    private int health;

    public HashMap<Weapon, Long> wasHitBy = new HashMap<>();

    public Monster(Type type, Vector2 position) {
        this.type = type;
        this.position = position;
        this.sprite = type.getNewSprite();
        this.health = type.getMaxHealth();
        this.animation = type.getWalkAnimation();
    }

    public void update(float deltaTime, Vector2 playerPosition) {
        updateAnimation(deltaTime);
        sprite.setFlip(position.x > playerPosition.x, false);
        if (!isDead()) {
            //TODO znaleźć lepszy sposób, aby potwór poruszał sie dokładnie w lini prostej do gracza
            if (position.y < playerPosition.y) {
                position.y += deltaTime * type.getSpeed();
            }
            if (position.y > playerPosition.y) {
                position.y -= deltaTime * type.getSpeed();
            }
            if (position.x < playerPosition.x) {
                position.x += deltaTime * type.getSpeed();
            }
            if (position.x > playerPosition.x) {
                position.x -= deltaTime * type.getSpeed();
            }
        }
    }

    public void draw(SpriteBatch batch) {
        sprite.setPosition(position.x, position.y);
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
                sprite.getBoundingRectangle().contains(player.getCenterPosition());
    }

    public void takeDamage(int damage, Weapon weapon) {
        // TODO może lepiej to częściowo przenieść do weapon.canAttack(Monster monster)? Tu powinno być tylko zadanie obrażeń
        if (!isDead()) {
            if (!wasHitBy.containsKey(weapon)) {
                wasHitBy.put(weapon, TimeUtils.millis());
                replaceAnimation(type.getHitAnimation());
                health -= damage;
            } else if (wasHitBy.get(weapon) + weapon.getAttackInterval() < TimeUtils.millis()) {
                replaceAnimation(type.getHitAnimation());
                health -= damage;
                wasHitBy.replace(weapon, TimeUtils.millis());
            }
            if (isDead()) {
                replaceAnimation(type.getDieAnimation());
            }
        }
    }

    public boolean isDead() {
        return health <= 0;
    }

    public boolean shouldBeRemoved() {
        return isDead() && animation.isAnimationFinished(animationTime);
    }
}
