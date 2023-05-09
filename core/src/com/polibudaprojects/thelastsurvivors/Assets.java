package com.polibudaprojects.thelastsurvivors;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class Assets {
    // This static resource could potentially cause problems on Android
    @SuppressWarnings("GDXJavaStaticResource")
    private static final AssetManager assetManager = new AssetManager();

    public static void load() {
        assetManager.setLoader(TiledMap.class, new TmxMapLoader());

        assetManager.load("player.png", Texture.class);
        assetManager.load("playerHub.png", Texture.class);
        assetManager.load("portrait.png", Texture.class);
        assetManager.load("intro.png", Texture.class);
        assetManager.load("button.png", Texture.class);
        assetManager.load("background.png", Texture.class);
        assetManager.load("gameOver.png", Texture.class);
        assetManager.load("startAgain.png", Texture.class);
        assetManager.load("fireBullet.png", Texture.class);

        assetManager.load("monsters/maw-flower.atlas", TextureAtlas.class);
        assetManager.load("monsters/gobbler.atlas", TextureAtlas.class);
        assetManager.load("monsters/scarecrow.atlas", TextureAtlas.class);
        assetManager.load("monsters/naga.atlas", TextureAtlas.class);
        assetManager.load("items/experience.atlas", TextureAtlas.class);

        assetManager.load("music/BackgroundTheLastSurvivors.mp3", Music.class);
        assetManager.load("music/brains.wav", Sound.class);
        assetManager.load("music/groan.wav", Sound.class);
        assetManager.load("music/rar.wav", Sound.class);

        assetManager.load("map/game-dev.tmx", TiledMap.class);
    }

    public static void finishLoading() {
        assetManager.finishLoading();
    }

    public static <T> T get(String fileName, Class<T> type) {
        return assetManager.get(fileName, type);
    }

    public static void dispose() {
        assetManager.dispose();
    }
}
