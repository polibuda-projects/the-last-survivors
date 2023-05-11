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

public class WeaponsList {

    public static final float SIZE = 32f;
    private final ShapeRenderer shapeRenderer = new ShapeRenderer();
    private final Player player;
    private final Vector2 position = new Vector2();
    private List<Texture> icons = new ArrayList<>();

    public WeaponsList(Player player) {
        this.player = player;
    }

    public void updatePosition(float x, float y) {
        position.set(x - Gdx.graphics.getWidth() / 2f, y + Gdx.graphics.getHeight() / 2f - SIZE);
    }

    public void update(float dt) {
        icons = player.getWeapons().stream().map(Weapon::getIcon).collect(Collectors.toList());
    }

    public void render(SpriteBatch batch) {
        batch.begin();
        for (int i = 0; i < icons.size(); i++) {
            batch.draw(icons.get(i), position.x + SIZE * i, position.y, SIZE, SIZE);
        }
        batch.end();
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.DARK_GRAY);
        for (int i = 0; i < Player.MAX_WEAPONS_COUNT; i++) {
            shapeRenderer.rect(position.x + SIZE * i, position.y, SIZE, SIZE);
        }
        shapeRenderer.end();
    }
}
