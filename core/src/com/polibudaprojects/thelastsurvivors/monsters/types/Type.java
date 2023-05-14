package com.polibudaprojects.thelastsurvivors.monsters.types;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.polibudaprojects.thelastsurvivors.Assets;
import com.polibudaprojects.thelastsurvivors.items.Item;

public abstract class Type {

    private final String name;
    private final float spriteSize;
    private final float speed;
    private final int damage;
    private final int maxHealth;
    private final long attackInterval;
    private final boolean boss;
    private final Rectangle hitbox;
    private final Animation<TextureRegion> walkAnimation;
    private final Animation<TextureRegion> attackAnimation;
    private final Animation<TextureRegion> hitAnimation;
    private final Animation<TextureRegion> dieAnimation;

    public Type(String textureAtlasPath, String name, Rectangle hitbox, float spriteSize, float speed, int damage, int maxHealth, boolean boss) {
        TextureAtlas textureAtlas = Assets.loadAndGet(textureAtlasPath, TextureAtlas.class);
        this.name = name;
        this.hitbox = hitbox;
        this.spriteSize = spriteSize;
        this.speed = speed;
        this.damage = damage;
        this.maxHealth = maxHealth;
        this.boss = boss;
        this.attackInterval = 800L;
        this.walkAnimation = new Animation<>(0.066f, textureAtlas.findRegions("walk"), Animation.PlayMode.LOOP);
        this.attackAnimation = new Animation<>(0.066f, textureAtlas.findRegions("attack"), Animation.PlayMode.NORMAL);
        this.hitAnimation = new Animation<>(0.066f, textureAtlas.findRegions("hit"), Animation.PlayMode.NORMAL);
        this.dieAnimation = new Animation<>(0.066f, textureAtlas.findRegions("die"), Animation.PlayMode.NORMAL);
    }

    public Type(String textureAtlasPath, String name, Rectangle hitbox, float spriteSize, float speed, int damage, int maxHealth) {
        this(textureAtlasPath, name, hitbox, spriteSize, speed, damage, maxHealth, false);
    }

    public abstract Item dropItem(Vector2 position);

    public Sprite getNewSprite() {
        Sprite sprite = new Sprite(walkAnimation.getKeyFrame(0));
        sprite.setSize(spriteSize, spriteSize);
        return sprite;
    }

    public Rectangle getHitbox(Vector2 position, boolean isFlipX) {
        float offsetX = isFlipX ? hitbox.x : spriteSize - hitbox.x - hitbox.width;
        float offsetY = hitbox.y;
        return new Rectangle(position.x + offsetX, position.y + offsetY, hitbox.width, hitbox.height);
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

    public boolean isBoss() {
        return boss;
    }

    public long getAttackInterval() {
        return attackInterval;
    }

    public Animation<TextureRegion> getWalkAnimation() {
        return walkAnimation;
    }

    public Animation<TextureRegion> getAttackAnimation() {
        return attackAnimation;
    }

    public Animation<TextureRegion> getHitAnimation() {
        return hitAnimation;
    }

    public Animation<TextureRegion> getDieAnimation() {
        return dieAnimation;
    }
}
