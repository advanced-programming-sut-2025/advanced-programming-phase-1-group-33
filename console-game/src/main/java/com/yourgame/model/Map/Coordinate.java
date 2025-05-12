package com.yourgame.model.Map;

public class Coordinate {
    private int x;
    private int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Getters
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    // Setters
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    // Override equals for proper comparisons
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Coordinate))
            return false;
        Coordinate other = (Coordinate) obj;
        return x == other.x && y == other.y;
    }

    // Override hashCode for use in hash-based collections
    @Override
    public int hashCode() {
        return 31 * x + y;
    }

    // Override toString for easy debugging and logging
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
