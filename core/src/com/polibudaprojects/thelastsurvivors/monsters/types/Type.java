package com.polibudaprojects.thelastsurvivors.monsters.types;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public abstract class Type {

    private final Texture texture;
    private final String name;
    private final float spriteSize;
    private final float speed;
    private final int damage;
    private final int maxHealth;

    public Type(Texture texture, String name, float spriteSize, float speed, int damage, int maxHealth) {
        this.texture = texture;
        this.name = name;
        this.spriteSize = spriteSize;
        this.speed = speed;
        this.damage = damage;
        this.maxHealth = maxHealth;
    }

    public Sprite getNewSprite() {
        Sprite sprite = new Sprite(texture);
        sprite.setSize(spriteSize, spriteSize);
        return sprite;
    }

    public String getName() {
        return name;
    }

    public float getSpeed() {
        return speed;
    }

    public int getDamage() {
        return damage;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void dispose() {
        texture.dispose();
    }
}
