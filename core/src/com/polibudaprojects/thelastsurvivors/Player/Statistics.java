package com.polibudaprojects.thelastsurvivors.Player;

public class Statistics {

    private static volatile Statistics instance;
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
