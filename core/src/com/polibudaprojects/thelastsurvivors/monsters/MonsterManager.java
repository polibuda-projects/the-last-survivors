package com.polibudaprojects.thelastsurvivors.monsters;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.polibudaprojects.thelastsurvivors.Player.DemoPlayer;
import com.polibudaprojects.thelastsurvivors.items.ItemManager;
import com.polibudaprojects.thelastsurvivors.monsters.phases.PhaseManager;
import com.polibudaprojects.thelastsurvivors.weapons.Weapon;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class MonsterManager {

    private final List<Monster> monsters = new ArrayList<>();
    private final PhaseManager phaseManager;
    private final ItemManager itemManager;
    private final DemoPlayer player;

    public MonsterManager(DemoPlayer player, ItemManager itemManager) {
        this.player = player;
        this.itemManager = itemManager;
        this.phaseManager = new PhaseManager(player);
    }

    public void reset() {
        monsters.clear();
        phaseManager.reset();
    }

    public void draw(SpriteBatch batch) {
        for (Monster monster : monsters) {
            monster.draw(batch);
        }
    }

    public void update(float deltaTime) {
        updateMonsters(deltaTime);
        phaseManager.update(deltaTime);

        if (phaseManager.shouldSpawn()) {
            phaseManager.spawnMonsters(monsters);
        }
    }

    private void updateMonsters(float deltaTime) {
        ListIterator<Monster> iter = monsters.listIterator();
        while (iter.hasNext()) {
            Monster monster = iter.next();
            if (shouldBeRemoved(monster)) {
                itemManager.addItem(monster.tryToDropItem());
                iter.remove();
                continue;
            }

            monster.update(deltaTime, player.getCenterPosition());
            monster.attackIfPossible(player);

            for (Weapon weapon : player.getWeapons()) {
                if (weapon.canAttack(monster)) {
                    monster.takeDamage(weapon.getDamage());
                }
            }
        }
    }

    private boolean shouldBeRemoved(Monster monster) {
        return monster.isDeathAnimationFinished() || monster.hasExceededMaxDistance(player.getPosition());
    }
}
