package com.polibudaprojects.thelastsurvivors.Music;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.audio.Music;
import com.polibudaprojects.thelastsurvivors.Assets;

public class BackgroundMusic extends ApplicationAdapter {

    private final Music backgroundMusic;

    public BackgroundMusic() {
        backgroundMusic = Assets.get("music/BackgroundTheLastSurvivors.mp3", Music.class);
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.8f);
    }

    public void restart() {
        backgroundMusic.stop();
        backgroundMusic.play();
    }
}
