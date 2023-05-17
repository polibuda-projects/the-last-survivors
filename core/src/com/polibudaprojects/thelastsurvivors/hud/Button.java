package com.polibudaprojects.thelastsurvivors.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.polibudaprojects.thelastsurvivors.assets.Assets;
import com.polibudaprojects.thelastsurvivors.assets.FontFactory;

public class Button extends Actor {
    private static final int PADDING = 50;
    private final Color hoverColor = new Color(0.7f, 0.7f, 0.7f, 1);
    private final Texture buttonTexture = Assets.get("button.png", Texture.class);
    private final BitmapFont font;
    private final GlyphLayout glyphLayout;
    private boolean isHovered;

    public Button(String text, int size, Runnable onClickAction) {
        font = FontFactory.getFont(size, Color.BLACK);
        glyphLayout = new GlyphLayout(font, text);

        setBounds(getX(), getY(), glyphLayout.width + PADDING, glyphLayout.height + PADDING);

        addOnClickListener(onClickAction);
    }

    public Button(String buttonText, Runnable onClickAction) {
        this(buttonText, 72, onClickAction);
    }

    private void addOnClickListener(Runnable onClickAction) {
        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                onClickAction.run();
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                isHovered = true;
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                isHovered = false;
            }
        });
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(isHovered ? hoverColor : Color.WHITE);
        batch.draw(buttonTexture, getX(), getY(), getWidth(), getHeight());
        font.draw(batch, glyphLayout, getX() + getWidth() / 2 - glyphLayout.width / 2, getY() + getHeight() / 2 + glyphLayout.height / 2);
        super.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
}
