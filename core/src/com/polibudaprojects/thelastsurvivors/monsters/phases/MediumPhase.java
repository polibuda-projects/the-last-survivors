package com.polibudaprojects.thelastsurvivors.monsters.phases;

import com.polibudaprojects.thelastsurvivors.monsters.types.MawFlower;
import com.polibudaprojects.thelastsurvivors.monsters.types.Scarecrow;
import com.polibudaprojects.thelastsurvivors.monsters.types.Type;

public class MediumPhase extends Phase {

    public MediumPhase() {
        super(
                30,
                500000000L
        );
    }

    @Override
    protected Class<? extends Type> getSpawnedMonsterType() {
        if (rand.nextInt(2) == 0) {
            return Scarecrow.class;
        }
        return MawFlower.class;
    }
}
