package com.polibudaprojects.thelastsurvivors;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.polibudaprojects.thelastsurvivors.States.StatesManager;

public class GameMain extends ApplicationAdapter {
    SpriteBatch batch;
    private StatesManager gsm;

    @Override
    public void create() {
        Assets.load();
        batch = new SpriteBatch();
        gsm = new StatesManager();
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gsm.update(Gdx.graphics.getDeltaTime());
        gsm.render(batch);
    }

    @Override
    public void dispose() {
        super.dispose();
        Assets.dispose();
    }
}
