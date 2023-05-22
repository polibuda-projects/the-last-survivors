package com.polibudaprojects.thelastsurvivors.weapons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.polibudaprojects.thelastsurvivors.assets.Assets;
import com.polibudaprojects.thelastsurvivors.monsters.Monster;
import com.polibudaprojects.thelastsurvivors.player.Player;

public class ShurikenRing implements Weapon {
    private static final int BASE_DAMAGE = 3;
    private static final int MAX_LEVEL = 12;
    private final int RADIUS = 150;
    private final float ROTATING_SPEED = 1.5f;
    private final int SHURIKEN_NUMBER = 4;
    private final Sprite[] sprites;
    private final Texture icon;
    private final Player player;
    private Vector2 position;
    private float circlePosition;

    public ShurikenRing(Player player) {
        this.player = player;

        sprites = new Sprite[SHURIKEN_NUMBER];
        Texture shuriken = Assets.get("shuriken.png", Texture.class);
        icon = Assets.get("shuriken.png", Texture.class);
        for (int i = 0; i < SHURIKEN_NUMBER; i++) {
            sprites[i] = new Sprite(shuriken);
            sprites[i].setSize(32f, 32f);
        }

        position = player.getCenterPosition();

        circlePosition = 0;
    }

    @Override
    public void draw(SpriteBatch sb) {
        if (player.getCurrentHealth() > 0) {
            for (int i = 0; i < SHURIKEN_NUMBER; i++) {
                sprites[i].draw(sb);
            }
        }
    }

    @Override
    public void update(float dt) {
        position = player.getCenterPosition();
        circlePosition += dt * ROTATING_SPEED;
        float offset = (float) Math.PI / 2;
        for (int i = 0; i < SHURIKEN_NUMBER; i++) {
            float x = (float) (RADIUS * Math.cos(circlePosition + (offset * i)));
            float y = (float) (RADIUS * Math.sin(circlePosition + (offset * i)));
            sprites[i].setPosition(position.x + x, position.y + y);
        }
    }

    @Override
    public int getDamage() {
        return BASE_DAMAGE;
    }

    @Override
    public boolean canAttack(Monster monster) {
        for (int i = 0; i < SHURIKEN_NUMBER; i++) {
            if (Intersector.overlaps(monster.getHitbox(), sprites[i].getBoundingRectangle())) {
                if (!monster.wasHitBy.containsKey(this)) {
                    monster.wasHitBy.put(this, i);
                    return true;
                } else if (monster.wasHitBy.get(this) != i) {
                    monster.wasHitBy.replace(this, i);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Texture getIcon() {
        return icon;
    }
}
