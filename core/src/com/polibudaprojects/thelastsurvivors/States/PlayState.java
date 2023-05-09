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
import com.polibudaprojects.thelastsurvivors.Assets;
import com.polibudaprojects.thelastsurvivors.Music.BackgroundMusic;
import com.polibudaprojects.thelastsurvivors.Music.SoundFx;
import com.polibudaprojects.thelastsurvivors.Player.FireWarrior;
import com.polibudaprojects.thelastsurvivors.Player.Player;
import com.polibudaprojects.thelastsurvivors.Player.Statistics;
import com.polibudaprojects.thelastsurvivors.hud.GameTimer;
import com.polibudaprojects.thelastsurvivors.items.ItemManager;
import com.polibudaprojects.thelastsurvivors.map.InfiniteTiledMap;
import com.polibudaprojects.thelastsurvivors.monsters.MonsterManager;
import com.polibudaprojects.thelastsurvivors.weapons.Weapon;

import java.util.Random;

public class PlayState extends State {

    private static final float TIME_LIMIT = 30 * 60f;
    private final InfiniteTiledMap infiniteTiledMap;
    private final ShapeRenderer shapeRenderer;
    private final Texture playerPortraitPng;
    private final TextureRegion playerStats;
    private final GameTimer gameTimer;
    private final Texture background;
    private final BitmapFont font;
    private final OrthographicCamera pause;
    private final Texture resumeBtn;
    private final Rectangle resumeBtnRectangle;
    private final SoundFx soundFx;
    private final Random random;
    private final float soundEffectDelay;
    private final BackgroundMusic backgroundMusic;
    private final MonsterManager monsterManager;
    private final ItemManager itemManager;
    private final Player player;
    private boolean isPaused;
    private boolean justPressed;
    private String stats;
    private float elapsedTimeSinceLastSoundEffect;

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
        gameTimer = new GameTimer(TIME_LIMIT, timerFont);

        isPaused = false;
        justPressed = false;
        background = Assets.get("background.png", Texture.class);
        font = new BitmapFont();
        stats = "HEALTH: " + player.getCurrentHealth() + "/" + player.getMaxHealth() + "\n" + "HP REGEN: " + player.getHpRegen() + "\n" + "LEVEL: " + player.getLevel() + "\n" + "EXP: " + player.getScore() + "/" + player.getMaxScore() + "\n\n";

        pause = new OrthographicCamera();
        pause.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //TODO change button image
        resumeBtn = Assets.get("button.png", Texture.class);
        resumeBtnRectangle = new Rectangle(10, Gdx.graphics.getHeight() - resumeBtn.getHeight() - 10, resumeBtn.getWidth(), resumeBtn.getHeight());

        soundFx = new SoundFx();
        soundFx.loadSound("sound1", "music/brains.wav");
        soundFx.loadSound("sound2", "music/groan.wav");
        soundFx.loadSound("sound3", "music/rar.wav");

        random = new Random();
        soundEffectDelay = 3.0f;
        elapsedTimeSinceLastSoundEffect = 0.0f;
    }

    @Override
    public void handleInput() {
    }

    @Override
    public void reset() {
        Statistics.getInstance().reset();
        gameTimer.setTimeRemaining(TIME_LIMIT);
        player.reset();
        monsterManager.reset();
        itemManager.reset();
        backgroundMusic.restart();
    }

    @Override
    public void update(float dt) {
        if (!isPaused) {
            handleInput();
            infiniteTiledMap.update(cam, player.getPosition().x + 90, player.getPosition().y + 50);
            player.update(dt);
            monsterManager.update(dt);
            itemManager.update(dt);
            gameTimer.update(dt);
            gameTimer.updatePosition(cam.position.x, Gdx.graphics.getHeight() / 2f + cam.position.y - 10f);

            if (player.isGameOver() || gameTimer.isTimeUp()) {
                Statistics.getInstance().setTimeLeft(gameTimer.getTimeRemaining());
                gsm.set(gsm.getEnd());
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) && !justPressed) {
                isPaused = true;
                justPressed = true;
            }

            elapsedTimeSinceLastSoundEffect += dt;

            if (elapsedTimeSinceLastSoundEffect >= soundEffectDelay) {
                if (random.nextInt(100) < 1) {
                    int soundToPlay = random.nextInt(3) + 1;
                    soundFx.playSound("sound" + soundToPlay);
                    elapsedTimeSinceLastSoundEffect = 0.0f;
                }
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
            player.draw(sb);
            monsterManager.draw(sb);
            itemManager.draw(sb);
            sb.draw(playerStats, cam.position.x - 540, cam.position.y - 312);
            sb.draw(playerPortraitPng, cam.position.x - 525, cam.position.y - 285);
            sb.end();

            //HUD
            gameTimer.render(sb);

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
        } else {
            stats = "HEALTH: " + player.getCurrentHealth() + "/" + player.getMaxHealth() + "\n" + "HP REGEN: " + player.getHpRegen() + "\n" + "LEVEL: " + player.getLevel() + "\n" + "EXP: " + player.getScore() + "/" + player.getMaxScore() + "\n\n";

            for (Weapon weapon : player.getWeapons()) {
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
}