package com.polibudaprojects.thelastsurvivors.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.polibudaprojects.thelastsurvivors.Assets;
import com.polibudaprojects.thelastsurvivors.Player.Statistics;
import com.polibudaprojects.thelastsurvivors.weapons.Weapon;

public class PauseState extends State {
    private final Statistics statistics = Statistics.getInstance();
    private final Texture background;
    private final BitmapFont font;
    private final Texture resumeBtn;
    private final Rectangle resumeBtnRectangle;
    private String stats;

    public PauseState(StatesManager gsm) {
        super(gsm);
        background = Assets.get("background.png", Texture.class);
        font = new BitmapFont();
        font.getData().setScale(1.5f);

        //TODO change button image
        resumeBtn = Assets.get("button.png", Texture.class);
        resumeBtnRectangle = new Rectangle(10, Gdx.graphics.getHeight() - resumeBtn.getHeight() - 10, resumeBtn.getWidth(), resumeBtn.getHeight());
    }

    @Override
    public void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) ||
                Gdx.input.justTouched() && resumeBtnRectangle.contains(Gdx.input.getX(), Gdx.input.getY())) {
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
        sb.draw(background, 0, 0);
        font.draw(sb, stats, 10, Gdx.graphics.getHeight() - 10);
        sb.draw(resumeBtn, 10, 10);
        sb.end();
    }
}