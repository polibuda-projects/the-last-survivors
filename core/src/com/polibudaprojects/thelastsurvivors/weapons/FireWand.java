package com.polibudaprojects.thelastsurvivors.weapons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.polibudaprojects.thelastsurvivors.Assets;
import com.polibudaprojects.thelastsurvivors.Player.Player;
import com.polibudaprojects.thelastsurvivors.monsters.Monster;

import java.util.ArrayList;

public class FireWand implements Weapon {
    private static final int BASE_DAMAGE = 2;
    private static final long BASE_COOLDOWN = 1000L;
    private static final int MAX_LEVEL = 12;
    private static final int SPEED = 350;
    private final Texture bullet;
    private final Player player;
    private final ArrayList<Bullet> bullets = new ArrayList<>();
    private long lastAttackTime = 0L;

    public FireWand(Player player) {
        this.player = player;
        bullet = Assets.get("fireBullet.png", Texture.class);
    }

    @Override
    public void draw(SpriteBatch sb) {
        if (player.getCurrentHealth() > 0) {
            for (Bullet bullet : bullets) {
                bullet.draw(sb);
            }
        }
    }

    @Override
    public void update(float dt) {
        if (TimeUtils.millis() - lastAttackTime > getCooldown()) {
            lastAttackTime = TimeUtils.millis();
            Vector2 velocity = player.getLastVelocity().nor().scl(SPEED);
            bullets.add(new Bullet(
                    player.getCenterPosition(),
                    velocity,
                    createBulletSprite(velocity.angleDeg())
            ));
        }

        for (Bullet bullet : bullets) {
            bullet.update(dt);
        }
    }

    private Sprite createBulletSprite(float angle) {
        Sprite sprite = new Sprite(bullet);
        sprite.setSize(47f, 33f);
        sprite.setRotation(angle);
        sprite.setFlip(false, 90f < angle && angle < 270f);
        return sprite;
    }

    @Override
    public boolean canAttack(Monster monster) {
        for (Bullet bullet : bullets) {
            if (Intersector.overlaps(monster.getBoundingRectangle(), bullet.getHitbox())) {
                bullets.remove(bullet);
                return true;
            }
        }
        return false;
    }

    @Override
    public int getDamage() {
        return BASE_DAMAGE + getLevel() * 3;
    }

    public long getCooldown() {
        return BASE_COOLDOWN - getLevel() * 50L;
    }

    private int getLevel() {
        return Math.min(MAX_LEVEL, (player.getLevel() - 1));
    }

    @Override
    public String toString() {
        return "FIRE WAND\n DAMAGE: " + getDamage() + "\n COOLDOWN: " + (float) getCooldown() / 1000;
    }
}
