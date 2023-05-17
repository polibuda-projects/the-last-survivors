package com.polibudaprojects.thelastsurvivors.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.polibudaprojects.thelastsurvivors.player.Player;
import com.polibudaprojects.thelastsurvivors.player.Statistics;
import com.polibudaprojects.thelastsurvivors.weapons.Weapon;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.polibudaprojects.thelastsurvivors.GameMain.SCREEN_HEIGHT;

public class WeaponsList implements HUD {

    public static final float FRAME_SIZE = 32f;
    public static final float ICON_SIZE = 30f;
    private static final float MARGIN = (FRAME_SIZE - ICON_SIZE) / 2;
    private final Statistics statistics = Statistics.getInstance();
    private final ShapeRenderer shapeRenderer = new ShapeRenderer();
    private final Vector2 position = new Vector2(0, SCREEN_HEIGHT - FRAME_SIZE);
    private List<Texture> icons = new ArrayList<>();

    @Override
    public void update(float dt) {
        icons = statistics.getWeapons().stream()
                .map(Weapon::getIcon)
                .collect(Collectors.toList());
    }

    @Override
    public void draw(SpriteBatch batch) {
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
