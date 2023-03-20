package com.polibudaprojects.thelastsurvivors;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.polibudaprojects.thelastsurvivors.States.StartState;
import com.polibudaprojects.thelastsurvivors.States.StatesManager;

public class GameMain extends ApplicationAdapter {
    SpriteBatch batch;
    Texture img;
    DemoPlayer demoPlayer;
    private StatesManager gsm;

    @Override
    public void create() {
        batch = new SpriteBatch();
        img = new Texture("demo.png");
        demoPlayer = new DemoPlayer(img);
        gsm = new StatesManager();
        gsm.push(new StartState(gsm));
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
    }
}
