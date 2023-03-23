package com.polibudaprojects.thelastsurvivors.monsters.phases;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.polibudaprojects.thelastsurvivors.monsters.Monster;
import com.polibudaprojects.thelastsurvivors.monsters.MonsterFactory;
import com.polibudaprojects.thelastsurvivors.monsters.types.Type;

import java.util.Random;

import static com.polibudaprojects.thelastsurvivors.monsters.MonsterSpawner.SPAWN_RADIUS;

public abstract class Phase {

    protected final Random rand = new Random();
    protected final float duration;
    protected final long interval;
    protected long lastSpawnTime = 0;

    public Phase(float duration, long interval) {
        this.duration = duration;
        this.interval = interval;
    }

    public boolean hasPhaseEnded(float timePassed) {
        return timePassed > duration;
    }

    public boolean shouldSpawn() {
        return TimeUtils.nanoTime() - lastSpawnTime > interval;
    }

    public Monster spawn(Vector2 playerPosition) {
        lastSpawnTime = TimeUtils.nanoTime();
        return MonsterFactory.getMonster(
                getSpawnedMonsterType(),
                getSpawnPosition(playerPosition)
        ).orElseThrow(() -> new RuntimeException("Can not spawn monster"));
    }

    protected abstract Class<? extends Type> getSpawnedMonsterType();

    private Vector2 getSpawnPosition(Vector2 playerPosition) {
        double randomAngle = rand.nextDouble() * 2. * Math.PI;
        float x = (float) (playerPosition.x + SPAWN_RADIUS * Math.cos(randomAngle));
        float y = (float) (playerPosition.y + SPAWN_RADIUS * Math.sin(randomAngle));
        return new Vector2(x, y);
    }
}
