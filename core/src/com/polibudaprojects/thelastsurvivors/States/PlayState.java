package com.polibudaprojects.thelastsurvivors.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.polibudaprojects.thelastsurvivors.Assets;
import com.polibudaprojects.thelastsurvivors.Music.BackgroundMusic;
import com.polibudaprojects.thelastsurvivors.Music.SoundFx;
import com.polibudaprojects.thelastsurvivors.Player.FireWarrior;
import com.polibudaprojects.thelastsurvivors.Player.Player;
import com.polibudaprojects.thelastsurvivors.Player.Statistics;
import com.polibudaprojects.thelastsurvivors.hud.GameTimer;
import com.polibudaprojects.thelastsurvivors.hud.HUD;
import com.polibudaprojects.thelastsurvivors.hud.WeaponsList;
import com.polibudaprojects.thelastsurvivors.items.ItemManager;
import com.polibudaprojects.thelastsurvivors.map.InfiniteTiledMap;
import com.polibudaprojects.thelastsurvivors.monsters.MonsterManager;

import java.util.ArrayList;
import java.util.List;

public class PlayState extends State {

    public static final float TIME_LIMIT = 30 * 60f;
    private final List<HUD> hudElements = new ArrayList<>();
    private final InfiniteTiledMap infiniteTiledMap;
    private final ShapeRenderer shapeRenderer;
    private final Texture playerPortraitPng;
    private final TextureRegion playerStats;
    private final GameTimer gameTimer;
    private final SoundFx soundFx;
    private final BackgroundMusic backgroundMusic;
    private final MonsterManager monsterManager;
    private final ItemManager itemManager;
    private final Player player;

    public PlayState(StatesManager gsm) {
        super(gsm);

        //Map
        infiniteTiledMap = new InfiniteTiledMap("map/game-dev.tmx", 3, 3, 0.5f); // original view = 1f

        //Player Stats Background
        Texture playerStatsPng = Assets.get("playerHub.png", Texture.class);
        playerStats = new TextureRegion(playerStatsPng, 0, 0, 300, 82);

        //Player Portrait
        playerPortraitPng = Assets.get("portrait.png", Texture.class);

        //Used to generate HP Bar and Xp Bar
        shapeRenderer = new ShapeRenderer();
        player = new FireWarrior();
        itemManager = new ItemManager(player);
        monsterManager = new MonsterManager(player, itemManager);

        backgroundMusic = new BackgroundMusic();

        BitmapFont timerFont = new BitmapFont();
        BitmapFont.BitmapFontData fontData = timerFont.getData();
        fontData.setScale(1.5f);
        gameTimer = new GameTimer(timerFont);
        hudElements.add(gameTimer);
        hudElements.add(new WeaponsList(player));

        soundFx = new SoundFx();
    }

    @Override
    public void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Statistics.getInstance().setPlayer(player);
            gsm.setState(gsm.getPause());
        }
    }

    @Override
    public void reset() {
        Statistics.getInstance().reset();
        gameTimer.reset();
        player.reset();
        monsterManager.reset();
        itemManager.reset();
        backgroundMusic.restart();
    }

    @Override
    public void update(float dt) {
        handleInput();
        infiniteTiledMap.update(cam, player.getPosition().x + 90, player.getPosition().y + 50);
        player.update(dt);
        monsterManager.update(dt);
        itemManager.update(dt);
        updateHUD(dt);

        if (player.isGameOver() || gameTimer.isTimeUp()) {
            gsm.setState(gsm.getEnd());
        }

        soundFx.playRandomMonsterSound(dt);
    }

    private void updateHUD(float dt) {
        for (HUD hud : hudElements) {
            hud.update(dt);
            hud.updatePosition(cam.position.x, cam.position.y);
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        infiniteTiledMap.render(cam);

        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        player.draw(sb);
        monsterManager.draw(sb);
        itemManager.draw(sb);
        sb.draw(playerStats, cam.position.x - 540, cam.position.y - 312);
        sb.draw(playerPortraitPng, cam.position.x - 525, cam.position.y - 285);
        sb.end();

        //HUD
        hudElements.forEach(hud -> hud.render(sb));

        //HP BAR
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(82, 53, (195f * player.getCurrentHealth()) / player.getMaxHealth(), 10);
        shapeRenderer.end();

        //XP BAR
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.SKY);
        shapeRenderer.rect(82, 34, (195f * player.getScore()) / player.getMaxScore(), 10);
        shapeRenderer.end();
    }
}