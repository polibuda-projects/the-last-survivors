package com.polibudaprojects.thelastsurvivors.weapons;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Bullet {
    private final Vector2 position;
    private final Vector2 velocity;
    private final Sprite sprite;
    private final Rectangle hitbox;

    public Bullet(Vector2 position, Vector2 velocity, Sprite sprite) {
        this.position = position;
        this.velocity = velocity;
        this.sprite = sprite;
        hitbox = new Rectangle(position.x, position.y, 47, 33);
    }

    public void draw(SpriteBatch sb) {
        sprite.setPosition(position.x, position.y);
        sprite.draw(sb);
    }

    public void update(float dt) {
        position.mulAdd(velocity, dt);
        hitbox.setPosition(position);
    }

    public Rectangle getHitbox() {
        return hitbox;
    }
}
