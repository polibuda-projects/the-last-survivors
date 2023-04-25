package com.polibudaprojects.thelastsurvivors.items;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.polibudaprojects.thelastsurvivors.Player.DemoPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class ItemManager {

    private final List<Item> items = new ArrayList<>();
    private final DemoPlayer player;

    public ItemManager(DemoPlayer player) {
        this.player = player;
    }

    public void reset() {
        items.clear();
    }

    public void addItem(Item item) {
        if (item != null) {
            items.add(item);
        }
    }

    public void draw(SpriteBatch batch) {
        for (Item items : items) {
            items.draw(batch);
        }
    }

    public void update(float deltaTime) {
        ListIterator<Item> iter = items.listIterator();
        while (iter.hasNext()) {
            Item item = iter.next();
            if (item.hasExceedLifetime()) {
                iter.remove();
                continue;
            }

            if (item.canBeTaken(player.getCenterPosition())) {
                item.takeItem(player);
                iter.remove();
                continue;
            }
            item.update(deltaTime, player.getCenterPosition());
        }
    }
}
