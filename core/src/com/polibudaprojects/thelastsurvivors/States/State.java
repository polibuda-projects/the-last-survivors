package com.polibudaprojects.thelastsurvivors.States;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class State {
    protected final StatesManager gsm;
    protected final OrthographicCamera cam;

    protected State(StatesManager gsm) {
        this.gsm = gsm;
        cam = new OrthographicCamera();
        cam.setToOrtho(false, 240, 400);
    }

    public abstract void handleInput();

    public abstract void update(float dt);

    public abstract void render(SpriteBatch sb);

    public abstract void dispose();
}