package com.polibudaprojects.thelastsurvivors.monsters;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.polibudaprojects.thelastsurvivors.monsters.types.Type;

public class Monster {

    private final Type type;
    private final Vector2 position;
    private final Sprite sprite;
    private int health;
    private boolean dead = false;

    public Monster(Type type, Vector2 position) {
        this.type = type;
        this.position = position;
        this.sprite = type.getNewSprite();
        this.health = type.getMaxHealth();
    }

    public void update(float deltaTime, Vector2 playerPosition) {
        //TODO znaleźć lepszy sposób, aby potwór poruszał sie dokładnie w lini prostej do gracza
        if (position.y < playerPosition.y) {
            position.y += deltaTime * type.getSpeed();
        }
        if (position.y > playerPosition.y) {
            position.y -= deltaTime * type.getSpeed();
        }
        if (position.x < playerPosition.x) {
            position.x += deltaTime * type.getSpeed();
            sprite.setFlip(false, false);
        }
        if (position.x > playerPosition.x) {
            position.x -= deltaTime * type.getSpeed();
            sprite.setFlip(true, false);
        }
    }

    public void draw(SpriteBatch batch) {
        sprite.setPosition(position.x, position.y);
        sprite.draw(batch);
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            dead = true;
        }
    }

    public boolean isDead() {
        return dead;
    }
}
