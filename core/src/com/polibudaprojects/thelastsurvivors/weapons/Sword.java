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
    private long cooldown = 1000L;
    private long lastAttackTime;
    private final Sprite sprite;
    private final Animation<TextureRegion> animation;
    private Vector2 position;
    private float animationTime = 0f;
    private final DemoPlayer player;

    private final Rectangle hitboxRight;
    private final Rectangle hitboxLeft;
    public int animationCount;

    public Sword(DemoPlayer player) {
        this.player = player;

        Texture img = new Texture("player.png");
        TextureRegion sword = new TextureRegion(img, 0, 880, 144, 80);
        sprite = new Sprite(sword);
        sprite.setSize(180f, 100f);
        position = player.getPosition();

        Array<TextureRegion> frames = new Array<>();

        for (int i = 0; i < 5; i++) {
            frames.add(new TextureRegion(img, i * 144, 880, 144, 80));
        }
        this.animation = new Animation<>(0.3f, frames, Animation.PlayMode.NORMAL);

        lastAttackTime = 0L;
        hitboxRight = new Rectangle(player.getCenterPosition().x, player.getCenterPosition().y, 30, 20);
        hitboxLeft = new Rectangle(player.getCenterPosition().x - 30, player.getCenterPosition().y, 30, 20);
        animationCount = 0;
    }

    @Override
    public void draw(SpriteBatch sb) {
        if (player.getCurrentHealth() > 0) {
            sprite.setPosition(position.x, position.y);
            sprite.setRegion(animation.getKeyFrame(animationTime));
            if (player.isRunningRight() && sprite.isFlipX()) {
                sprite.setFlip(false, false);
            } else if (!player.isRunningRight() && !sprite.isFlipX()) {
                sprite.setFlip(true, false);
            }
            sprite.draw(sb);
        }

    }

    @Override
    public void update(float dt) {
        position = player.getPosition();
        animationTime += dt;

        if (TimeUtils.millis() - lastAttackTime > cooldown) {
            lastAttackTime = TimeUtils.millis();
            animationTime = 0;
            animationCount += 1;
        }
    }

    @Override
    public Rectangle getHitbox() {
        if (player.isRunningRight()) {
            return hitboxRight.setPosition(player.getCenterPosition());
        } else {
            return hitboxLeft.setPosition(player.getCenterPosition().x - 30, player.getCenterPosition().y);
        }
    }

    @Override
    public int getDamage() {
        return this.damage;
    }

    @Override
    public boolean canAttack() {
        if (animationTime > 1.2f) {
            return !animation.isAnimationFinished(animationTime + 0.3f);
        } else {
            return !animation.isAnimationFinished(animationTime);
        }
    }

    @Override
    public int getAttackInterval() {
        return animationCount;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setCooldown(long cooldown) {
        this.cooldown = cooldown;
    }
}
