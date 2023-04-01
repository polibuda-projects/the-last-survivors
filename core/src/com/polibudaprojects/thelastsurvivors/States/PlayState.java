package com.polibudaprojects.thelastsurvivors.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.polibudaprojects.thelastsurvivors.Player.DemoPlayer;
import com.polibudaprojects.thelastsurvivors.Music.BackgroundMusic;
import com.polibudaprojects.thelastsurvivors.monsters.MonsterFactory;
import com.polibudaprojects.thelastsurvivors.monsters.MonsterManager;
import java.nio.file.Paths;

public class PlayState extends State {
    private ShapeRenderer shapeRenderer;
    static private boolean projectionMatrixSet;
    private final MonsterManager monsterManager;
    Texture img;
    private final DemoPlayer demoPlayer;
    private final Texture bg;

    Texture portrait;

    Texture rectangle1;

    SpriteBatch batch;

    TextureRegion region1;

    TextureRegion region2;

    public PlayState(StatesManager gsm) {
        super(gsm);
        batch = new SpriteBatch();
        img = new Texture("player.png");
        rectangle1 = new Texture("playerHub.png");
        portrait = new Texture("portrait.png");
        shapeRenderer = new ShapeRenderer();
        projectionMatrixSet = false;
        region1 = new TextureRegion(img, 0, 0, 144, 80);
        region2 = new TextureRegion(rectangle1, 0, 0, 300, 82);
        demoPlayer = new DemoPlayer();
        monsterManager = new MonsterManager(demoPlayer);
        cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        bg = new Texture("background.png");
        BackgroundMusic backgroundMusic = new BackgroundMusic(Paths.get("music/BackgroundTheLastSurvivors.mp3"));
        TiledMap tiledMap = new TmxMapLoader().load("example.tmx");
        OrthogonalTiledMapRenderer tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
    }

    @Override
    public void handleInput() {

    }

    @Override
    public void update(float dt) {
        handleInput();
        demoPlayer.update(dt);
        monsterManager.update(dt);
        if (demoPlayer.dead) {
            gsm.set(new EndState(gsm));
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(bg, cam.position.x - (cam.viewportWidth / 2), 0);
        demoPlayer.draw(sb);
        monsterManager.draw(sb);
        sb.draw(region2,10,0);
        sb.draw(portrait,25,27);
        sb.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(82, 53, (195f*demoPlayer.currentHealth)/demoPlayer.maxHealth, 10);
        shapeRenderer.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.SKY);
        shapeRenderer.rect(82, 34, 100, 10);
        shapeRenderer.end();
    }

    @Override
    public void dispose() {
        MonsterFactory.dispose();
    }

}