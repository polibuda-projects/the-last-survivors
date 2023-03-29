package com.polibudaprojects.thelastsurvivors.Music;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.ApplicationAdapter;

import java.nio.file.Path;

public class BackgroundMusic extends ApplicationAdapter {

    private final Music backgroundMusic;

    public BackgroundMusic(Path path) {
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal(String.valueOf(path)));
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.8f);
        backgroundMusic.play();
    }

    @Override
    public void dispose() {
        backgroundMusic.dispose();
    }
}