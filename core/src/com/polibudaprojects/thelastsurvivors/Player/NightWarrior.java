package com.polibudaprojects.thelastsurvivors.Player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.polibudaprojects.thelastsurvivors.weapons.Sword;

public class NightWarrior extends Player {

    public NightWarrior() {
        super(
                "players/NightWarrior.png",
                140,
                40,
                180f,
                160f,
                160f,
                5500,
                5500,
                80,
                65);
    }

    @Override
    protected void addDefaultWeapon() {
        weapons.add(new Sword(this));
    }

    @Override
    protected Animation<TextureRegion> loadRunningAnimation(Texture texture) {
        Array<TextureRegion> frames = new Array<>();
        for (int i = 0; i < 6; i++) {
            frames.add(new TextureRegion(texture, i * 80, 80, 80, 80));
        }
        return new Animation<>(0.1f, frames, Animation.PlayMode.LOOP);
    }

    @Override
    protected Animation<TextureRegion> loadStandingAnimation(Texture texture) {
        Array<TextureRegion> frames = new Array<>();
        for (int i = 0; i < 9; i++) {
            frames.add(new TextureRegion(texture, i * 80, 0, 80, 80));
        }
        return new Animation<>(0.15f, frames, Animation.PlayMode.LOOP);
    }

    @Override
    protected Animation<TextureRegion> loadDeathAnimation(Texture texture) {
        Array<TextureRegion> frames = new Array<>();
        for (int i = 0; i < 23; i++) {
            frames.add(new TextureRegion(texture, i * 80, 320, 80, 80));
        }
        return new Animation<>(0.2f, frames, Animation.PlayMode.NORMAL);
    }

    @Override
    protected Animation<TextureRegion> loadHpRegenAnimation(Texture texture) {
        Array<TextureRegion> frames = new Array<>();
        for (int i = 0; i < 5; i++) {
            frames.add(new TextureRegion(texture, i * 80, 160, 80, 80));
        }
        return new Animation<>(0.15f, frames, Animation.PlayMode.NORMAL);
    }

    @Override
    protected Animation<TextureRegion> loadHitAnimation(Texture texture) {
        Array<TextureRegion> frames = new Array<>();
        for (int i = 0; i < 5; i++) {
            frames.add(new TextureRegion(texture, i * 80, 240, 80, 80));
        }
        return new Animation<>(0.08f, frames, Animation.PlayMode.NORMAL);
    }
}
