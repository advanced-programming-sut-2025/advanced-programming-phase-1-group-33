package com.yourgame.model.Map;


import com.yourgame.model.Item.ForagingMineral;

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
    public char getSymbol() {
        return 'Q';
    }
    public ArrayList<ForagingMineral> getForagingMinerals() {
        return foragingMinerals;
    }
    public void addForagingMineral(ForagingMineral foragingMineral) {
        foragingMinerals.add(foragingMineral);
    }
}
