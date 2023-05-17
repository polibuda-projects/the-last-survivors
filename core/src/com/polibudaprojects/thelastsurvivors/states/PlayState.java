package com.polibudaprojects.thelastsurvivors.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.polibudaprojects.thelastsurvivors.hud.PlayStateHud;
import com.polibudaprojects.thelastsurvivors.items.ItemManager;
import com.polibudaprojects.thelastsurvivors.map.InfiniteTiledMap;
import com.polibudaprojects.thelastsurvivors.monsters.MonsterManager;
import com.polibudaprojects.thelastsurvivors.music.BackgroundMusic;
import com.polibudaprojects.thelastsurvivors.music.SoundFx;
import com.polibudaprojects.thelastsurvivors.player.Player;
import com.polibudaprojects.thelastsurvivors.player.Statistics;

public class PlayState extends State {

    public static final float TIME_LIMIT = 30 * 60f;
    private final InfiniteTiledMap infiniteTiledMap;
    private final PlayStateHud hud;
    private final SoundFx soundFx;
    private final BackgroundMusic backgroundMusic;
    private final MonsterManager monsterManager;
    private final ItemManager itemManager;
    private final Player player;

    public PlayState(StatesManager gsm, Player player) {
        super(gsm);
        this.player = player;

        infiniteTiledMap = new InfiniteTiledMap("map/game-dev.tmx", 3, 3, 0.5f); // original view = 1f
        hud = new PlayStateHud(player);
        itemManager = new ItemManager(player);
        monsterManager = new MonsterManager(player, itemManager);
        backgroundMusic = new BackgroundMusic();
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
        hud.getGameTimer().reset();
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
        hud.update(dt);

        if (player.isGameOver() || hud.getGameTimer().isTimeUp()) {
            gsm.setState(gsm.getEnd());
        }

        soundFx.playRandomMonsterSound(dt);
    }

    @Override
    public void render(SpriteBatch sb) {
        infiniteTiledMap.render(cam);
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        player.draw(sb);
        monsterManager.draw(sb);
        itemManager.draw(sb);
        sb.end();
        hud.draw(sb);
    }

    public Player getPlayer() {
        return player;
    }
}