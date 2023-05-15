package com.polibudaprojects.thelastsurvivors.Player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.polibudaprojects.thelastsurvivors.weapons.Sword;

public class MageWarrior extends Player {

    public MageWarrior() {
        super(
                "players/MageWarrior.png",
                70,
                30,
                220f,
                225f,
                180f,
                5500,
                5500,
                110,
                60);
    }

    @Override
    protected void addDefaultWeapon() {
        weapons.add(new Sword(this));
    }

    @Override
    protected Animation<TextureRegion> loadRunningAnimation(Texture texture) {
        Array<TextureRegion> frames = new Array<>();
        for (int i = 0; i < 8; i++) {
            frames.add(new TextureRegion(texture, i * 160, 128, 160, 128));
        }
        return new Animation<>(0.1f, frames, Animation.PlayMode.LOOP);
    }

    @Override
    protected Animation<TextureRegion> loadStandingAnimation(Texture texture) {
        Array<TextureRegion> frames = new Array<>();
        for (int i = 0; i < 8; i++) {
            frames.add(new TextureRegion(texture, i * 160, 0, 160, 128));
        }
        return new Animation<>(0.15f, frames, Animation.PlayMode.LOOP);
    }

    @Override
    protected Animation<TextureRegion> loadDeathAnimation(Texture texture) {
        Array<TextureRegion> frames = new Array<>();
        for (int i = 0; i < 9; i++) {
            frames.add(new TextureRegion(texture, i * 160, 768, 160, 128));
        }
        return new Animation<>(0.2f, frames, Animation.PlayMode.NORMAL);
    }

    @Override
    protected Animation<TextureRegion> loadHpRegenAnimation(Texture texture) {
        Array<TextureRegion> frames = new Array<>();
        for (int i = 0; i < 13; i++) {
            frames.add(new TextureRegion(texture, i * 160, 256, 160, 128));
        }
        return new Animation<>(0.15f, frames, Animation.PlayMode.NORMAL);
    }

    @Override
    protected Animation<TextureRegion> loadHitAnimation(Texture texture) {
        Array<TextureRegion> frames = new Array<>();
        for (int i = 0; i < 5; i++) {
            frames.add(new TextureRegion(texture, i * 160, 640, 160, 128));
        }
        return new Animation<>(0.08f, frames, Animation.PlayMode.NORMAL);
    }
}
