package com.polibudaprojects.thelastsurvivors.monsters.phases;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.polibudaprojects.thelastsurvivors.monsters.Monster;
import com.polibudaprojects.thelastsurvivors.monsters.MonsterFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Phase {

    public static final int SPAWN_RADIUS_A_MIN = Gdx.graphics.getWidth() / 2 + 80;
    public static final int SPAWN_RADIUS_B_MIN = Gdx.graphics.getHeight() / 2 + 80;
    public static final int SPAWN_RADIUS_A_MAX = SPAWN_RADIUS_A_MIN + 150;
    public static final int SPAWN_RADIUS_B_MAX = SPAWN_RADIUS_B_MIN + 150;
    protected final Random rand = new Random();
    protected final float duration;
    protected final long spawnInterval;
    protected final int initialMonstersLimit;
    protected final float limitGrowRate;
    protected long lastSpawnTime;

    public Phase(float duration, long spawnInterval, int initialMonstersLimit, float limitGrowRate) {
        this.duration = duration;
        this.spawnInterval = spawnInterval;
        this.initialMonstersLimit = initialMonstersLimit;
        this.limitGrowRate = limitGrowRate;
    }

    public boolean hasPhaseEnded(float timePassed) {
        return timePassed > duration;
    }

    public int getMonstersLimit(float timePassed) {
        return initialMonstersLimit + (int) (limitGrowRate * timePassed);
    }

    public boolean shouldSpawn() {
        return TimeUtils.millis() - lastSpawnTime > spawnInterval;
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
            int radiusA = SPAWN_RADIUS_A_MIN + rand.nextInt(SPAWN_RADIUS_A_MAX - SPAWN_RADIUS_A_MIN);
            int radiusB = SPAWN_RADIUS_B_MIN + rand.nextInt(SPAWN_RADIUS_B_MAX - SPAWN_RADIUS_B_MIN);
            spawnPositions[i] = new Vector2(
                    (float) (playerPosition.x + radiusA * Math.cos(angle)),
                    (float) (playerPosition.y + radiusB * Math.sin(angle))
            );
        }
        return spawnPositions;
    }
}
