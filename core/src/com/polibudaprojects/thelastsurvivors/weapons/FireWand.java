package com.polibudaprojects.thelastsurvivors.weapons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.polibudaprojects.thelastsurvivors.Player.Player;
import com.polibudaprojects.thelastsurvivors.monsters.Monster;

import java.util.ArrayList;

public class FireWand implements Weapon {
    private static final int BASE_DAMAGE = 2;
    private static final long BASE_COOLDOWN = 1000L;
    private static final int MAX_LEVEL = 12;
    private static final int SPEED = 350;
    private final Player player;
    private final ArrayList<Bullet> bullets = new ArrayList<>();
    private final Sprite sprite0;
    private final Sprite sprite1;
    private final Sprite sprite2;
    private final Sprite sprite3;
    private long lastAttackTime;
    private Vector2 position;

    public FireWand(Player player) {
        this.player = player;
        lastAttackTime = 0L;
        position = player.getCenterPosition();

        Texture bullet = new Texture("fireBullet.png");
        sprite0 = new Sprite(bullet);
        sprite0.setSize(33f, 47f);
        sprite0.rotate90(false);

        sprite1 = new Sprite(bullet);
        sprite1.setSize(33f, 47f);
        sprite1.rotate90(true);

        sprite2 = new Sprite(bullet);
        sprite2.setSize(47f, 33f);
        sprite2.setFlip(true, false);

        sprite3 = new Sprite(bullet);
        sprite3.setSize(47f, 33f);
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
        position = player.getCenterPosition();
        if (TimeUtils.millis() - lastAttackTime > getCooldown()) {
            lastAttackTime = TimeUtils.millis();
            int lastPosition = player.getLastInput();
            switch (lastPosition) {
                case 0:
                    bullets.add(new Bullet(position, sprite0, 0, SPEED));
                    break;
                case 1:
                    bullets.add(new Bullet(position, sprite1, 0, -SPEED));
                    break;
                case 2:
                    bullets.add(new Bullet(position, sprite2, -SPEED, 0));
                    break;
                case 3:
                    bullets.add(new Bullet(position, sprite3, SPEED, 0));
                    break;
            }
        }

        for (Bullet bullet : bullets) {
            bullet.update(dt);
        }
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
