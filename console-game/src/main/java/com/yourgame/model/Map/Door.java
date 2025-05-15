package com.yourgame.model.Map;


import java.awt.*;

import com.yourgame.model.enums.SymbolType;

public class Door implements Placeable {
    private final Rectangle doorPosition;
    public Door(int x, int y , int width, int height) {
        this.doorPosition = new Rectangle(x, y,width, height);
    }
    public Rectangle getBounds() {
        return doorPosition;
    }
    public SymbolType getSymbol() {
        return SymbolType.WalkableDoor;
    }
}
