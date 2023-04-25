package com.polibudaprojects.thelastsurvivors.monsters;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.polibudaprojects.thelastsurvivors.Player.DemoPlayer;
import com.polibudaprojects.thelastsurvivors.XP.XP;
import com.polibudaprojects.thelastsurvivors.monsters.phases.PhaseManager;
import com.polibudaprojects.thelastsurvivors.weapons.Weapon;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class MonsterManager {

    private final List<Monster> monsters = new ArrayList<>();
    private final List<XP> xps = new ArrayList<>();
    private final PhaseManager phaseManager;
    private final DemoPlayer player;

    public MonsterManager(DemoPlayer player) {
        this.player = player;
        this.phaseManager = new PhaseManager(player);
    }

    public void reset() {
        monsters.clear();
        xps.clear();
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
            if (shouldBeRemovedWithXP(monster)) {
                XP xp = new XP(player, monster.getPosition());
                xps.add(xp);
                iter.remove();
                continue;
            }
            if (shouldBeRemovedWithOutXP(monster)) {
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

    private boolean shouldBeRemovedWithXP(Monster monster) {
        return monster.isDeathAnimationFinished();
    }

    private boolean shouldBeRemovedWithOutXP(Monster monster) {
        return monster.hasExceededMaxDistance(player.getPosition());
    }

    public List<XP> getXps() {
        return xps;
    }
}
