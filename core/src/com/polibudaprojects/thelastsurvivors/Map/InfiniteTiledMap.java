package com.polibudaprojects.thelastsurvivors.Map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class InfiniteTiledMap {
    private TiledMapRenderer[][] renderers;
    private TiledMap[][] maps;
    private int mapWidth;
    private int mapHeight;
    private int gridWidth;
    private int gridHeight;

    public InfiniteTiledMap(String mapFileName, int gridWidth, int gridHeight) {
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        renderers = new TiledMapRenderer[gridWidth][gridHeight];
        maps = new TiledMap[gridWidth][gridHeight];

        TmxMapLoader loader = new TmxMapLoader();

        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                maps[i][j] = loader.load(mapFileName);
                renderers[i][j] = new OrthogonalTiledMapRenderer(maps[i][j]);
                mapWidth = (int) maps[i][j].getProperties().get("width", Integer.class) * (int) maps[i][j].getProperties().get("tilewidth", Integer.class);
                mapHeight = (int) maps[i][j].getProperties().get("height", Integer.class) * (int) maps[i][j].getProperties().get("tileheight", Integer.class);
            }
        }
    }

    public void update(OrthographicCamera cam, float playerX, float playerY) {
        int centerX = (int) (playerX / mapWidth) % gridWidth;
        int centerY = (int) (playerY / mapHeight) % gridHeight;

        cam.position.set(centerX * mapWidth + playerX % mapWidth, centerY * mapHeight + playerY % mapHeight, 0);
        cam.update();
    }

    public void render(OrthographicCamera cam) {
        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                renderers[i][j].setView(cam);
                renderers[i][j].render();
            }
        }
    }

    public void dispose() {
        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                maps[i][j].dispose();
            }
        }
    }
}
