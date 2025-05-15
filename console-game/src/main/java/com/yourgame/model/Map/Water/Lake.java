package com.yourgame.model.Map.Water;


import com.yourgame.model.Map.Placeable;

import java.awt.*;

public class Lake extends WaterBody implements Placeable {
    private final Rectangle bounds;
    public Lake(int x, int y, int width, int height) {
        this.bounds = new Rectangle(x, y, width, height);

    }
    public Rectangle getBounds() {
        return bounds;
    }
    public char getSymbol() {
        return 'L';
    }
}
