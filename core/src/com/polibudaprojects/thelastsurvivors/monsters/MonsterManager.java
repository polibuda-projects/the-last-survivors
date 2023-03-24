package com.polibudaprojects.thelastsurvivors.monsters;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.polibudaprojects.thelastsurvivors.DemoPlayer;
import com.polibudaprojects.thelastsurvivors.monsters.phases.*;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class MonsterManager {

    public static final float SPAWN_RADIUS = 400f;
    private final List<Monster> monsters = new ArrayList<>();
    private final List<Phase> phases = new ArrayList<>();
    private final DemoPlayer player;
    private float phaseTimer = 0f;
    private int currentPhase = 0;

    public MonsterManager(DemoPlayer player) {
        this.player = player;
        phases.add(new EasyPhase());
        phases.add(new MediumPhase());
        phases.add(new HardPhase());
        phases.add(new InfinitePhase());
    }

    public void draw(SpriteBatch batch) {
        for (Monster monster : monsters) {
            monster.draw(batch);
        }
    }

    public void update(float deltaTime) {
        updateMonsters(deltaTime);
        updatePhase();
        phaseTimer += deltaTime;
    }

    private void updateMonsters(float deltaTime) {
        ListIterator<Monster> iter = monsters.listIterator();
        while (iter.hasNext()) {
            Monster monster = iter.next();
            monster.update(deltaTime, player.getPosition());
            if (monster.isDead()) {
                iter.remove();
            }
            monster.attackIfPossible(player);
        }
    }

    private void updatePhase() {
        if (getCurrentPhase().hasPhaseEnded(phaseTimer)) {
            startNextPhase();
            phaseTimer = 0f;
        }

        if (getCurrentPhase().shouldSpawn()) {
            spawn();
        }
    }

    private void spawn() {
        Monster spawnedMonster = getCurrentPhase().spawn(player.getPosition());
        monsters.add(spawnedMonster);
    }

    private Phase getCurrentPhase() {
        return phases.get(currentPhase);
    }

    private void startNextPhase() {
        if (currentPhase < phases.size() - 1) {
            currentPhase++;
        }
    }
}
