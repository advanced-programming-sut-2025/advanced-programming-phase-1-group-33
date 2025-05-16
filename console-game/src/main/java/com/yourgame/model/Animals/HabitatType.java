package com.yourgame.model.Animals;

public enum HabitatType {
    Barn(7, 4),
    Coop(6, 3);

    private final int lengthX;
    private final int lengthY;

    HabitatType(int lengthX, int lengthY) {
        this.lengthX = lengthX;
        this.lengthY = lengthY;
    }

    public int getLengthX() {
        return lengthX;
    }

    public int getLengthY() {
        return lengthY;
    }
}
