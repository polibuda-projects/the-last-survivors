package com.polibudaprojects.thelastsurvivors.Music;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class SoundFx extends ApplicationAdapter {

    private Map<String, Sound> soundMap;

    public SoundFx() {
        soundMap = new HashMap<>();
    }

    public void loadSound(String name, Path path) {
        soundMap.put(name, Gdx.audio.newSound(Gdx.files.internal(String.valueOf(path))));
    }

    public void playSound(String name) {
        soundMap.get(name);
        if (soundMap.get(name) != null) {
            soundMap.get(name).play();
        }
    }

    @Override
    public void dispose() {
        for (Sound sound : soundMap.values()) {
            sound.dispose();
        }
    }
}
