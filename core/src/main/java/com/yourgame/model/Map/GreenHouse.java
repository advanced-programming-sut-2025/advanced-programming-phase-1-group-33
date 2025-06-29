package com.yourgame.model.Map;


import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

import com.yourgame.model.App;
import com.yourgame.model.Item.Crop;
import com.yourgame.model.Item.Growable;
import com.yourgame.model.Item.Tree;
import com.yourgame.model.enums.SymbolType;

public class GreenHouse implements Placeable {
    private boolean isBroken = true;
    private final Rectangle bounds;
    private ArrayList<Growable> growables = new ArrayList<>();
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
        if(isBroken){
            return SymbolType.BrokenGreenHouse;
        }
        return SymbolType.GreenHouse;
    }


    public void watering(){
        if(!isBroken()) {
            for (Growable growable : growables) {
                growable.watering();

            }
        }
    }
    public void harvestGrowable(){
        if(!isBroken()) {
            Iterator<Growable> iterator = growables.iterator();
            while (iterator.hasNext()) {
                Growable growable = iterator.next();
                if (growable.harvest()) {
                    growables.removeIf(c -> c == growable);
                    if (growable instanceof Tree tree) {
                        int numberOfWoods = tree.getCurrentStage();
                        App.getGameState().getCurrentPlayer().getBackpack().addIngredients(new Wood(), numberOfWoods);
                    } else if (growable instanceof Crop crop) {
                        App.getGameState().getCurrentPlayer().getBackpack().addIngredients(crop, 1);
                    }
                }
            }
        }
    }
    public ArrayList<Growable> getGrowables() {
        return growables;
    }
}
