package com.polibudaprojects.thelastsurvivors.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.polibudaprojects.thelastsurvivors.hud.GameTimer;
import com.polibudaprojects.thelastsurvivors.map.CollisionDetector;
import com.polibudaprojects.thelastsurvivors.map.InfiniteTiledMap;
import com.polibudaprojects.thelastsurvivors.Player.DemoPlayer;
import com.polibudaprojects.thelastsurvivors.Music.BackgroundMusic;
import com.polibudaprojects.thelastsurvivors.monsters.MonsterFactory;
import com.polibudaprojects.thelastsurvivors.monsters.MonsterManager;
import java.nio.file.Paths;

public class PlayState extends State {
    private InfiniteTiledMap infiniteTiledMap;
    private final ShapeRenderer shapeRenderer;
    private final MonsterManager monsterManager;
    private final DemoPlayer demoPlayer;
    private final Texture playerPortraitPng;
    private final TextureRegion playerStats;
    private GameTimer gameTimer;


    public PlayState(StatesManager gsm) {

        super(gsm);

        //Map
        infiniteTiledMap = new InfiniteTiledMap("map/game-dev1.tmx", 3, 3, 0.5f); // original view = 1f

        //Player Stats Background
        Texture playerStatsPng = new Texture("playerHub.png");
        playerStats = new TextureRegion(playerStatsPng, 0, 0, 300, 82);

        //Player Portrait
        playerPortraitPng = new Texture("portrait.png");

        //Used to generate HP Bar and Xp Bar
        shapeRenderer = new ShapeRenderer();
        demoPlayer = new DemoPlayer();
        monsterManager = new MonsterManager(demoPlayer);

        cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        BackgroundMusic backgroundMusic = new BackgroundMusic(Paths.get("music/BackgroundTheLastSurvivors.mp3"));

        BitmapFont timerFont = new BitmapFont();
        float timeRemaining = 30 * 60;
        gameTimer = new GameTimer(timeRemaining, timerFont);

    }


    @Override
    public void handleInput() {

    }

    @Override
    public void update(float dt) {
        handleInput();
        infiniteTiledMap.update(cam, demoPlayer.getX()+90, demoPlayer.getY()+50);
        demoPlayer.update(dt);
        monsterManager.update(dt);
        gameTimer.update(dt);
        if (demoPlayer.isGameOver() || gameTimer.isTimeUp()) {
            gsm.set(new EndState(gsm));
        }
    }

    @Override
    public void render(SpriteBatch sb) {

        infiniteTiledMap.render(cam);

        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        demoPlayer.draw(sb);
        monsterManager.draw(sb);
        sb.draw(playerStats,cam.position.x-310,cam.position.y-240);
        sb.draw(playerPortraitPng,cam.position.x-295,cam.position.y-213);
        sb.end();

        //HUD
        gameTimer.render(sb);



        //HP BAR
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(82, 53, (195f*demoPlayer.getCurrentHealth())/demoPlayer.getMaxHealth(), 10);
        shapeRenderer.end();

        //XP BAR
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.SKY);
        shapeRenderer.rect(82, 34, 100, 10);
        shapeRenderer.end();
    }

    @Override
    public void dispose() {
        MonsterFactory.dispose();
        infiniteTiledMap.dispose();
    }

}