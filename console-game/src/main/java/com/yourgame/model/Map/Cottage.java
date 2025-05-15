package com.yourgame.model.Map;


import com.yourgame.model.Placeable;
import java.awt.*;

public class Cottage implements Placeable {
    private final Rectangle bounds;
    public Cottage(int x, int y, int width, int height) {
        bounds = new Rectangle(x, y, width, height);
    }
    public Rectangle getBounds() {
        return bounds;
    }
    public char getSymbol() {
        return 'C';
    }

}
