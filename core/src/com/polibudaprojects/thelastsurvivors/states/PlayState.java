package com.polibudaprojects.thelastsurvivors.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.polibudaprojects.thelastsurvivors.assets.Assets;
import com.polibudaprojects.thelastsurvivors.hud.GameTimer;
import com.polibudaprojects.thelastsurvivors.hud.HUD;
import com.polibudaprojects.thelastsurvivors.hud.Level;
import com.polibudaprojects.thelastsurvivors.hud.WeaponsList;
import com.polibudaprojects.thelastsurvivors.items.ItemManager;
import com.polibudaprojects.thelastsurvivors.map.InfiniteTiledMap;
import com.polibudaprojects.thelastsurvivors.monsters.MonsterManager;
import com.polibudaprojects.thelastsurvivors.music.BackgroundMusic;
import com.polibudaprojects.thelastsurvivors.music.SoundFx;
import com.polibudaprojects.thelastsurvivors.player.Player;
import com.polibudaprojects.thelastsurvivors.player.Statistics;

import java.util.ArrayList;
import java.util.List;

public class PlayState extends State {

    public static final float TIME_LIMIT = 30 * 60f;
    private final List<HUD> hudElements = new ArrayList<>();
    private final InfiniteTiledMap infiniteTiledMap;
    private final ShapeRenderer shapeRenderer;
    private final TextureRegion playerStats;
    private final GameTimer gameTimer;
    private final SoundFx soundFx;
    private final BackgroundMusic backgroundMusic;
    private final MonsterManager monsterManager;
    private final ItemManager itemManager;
    private final Player player;

    public PlayState(StatesManager gsm, Player player) {
        super(gsm);

        //Map
        infiniteTiledMap = new InfiniteTiledMap("map/game-dev.tmx", 3, 3, 0.5f); // original view = 1f

        //Player Stats Background
        Texture playerStatsPng = Assets.get("hub/playerHub.png", Texture.class);
        playerStats = new TextureRegion(playerStatsPng, 0, 0, 300, 82);

        //Used to generate HP Bar and Xp Bar
        shapeRenderer = new ShapeRenderer();
        this.player = player;
        itemManager = new ItemManager(player);
        monsterManager = new MonsterManager(player, itemManager);

        backgroundMusic = new BackgroundMusic();

        gameTimer = new GameTimer();
        hudElements.add(gameTimer);
        hudElements.add(new Level());
        hudElements.add(new WeaponsList());

        soundFx = new SoundFx();

        Statistics.getInstance().setPlayer(player);
    }

    @Override
    public void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
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
        infiniteTiledMap.update(cam, player.getCenterPosition());
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

    public Player getPlayer() {
        return player;
    }
}