package com.polibudaprojects.thelastsurvivors.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.polibudaprojects.thelastsurvivors.weapons.FireWand;
import com.polibudaprojects.thelastsurvivors.weapons.Sword;

public class FireWarrior extends Player {

    public FireWarrior() {
        super(
                "players/FireWarrior.png",
                100,
                20,
                200f,
                180f,
                100f,
                5500,
                5500,
                90,
                50);
    }

    @Override
    protected void addDefaultWeapon() {
        weapons.add(new Sword(this));
    }

    @Override
    protected void addAdditionalWeapon() {
        weapons.add(new FireWand(this));
    }

    @Override
    protected Animation<TextureRegion> loadRunningAnimation(Texture texture) {
        Array<TextureRegion> frames = new Array<>();
        for (int i = 0; i < 8; i++) {
            frames.add(new TextureRegion(texture, i * 144, 80, 144, 80));
        }
        return new Animation<>(0.1f, frames, Animation.PlayMode.LOOP);
    }

    @Override
    protected Animation<TextureRegion> loadStandingAnimation(Texture texture) {
        Array<TextureRegion> frames = new Array<>();
        for (int i = 0; i < 8; i++) {
            frames.add(new TextureRegion(texture, i * 144, 0, 144, 80));
        }
        return new Animation<>(0.15f, frames, Animation.PlayMode.LOOP);
    }

    @Override
    protected Animation<TextureRegion> loadDeathAnimation(Texture texture) {
        Array<TextureRegion> frames = new Array<>();
        for (int i = 0; i < 12; i++) {
            frames.add(new TextureRegion(texture, i * 144, 1920, 144, 80));
        }
        return new Animation<>(0.2f, frames, Animation.PlayMode.NORMAL);
    }

    @Override
    protected Animation<TextureRegion> loadHpRegenAnimation(Texture texture) {
        Array<TextureRegion> frames = new Array<>();
        for (int i = 0; i < 6; i++) {
            frames.add(new TextureRegion(texture, i * 144, 1600, 144, 80));
        }
        return new Animation<>(0.15f, frames, Animation.PlayMode.NORMAL);
    }

    @Override
    protected Animation<TextureRegion> loadHitAnimation(Texture texture) {
        Array<TextureRegion> frames = new Array<>();
        for (int i = 0; i < 5; i++) {
            frames.add(new TextureRegion(texture, i * 144, 1840, 144, 80));
        }
        return new Animation<>(0.08f, frames, Animation.PlayMode.NORMAL);
    }
}
