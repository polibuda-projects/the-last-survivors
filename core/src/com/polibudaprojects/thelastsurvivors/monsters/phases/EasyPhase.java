package com.polibudaprojects.thelastsurvivors.monsters.phases;

import com.polibudaprojects.thelastsurvivors.monsters.types.MawFlower;

public class EasyPhase extends Phase {

    public EasyPhase() {
        super(
                60f,
                800L,
                15,
                0.2f
        );
    }

    @Override
    protected TypeAndCount getMonsterTypeAndCountToSpawn() {
        return TypeAndCount.of(MawFlower.class, 5 + rand.nextInt(5));
    }
}
