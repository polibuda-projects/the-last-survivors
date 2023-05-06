package com.polibudaprojects.thelastsurvivors.weapons;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Bullet {
    private final Vector2 position;
    private final Vector2 velocity;
    private final Sprite sprite;

    public Bullet(Vector2 position, Vector2 velocity, Sprite sprite) {
        this.position = position;
        this.velocity = velocity;
        this.sprite = sprite;
    }

    public void draw(SpriteBatch sb) {
        sprite.draw(sb);
    }

    public void update(float dt) {
        position.mulAdd(velocity, dt);
        sprite.setPosition(position.x, position.y);
    }

    public Rectangle getHitbox() {
        return sprite.getBoundingRectangle();
    }
}
