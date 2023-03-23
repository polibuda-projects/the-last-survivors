package com.polibudaprojects.thelastsurvivors.monsters.phases;

import com.polibudaprojects.thelastsurvivors.monsters.types.MawFlower;
import com.polibudaprojects.thelastsurvivors.monsters.types.Naga;
import com.polibudaprojects.thelastsurvivors.monsters.types.Scarecrow;
import com.polibudaprojects.thelastsurvivors.monsters.types.Type;

public class InfinitePhase extends Phase {

    public InfinitePhase() {
        super(
                0,
                100000000L
        );
    }

    @Override
    public boolean hasPhaseEnded(float timePassed) {
        return false;
    }

    @Override
    protected Class<? extends Type> getSpawnedMonsterType() {
        switch (rand.nextInt(3)) {
            case 0:
                return Naga.class;
            case 1:
                return Scarecrow.class;
            default:
                return MawFlower.class;
        }
    }
}
