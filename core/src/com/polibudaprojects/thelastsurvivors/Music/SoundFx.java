package com.polibudaprojects.thelastsurvivors.Music;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import java.nio.file.Path;

public class SoundFx extends ApplicationAdapter {

    private Sound sound;

    public SoundFx(Path path) {
        sound = Gdx.audio.newSound(Gdx.files.internal(String.valueOf(path)));
        sound.play();
    }

    @Override
    public void dispose() {
        sound.dispose();
    }
}
