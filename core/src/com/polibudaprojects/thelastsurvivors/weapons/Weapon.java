package com.polibudaprojects.thelastsurvivors.weapons;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public interface Weapon {
    void draw(SpriteBatch sb);

    void update(float dt);

    Rectangle getHitbox();

    int getDamage();

    boolean canAttack();

    long getAttackInterval();
}
