package com.polibudaprojects.thelastsurvivors.map;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class CollisionDetector {
    private final TiledMapTileLayer obstacleLayer;

    public CollisionDetector(TiledMap tiledMap) {
        obstacleLayer = (TiledMapTileLayer) tiledMap.getLayers().get("obstacle");
    }

    public boolean isCellBlocked(float x, float y) {
        int tileWidth = obstacleLayer.getTileWidth();
        int tileHeight = obstacleLayer.getTileHeight();
        int cellX = (int) (x / tileWidth);
        int cellY = (int) (y / tileHeight);

        TiledMapTileLayer.Cell cell = obstacleLayer.getCell(cellX, cellY);
        return cell != null;
    }
}
