package com.polibudaprojects.thelastsurvivors.player;

import com.polibudaprojects.thelastsurvivors.weapons.Weapon;

import java.util.ArrayList;

public class Statistics {

    private static volatile Statistics instance;
    private Player player;
    private int monstersKilled;
    private int totalDamage;
    private float timeLeft;

    private Statistics() {
    }

    public static Statistics getInstance() {
        if (instance == null) {
            synchronized (Statistics.class) {
                if (instance == null) {
                    instance = new Statistics();
                }
            }
        }
        return instance;
    }

    public void reset() {
        monstersKilled = 0;
        totalDamage = 0;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public ArrayList<Weapon> getWeapons() {
        return player.getWeapons();
    }

    public int getScore() {
        return player.getScore();
    }

    public int getMaxHealth() {
        return player.getMaxHealth();
    }

    public int getCurrentHealth() {
        return player.getCurrentHealth();
    }

    public int getHpRegen() {
        return player.getHpRegen();
    }

    public int getMaxScore() {
        return player.getMaxScore();
    }

    public int getLevel() {
        return player.getLevel();
    }

    public int getMonstersKilled() {
        return monstersKilled;
    }

    public void addToKilledMonsters(int monstersKilled) {
        this.monstersKilled += monstersKilled;
    }

    public int getTotalDamage() {
        return totalDamage;
    }

    public void addToTotalDamage(int totalDamage) {
        this.totalDamage += totalDamage;
    }

    public float getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(float timeLeft) {
        this.timeLeft = timeLeft;
    }
}
