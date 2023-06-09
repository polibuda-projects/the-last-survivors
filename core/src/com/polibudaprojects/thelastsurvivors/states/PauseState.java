package com.polibudaprojects.thelastsurvivors.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.polibudaprojects.thelastsurvivors.assets.Assets;
import com.polibudaprojects.thelastsurvivors.assets.FontFactory;
import com.polibudaprojects.thelastsurvivors.hud.Button;
import com.polibudaprojects.thelastsurvivors.player.Statistics;
import com.polibudaprojects.thelastsurvivors.weapons.Weapon;

import static com.polibudaprojects.thelastsurvivors.GameMain.SCREEN_HEIGHT;
import static com.polibudaprojects.thelastsurvivors.GameMain.SCREEN_WIDTH;

public class PauseState extends State {
    private final Statistics statistics = Statistics.getInstance();
    private final Texture background = Assets.get("background.png", Texture.class);
    private final BitmapFont font = FontFactory.getFont(32);
    private final Button resumeBtn;
    private final Button exitBtn;
    private String stats;

    public PauseState(StatesManager gsm) {
        super(gsm);
        resumeBtn = new Button("RESUME", gsm::returnToPreviousState);
        exitBtn = new Button("EXIT", () -> gsm.setState(gsm.getStart()));
        resumeBtn.setPosition(10, 10);
        exitBtn.setPosition(resumeBtn.getWidth() + 50, 10);
    }

    @Override
    public void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            gsm.returnToPreviousState();
        }
    }

    @Override
    public void reset() {
        int monstersKilled = statistics.getMonstersKilled();
        float timeLeft = statistics.getTimeLeft();

        stats = "HEALTH: " + statistics.getCurrentHealth() + "/" + statistics.getMaxHealth() + "\n" + "HP REGEN: " + statistics.getHpRegen() + "\n" + "LEVEL: " + statistics.getLevel() + "\n" + "EXP: " + statistics.getScore() + "/" + statistics.getMaxScore() + "\n\n";

        for (Weapon weapon : statistics.getWeapons()) {
            stats = stats.concat(weapon.toString() + "\n\n");
        }

        stats += "MONSTERS KILLED: " + monstersKilled + "\n" +
                "TIME LEFT: " + (int) timeLeft / 60 + ":" + (int) timeLeft % 60 + "\n";
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
        font.draw(sb, stats, 10, SCREEN_HEIGHT - 10);
        resumeBtn.render(sb);
        exitBtn.render(sb);
        sb.end();
    }
}