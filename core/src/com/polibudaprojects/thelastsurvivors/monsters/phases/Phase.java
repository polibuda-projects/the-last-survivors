package com.polibudaprojects.thelastsurvivors.monsters.phases;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.polibudaprojects.thelastsurvivors.monsters.Monster;
import com.polibudaprojects.thelastsurvivors.monsters.MonsterFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Phase {

    public static final float SPAWN_RADIUS_MIN = 500f;
    public static final float SPAWN_RADIUS_MAX = 700f;
    protected final Random rand = new Random();
    protected final float duration;
    protected final long interval;
    protected long lastSpawnTime;

    public Phase(float duration, long interval) {
        this.duration = duration;
        this.interval = interval;
    }

    public boolean hasPhaseEnded(float timePassed) {
        return timePassed > duration;
    }

    public boolean shouldSpawn() {
        return TimeUtils.millis() - lastSpawnTime > interval;
    }

    public List<Monster> getSpawnedMonsters(Vector2 playerPosition) {
        List<Monster> spawnedMonsters = new ArrayList<>();
        TypeAndCount typeAndCount = getMonsterTypeAndCountToSpawn();
        Vector2[] spawnPositions = getSpawnPositions(playerPosition, typeAndCount.getCount());
        lastSpawnTime = TimeUtils.millis();

        for (Vector2 spawnPosition : spawnPositions) {
            spawnedMonsters.add(MonsterFactory.getMonster(
                    typeAndCount.getType(),
                    spawnPosition
            ).orElseThrow(() -> new RuntimeException("Can not spawn monster")));
        }
        return spawnedMonsters;
    }

    protected abstract TypeAndCount getMonsterTypeAndCountToSpawn();

    protected Vector2[] getSpawnPositions(Vector2 playerPosition, int monstersCount) {
        Vector2[] spawnPositions = new Vector2[monstersCount];
        double angleInterval = 2. * Math.PI / monstersCount;
        double randomAngle = rand.nextDouble();
        for (int i = 0; i < monstersCount; i++) {
            double angle = (randomAngle + i) * angleInterval;
            spawnPositions[i] = new Vector2(
                    (float) (playerPosition.x + rand.nextFloat(SPAWN_RADIUS_MIN, SPAWN_RADIUS_MAX) * Math.cos(angle)),
                    (float) (playerPosition.y + rand.nextFloat(SPAWN_RADIUS_MIN, SPAWN_RADIUS_MAX) * Math.sin(angle))
            );
        }
        return spawnPositions;
    }
}
