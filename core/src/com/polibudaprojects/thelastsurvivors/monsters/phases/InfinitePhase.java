package com.polibudaprojects.thelastsurvivors.monsters.phases;

import com.polibudaprojects.thelastsurvivors.monsters.types.Gobbler;
import com.polibudaprojects.thelastsurvivors.monsters.types.MawFlower;
import com.polibudaprojects.thelastsurvivors.monsters.types.Naga;
import com.polibudaprojects.thelastsurvivors.monsters.types.Scarecrow;

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
    protected TypeAndCount getMonsterTypeAndCountToSpawn() {
        switch (rand.nextInt(4)) {
            case 0:
                return TypeAndCount.of(Naga.class, rand.nextInt(5, 10));
            case 1:
                return TypeAndCount.of(Scarecrow.class, rand.nextInt(5, 10));
            case 2:
                return TypeAndCount.of(Gobbler.class, rand.nextInt(5, 10));
            default:
                return TypeAndCount.of(MawFlower.class, rand.nextInt(5, 10));
        }
    }
}
