package com.polibudaprojects.thelastsurvivors.states;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.polibudaprojects.thelastsurvivors.assets.Assets;
import com.polibudaprojects.thelastsurvivors.assets.FontFactory;
import com.polibudaprojects.thelastsurvivors.hud.Button;
import com.polibudaprojects.thelastsurvivors.player.Statistics;

import static com.polibudaprojects.thelastsurvivors.GameMain.SCREEN_HEIGHT;
import static com.polibudaprojects.thelastsurvivors.GameMain.SCREEN_WIDTH;

public class EndState extends State {
    private final Statistics statistics = Statistics.getInstance();
    private final Texture background = Assets.get("background.png", Texture.class);
    private final Texture gameOver = Assets.get("gameOver.png", Texture.class);
    private final BitmapFont font = FontFactory.getFont(32);
    private final Button tryAgainBtn;
    private String stats;

    public EndState(StatesManager gsm) {
        super(gsm);

        tryAgainBtn = new Button("TRY AGAIN?", () -> gsm.setState(gsm.getStart()));
        tryAgainBtn.setPosition(cam.position.x - tryAgainBtn.getWidth() / 2f, 20);
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
        sb.draw(background, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        sb.draw(gameOver, cam.position.x - gameOver.getWidth() / 2f, SCREEN_HEIGHT - gameOver.getHeight() + 50);
        font.draw(sb, stats, 120, SCREEN_HEIGHT - 150);
        tryAgainBtn.render(sb);
        sb.end();
    }
}
