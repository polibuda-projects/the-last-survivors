package com.polibudaprojects.thelastsurvivors.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.polibudaprojects.thelastsurvivors.Player.DemoPlayer;

public class XP extends Item {

    private static final Texture texture = new Texture("experience.png");
    private final int value;

    protected XP(Sprite sprite, Vector2 position, int value) {
        super(sprite, position);
        this.value = value;
    }

    public static Item getCommonXP(Vector2 position) {
        TextureRegion xp = new TextureRegion(texture, 0, 174, 87, 89);
        Sprite sprite = new Sprite(xp);
        sprite.setSize(30f, 30f);
        return new XP(sprite, position, 10);
    }

    public static Item getRareXP(Vector2 position) {
        TextureRegion xp = new TextureRegion(texture, 0, 0, 87, 89);
        Sprite sprite = new Sprite(xp);
        sprite.setSize(30f, 30f);
        return new XP(sprite, position, 30);
    }

    public static void dispose() {
        texture.dispose();
    }

    @Override
    public void takeItem(DemoPlayer player) {
        player.setScore(player.getScore() + value);
    }
}

