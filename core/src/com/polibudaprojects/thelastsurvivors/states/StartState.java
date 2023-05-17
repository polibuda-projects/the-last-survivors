package com.polibudaprojects.thelastsurvivors.states;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.polibudaprojects.thelastsurvivors.assets.Assets;
import com.polibudaprojects.thelastsurvivors.hud.Button;

import static com.polibudaprojects.thelastsurvivors.GameMain.SCREEN_HEIGHT;
import static com.polibudaprojects.thelastsurvivors.GameMain.SCREEN_WIDTH;

public class StartState extends State {
    private final Texture background = Assets.get("intro.png", Texture.class);
    private final Button startButton;

    public StartState(StatesManager gsm) {
        super(gsm);
        startButton = new Button("START", () -> gsm.setState(gsm.getSelect()));
        startButton.setPosition(cam.position.x - startButton.getWidth() / 2f, cam.position.y - startButton.getHeight() / 2f);
    }

    @Override
    public void handleInput() {
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
        sb.draw(background, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        startButton.render(sb);
        sb.end();
    }
}