package com.polibudaprojects.thelastsurvivors.Music;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.audio.Sound;
import com.polibudaprojects.thelastsurvivors.Assets;

import java.util.HashMap;
import java.util.Map;

public class SoundFx extends ApplicationAdapter {

    private final Map<String, Sound> soundMap;

    public SoundFx() {
        soundMap = new HashMap<>();
    }

    public void loadSound(String name, String path) {
        soundMap.put(name, Assets.get(path, Sound.class));
    }

    public void playSound(String name) {
        if (soundMap.containsKey(name)) {
            soundMap.get(name).play();
        }
    }
}
