package com.polibudaprojects.thelastsurvivors.monsters.types;

import com.badlogic.gdx.graphics.Texture;

public class Scarecrow extends Type {

    public Scarecrow() {
        super(
                new Texture("monster2.png"),
                "Scarecrow",
                100f,
                40f,
                10,
                30
        );
    }
}
