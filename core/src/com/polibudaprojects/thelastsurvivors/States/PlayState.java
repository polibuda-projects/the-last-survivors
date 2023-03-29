package com.polibudaprojects.thelastsurvivors.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.polibudaprojects.thelastsurvivors.Player.DemoPlayer;
import com.polibudaprojects.thelastsurvivors.Music.BackgroundMusic;
import com.polibudaprojects.thelastsurvivors.monsters.MonsterFactory;
import com.polibudaprojects.thelastsurvivors.monsters.MonsterManager;

import java.nio.file.Paths;

public class PlayState extends State {

    private final MonsterManager monsterManager;
    Texture img;
    private final DemoPlayer demoPlayer;
    private Texture bg;
    private BackgroundMusic backgroundMusic;
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer tiledMapRenderer;

     SpriteBatch batch;

     TextureRegion region;

    public PlayState(StatesManager gsm) {
        super(gsm);
        batch = new SpriteBatch();
        img = new Texture("player.png");
        region = new TextureRegion(img,0,0,144,80);
        demoPlayer = new DemoPlayer();
        monsterManager = new MonsterManager(demoPlayer);
        cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        bg = new Texture("background.png");
        backgroundMusic = new BackgroundMusic(Paths.get("music/BackgroundTheLastSurvivors.mp3"));
        tiledMap = new TmxMapLoader().load("example.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
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
        demoPlayer.draw(sb);
        monsterManager.draw(sb);
        sb.end();
    }

    @Override
    public void dispose() {
        MonsterFactory.dispose();
    }

}