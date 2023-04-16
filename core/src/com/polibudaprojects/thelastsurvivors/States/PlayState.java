package com.polibudaprojects.thelastsurvivors.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.polibudaprojects.thelastsurvivors.Map.InfiniteTiledMap;
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
//    private final Texture backgroundPng;

    private final Texture playerPortraitPng;
    private final TextureRegion playerStats;

    public PlayState(StatesManager gsm) {

        super(gsm);
        infiniteTiledMap = new InfiniteTiledMap("map/gameart2d-desert.tmx", 3, 3);
        //Player Stats Background
        Texture playerStatsPng = new Texture("playerHub.png");
        playerStats = new TextureRegion(playerStatsPng, 0, 0, 300, 82);

        //Player Portrait
        playerPortraitPng = new Texture("portrait.png");

        //Game Background
//        backgroundPng = new Texture("background.png");

        //Used to generate HP Bar and Xp Bar
        shapeRenderer = new ShapeRenderer();

        demoPlayer = new DemoPlayer();
        monsterManager = new MonsterManager(demoPlayer);

        cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        BackgroundMusic backgroundMusic = new BackgroundMusic(Paths.get("music/BackgroundTheLastSurvivors.mp3"));

    }

    @Override
    public void handleInput() {

    }

    @Override
    public void update(float dt) {
        handleInput();
        infiniteTiledMap.update(cam, demoPlayer.getX(), demoPlayer.getY());
        demoPlayer.update(dt);
        monsterManager.update(dt);
        if (demoPlayer.isGameOver()) {
            gsm.set(new EndState(gsm));
        }
    }

    @Override
    public void render(SpriteBatch sb) {

        infiniteTiledMap.render(cam);

        sb.setProjectionMatrix(cam.combined);
        sb.begin();
//        sb.draw(backgroundPng, cam.position.x - (cam.viewportWidth / 2), 0);
        demoPlayer.draw(sb);
        monsterManager.draw(sb);
        sb.draw(playerStats,10,0);
        sb.draw(playerPortraitPng,25,27);
        sb.end();


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