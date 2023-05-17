package com.polibudaprojects.thelastsurvivors.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector2;
import com.polibudaprojects.thelastsurvivors.assets.Assets;
import com.polibudaprojects.thelastsurvivors.assets.FontFactory;

import static com.polibudaprojects.thelastsurvivors.GameMain.SCREEN_HEIGHT;
import static com.polibudaprojects.thelastsurvivors.GameMain.SCREEN_WIDTH;

public class Button {
    private static final int PADDING = 50;
    private final Color hoverColor = new Color(0.7f, 0.7f, 0.7f, 1);
    private final Texture buttonTexture = Assets.get("button.png", Texture.class);
    private final BitmapFont font;
    private final GlyphLayout glyphLayout;
    private final Runnable onClickAction;
    private final Vector2 position = new Vector2();
    private boolean isHovered;
    private boolean justTouched;

    public Button(String text, int size, Runnable onClickAction) {
        font = FontFactory.getFont(size, Color.BLACK);
        glyphLayout = new GlyphLayout(font, text);
        this.onClickAction = onClickAction;
    }

    public Button(String buttonText, Runnable onClickAction) {
        this(buttonText, 72, onClickAction);
    }

    private void update() {
        isHovered = isMouseOver();
        handleClick();
    }

    private void handleClick() {
        if (Gdx.input.justTouched() && isHovered) {
            justTouched = true;
        }
        if (justTouched && !Gdx.input.isTouched()) {
            justTouched = false;
            onClickAction.run();
        }
    }

    private boolean isMouseOver() {
        float mouseX = Gdx.input.getX() * (float) SCREEN_WIDTH / Gdx.graphics.getWidth();
        float mouseY = (Gdx.graphics.getHeight() - Gdx.input.getY()) * (float) SCREEN_HEIGHT / Gdx.graphics.getHeight();

        return mouseX >= position.x && mouseX <= position.x + getWidth() &&
                mouseY >= position.y && mouseY <= position.y + getHeight();
    }

    public void render(Batch batch) {
        update();
        batch.setColor(isHovered ? hoverColor : Color.WHITE);
        batch.draw(buttonTexture, position.x, position.y, getWidth(), getHeight());
        batch.setColor(Color.WHITE);
        font.draw(batch, glyphLayout, position.x + getWidth() / 2 - glyphLayout.width / 2, position.y + getHeight() / 2 + glyphLayout.height / 2);
    }

    public void setPosition(float x, float y) {
        this.position.set(x, y);
    }

    public float getWidth() {
        return glyphLayout.width + PADDING;
    }

    public float getHeight() {
        return glyphLayout.height + PADDING;
    }
}