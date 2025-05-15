package com.yourgame.model.Map;

import java.util.HashMap;

public enum Direction {
    UP, DOWN, LEFT, RIGHT,
    UP_RIGHT, UP_LEFT, DOWN_RIGHT, DOWN_LEFT;

    private static final HashMap<String, Direction> stringToDirection = new HashMap<>();

    static {
        for (Direction value : Direction.values()) {
            stringToDirection.put(value.name().toLowerCase(), value);
        }
    }

    public static Direction getDirectionByInput(String direction) {
        if (direction == null || direction.isEmpty())
            return null;
        return stringToDirection.getOrDefault(direction.toLowerCase(), null);
    }
}
