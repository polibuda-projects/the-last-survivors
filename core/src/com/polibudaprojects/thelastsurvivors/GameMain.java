package com.polibudaprojects.thelastsurvivors;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.polibudaprojects.thelastsurvivors.assets.Assets;
import com.polibudaprojects.thelastsurvivors.assets.FontFactory;
import com.polibudaprojects.thelastsurvivors.states.StatesManager;

public class GameMain extends ApplicationAdapter {

    public static final int SCREEN_WIDTH = 1104;
    public static final int SCREEN_HEIGHT = 621;
    private SpriteBatch batch;
    private StatesManager gsm;

    @Override
    public void create() {
        Assets.load();
        batch = new SpriteBatch();
        gsm = new StatesManager();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        gsm.resize();
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gsm.update(Gdx.graphics.getDeltaTime());
        gsm.render(batch);
        handleFullscreenToggle();
    }

    @Override
    public void dispose() {
        super.dispose();
        Assets.dispose();
        FontFactory.dispose();
    }

    private void handleFullscreenToggle() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
            if (Gdx.graphics.isFullscreen()) {
                Gdx.graphics.setWindowedMode(SCREEN_WIDTH, SCREEN_HEIGHT);
            } else {
                Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
            }
        }
    }
}
