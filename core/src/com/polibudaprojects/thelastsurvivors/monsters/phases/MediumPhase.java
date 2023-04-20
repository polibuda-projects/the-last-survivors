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
                return TypeAndCount.of(Scarecrow.class, rand.nextInt(1, 4));
            case 1:
                return TypeAndCount.of(Gobbler.class, rand.nextInt(3, 6));
            default:
                return TypeAndCount.of(MawFlower.class, rand.nextInt(5, 15));
        }
    }
}
