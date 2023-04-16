package com.polibudaprojects.thelastsurvivors.weapons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.polibudaprojects.thelastsurvivors.Player.DemoPlayer;

public class Sword implements Weapon {
    private int damage = 5;
    private final long cooldown = 1000L;
    private long lastAttackTime;
    private final Sprite sprite;
    private Animation<TextureRegion> animation;
    private Vector2 position;
    private float animationTime = 0f;
    private DemoPlayer player;

    public Sword(DemoPlayer player) {
        this.player = player;

        Texture img = new Texture("player.png");
        TextureRegion sword = new TextureRegion(img, 0, 880, 144, 80);
        sprite = new Sprite(sword);
        sprite.setSize(180f, 100f);
        position = player.getPosition();

        Array<TextureRegion> frames = new Array<>();

        for (int i = 0; i < 4; i++) {
            frames.add(new TextureRegion(img, i * 144, 880, 144, 80));
        }
        this.animation = new Animation<>(0.3f, frames, Animation.PlayMode.NORMAL);

        lastAttackTime = 0L;
    }

    @Override
    public void draw(SpriteBatch sb) {
        sprite.setPosition(position.x, position.y);
        sprite.setRegion(animation.getKeyFrame(animationTime));
        if (player.isRunningRight() && sprite.isFlipX()) {
            sprite.setFlip(false, false);
        } else if (!player.isRunningRight() && !sprite.isFlipX()) {
            sprite.setFlip(true, false);
        }
        sprite.draw(sb);
    }

    @Override
    public void update(float dt) {
        position = player.getPosition();
        animationTime += dt;

        if (TimeUtils.millis() - lastAttackTime > cooldown) {
            lastAttackTime = TimeUtils.millis();
            animationTime = 0;
        }
    }

    @Override
    public Rectangle getHitbox() {
        if (player.isRunningRight()) {
            return new Rectangle(player.getCenterPosition().x, player.getCenterPosition().y, 25, 20);
        } else {
            return new Rectangle(player.getCenterPosition().x - 25, player.getCenterPosition().y, 25, 20);
        }
    }

    @Override
    public int getDamage() {
        return this.damage;
    }

    @Override
    public boolean canAttack() {
        return !animation.isAnimationFinished(animationTime);
    }

    @Override
    public long getAttackInterval() {
        return (long) ((animation.getAnimationDuration() * 1000) - animationTime);
    }
}
