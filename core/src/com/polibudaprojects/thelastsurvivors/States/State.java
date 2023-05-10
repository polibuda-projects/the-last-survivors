package com.polibudaprojects.thelastsurvivors.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class State {
    protected final StatesManager gsm;
    protected final OrthographicCamera cam;

    protected State(StatesManager gsm) {
        this.gsm = gsm;
        cam = new OrthographicCamera();
        cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public abstract void handleInput();

    public abstract void reset();

    public abstract void update(float dt);

    public abstract void render(SpriteBatch sb);
}