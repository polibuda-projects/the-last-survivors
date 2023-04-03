package com.polibudaprojects.thelastsurvivors.monsters.phases;

import com.polibudaprojects.thelastsurvivors.monsters.types.*;

public class InfinitePhase extends Phase {

    public InfinitePhase() {
        super(
                0,
                100L
        );
    }

    @Override
    public boolean hasPhaseEnded(float timePassed) {
        return false;
    }

    @Override
    protected Class<? extends Type> getSpawnedMonsterType() {
        switch (rand.nextInt(4)) {
            case 0:
                return Naga.class;
            case 1:
                return Scarecrow.class;
            case 2:
                return Gobbler.class;
            default:
                return MawFlower.class;
        }
    }
}
