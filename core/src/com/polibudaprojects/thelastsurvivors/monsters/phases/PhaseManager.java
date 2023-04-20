package com.polibudaprojects.thelastsurvivors.monsters.phases;

import com.badlogic.gdx.math.Vector2;
import com.polibudaprojects.thelastsurvivors.monsters.Monster;

import java.util.ArrayList;
import java.util.List;

public class PhaseManager {

    private final List<Phase> phases = new ArrayList<>();
    private float phaseTimer = 0f;
    private int currentPhase = 0;

    public PhaseManager() {
        phases.add(new EasyPhase());
        phases.add(new MediumPhase());
        phases.add(new HardPhase());
        phases.add(new InfinitePhase());
    }

    public void update(float deltaTime) {
        phaseTimer += deltaTime;
        if (phases.get(currentPhase).hasPhaseEnded(phaseTimer)) {
            startNextPhase();
            phaseTimer = 0f;
        }
    }

    public boolean shouldSpawn() {
        return phases.get(currentPhase).shouldSpawn();
    }

    public List<Monster> getSpawnedMonsters(Vector2 playerPosition) {
        return phases.get(currentPhase).getSpawnedMonsters(playerPosition);
    }

    private void startNextPhase() {
        if (currentPhase < phases.size() - 1) {
            currentPhase++;
        }
    }
}
