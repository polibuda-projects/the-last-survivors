package com.polibudaprojects.thelastsurvivors.map;

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
    private float zoomLevel;
    private CollisionDetector collisionDetector;

    public InfiniteTiledMap(String mapFileName, int gridWidth, int gridHeight, float zoomLevel) {
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.zoomLevel = zoomLevel;
        renderers = new TiledMapRenderer[gridWidth][gridHeight];
        maps = new TiledMap[gridWidth][gridHeight];

        TmxMapLoader loader = new TmxMapLoader();
        TiledMap tiledMap = loader.load(mapFileName);
        collisionDetector = new CollisionDetector(tiledMap);

        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                maps[i][j] = tiledMap;
                renderers[i][j] = new OrthogonalTiledMapRenderer(maps[i][j]);
                mapWidth = (int) maps[i][j].getProperties().get("width", Integer.class) * (int) maps[i][j].getProperties().get("tilewidth", Integer.class);
                mapHeight = (int) maps[i][j].getProperties().get("height", Integer.class) * (int) maps[i][j].getProperties().get("tileheight", Integer.class);
            }
        }
    }

    public TiledMap getTiledMap() {
        return maps[0][0];
    }

    public void update(OrthographicCamera cam, float playerX, float playerY) {
        int centerX = (int) (playerX / mapWidth) % gridWidth;
        int centerY = (int) (playerY / mapHeight) % gridHeight;

        cam.position.set(centerX * mapWidth + playerX % mapWidth, centerY * mapHeight + playerY % mapHeight, 0);
        cam.update();
    }

    public void render(OrthographicCamera cam) {
        OrthographicCamera mapCamera = new OrthographicCamera(cam.viewportWidth, cam.viewportHeight);
        mapCamera.zoom = zoomLevel;
        float adjustedX = (cam.position.x - (cam.viewportWidth / 2)) * zoomLevel;
        float adjustedY = (cam.position.y - (cam.viewportHeight / 2)) * zoomLevel;
        mapCamera.position.set(adjustedX, adjustedY, 0);
        mapCamera.update();

        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                renderers[i][j].setView(mapCamera);
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
