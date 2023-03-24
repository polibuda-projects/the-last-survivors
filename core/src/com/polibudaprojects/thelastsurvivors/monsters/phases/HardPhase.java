package com.polibudaprojects.thelastsurvivors.monsters.phases;

import com.polibudaprojects.thelastsurvivors.monsters.types.Naga;
import com.polibudaprojects.thelastsurvivors.monsters.types.Scarecrow;
import com.polibudaprojects.thelastsurvivors.monsters.types.Type;

public class HardPhase extends Phase {

    public HardPhase() {
        super(
                60,
                300L
        );
    }

    @Override
    protected Class<? extends Type> getSpawnedMonsterType() {
        if (rand.nextInt(3) == 0) {
            return Naga.class;
        }
        return Scarecrow.class;
    }
}
