package com.polibudaprojects.thelastsurvivors.hud;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface HUD {
    void updatePosition(float cameraX, float cameraY);

    void update(float dt);

    void render(SpriteBatch batch);
}
