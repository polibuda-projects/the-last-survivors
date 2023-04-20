package com.polibudaprojects.thelastsurvivors.monsters;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.polibudaprojects.thelastsurvivors.Player.DemoPlayer;
import com.polibudaprojects.thelastsurvivors.monsters.phases.*;
import com.polibudaprojects.thelastsurvivors.weapons.Weapon;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class MonsterManager {

    private final List<Monster> monsters = new ArrayList<>();
    private final PhaseManager phaseManager = new PhaseManager();
    private final DemoPlayer player;

    public MonsterManager(DemoPlayer player) {
        this.player = player;
    }

    public void draw(SpriteBatch batch) {
        for (Monster monster : monsters) {
            monster.draw(batch);
        }
    }

    public void update(float deltaTime) {
        updateMonsters(deltaTime);
        phaseManager.update(deltaTime);

        if(phaseManager.shouldSpawn()){
            monsters.addAll(phaseManager.getSpawnedMonsters(player.getPosition()));
        }
    }

    private void updateMonsters(float deltaTime) {
        ListIterator<Monster> iter = monsters.listIterator();
        while (iter.hasNext()) {
            Monster monster = iter.next();
            if (shouldBeRemoved(monster)) {
                iter.remove();
                continue;
            }
            monster.update(deltaTime, player.getCenterPosition());
            monster.attackIfPossible(player);

            for (Weapon weapon : player.getWeapons()) {
                if (Intersector.overlaps(monster.getBoundingRectangle(), weapon.getHitbox())) {
                    if (weapon.canAttack()) {
                        monster.takeDamage(weapon.getDamage(), weapon);
                    }
                }
            }
        }
    }

    private boolean shouldBeRemoved(Monster monster) {
        return monster.isDeathAnimationFinished() || monster.hasExceededMaxDistance(player.getPosition());
    }
}
