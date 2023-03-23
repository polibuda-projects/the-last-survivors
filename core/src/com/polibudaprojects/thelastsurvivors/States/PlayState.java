package com.polibudaprojects.thelastsurvivors.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.polibudaprojects.thelastsurvivors.DemoPlayer;
import com.polibudaprojects.thelastsurvivors.monsters.MonsterFactory;
import com.polibudaprojects.thelastsurvivors.monsters.MonsterSpawner;

public class PlayState extends State {

    private final MonsterSpawner spawner;
    Texture img;
    private DemoPlayer demoPlayer;
    private Texture bg;

    public PlayState(StatesManager gsm) {
        super(gsm);
        img = new Texture("demo.png");
        demoPlayer = new DemoPlayer(img);
        spawner = new MonsterSpawner(demoPlayer);
        cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        bg = new Texture("background.png");
    }

    @Override
    public void handleInput() {

    }

    @Override
    public void update(float dt) {
        handleInput();
        demoPlayer.update(dt);
        spawner.update(dt);
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(bg, cam.position.x - (cam.viewportWidth / 2), 0);
        sb.draw(img, demoPlayer.position.x, demoPlayer.position.y);
        spawner.draw(sb);
        sb.end();
    }

    @Override
    public void dispose() {
        MonsterFactory.dispose();
    }

}