package com.yourgame.model.Stores;

import com.yourgame.model.Placeable;
import com.yourgame.model.App;

import java.awt.*;

public abstract class Store implements Placeable {

    protected final Rectangle bounds;
    protected final String shopAssistantName;
    protected final int startHour;
    protected final int endHour;

    public Store(Rectangle bounds, String shopAssistantName, int startHour, int endHour) {
        this.bounds = bounds;
        this.shopAssistantName = shopAssistantName;
        this.startHour = startHour;
        this.endHour = endHour;
        loadInventory();
    }

    public String getShopAssistantName() {
        return shopAssistantName;
    }

    public int getEndHour() {
        return endHour;
    }

    public int getStartHour() {
        return startHour;
    }

    public Rectangle getBounds() {
        return bounds;
    }


    public boolean isOpen() {
        return App.getGameState().getGameTime().getHour() >= startHour && App.getGameState().getGameTime().getHour() <= endHour;
    }

    public abstract char getSymbol();

    public void loadInventory() {

    }
}
