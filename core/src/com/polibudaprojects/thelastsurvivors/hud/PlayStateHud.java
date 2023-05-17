package com.polibudaprojects.thelastsurvivors.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.polibudaprojects.thelastsurvivors.assets.Assets;
import com.polibudaprojects.thelastsurvivors.player.Player;

import java.util.ArrayList;
import java.util.List;

import static com.polibudaprojects.thelastsurvivors.GameMain.SCREEN_HEIGHT;
import static com.polibudaprojects.thelastsurvivors.GameMain.SCREEN_WIDTH;

public class PlayStateHud implements HUD {

    private final List<HUD> hudElements = new ArrayList<>();
    private final OrthographicCamera cam = new OrthographicCamera();
    private final ShapeRenderer shapeRenderer = new ShapeRenderer();
    private final TextureRegion playerStats;
    private final GameTimer gameTimer;
    private final Player player;

    public PlayStateHud(Player player) {
        this.player = player;
        cam.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);

        Texture playerStatsPng = Assets.get("hub/playerHub.png", Texture.class);
        playerStats = new TextureRegion(playerStatsPng, 0, 0, 300, 82);

        gameTimer = new GameTimer();
        hudElements.add(gameTimer);
        hudElements.add(new Level());
        hudElements.add(new WeaponsList());
    }

    public GameTimer getGameTimer() {
        return gameTimer;
    }

    @Override
    public void update(float dt) {
        hudElements.forEach(hud -> hud.update(dt));
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.setProjectionMatrix(cam.combined);
        batch.begin();
        batch.draw(playerStats, 10, 0);
        batch.end();
        hudElements.forEach(hud -> hud.draw(batch));

        shapeRenderer.setProjectionMatrix(cam.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED); //HP BAR
        shapeRenderer.rect(82, 53, (195f * player.getCurrentHealth()) / player.getMaxHealth(), 10);
        shapeRenderer.setColor(Color.SKY); //XP BAR
        shapeRenderer.rect(82, 34, (195f * player.getScore()) / player.getMaxScore(), 10);
        shapeRenderer.end();
    }
}
