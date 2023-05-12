package com.polibudaprojects.thelastsurvivors.monsters.phases;

import com.polibudaprojects.thelastsurvivors.monsters.types.GrimReaper;
import com.polibudaprojects.thelastsurvivors.monsters.types.Naga;
import com.polibudaprojects.thelastsurvivors.monsters.types.Scarecrow;

public class HardPhase extends Phase {

    private boolean wasBossSpawned = false;

    public HardPhase() {
        super(
                180f,
                400L,
                150,
                0.5f
        );
    }

    @Override
    protected TypeAndCount getMonsterTypeAndCountToSpawn() {
        if (!wasBossSpawned) {
            wasBossSpawned = true;
            return TypeAndCount.of(GrimReaper.class, 1);
        }
        if (rand.nextInt(3) == 0) {
            return TypeAndCount.of(Naga.class, 2 + rand.nextInt(8));
        }
        return TypeAndCount.of(Scarecrow.class, 15 + rand.nextInt(20));
    }
}
