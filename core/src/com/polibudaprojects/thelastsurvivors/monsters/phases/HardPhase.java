package com.polibudaprojects.thelastsurvivors.monsters.phases;

import com.polibudaprojects.thelastsurvivors.monsters.types.Naga;
import com.polibudaprojects.thelastsurvivors.monsters.types.Scarecrow;

public class HardPhase extends Phase {

    public HardPhase() {
        super(
                60,
                300L
        );
    }

    @Override
    protected TypeAndCount getMonsterTypeAndCountToSpawn() {
        if (rand.nextInt(3) == 0) {
            return TypeAndCount.of(Naga.class, rand.nextInt(1, 4));
        }
        return TypeAndCount.of(Scarecrow.class, rand.nextInt(5, 10));
    }
}
