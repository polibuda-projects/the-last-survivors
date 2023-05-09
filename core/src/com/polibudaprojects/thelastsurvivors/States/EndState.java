package com.polibudaprojects.thelastsurvivors.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.polibudaprojects.thelastsurvivors.Assets;
import com.polibudaprojects.thelastsurvivors.Player.Statistics;

public class EndState extends State {
    private final Texture background;
    private final Texture startAgain;
    private final Texture gameOver;
    private final BitmapFont font;
    private String stats;

    public EndState(StatesManager gsm) {
        super(gsm);
        background = Assets.get("background.png", Texture.class);
        gameOver = Assets.get("gameOver.png", Texture.class);
        startAgain = Assets.get("startAgain.png", Texture.class);
        font = new BitmapFont();
    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()) {
            gsm.set(gsm.getStart());
        }
    }

    @Override
    public void reset() {
        int monstersKilled = Statistics.getInstance().getMonstersKilled();
        int totalDamage = Statistics.getInstance().getTotalDamage();
        float timeLeft = Statistics.getInstance().getTimeLeft();

        stats = "MONSTERS KILLED: " + monstersKilled + "\n" +
                "TIME LEFT: " + (int) timeLeft / 60 + ":" + (int) timeLeft % 60 + "\n" +
                "TOTAL DAMAGE: " + totalDamage + "\n" +
                "DAMAGE PER SECOND: " + String.format("%.2f", totalDamage / (30 * 60 - timeLeft)) + "\n";
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
}
