package com.polibudaprojects.thelastsurvivors.monsters.phases;

import com.polibudaprojects.thelastsurvivors.monsters.types.Gobbler;
import com.polibudaprojects.thelastsurvivors.monsters.types.MawFlower;
import com.polibudaprojects.thelastsurvivors.monsters.types.Scarecrow;

public class MediumPhase extends Phase {

    public MediumPhase() {
        super(
                30,
                500L
        );
    }

    @Override
    protected TypeAndCount getMonsterTypeAndCountToSpawn() {
        switch (rand.nextInt(4)) {
            case 0:
                return TypeAndCount.of(Scarecrow.class, 1 + rand.nextInt(3));
            case 1:
                return TypeAndCount.of(Gobbler.class, 3 + rand.nextInt(3));
            default:
                return TypeAndCount.of(MawFlower.class, 5 + rand.nextInt(10));
        }
    }
}
