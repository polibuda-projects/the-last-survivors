package com.polibudaprojects.thelastsurvivors.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class GameTimer {
    private final BitmapFont font;
    private final GlyphLayout layout;
    private final Vector2 position;
    private float timeRemaining;

    public GameTimer(float timeRemaining, BitmapFont font) {
        this.timeRemaining = timeRemaining;
        this.font = font;
        this.layout = new GlyphLayout();
        this.position = new Vector2(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() - 10f);
    }

    public void update(float delta) {
        timeRemaining -= delta;
        if (timeRemaining < 0) {
            timeRemaining = 0;
        }
    }

    public void updatePosition(float x, float y) {
        position.set(x, y);
    }

    public void render(SpriteBatch batch) {
        batch.begin();
        String text = (int) timeRemaining / 60 + ":" + (int) timeRemaining % 60;
        layout.setText(font, text);
        float x = position.x - layout.width / 2f;
        float y = position.y - layout.height / 2f;
        font.draw(batch, layout, x, y);
        batch.end();
    }

    public float getTimeRemaining() {
        return timeRemaining;
    }

    public void setTimeRemaining(float timeRemaining) {
        this.timeRemaining = timeRemaining;
    }

    public boolean isTimeUp() {
        return timeRemaining <= 0;
    }
}
