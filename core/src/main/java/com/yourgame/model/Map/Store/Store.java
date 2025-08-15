package com.yourgame.model.Map.Store;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.yourgame.model.App;
import com.yourgame.model.Map.Map;
import com.yourgame.model.UserInfo.Player;
import com.badlogic.gdx.math.Rectangle;

public class Store extends Map {
    private MapObjects tradeZoneObjects;
    protected final int startHour;
    protected final int endHour;

    public Store(String name, String pathToTmx, int startHour, int endHour) {
        super(name, pathToTmx);
        this.tradeZoneObjects = tiledMap.getLayers().get("Trade").getObjects();
        this.startHour = startHour;
        this.endHour = endHour;
    }

    public boolean isPlayerInTradeZone(Player player) {
        for (MapObject object : tradeZoneObjects) {
            if (object instanceof RectangleMapObject rectObj && overLaps(rectObj.getRectangle(), player)) {
                return true;
            }
        }
        return false;
    }

    public boolean isPlayerInBuyZone(Player player) {
        MapObject object = tradeZoneObjects.get("buy");
        if (object instanceof RectangleMapObject rectObj) {
            return overLaps(rectObj.getRectangle(), player);
        }
        return false;
    }

    public boolean isPlayerInSellZone(Player player) {
        MapObject object = tradeZoneObjects.get("sell");
        if (object instanceof RectangleMapObject rectObj) {
            return overLaps(rectObj.getRectangle(), player);
        }
        return false;
    }

    private boolean overLaps(Rectangle rectangle, Player player) {
        float x = rectangle.x;
        float y = rectangle.y;
        float width = rectangle.width;
        float height = rectangle.height;

        return (player.playerPosition.x >= x && player.playerPosition.x <= x + width
             && player.playerPosition.y >= y && player.playerPosition.y <= y + height);
    }

    public int getEndHour() {
        return endHour;
    }

    public int getStartHour() {
        return startHour;
    }

    public boolean isOpen() {
        return App.getGameState().getGameTime().getHour() >= startHour && App.getGameState().getGameTime().getHour() <= endHour;
    }

    public void loadInventory() {}
}
