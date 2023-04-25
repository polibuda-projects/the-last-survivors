package com.polibudaprojects.thelastsurvivors.monsters.types;

import com.badlogic.gdx.math.Vector2;
import com.polibudaprojects.thelastsurvivors.items.Item;
import com.polibudaprojects.thelastsurvivors.items.XP;

public class MawFlower extends Type {

    public MawFlower() {
        super(
                "assets/monsters/maw-flower.atlas",
                "Maw Flower",
                80f,
                30f,
                5,
                7
        );
    }

    @Override
    public Item dropItem(Vector2 position) {
        if(Math.random() > 0.5){
            return XP.getCommonXP(position);
        }
        return null;
    }
}
