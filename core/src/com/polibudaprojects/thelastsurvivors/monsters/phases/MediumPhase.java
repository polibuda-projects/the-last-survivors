package com.polibudaprojects.thelastsurvivors.monsters.phases;

import com.polibudaprojects.thelastsurvivors.monsters.types.Gobbler;
import com.polibudaprojects.thelastsurvivors.monsters.types.MawFlower;
import com.polibudaprojects.thelastsurvivors.monsters.types.Scarecrow;
import com.polibudaprojects.thelastsurvivors.monsters.types.Type;

public class MediumPhase extends Phase {

    public MediumPhase() {
        super(
                30,
                500L
        );
    }

    @Override
    protected Class<? extends Type> getSpawnedMonsterType() {
        switch (rand.nextInt(4)) {
            case 0:
                return Scarecrow.class;
            case 1:
                return Gobbler.class;
            default:
                return MawFlower.class;
        }
    }
}
