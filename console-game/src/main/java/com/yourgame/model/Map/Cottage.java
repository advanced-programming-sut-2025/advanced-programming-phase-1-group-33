package com.yourgame.model.Map;


import java.awt.*;

import com.yourgame.model.enums.SymbolType;

public class Cottage implements Placeable {
    private final Rectangle bounds;
    public Cottage(int x, int y, int width, int height) {
        bounds = new Rectangle(x, y, width, height);
    }
    public Rectangle getBounds() {
        return bounds;
    }
    public SymbolType getSymbol() {
        return SymbolType.Cottage;
    }

}
