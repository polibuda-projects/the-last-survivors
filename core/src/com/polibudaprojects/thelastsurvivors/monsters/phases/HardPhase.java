package com.polibudaprojects.thelastsurvivors.monsters.phases;

import com.polibudaprojects.thelastsurvivors.monsters.types.Naga;
import com.polibudaprojects.thelastsurvivors.monsters.types.Scarecrow;

public class HardPhase extends Phase {

    public HardPhase() {
        super(
                60f,
                400L,
                50
        );
    }

    @Override
    protected TypeAndCount getMonsterTypeAndCountToSpawn() {
        if (rand.nextInt(3) == 0) {
            return TypeAndCount.of(Naga.class, 1 + rand.nextInt(4));
        }
        return TypeAndCount.of(Scarecrow.class, 5 + rand.nextInt(10));
    }
}
