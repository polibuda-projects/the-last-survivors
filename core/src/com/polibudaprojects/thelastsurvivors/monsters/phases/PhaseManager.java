package com.polibudaprojects.thelastsurvivors.monsters.phases;

import com.polibudaprojects.thelastsurvivors.Player.DemoPlayer;
import com.polibudaprojects.thelastsurvivors.monsters.Monster;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PhaseManager {

    private final DemoPlayer player;
    private final List<Phase> phases = new ArrayList<>();
    private float phaseTimer = 0f;
    private int currentPhase = 0;

    public PhaseManager(DemoPlayer player) {
        this.player = player;
        phases.add(new EasyPhase());
        phases.add(new MediumPhase());
        phases.add(new HardPhase());
        phases.add(new InfinitePhase());
    }

    public void reset() {
        phaseTimer = 0f;
        currentPhase = 0;
    }

    public void update(float deltaTime) {
        phaseTimer += deltaTime;
        if (getCurrentPhase().hasPhaseEnded(phaseTimer)) {
            startNextPhase();
            phaseTimer = 0f;
        }
    }

    public boolean shouldSpawn() {
        return getCurrentPhase().shouldSpawn();
    }

    public void spawnMonsters(List<Monster> monsters) {
        List<Monster> spawnedMonsters = getSpawnedMonsters();
        int numberOfMonstersToRemove = Math.min(
                monsters.size() + spawnedMonsters.size() - getCurrentPhase().getMonstersLimit(phaseTimer),
                spawnedMonsters.size()
        );

        if (numberOfMonstersToRemove > 0) {
            Collections.shuffle(spawnedMonsters);
            spawnedMonsters.subList(0, numberOfMonstersToRemove).clear();
        }

        monsters.addAll(spawnedMonsters);
    }

    private Phase getCurrentPhase() {
        return phases.get(currentPhase);
    }

    private List<Monster> getSpawnedMonsters() {
        return getCurrentPhase().getSpawnedMonsters(player.getPosition());
    }

    private void startNextPhase() {
        if (currentPhase < phases.size() - 1) {
            currentPhase++;
        }
    }
}
