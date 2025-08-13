package com.yourgame.model.Map.Store;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
<<<<<<< HEAD:core/src/main/java/com/yourgame/model/Map/Store.java
import com.badlogic.gdx.math.Rectangle;
import com.yourgame.model.UserInfo.Player;

public class Store extends Map {
    private final MapObjects tradeZoneObjects;
=======
import com.yourgame.model.App;
import com.yourgame.model.Map.Map;
import com.yourgame.model.UserInfo.Player;

public class Store extends Map {
    private final MapObjects tradeObjects;
    protected final int startHour;
    protected final int endHour;
>>>>>>> shop:core/src/main/java/com/yourgame/model/Map/Store/Store.java


    public Store(String name, String pathToTmx, int startHour, int endHour) {
        super(name, pathToTmx);
<<<<<<< HEAD:core/src/main/java/com/yourgame/model/Map/Store.java
        this.tradeZoneObjects = tiledMap.getLayers().get("Trade").getObjects();
=======
        this.tradeObjects = tiledMap.getLayers().get("Trade").getObjects();
        this.startHour = startHour;
        this.endHour = endHour;
>>>>>>> shop:core/src/main/java/com/yourgame/model/Map/Store/Store.java
    }

    public boolean isPlayerInTradeZone(Player player) {
        for (MapObject object : tradeZoneObjects) {
            if (object instanceof RectangleMapObject rectObj && overLaps(rectObj.getRectangle(), player)) {
                return true;
            }
        }
        return false;
    }
<<<<<<< HEAD:core/src/main/java/com/yourgame/model/Map/Store.java

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
=======
    
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
>>>>>>> shop:core/src/main/java/com/yourgame/model/Map/Store/Store.java
}
