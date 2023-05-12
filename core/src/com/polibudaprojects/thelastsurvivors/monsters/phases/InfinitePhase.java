package com.polibudaprojects.thelastsurvivors.monsters.phases;

import com.polibudaprojects.thelastsurvivors.monsters.types.Gobbler;
import com.polibudaprojects.thelastsurvivors.monsters.types.MawFlower;
import com.polibudaprojects.thelastsurvivors.monsters.types.Naga;
import com.polibudaprojects.thelastsurvivors.monsters.types.Scarecrow;

public class InfinitePhase extends Phase {

    public InfinitePhase() {
        super(
                0f,
                200L,
                300,
                0.5f
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
                return TypeAndCount.of(Naga.class, 5 + rand.nextInt(10));
            case 1:
                return TypeAndCount.of(Scarecrow.class, 5 + rand.nextInt(10));
            case 2:
                return TypeAndCount.of(Gobbler.class, 5 + rand.nextInt(10));
            default:
                return TypeAndCount.of(MawFlower.class, 5 + rand.nextInt(10));
        }
    }
}
