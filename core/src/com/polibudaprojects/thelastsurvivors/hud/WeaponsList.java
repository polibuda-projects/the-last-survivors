package com.polibudaprojects.thelastsurvivors.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.polibudaprojects.thelastsurvivors.Player.Player;
import com.polibudaprojects.thelastsurvivors.weapons.Weapon;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WeaponsList implements HUD {

    public static final float FRAME_SIZE = 32f;
    public static final float ICON_SIZE = 30f;
    private static final float MARGIN = (FRAME_SIZE - ICON_SIZE) / 2;
    private final ShapeRenderer shapeRenderer = new ShapeRenderer();
    private final Player player;
    private final Vector2 position = new Vector2();
    private List<Texture> icons = new ArrayList<>();

    public WeaponsList(Player player) {
        this.player = player;
    }

    @Override
    public void updatePosition(float cameraX, float cameraY) {
        position.set(cameraX - Gdx.graphics.getWidth() / 2f, cameraY + Gdx.graphics.getHeight() / 2f - FRAME_SIZE);
    }

    @Override
    public void update(float dt) {
        icons = player.getWeapons().stream()
                .map(Weapon::getIcon)
                .collect(Collectors.toList());
    }

    @Override
    public void render(SpriteBatch batch) {
        renderIcons(batch);
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        for (int i = 0; i < Player.MAX_WEAPONS_COUNT; i++) {
            shapeRenderer.setColor(Color.DARK_GRAY);
            shapeRenderer.rect(position.x + FRAME_SIZE * i, position.y, FRAME_SIZE, FRAME_SIZE);
            shapeRenderer.rect(position.x + FRAME_SIZE * i + 2, position.y + 2, FRAME_SIZE - 4, FRAME_SIZE - 4);
            shapeRenderer.setColor(Color.GRAY);
            shapeRenderer.rect(position.x + FRAME_SIZE * i + 1, position.y + 1, FRAME_SIZE - 2, FRAME_SIZE - 2);
        }
        shapeRenderer.end();
    }

    private void renderIcons(SpriteBatch batch) {
        batch.begin();
        for (int i = 0; i < icons.size(); i++) {
            batch.draw(icons.get(i), position.x + MARGIN + FRAME_SIZE * i, position.y + MARGIN, ICON_SIZE, ICON_SIZE);
        }
        batch.end();
    }
}
