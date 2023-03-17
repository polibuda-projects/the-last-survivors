package com.polibudaprojects.thelastsurvivors;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameMain extends ApplicationAdapter {
    SpriteBatch batch;
    Texture img;
    DemoPlayer demoPlayer;

    @Override
    public void create() {
        batch = new SpriteBatch();
        img = new Texture("demo.png");
        demoPlayer = new DemoPlayer(img);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0, 0, 0, 1);
        batch.begin();
        demoPlayer.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
    }
}
