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
import com.polibudaprojects.thelastsurvivors.Assets;
import com.polibudaprojects.thelastsurvivors.Player.Player;
import com.polibudaprojects.thelastsurvivors.monsters.Monster;

public class Sword implements Weapon {
    private static final int BASE_DAMAGE = 5;
    private static final long BASE_COOLDOWN = 1000L;
    private static final int MAX_LEVEL = 12;
    private final Sprite sprite;
    private final Animation<TextureRegion> animation;
    private final Texture icon;
    private final Player player;
    private final Rectangle hitboxRight;
    private final Rectangle hitboxLeft;
    public int animationCount;
    private long lastAttackTime;
    private Vector2 position;
    private float animationTime = 0f;

    public Sword(Player player) {
        this.player = player;

        Texture img = Assets.get("players/FireWarrior.png", Texture.class);
        icon = Assets.get("sword_icon.png", Texture.class);
        TextureRegion sword = new TextureRegion(img, 0, 880, 144, 80);
        sprite = new Sprite(sword);
        sprite.setSize(180f, 100f);
        position = player.getPosition();

        Array<TextureRegion> frames = new Array<>();

        for (int i = 0; i < 5; i++) {
            frames.add(new TextureRegion(img, i * 144, 880, 144, 80));
        }
        this.animation = new Animation<>(0.15f, frames, Animation.PlayMode.NORMAL);

        lastAttackTime = 0L;
        hitboxRight = new Rectangle(0, 0, 60, 50);
        hitboxLeft = new Rectangle(0, 0, 60, 50);
        animationCount = 0;
    }

    @Override
    public void draw(SpriteBatch sb) {
        if (player.getCurrentHealth() > 0) {
            sprite.setPosition(position.x, position.y);
            sprite.setRegion(animation.getKeyFrame(animationTime));
            sprite.setFlip(player.isFacingLeft(), false);
            sprite.draw(sb);
        }
    }

    @Override
    public void update(float dt) {
        position = player.getPosition();
        animationTime += dt;
        if (TimeUtils.millis() - lastAttackTime > getCooldown()) {
            lastAttackTime = TimeUtils.millis();
            animationTime = 0;
            animationCount += 1;
        }
    }

    public Rectangle getHitbox() {
        Vector2 playerCenterPosition = player.getCenterPosition();
        if (player.isFacingLeft()) {
            return hitboxLeft.setPosition(playerCenterPosition.x - 50, playerCenterPosition.y - 10);
        } else {
            return hitboxRight.setPosition(playerCenterPosition.x - 10, playerCenterPosition.y - 10);
        }
    }

    @Override
    public boolean canAttack(Monster monster) {
        if (Intersector.overlaps(monster.getHitbox(), this.getHitbox())) {
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

    @Override
    public int getDamage() {
        return BASE_DAMAGE + getLevel() * 4;
    }

    public long getCooldown() {
        return BASE_COOLDOWN - getLevel() * 70L;
    }

    private int getLevel() {
        return Math.min(MAX_LEVEL, (player.getLevel() - 1));
    }

    @Override
    public Texture getIcon() {
        return icon;
    }

    @Override
    public String toString() {
        return "SWORD\n DAMAGE: " + getDamage() + "\n COOLDOWN: " + (float) getCooldown() / 1000;
    }
}
