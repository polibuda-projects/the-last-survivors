package com.polibudaprojects.thelastsurvivors.weapons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.polibudaprojects.thelastsurvivors.monsters.Monster;

public interface Weapon {
    void draw(SpriteBatch sb);

    void update(float dt);

    int getDamage();

    boolean canAttack(Monster monster);

    Texture getIcon();
}
