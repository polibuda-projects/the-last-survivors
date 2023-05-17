package com.polibudaprojects.thelastsurvivors.monsters;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.polibudaprojects.thelastsurvivors.items.ItemManager;
import com.polibudaprojects.thelastsurvivors.monsters.phases.PhaseManager;
import com.polibudaprojects.thelastsurvivors.player.Player;
import com.polibudaprojects.thelastsurvivors.weapons.Weapon;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class MonsterManager {

    private final List<Monster> monsters = new ArrayList<>();
    private final PhaseManager phaseManager;
    private final ItemManager itemManager;
    private final Player player;

    public MonsterManager(Player player, ItemManager itemManager) {
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
        Vector2 playerCenterPosition = player.getCenterPosition();

        while (iter.hasNext()) {
            Monster monster = iter.next();
            if (monster.shouldBeRemoved(playerCenterPosition)) {
                itemManager.addItem(monster.tryToDropItem());
                iter.remove();
                continue;
            }

            monster.update(monsters, playerCenterPosition, deltaTime);

            if (monster.canAttack(playerCenterPosition)) {
                monster.attack(player);
            }

            for (Weapon weapon : player.getWeapons()) {
                if (weapon.canAttack(monster)) {
                    monster.takeDamage(weapon.getDamage());
                }
            }
        }
    }
}
