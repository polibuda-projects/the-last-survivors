package com.polibudaprojects.thelastsurvivors.monsters;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.polibudaprojects.thelastsurvivors.DemoPlayer;
import com.polibudaprojects.thelastsurvivors.monsters.types.MawFlower;
import com.polibudaprojects.thelastsurvivors.monsters.types.Naga;
import com.polibudaprojects.thelastsurvivors.monsters.types.Scarecrow;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

public class MonsterSpawner {

    public static final float RADIUS = 400f;
    public static final long INTERVAL = 2000000000;
    private final List<Monster> monsters = new ArrayList<>();
    private final DemoPlayer player;
    private final Random rand = new Random();
    private long lastSpawnTime;

    public MonsterSpawner(DemoPlayer player) {
        this.player = player;
    }

    public void draw(SpriteBatch batch) {
        for (Monster monster : monsters) {
            monster.draw(batch);
        }
    }

    public void update(float deltaTime) {
        ListIterator<Monster> iter = monsters.listIterator();
        while (iter.hasNext()) {
            Monster monster = iter.next();
            monster.update(deltaTime, player.getPosition());
            if (monster.isDead()) {
                iter.remove();
            }
        }
        if (TimeUtils.nanoTime() - lastSpawnTime > INTERVAL) {
            spawn();
        }
    }

    private void spawn() {
        // TODO to będzie jeszcze ulepszone
        monsters.add(MonsterFactory.getMonster(
                MawFlower.class,
                getSpawnPosition()
        ).orElseThrow(() -> new RuntimeException("Can not spawn monster")));

        monsters.add(MonsterFactory.getMonster(
                Scarecrow.class,
                getSpawnPosition()
        ).orElseThrow(() -> new RuntimeException("Can not spawn monster")));

        monsters.add(MonsterFactory.getMonster(
                Naga.class,
                getSpawnPosition()
        ).orElseThrow(() -> new RuntimeException("Can not spawn monster")));
        lastSpawnTime = TimeUtils.nanoTime();
    }

    private Vector2 getSpawnPosition() {
        double randomAngle = rand.nextDouble() * 2. * Math.PI;
        float x = (float) (player.getPosition().x + RADIUS * Math.cos(randomAngle));
        float y = (float) (player.getPosition().y + RADIUS * Math.sin(randomAngle));
        return new Vector2(x, y);
    }
}
