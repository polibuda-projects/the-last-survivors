package com.polibudaprojects.thelastsurvivors.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.polibudaprojects.thelastsurvivors.Assets;
import com.polibudaprojects.thelastsurvivors.FontFactory;
import com.polibudaprojects.thelastsurvivors.Player.Statistics;
import com.polibudaprojects.thelastsurvivors.hud.Button;

public class EndState extends State {
    private final Statistics statistics = Statistics.getInstance();
    private final Texture background = Assets.get("background.png", Texture.class);
    private final Texture gameOver = Assets.get("gameOver.png", Texture.class);
    private final BitmapFont font = FontFactory.getFont(32);
    private String stats;

    public EndState(StatesManager gsm) {
        super(gsm);

        Button tryAgainBtn = new Button("TRY AGAIN?", () -> gsm.setState(gsm.getStart()));
        tryAgainBtn.setPosition(cam.position.x - tryAgainBtn.getWidth() / 2f, 20);

        stage.addActor(tryAgainBtn);
    }

    @Override
    public void handleInput() {
    }

    @Override
    public void reset() {
        int monstersKilled = statistics.getMonstersKilled();
        int totalDamage = statistics.getTotalDamage();
        float timeLeft = statistics.getTimeLeft();

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
        font.draw(sb, stats, 120, Gdx.graphics.getHeight() - 150);
        sb.end();
    }
}
