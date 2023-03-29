package com.polibudaprojects.thelastsurvivors.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EndState extends State{
    private final Texture background;
    private final Texture startAgain;

    private final Texture gameOver;
    public EndState(StatesManager gsm) {
        super(gsm);
        cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        background = new Texture("background.png");
        gameOver = new Texture("gameOver.png");
        startAgain = new Texture("startAgain.png");
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
            gsm.set(new StartState(gsm));
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, 0,0);
        sb.draw(gameOver, cam.position.x - gameOver.getWidth() / 2f, cam.position.y- gameOver.getHeight() / 2f + 50);
        sb.draw(startAgain, cam.position.x - startAgain.getWidth() / 2f, cam.position.y- startAgain.getHeight() / 2f - 50);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        startAgain.dispose();
        System.out.println("Menu State Disposed");
    }
}
