package com.polibudaprojects.thelastsurvivors.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.polibudaprojects.thelastsurvivors.DemoPlayer;
import com.polibudaprojects.thelastsurvivors.Music.BackgroundMusic;
import com.polibudaprojects.thelastsurvivors.monsters.MonsterFactory;
import com.polibudaprojects.thelastsurvivors.monsters.MonsterManager;

import java.nio.file.Paths;

public class PlayState extends State {

    private final MonsterManager monsterManager;
    Texture img;
    private DemoPlayer demoPlayer;
    private Texture bg;
    private BackgroundMusic backgroundMusic;

    public PlayState(StatesManager gsm) {
        super(gsm);
        img = new Texture("demo.png");
        demoPlayer = new DemoPlayer(img);
        monsterManager = new MonsterManager(demoPlayer);
        cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        bg = new Texture("background.png");
        backgroundMusic = new BackgroundMusic(Paths.get("music/BackgroundTheLastSurvivors.mp3"));
    }

    @Override
    public void handleInput() {

    }

    @Override
    public void update(float dt) {
        handleInput();
        demoPlayer.update(dt);
        monsterManager.update(dt);
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(bg, cam.position.x - (cam.viewportWidth / 2), 0);
        sb.draw(img, demoPlayer.position.x, demoPlayer.position.y);;
        monsterManager.draw(sb);
        sb.end();
    }

    @Override
    public void dispose() {
        MonsterFactory.dispose();
    }

}