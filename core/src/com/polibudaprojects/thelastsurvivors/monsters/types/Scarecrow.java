package com.polibudaprojects.thelastsurvivors.monsters.types;

import com.badlogic.gdx.math.Vector2;
import com.polibudaprojects.thelastsurvivors.items.Item;
import com.polibudaprojects.thelastsurvivors.items.XP;

public class Scarecrow extends Type {

    public Scarecrow() {
        super(
                "monsters/scarecrow.atlas",
                "Scarecrow",
                90f,
                50f,
                15,
                75
        );
    }

    @Override
    public Item dropItem(Vector2 position) {
        if (Math.random() > 0.5) {
            return XP.getRareXP(position);
        }
        return XP.getCommonXP(position);
    }
}
