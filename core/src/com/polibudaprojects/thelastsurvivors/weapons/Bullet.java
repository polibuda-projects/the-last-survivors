package com.polibudaprojects.thelastsurvivors.weapons;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Bullet {
    private final Vector2 position;
    private final Sprite sprite;
    private final Rectangle hitbox;
    private final int speedX;
    private final int speedY;

    public Bullet(Vector2 position, Sprite sprite, int speedX, int speedY) {
        this.position = position;
        this.sprite = sprite;
        hitbox = new Rectangle(position.x, position.y, 47, 33);

        this.speedX = speedX;
        this.speedY = speedY;
    }

    public void draw(SpriteBatch sb) {
        sprite.setPosition(position.x, position.y);
        sprite.draw(sb);
    }

    public void update(float dt) {
        position.x += dt * speedX;
        position.y += dt * speedY;
        hitbox.setPosition(position);
    }

    public Rectangle getHitbox() {
        return hitbox;
    }
}
