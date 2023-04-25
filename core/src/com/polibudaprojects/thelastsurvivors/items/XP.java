package com.polibudaprojects.thelastsurvivors.items;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.polibudaprojects.thelastsurvivors.Player.DemoPlayer;

public class XP extends Item {

    private static final TextureAtlas TEXTURE_ATLAS = new TextureAtlas("items/experience.atlas");
    private final int value;

    protected XP(Sprite sprite, Animation<TextureRegion> animation, Vector2 position, int value) {
        super(sprite, animation, position);
        this.value = value;
    }

    public static Item getCommonXP(Vector2 position) {
        Animation<TextureRegion> animation = new Animation<>(0.08f, TEXTURE_ATLAS.findRegions("common"), Animation.PlayMode.LOOP);
        Sprite sprite = new Sprite(animation.getKeyFrame(0));
        sprite.setSize(20f, 27f);
        return new XP(sprite, animation, position, 10);
    }

    public static Item getRareXP(Vector2 position) {
        Animation<TextureRegion> animation = new Animation<>(0.08f, TEXTURE_ATLAS.findRegions("rare"), Animation.PlayMode.LOOP);
        Sprite sprite = new Sprite(animation.getKeyFrame(0));
        sprite.setSize(20f, 27f);
        return new XP(sprite, animation, position, 30);
    }

    public static void dispose() {
        TEXTURE_ATLAS.dispose();
    }

    @Override
    public void takeItem(DemoPlayer player) {
        player.setScore(player.getScore() + value);
    }
}

