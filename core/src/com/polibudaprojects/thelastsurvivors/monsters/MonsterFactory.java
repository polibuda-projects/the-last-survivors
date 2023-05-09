package com.polibudaprojects.thelastsurvivors.monsters;

import com.badlogic.gdx.math.Vector2;
import com.polibudaprojects.thelastsurvivors.monsters.types.Type;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MonsterFactory {

    private static final Map<Class<? extends Type>, Type> monsterTypes = new HashMap<>();

    public static <T extends Type> Optional<Monster> getMonster(Class<T> typeClass, Vector2 position) {
        Type type = monsterTypes.get(typeClass);
        if (type == null) {
            try {
                type = typeClass.getConstructor().newInstance();
                monsterTypes.put(typeClass, type);
            } catch (Exception e) {
                return Optional.empty();
            }
        }
        return Optional.of(new Monster(type, position));
    }
}
