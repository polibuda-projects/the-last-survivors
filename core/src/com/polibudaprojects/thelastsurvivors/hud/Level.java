package com.polibudaprojects.thelastsurvivors.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.polibudaprojects.thelastsurvivors.Assets;
import com.polibudaprojects.thelastsurvivors.Player.Player;

public class Level implements HUD {
    private final Player player;
    private final Vector2 position = new Vector2();

    Texture playerLevelPng = Assets.get("hub/level.png", Texture.class);

    private final TextureRegion playerLevel;
    public Level(Player player) {
        this.player = player;
        playerLevel = new TextureRegion(playerLevelPng, 0, 0, 40, 55);
    }

    @Override
    public void updatePosition(float cameraX, float cameraY) {
        if (player.getLevel() == 1) {
            position.set(cameraX - Gdx.graphics.getWidth() / 2f + (1.7f * playerLevel.getRegionWidth()), cameraY + Gdx.graphics.getHeight() / 2f - (15.62f * playerLevel.getRegionHeight()));
        } else if (player.getLevel() == 6 || player.getLevel() == 9) {
            position.set(cameraX - Gdx.graphics.getWidth() / 2f + (playerLevel.getRegionWidth()), cameraY + Gdx.graphics.getHeight() / 2f - (15.6f * playerLevel.getRegionHeight()));
        } else if (player.getLevel() == 2) {
            position.set(cameraX - Gdx.graphics.getWidth() / 2f + (1.17f * playerLevel.getRegionWidth()), cameraY + Gdx.graphics.getHeight() / 2f - (15.6f * playerLevel.getRegionHeight()));
        } else if (player.getLevel() == 3) {
            position.set(cameraX - Gdx.graphics.getWidth() / 2f + (1.15f * playerLevel.getRegionWidth()), cameraY + Gdx.graphics.getHeight() / 2f - (15.62f * playerLevel.getRegionHeight()));
        } else if (player.getLevel() == 5) {
            position.set(cameraX - Gdx.graphics.getWidth() / 2f + (1.15f * playerLevel.getRegionWidth()), cameraY + Gdx.graphics.getHeight() / 2f - (15.65f * playerLevel.getRegionHeight()));
        } else if (player.getLevel() == 4) {
            position.set(cameraX - Gdx.graphics.getWidth() / 2f + (1.08f * playerLevel.getRegionWidth()), cameraY + Gdx.graphics.getHeight() / 2f - (15.6f * playerLevel.getRegionHeight()));
        } else if (player.getLevel() == 7) {
            position.set(cameraX - Gdx.graphics.getWidth() / 2f + (1.18f * playerLevel.getRegionWidth()), cameraY + Gdx.graphics.getHeight() / 2f - (15.68f * playerLevel.getRegionHeight()));
        } else if (player.getLevel() == 8) {
            position.set(cameraX - Gdx.graphics.getWidth() / 2f + (1.17f * playerLevel.getRegionWidth()), cameraY + Gdx.graphics.getHeight() / 2f - (15.62f * playerLevel.getRegionHeight()));
        } else if (player.getLevel() == 10) {
            position.set(cameraX - Gdx.graphics.getWidth() / 2f + (1.2f * playerLevel.getRegionWidth()), cameraY + Gdx.graphics.getHeight() / 2f - (15.62f * playerLevel.getRegionHeight()));
        } else if (player.getLevel() == 11) {
            position.set(cameraX - Gdx.graphics.getWidth() / 2f + (2.15f * playerLevel.getRegionWidth()), cameraY + Gdx.graphics.getHeight() / 2f - (15.62f * playerLevel.getRegionHeight()));
        }else if (player.getLevel() == 12) {
            position.set(cameraX - Gdx.graphics.getWidth() / 2f + (1.45f * playerLevel.getRegionWidth()), cameraY + Gdx.graphics.getHeight() / 2f - (15.62f * playerLevel.getRegionHeight()));
        }
    }
    @Override
    public void update(float dt) {
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.begin();
        switch (player.getLevel()) {
            case 1:
                playerLevel.setRegion(new TextureRegion(playerLevelPng, 16, 18, 20, 38));
                batch.draw(playerLevel, position.x, position.y);
                break;
            case 2:
                playerLevel.setRegion(new TextureRegion(playerLevelPng, 46, 18, 28, 38));
                batch.draw(playerLevel, position.x, position.y);
                break;
            case 3:
                playerLevel.setRegion(new TextureRegion(playerLevelPng, 82, 18, 28, 38));
                batch.draw(playerLevel, position.x, position.y);
                break;
            case 4:
                playerLevel.setRegion(new TextureRegion(playerLevelPng, 116, 18, 28, 38));
                batch.draw(playerLevel, position.x, position.y);
                break;
            case 5:
                playerLevel.setRegion(new TextureRegion(playerLevelPng, 152, 18, 28, 38));
                batch.draw(playerLevel, position.x, position.y);
                break;
            case 6:
                playerLevel.setRegion(new TextureRegion(playerLevelPng, 12, 70, 32, 38));
                batch.draw(playerLevel, position.x, position.y);
                break;
            case 7:
                playerLevel.setRegion(new TextureRegion(playerLevelPng, 48, 70, 28, 38));
                batch.draw(playerLevel, position.x, position.y);
                break;
            case 8:
                playerLevel.setRegion(new TextureRegion(playerLevelPng, 81, 70, 28, 38));
                batch.draw(playerLevel, position.x, position.y);
                break;
            case 9:
                playerLevel.setRegion(new TextureRegion(playerLevelPng, 116, 70, 32, 38));
                batch.draw(playerLevel, position.x, position.y);
                break;
            case 10:
                playerLevel.setRegion(new TextureRegion(playerLevelPng, 16, 18, 20, 38));
                batch.draw(playerLevel, position.x-20f, position.y);
                playerLevel.setRegion(new TextureRegion(playerLevelPng, 155, 70, 33, 38));
                batch.draw(playerLevel, position.x, position.y);
                break;
            case 11:
                playerLevel.setRegion(new TextureRegion(playerLevelPng, 16, 18, 20, 38));
                batch.draw(playerLevel, position.x-20f, position.y);
                playerLevel.setRegion(new TextureRegion(playerLevelPng, 16, 18, 20, 38));
                batch.draw(playerLevel, position.x, position.y);
                break;
            case 12:
                playerLevel.setRegion(new TextureRegion(playerLevelPng, 16, 18, 20, 38));
                batch.draw(playerLevel, position.x-22f, position.y);
                playerLevel.setRegion(new TextureRegion(playerLevelPng, 46, 18, 28, 38));
                batch.draw(playerLevel, position.x, position.y);
                break;
        }
        batch.end();

    }
}
