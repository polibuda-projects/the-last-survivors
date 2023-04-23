package com.polibudaprojects.thelastsurvivors.XP;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.polibudaprojects.thelastsurvivors.Player.DemoPlayer;

public class XP {
    private float timer;
    private final Sprite sprite;
    private Vector2 velocity;
    private final Vector2 position;
    private final DemoPlayer player;

    private boolean taken = false;

    public XP(DemoPlayer demoPlayer, Vector2 position) {
        Texture img = new Texture("experience.png");
        TextureRegion xp = new TextureRegion(img, 0, 174, 87, 89);
        sprite = new Sprite(xp);
        sprite.setSize(32f, 35f);
        this.position = position;
        this.player = demoPlayer;
    }

    public void update(float deltaTime, Vector2 playerPosition) {
        timer += deltaTime;
        int seconds = (int) timer;
        if (seconds >= 17f) {
            taken = true;
        }
        updateVelocity(playerPosition);
        if (position.dst(playerPosition) < 80) {
            position.mulAdd(velocity, deltaTime);
        }
        if (canReach(playerPosition)) {
            addXP(player);
        }
    }

    private void updateVelocity(Vector2 playerPosition) {
        Vector2 direction = new Vector2(
                playerPosition.x - position.x - sprite.getWidth() / 2f,
                playerPosition.y - position.y - sprite.getHeight() / 2f
        );
        velocity = direction.nor().scl(200f);
    }

    public void addXP(DemoPlayer player) {
        if (canReach(player.getCenterPosition())) {
            player.setScore(player.getScore() + 10);
            taken = true;
        }
    }


    public void draw(SpriteBatch batch) {
        sprite.setPosition(position.x, position.y);
        sprite.draw(batch);
    }


    public boolean canReach(Vector2 playerPosition) {
        return sprite.getBoundingRectangle().contains(playerPosition);
    }

    public boolean isTaken() {
        return taken;
    }
}

