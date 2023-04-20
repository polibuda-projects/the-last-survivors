package com.polibudaprojects.thelastsurvivors.monsters.phases;

import com.polibudaprojects.thelastsurvivors.monsters.types.MawFlower;

public class EasyPhase extends Phase {

    public EasyPhase() {
        super(
                30,
                1500L
        );
    }

    @Override
    protected TypeAndCount getMonsterTypeAndCountToSpawn() {
        return TypeAndCount.of(MawFlower.class, 5 + rand.nextInt(5));
    }
}
