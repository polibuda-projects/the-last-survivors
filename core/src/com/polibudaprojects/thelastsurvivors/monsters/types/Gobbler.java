package com.polibudaprojects.thelastsurvivors.monsters.types;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.polibudaprojects.thelastsurvivors.items.Item;
import com.polibudaprojects.thelastsurvivors.items.XP;

public class Gobbler extends Type {

    public Gobbler() {
        super(
                "monsters/gobbler.atlas",
                "Gobbler",
                new Rectangle(20, 0, 70, 60),
                90f,
                35f,
                10,
                25
        );
    }

    @Override
    public Item dropItem(Vector2 position) {
        if (Math.random() > 0.2) {
            return XP.getCommonXP(position);
        }
        return null;
    }
}
