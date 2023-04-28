package com.polibudaprojects.thelastsurvivors.weapons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.polibudaprojects.thelastsurvivors.Player.DemoPlayer;
import com.polibudaprojects.thelastsurvivors.monsters.Monster;

public class Sword implements Weapon {
    private final Sprite sprite;
    private final Animation<TextureRegion> animation;
    private final DemoPlayer player;
    private final Rectangle hitboxRight;
    private final Rectangle hitboxLeft;
    public int animationCount;
    private int damage = 5;
    private long cooldown = 1000L;
    private long lastAttackTime;
    private Vector2 position;
    private float animationTime = 0f;
    private int level = 1;

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
        Vector2 playerCenterPosition = player.getCenterPosition();
        hitboxRight = new Rectangle(playerCenterPosition.x, playerCenterPosition.y, 30, 20);
        hitboxLeft = new Rectangle(playerCenterPosition.x - 30, playerCenterPosition.y, 30, 20);
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
        if (!(player.getLevel() > 12)) {
            if (level < player.getLevel()) {
                level = player.getLevel();
                setDamage(getDamage() + 4);
                setCooldown(getCooldown() - 70L);
            }
        }
        animationTime += dt;
        if (TimeUtils.millis() - lastAttackTime > cooldown) {
            lastAttackTime = TimeUtils.millis();
            animationTime = 0;
            animationCount += 1;
        }
    }

    public Rectangle getHitbox() {
        Vector2 playerCenterPosition = player.getCenterPosition();
        if (player.isRunningRight()) {
            return hitboxRight.setPosition(playerCenterPosition);
        } else {
            return hitboxLeft.setPosition(playerCenterPosition.x - 30, playerCenterPosition.y);
        }
    }

    @Override
    public int getDamage() {
        return this.damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    @Override
    public boolean canAttack(Monster monster) {
        if (Intersector.overlaps(monster.getBoundingRectangle(), this.getHitbox())) {
            if (animationTime > 1.2f) {
                return false;
            } else {
                if (!animation.isAnimationFinished(animationTime)) {
                    if (!monster.wasHitBy.containsKey(this)) {
                        monster.wasHitBy.put(this, animationCount);
                        return true;
                    } else if (monster.wasHitBy.get(this) < animationCount) {
                        monster.wasHitBy.replace(this, animationCount);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public long getCooldown() {
        return cooldown;
    }

    public void setCooldown(long cooldown) {
        this.cooldown = cooldown;
    }

    @Override
    public String toString() {
        return "SWORD\n DAMAGE: " + damage + "\n COOLDOWN: " + (float) cooldown / 1000;
    }
}
