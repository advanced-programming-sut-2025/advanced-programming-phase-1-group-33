package com.yourgame.model.Resource;

public enum ResourceType {
    Stone("Stone",2),
    Fiber("Fiber",1),
    Wood("Wood",2);

    private final String name;
    private final int value;

    ResourceType(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }
}
