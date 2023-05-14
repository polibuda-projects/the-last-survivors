package com.polibudaprojects.thelastsurvivors.items;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.polibudaprojects.thelastsurvivors.Assets;
import com.polibudaprojects.thelastsurvivors.Player.Player;

public class XP extends Item {

    private final int value;

    protected XP(Sprite sprite, Animation<TextureRegion> animation, Vector2 position, int value) {
        super(sprite, animation, position);
        this.value = value;
    }

    public static Item getCommonXP(Vector2 position) {
        TextureAtlas textureAtlas = Assets.get("items/experience.atlas", TextureAtlas.class);
        Animation<TextureRegion> animation = new Animation<>(0.08f, textureAtlas.findRegions("common"), Animation.PlayMode.LOOP);
        Sprite sprite = new Sprite(animation.getKeyFrame(0));
        sprite.setSize(20f, 27f);
        return new XP(sprite, animation, position, 10);
    }

    public static Item getRareXP(Vector2 position) {
        TextureAtlas textureAtlas = Assets.get("items/experience.atlas", TextureAtlas.class);
        Animation<TextureRegion> animation = new Animation<>(0.08f, textureAtlas.findRegions("rare"), Animation.PlayMode.LOOP);
        Sprite sprite = new Sprite(animation.getKeyFrame(0));
        sprite.setSize(20f, 27f);
        return new XP(sprite, animation, position, 30);
    }

    // TODO This is only dropped by Grim Reaper and should be replaced with TreasureChest Item to make it interesting
    public static Item getEpicXP(Vector2 position) {
        TextureAtlas textureAtlas = Assets.get("items/experience.atlas", TextureAtlas.class);
        Animation<TextureRegion> animation = new Animation<>(0.08f, textureAtlas.findRegions("epic"), Animation.PlayMode.LOOP);
        Sprite sprite = new Sprite(animation.getKeyFrame(0));
        sprite.setSize(40f, 54f);
        return new XP(sprite, animation, position, 100);
    }

    @Override
    public void takeItem(Player player) {
        if(player.getLevel()<12){
            player.setScore(player.getScore() + value);
        }
    }
}

