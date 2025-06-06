package com.yourgame.model.Map;


import com.yourgame.model.Item.ForagingMineral;
import com.yourgame.model.enums.SymbolType;

import java.awt.*;
import java.util.ArrayList;

public class Quarry implements Placeable {
    private final Rectangle bounds;
    private final ArrayList<ForagingMineral> foragingMinerals = new ArrayList<>();
    public Quarry(int x, int y, int width, int height) {
        bounds = new Rectangle(x, y, width, height);

    }
    public Rectangle getBounds() {
        return bounds;
    }
    public SymbolType getSymbol() {
        return SymbolType.Quarry;
    }
    public ArrayList<ForagingMineral> getForagingMinerals() {
        return foragingMinerals;
    }
    public void addForagingMineral(ForagingMineral foragingMineral) {
        foragingMinerals.add(foragingMineral);
    }
}
