package com.polibudaprojects.thelastsurvivors.monsters.phases;

import com.polibudaprojects.thelastsurvivors.monsters.types.MawFlower;
import com.polibudaprojects.thelastsurvivors.monsters.types.Type;

public class EasyPhase extends Phase {

    public EasyPhase() {
        super(
                30,
                1000L
        );
    }

    @Override
    protected Class<? extends Type> getSpawnedMonsterType() {
        return MawFlower.class;
    }
}
