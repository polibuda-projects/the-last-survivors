package com.polibudaprojects.thelastsurvivors.monsters.types;

import com.badlogic.gdx.math.Vector2;
import com.polibudaprojects.thelastsurvivors.items.Item;
import com.polibudaprojects.thelastsurvivors.items.XP;

public class GrimReaper extends Type {

    public GrimReaper() {
        super(
                "monsters/grim-reaper.atlas",
                "Grim Reaper",
                250f,
                50f,
                100,
                2000,
                true
        );
    }

    @Override
    public Item dropItem(Vector2 position) {
        return XP.getRareXP(position);
    }
}
