package com.polibudaprojects.thelastsurvivors.monsters.types;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.polibudaprojects.thelastsurvivors.items.Item;
import com.polibudaprojects.thelastsurvivors.items.XP;

public class Naga extends Type {

    public Naga() {
        super(
                "monsters/naga.atlas",
                "Naga",
                new Rectangle(40, 0, 70, 100),
                110f,
                45f,
                30,
                200
        );
    }

    @Override
    public Item dropItem(Vector2 position) {
        return XP.getRareXP(position);
    }
}
