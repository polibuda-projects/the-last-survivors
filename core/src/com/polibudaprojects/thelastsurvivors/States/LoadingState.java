package com.polibudaprojects.thelastsurvivors.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.polibudaprojects.thelastsurvivors.Assets;

public class LoadingState extends State {
    private static final String LOADING_TEXT = "Loading...";
    private final ShapeRenderer shapeRenderer;
    private final BitmapFont font;
    private float progress;

    protected LoadingState(StatesManager gsm) {
        super(gsm);
        this.shapeRenderer = new ShapeRenderer();
        this.font = new BitmapFont();
        this.font.setColor(Color.WHITE);
        this.font.getData().setScale(3f);
    }

    @Override
    public void handleInput() {
    }

    @Override
    public void reset() {
    }

    @Override
    public void update(float dt) {
        if (Assets.finishedLoading()) {
            gsm.set(gsm.getStart());
        }
        progress = Math.min(Assets.getProgress() + 0.1f, 1f);
    }

    @Override
    public void render(SpriteBatch sb) {
        shapeRenderer.setProjectionMatrix(cam.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.GRAY);
        shapeRenderer.rect(cam.position.x - (Gdx.graphics.getWidth() / 4f), cam.position.y, Gdx.graphics.getWidth() / 2f, 50);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(cam.position.x - (Gdx.graphics.getWidth() / 4f), cam.position.y, Gdx.graphics.getWidth() / 2f * progress, 50);
        shapeRenderer.end();

        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        font.draw(sb, LOADING_TEXT, cam.position.x - (Gdx.graphics.getWidth() / 4f), cam.position.y + 100);
        sb.end();
    }
}
