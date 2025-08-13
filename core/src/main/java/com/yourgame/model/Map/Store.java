package com.yourgame.model.Map;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.yourgame.model.UserInfo.Player;

public class Store extends Map {
    private final MapObjects tradeZoneObjects;

    public Store(String name, String pathToTmx) {
        super(name, pathToTmx);
        this.tradeZoneObjects = tiledMap.getLayers().get("Trade").getObjects();
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
}
