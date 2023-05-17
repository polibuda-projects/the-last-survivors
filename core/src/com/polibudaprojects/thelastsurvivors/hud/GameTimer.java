package com.polibudaprojects.thelastsurvivors.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.polibudaprojects.thelastsurvivors.assets.FontFactory;
import com.polibudaprojects.thelastsurvivors.player.Statistics;
import com.polibudaprojects.thelastsurvivors.states.PlayState;

public class GameTimer implements HUD {

    private final BitmapFont font = FontFactory.getFont(32);
    private final GlyphLayout layout = new GlyphLayout();
    private final Vector2 position = new Vector2(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() - 10f);
    private final Statistics statistics = Statistics.getInstance();
    private float timeRemaining = PlayState.TIME_LIMIT;

    public void reset() {
        timeRemaining = PlayState.TIME_LIMIT;
    }

    @Override
    public void update(float delta) {
        timeRemaining -= delta;
        if (timeRemaining < 0) {
            timeRemaining = 0;
        }
        statistics.setTimeLeft(timeRemaining);
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.begin();
        String text = String.format("%d:%02d", (int) timeRemaining / 60, (int) timeRemaining % 60);
        layout.setText(font, text);
        float x = position.x - layout.width / 2f;
        float y = position.y - layout.height / 2f;
        font.draw(batch, layout, x, y);
        batch.end();
    }

    public boolean isTimeUp() {
        return timeRemaining <= 0;
    }
}
