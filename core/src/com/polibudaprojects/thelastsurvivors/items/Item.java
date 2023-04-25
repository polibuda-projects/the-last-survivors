package com.polibudaprojects.thelastsurvivors.items;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.polibudaprojects.thelastsurvivors.Player.DemoPlayer;

public abstract class Item {
    public static final float LIFETIME = 20f;
    public static final float PULL_DISTANCE = 70f;
    public static final float PULL_SPEED = 300f;
    protected final Sprite sprite;
    protected final Vector2 position;
    protected Vector2 velocity;
    protected float timer;

    protected Item(Sprite sprite, Vector2 position) {
        this.sprite = sprite;
        this.position = position;
    }

    public abstract void takeItem(DemoPlayer player);

    public void update(float deltaTime, Vector2 playerPosition) {
        timer += deltaTime;
        if (position.dst(playerPosition) < PULL_DISTANCE) {
            updateVelocity(playerPosition);
            position.mulAdd(velocity, deltaTime);
        }
    }

    private void updateVelocity(Vector2 playerPosition) {
        Vector2 direction = new Vector2(
                playerPosition.x - position.x - sprite.getWidth() / 2f,
                playerPosition.y - position.y - sprite.getHeight() / 2f
        );
        velocity = direction.nor().scl(PULL_SPEED);
    }

    public void draw(SpriteBatch batch) {
        sprite.setPosition(position.x, position.y);
        sprite.draw(batch);
    }

    public boolean canBeTaken(Vector2 playerPosition) {
        return sprite.getBoundingRectangle().contains(playerPosition);
    }

    public boolean hasExceedLifetime() {
        return timer >= LIFETIME;
    }
}

