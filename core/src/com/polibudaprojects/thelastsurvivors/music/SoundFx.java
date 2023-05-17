package com.polibudaprojects.thelastsurvivors.music;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.audio.Sound;
import com.polibudaprojects.thelastsurvivors.assets.Assets;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SoundFx extends ApplicationAdapter {

    private static final float SOUND_EFFECT_DELAY = 3f;
    private final List<Sound> monsterSounds = new ArrayList<>();
    private final Random random = new Random();
    private float elapsedTimeSinceLastSoundEffect = 0f;

    public SoundFx() {
        monsterSounds.add(Assets.get("music/brains.wav", Sound.class));
        monsterSounds.add(Assets.get("music/groan.wav", Sound.class));
        monsterSounds.add(Assets.get("music/rar.wav", Sound.class));
    }

    public void playMonsterSound(int index) {
        if (monsterSounds.get(index) != null) {
            monsterSounds.get(index).play();
        }
    }

    public void playRandomMonsterSound(float dt) {
        elapsedTimeSinceLastSoundEffect += dt;

        if (elapsedTimeSinceLastSoundEffect >= SOUND_EFFECT_DELAY) {
            if (random.nextInt(100) < 1) {
                playMonsterSound(random.nextInt(monsterSounds.size()));
                elapsedTimeSinceLastSoundEffect = 0.0f;
            }
        }
    }
}
