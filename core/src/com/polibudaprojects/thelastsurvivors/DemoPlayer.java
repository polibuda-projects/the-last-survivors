package com.polibudaprojects.thelastsurvivors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class DemoPlayer {
    public Vector2 position;
    public Sprite sprite;
    public float speed = 300f;

    public DemoPlayer(Texture img) {
        sprite = new Sprite(img);
        sprite.setSize(80f, 80f);
        position = new Vector2(
                (Gdx.graphics.getWidth() - sprite.getWidth()) / 2f,
                (Gdx.graphics.getHeight() - sprite.getHeight()) / 2f
        );
    }

    public void update(float deltaTime) {
        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
            position.y += deltaTime * speed;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            position.y -= deltaTime * speed;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            position.x -= deltaTime * speed;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            position.x += deltaTime * speed;
        }
    }

    public void draw(SpriteBatch batch) {
        update(Gdx.graphics.getDeltaTime());
        sprite.setPosition(position.x, position.y);
        sprite.draw(batch);
    }

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getCenterPosition() {
        float centerX = position.x + sprite.getWidth() / 2f;
        float centerY = position.y + sprite.getHeight() / 2f;
        return new Vector2(centerX, centerY);
    }
}
