package com.polibudaprojects.thelastsurvivors.weapons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.polibudaprojects.thelastsurvivors.Player.DemoPlayer;
import com.polibudaprojects.thelastsurvivors.monsters.Monster;

import java.util.ArrayList;

public class FireWand implements Weapon {
    private int damage = 2;
    private long cooldown = 500L;
    private long lastAttackTime;
    private final DemoPlayer player;
    private Vector2 position;
    private final ArrayList<Bullet> bullets = new ArrayList<>();

    private final Sprite sprite0;
    private final Sprite sprite1;
    private final Sprite sprite2;
    private final Sprite sprite3;

    private int level = 1;

    public FireWand(DemoPlayer player) {
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
        if (!(player.getLevel() > 12)) {
            if (level < player.getLevel()) {
                level = player.getLevel();
                setDamage(getDamage()+3);
                setCooldown(getCooldown()-30L);
            }
        }
        if (TimeUtils.millis() - lastAttackTime > cooldown) {
            lastAttackTime = TimeUtils.millis();
            int lastPosition = player.getLastInput();
            switch (lastPosition) {
                case 0:
                    bullets.add(new Bullet(position, sprite0, 0, 170));
                    break;
                case 1:
                    bullets.add(new Bullet(position, sprite1, 0, -170));
                    break;
                case 2:
                    bullets.add(new Bullet(position, sprite2, -170, 0));
                    break;
                case 3:
                    bullets.add(new Bullet(position, sprite3, 170, 0));
                    break;
            }
        }

        for (Bullet bullet : bullets) {
            bullet.update(dt);
        }
    }

    @Override
    public int getDamage() {
        return this.damage;
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

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setCooldown(long cooldown) {
        this.cooldown = cooldown;
    }

    public long getCooldown() {
        return cooldown;
    }

    @Override
    public String toString() {
        return "FIRE WAND\n DAMAGE: " + damage + "\n COOLDOWN: " + (float) cooldown / 1000;
    }
}
