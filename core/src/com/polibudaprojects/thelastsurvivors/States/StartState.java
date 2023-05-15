package com.polibudaprojects.thelastsurvivors.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.polibudaprojects.thelastsurvivors.Assets;

public class StartState extends State {
    private final Texture background;
    private final Texture playBtn;
    private boolean justTouched;

    public StartState(StatesManager gsm) {
        super(gsm);
        background = Assets.get("intro.png", Texture.class);
        playBtn = Assets.get("button.png", Texture.class);
    }

    @Override
    public void handleInput() {
        // TODO instead use button with ClickListener
        if (Gdx.input.justTouched()) {
            justTouched = true;
        } else if (!Gdx.input.isTouched() && justTouched) {
            justTouched = false;
            gsm.setState(gsm.getSelect());
        }
    }

    @Override
    public void reset() {
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, 0, 0);
        sb.draw(playBtn, cam.position.x - playBtn.getWidth() / 2f, cam.position.y - playBtn.getHeight() / 2f);
        sb.end();
    }
}