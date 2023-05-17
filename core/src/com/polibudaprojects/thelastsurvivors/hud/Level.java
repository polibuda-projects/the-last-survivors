package com.polibudaprojects.thelastsurvivors.hud;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.polibudaprojects.thelastsurvivors.assets.Assets;
import com.polibudaprojects.thelastsurvivors.player.Statistics;

public class Level implements HUD {
    private final Statistics statistics = Statistics.getInstance();
    private final Vector2 position = new Vector2(45, 28);
    private final TextureRegion[] digits = new TextureRegion[10];
    private final int width = 32;
    private final int height = 50;
    private int[] digitIndexes = new int[0];

    public Level() {
        Texture playerLevelPng = Assets.get("hub/level.png", Texture.class);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 2; j++) {
                int x = (i == 0) ? 3 : 36 * i;
                digits[i + 5 * j] = new TextureRegion(playerLevelPng, x, 50 * j, width, height);
            }
        }
    }

    @Override
    public void update(float dt) {
        String levelString = String.valueOf(statistics.getLevel());
        digitIndexes = new int[levelString.length()];
        for (int i = 0; i < digitIndexes.length; i++) {
            digitIndexes[i] = (Character.getNumericValue(levelString.charAt(i)) + 9) % 10;
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.begin();
        float scaleFactor = 0.7f;
        float startX = position.x - (digitIndexes.length * width * scaleFactor) / 2;
        for (int i = 0; i < digitIndexes.length; i++) {
            batch.draw(digits[digitIndexes[i]], startX + i * width * scaleFactor, position.y, width * scaleFactor, height * scaleFactor);
        }
        batch.end();
    }
}
