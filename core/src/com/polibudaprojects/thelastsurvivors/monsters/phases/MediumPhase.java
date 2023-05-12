package com.polibudaprojects.thelastsurvivors.monsters.phases;

import com.polibudaprojects.thelastsurvivors.monsters.types.Gobbler;
import com.polibudaprojects.thelastsurvivors.monsters.types.MawFlower;
import com.polibudaprojects.thelastsurvivors.monsters.types.Scarecrow;

public class MediumPhase extends Phase {

    public MediumPhase() {
        super(
                180f,
                600L,
                50,
                0.5f
        );
    }

    @Override
    protected TypeAndCount getMonsterTypeAndCountToSpawn() {
        switch (rand.nextInt(5)) {
            case 0:
                return TypeAndCount.of(Scarecrow.class, 1 + rand.nextInt(4));
            case 1:
                return TypeAndCount.of(Gobbler.class, 5 + rand.nextInt(10));
            default:
                return TypeAndCount.of(MawFlower.class, 10 + rand.nextInt(20));
        }
    }
}
