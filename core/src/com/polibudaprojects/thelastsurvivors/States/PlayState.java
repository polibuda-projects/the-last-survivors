package com.polibudaprojects.thelastsurvivors.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.polibudaprojects.thelastsurvivors.hud.GameTimer;
import com.polibudaprojects.thelastsurvivors.map.InfiniteTiledMap;
import com.polibudaprojects.thelastsurvivors.Player.DemoPlayer;
import com.polibudaprojects.thelastsurvivors.Music.BackgroundMusic;
import com.polibudaprojects.thelastsurvivors.monsters.MonsterFactory;
import com.polibudaprojects.thelastsurvivors.monsters.MonsterManager;
import com.polibudaprojects.thelastsurvivors.weapons.Weapon;

import java.nio.file.Paths;

public class PlayState extends State {
    private final InfiniteTiledMap infiniteTiledMap;
    private final ShapeRenderer shapeRenderer;
    private final MonsterManager monsterManager;
    private final DemoPlayer demoPlayer;
    private final Texture playerPortraitPng;
    private final TextureRegion playerStats;
    private final GameTimer gameTimer;
    private boolean isPaused;
    private boolean justPressed;
    private final Texture background;
    private final BitmapFont font;
    private String stats;
    private final OrthographicCamera pause;
    private final Texture resumeBtn;
    private final Rectangle resumeBtnRectangle;
    public static int monstersKilled;
    public static int totalDamage;


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

        isPaused = false;
        justPressed = false;
        background = new Texture("background.png");
        font = new BitmapFont();
        stats = "HEALTH: " + demoPlayer.getCurrentHealth() + "/" + demoPlayer.getMaxHealth() + "\n" +
                "HP REGEN: " + demoPlayer.getHpRegen() + "\n" +
                "EXP: " + 50 + "/" + 100 + "\n\n";

        pause = new OrthographicCamera();
        pause.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //TODO change button image
        resumeBtn = new Texture("button.png");
        resumeBtnRectangle = new Rectangle(10, Gdx.graphics.getHeight() - resumeBtn.getHeight() - 10, resumeBtn.getWidth(), resumeBtn.getHeight());
        monstersKilled = 0;
        totalDamage = 0;
    }


    @Override
    public void handleInput() {

    }

    @Override
    public void update(float dt) {
        if (!isPaused) {
            handleInput();
            infiniteTiledMap.update(cam, demoPlayer.getX() + 90, demoPlayer.getY() + 50);
            demoPlayer.update(dt);
            monsterManager.update(dt);
            gameTimer.update(dt);
            gameTimer.updatePosition(demoPlayer.getX() + 90, demoPlayer.getY() + 280);
            if (demoPlayer.isGameOver() || gameTimer.isTimeUp()) {
                gsm.set(new EndState(gsm, monstersKilled, gameTimer.getTimeRemaining(), totalDamage));
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) && !justPressed) {
                isPaused = true;
                justPressed = true;
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) && !justPressed) {
            isPaused = false;

        }
        justPressed = false;
    }


    @Override
    public void render(SpriteBatch sb) {
        if (!isPaused) {
            infiniteTiledMap.render(cam);

            sb.setProjectionMatrix(cam.combined);
            sb.begin();
            demoPlayer.draw(sb);
            monsterManager.draw(sb);
            sb.draw(playerStats, cam.position.x - 310, cam.position.y - 240);
            sb.draw(playerPortraitPng, cam.position.x - 295, cam.position.y - 213);
            sb.end();

            //HUD
            gameTimer.render(sb);


            //HP BAR
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.RED);
            shapeRenderer.rect(82, 53, (195f * demoPlayer.getCurrentHealth()) / demoPlayer.getMaxHealth(), 10);
            shapeRenderer.end();

            //XP BAR
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.SKY);
            shapeRenderer.rect(82, 34, 100, 10);
            shapeRenderer.end();
        } else {
            stats = "HEALTH: " + demoPlayer.getCurrentHealth() + "/" + demoPlayer.getMaxHealth() + "\n" +
                    "HP REGEN: " + demoPlayer.getHpRegen() + "\n" +
                    "EXP: " + 50 + "/" + 100 + "\n\n";

            for (Weapon weapon : demoPlayer.getWeapons()) {
                stats = stats.concat(weapon.toString() + "\n");
            }

            sb.setProjectionMatrix(pause.combined);
            sb.begin();
            sb.draw(background, 0, 0);
            font.getData().setScale(1.5f, 1.5f);
            font.draw(sb, stats, 10, Gdx.graphics.getHeight() - 10);
            sb.draw(resumeBtn, 10, 10);
            sb.end();

            if (Gdx.input.justTouched()) {
                if (resumeBtnRectangle.contains(Gdx.input.getX(), Gdx.input.getY())) {
                    isPaused = false;
                }
            }
        }
    }

    @Override
    public void dispose() {
        MonsterFactory.dispose();
        infiniteTiledMap.dispose();
    }

}