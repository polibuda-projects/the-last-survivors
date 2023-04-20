package com.polibudaprojects.thelastsurvivors.monsters.phases;

import com.polibudaprojects.thelastsurvivors.monsters.types.Type;

public class TypeAndCount {

    private final Class<? extends Type> type;
    private final int count;

    private TypeAndCount(Class<? extends Type> type, int count) {
        this.type = type;
        this.count = count;
    }

    public static TypeAndCount of(Class<? extends Type> type, int count) {
        return new TypeAndCount(type, count);
    }

    public Class<? extends Type> getType() {
        return type;
    }

    public int getCount() {
        return count;
    }
}
