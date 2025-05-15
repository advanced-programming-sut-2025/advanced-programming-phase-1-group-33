package com.yourgame.model.Map;


import java.awt.*;

import com.yourgame.model.enums.SymbolType;

public class GreenHouse implements Placeable {
    private boolean isBroken;
    private final Rectangle bounds;
    public GreenHouse(int x, int y, int width, int height) {
        bounds = new Rectangle(x, y, width, height);
    }
    public boolean isBroken() {
        return isBroken;
    }
    public Rectangle getBounds() {
        return bounds;
    }
    public void setBroken(boolean broken) {
        isBroken = broken;
    }
    public SymbolType getSymbol() {
        return SymbolType.GreenHouse;
    }
}
