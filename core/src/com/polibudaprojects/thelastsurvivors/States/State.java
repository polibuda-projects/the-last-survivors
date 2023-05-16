package com.polibudaprojects.thelastsurvivors.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public abstract class State {
    protected final StatesManager gsm;
    protected final OrthographicCamera cam;
    protected final Stage stage;

    protected State(StatesManager gsm) {
        this.gsm = gsm;
        cam = new OrthographicCamera();
        cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage = new Stage(new ScreenViewport(cam));
    }

    public abstract void handleInput();

    public abstract void reset();

    public abstract void update(float dt);

    public abstract void render(SpriteBatch sb);

    public Stage getStage() {
        return stage;
    }
}