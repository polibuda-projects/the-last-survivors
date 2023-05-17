package com.polibudaprojects.thelastsurvivors.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import static com.polibudaprojects.thelastsurvivors.GameMain.SCREEN_HEIGHT;
import static com.polibudaprojects.thelastsurvivors.GameMain.SCREEN_WIDTH;

public abstract class State {
    protected final StatesManager gsm;
    protected final OrthographicCamera cam;
    protected final Viewport viewport;

    protected State(StatesManager gsm) {
        this.gsm = gsm;
        cam = new OrthographicCamera();
        viewport = new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT, cam);
        resize();
    }

    public abstract void handleInput();

    public abstract void reset();

    public abstract void update(float dt);

    public abstract void render(SpriteBatch sb);

    public void resize() {
        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
    }
}