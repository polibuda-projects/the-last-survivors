package com.polibudaprojects.thelastsurvivors.weapons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.polibudaprojects.thelastsurvivors.assets.Assets;
import com.polibudaprojects.thelastsurvivors.monsters.Monster;
import com.polibudaprojects.thelastsurvivors.player.Player;

import java.util.ArrayList;
import java.util.Collections;

public class Spikes implements Weapon {
    private static final int BASE_DAMAGE = 5;
    private static final long BASE_COOLDOWN = 1000L;
    private static final int MAX_LEVEL = 12;
    private final int SPIKES_NUMBER = 3;
    private final Sprite[] sprites;
    private final Animation<TextureRegion> animation;
    private final Texture icon;
    private final Player player;
    private long lastAttackTime;
    private float animationTime = 0f;
    private final ArrayList<Monster> monsters;
    private int animationCount;

    public Spikes(Player player) {
        monsters = new ArrayList<>();
        this.player = player;
        animationCount = 0;

        sprites = new Sprite[SPIKES_NUMBER];
        Texture img = Assets.get("spike.png", Texture.class);
        icon = Assets.get("spike_icon.png", Texture.class);
        TextureRegion spike = new TextureRegion(img, 0, 0, 16, 16);
        for (int i = 0; i < SPIKES_NUMBER; i++) {
            sprites[i] = new Sprite(spike);
            sprites[i].setSize(32f, 32f);
        }

        Array<TextureRegion> frames = new Array<>();

        for (int i = 0; i < 5; i++) {
            frames.add(new TextureRegion(img, i * 16, 0, 16, 16));
        }
        this.animation = new Animation<>(0.15f, frames, Animation.PlayMode.NORMAL);
    }

    @Override
    public void draw(SpriteBatch sb) {
        if (player.getCurrentHealth() > 0) {
            for (int i = 0; i < SPIKES_NUMBER; i++) {
                sprites[i].setRegion(animation.getKeyFrame(animationTime));
                sprites[i].draw(sb);
            }
        }
    }

    @Override
    public void update(float dt) {
        ArrayList<Monster> shuffled = new ArrayList<>(monsters);
        Collections.shuffle(shuffled);

        animationTime += dt;
        if (TimeUtils.millis() - lastAttackTime > getCooldown()) {
            lastAttackTime = TimeUtils.millis();
            animationTime = 0;
            animationCount += 1;
            if (!monsters.isEmpty()) {
                for (int i = 0; i < SPIKES_NUMBER; i++) {
                    sprites[i].setPosition(shuffled.get(i).getPosition().x + 10, shuffled.get(i).getPosition().y);
                }
            }
        }
    }

    @Override
    public int getDamage() {
        return BASE_DAMAGE;
    }

    @Override
    public boolean canAttack(Monster monster) {
        if (!monsters.contains(monster)) {
            monsters.add(monster);
        }
        for (int i = 0; i < SPIKES_NUMBER; i++) {
            if (Intersector.overlaps(monster.getHitbox(), sprites[i].getBoundingRectangle())) {
                if (animationTime > 0.5f) {
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
        }
        return false;
    }

    @Override
    public Texture getIcon() {
        return icon;
    }

    private long getCooldown() {
        return BASE_COOLDOWN;
    }
}
