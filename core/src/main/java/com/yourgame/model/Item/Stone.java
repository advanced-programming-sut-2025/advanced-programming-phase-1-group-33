package com.yourgame.model.Item;

import com.yourgame.model.ManuFactor.Ingredient;
import com.yourgame.model.Map.Placeable;
import com.yourgame.model.enums.SymbolType;

import java.awt.*;

public class Stone implements Ingredient, Placeable {
    private Rectangle bounds;

    public Stone() {

    }

    public Stone(int x, int y) {
        this.bounds = new Rectangle(x, y, 1, 1);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public SymbolType getSymbol() {
        return SymbolType.StoneItem;
    }

    @Override
    public int hashCode() {
        return 2;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Stone;
    }
}
