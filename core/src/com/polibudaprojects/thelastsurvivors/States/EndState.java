package com.polibudaprojects.thelastsurvivors.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EndState extends State {
    private final Texture background;
    private final Texture startAgain;

    private final Texture gameOver;
    private final BitmapFont font;
    private final String stats;

    public EndState(StatesManager gsm, int monstersKilled, float timeLeft, int totalDamage) {
        super(gsm);
        cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        background = new Texture("background.png");
        gameOver = new Texture("gameOver.png");
        startAgain = new Texture("startAgain.png");
        font = new BitmapFont();
        stats = "MONSTERS KILLED: " + monstersKilled + "\n" +
                "TIME LEFT: " + (int) timeLeft / 60 + ":" + (int) timeLeft % 60 + "\n" +
                "TOTAL DAMAGE: " + totalDamage + "\n" +
                "DAMAGE PER SECOND: " + String.format("%.2f", totalDamage / (30 * 60 - timeLeft)) + "\n";
    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()) {
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
        sb.draw(background, 0, 0);
        sb.draw(gameOver, cam.position.x - gameOver.getWidth() / 2f, Gdx.graphics.getHeight() - gameOver.getHeight() + 50);
        sb.draw(startAgain, cam.position.x - startAgain.getWidth() / 2f, 10);
        font.getData().setScale(1.5f, 1.5f);
        font.draw(sb, stats, 120, Gdx.graphics.getHeight() - 150);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        startAgain.dispose();
        System.out.println("Menu State Disposed");
    }
}
