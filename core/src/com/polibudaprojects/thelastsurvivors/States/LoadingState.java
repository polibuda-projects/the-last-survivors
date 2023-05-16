package com.polibudaprojects.thelastsurvivors.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.polibudaprojects.thelastsurvivors.Assets;
import com.polibudaprojects.thelastsurvivors.FontFactory;

public class LoadingState extends State {
    private static final String LOADING_TEXT = "Loading...";
    private static final float DELAY = 0.2f;
    private static final Color BAR_COLOR = Color.FIREBRICK;
    private static final Color BAR_BACKGROUND_COLOR = new Color(0x302727ff);
    private static final Color TOP_COLOR = Color.BLACK;
    private static final Color BOTTOM_COLOR = new Color(0x133824ff);
    private final ShapeRenderer shapeRenderer = new ShapeRenderer();
    private final BitmapFont font = FontFactory.getFont(64);
    private float delayTimer = 0;
    private float progress;

    protected LoadingState(StatesManager gsm) {
        super(gsm);
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
            startAfterDelay(dt);
        }
        progress = Assets.getProgress();
    }

    private void startAfterDelay(float dt) {
        delayTimer += dt;
        if (delayTimer > DELAY) {
            gsm.setState(gsm.getStart());
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        shapeRenderer.setProjectionMatrix(cam.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.rect(cam.position.x - (Gdx.graphics.getWidth() / 2f), cam.position.y - (Gdx.graphics.getHeight() / 2f), Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),
                BOTTOM_COLOR, BOTTOM_COLOR, TOP_COLOR, TOP_COLOR);

        shapeRenderer.setColor(BAR_BACKGROUND_COLOR);
        shapeRenderer.rect(cam.position.x - (Gdx.graphics.getWidth() / 4f), cam.position.y, Gdx.graphics.getWidth() / 2f, 50);
        shapeRenderer.setColor(BAR_COLOR);
        shapeRenderer.rect(cam.position.x - (Gdx.graphics.getWidth() / 4f), cam.position.y, Gdx.graphics.getWidth() / 2f * progress, 50);

        shapeRenderer.end();

        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        font.draw(sb, LOADING_TEXT, cam.position.x - (Gdx.graphics.getWidth() / 4f), cam.position.y + 100);
        font.draw(sb, getPercentProgressText(), cam.position.x + Gdx.graphics.getWidth() / 4f - 110, cam.position.y + 100);
        sb.end();
    }

    private String getPercentProgressText() {
        return String.format("%3d%%", (int) (progress * 100));
    }
}
