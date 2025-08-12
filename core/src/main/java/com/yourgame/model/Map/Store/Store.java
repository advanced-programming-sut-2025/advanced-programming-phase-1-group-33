package com.yourgame.model.Map.Store;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.yourgame.model.App;
import com.yourgame.model.Map.Map;
import com.yourgame.model.UserInfo.Player;

public class Store extends Map {
    private final MapObjects tradeObjects;
    protected final int startHour;
    protected final int endHour;


    public Store(String name, String pathToTmx, int startHour, int endHour) {
        super(name, pathToTmx);
        this.tradeObjects = tiledMap.getLayers().get("Trade").getObjects();
        this.startHour = startHour;
        this.endHour = endHour;
    }

    public boolean isPlayerInTradeZone(Player player) {
        for (MapObject object : tradeObjects) {
            if (object instanceof RectangleMapObject rectObj) {
                float x = rectObj.getRectangle().x;
                float y = rectObj.getRectangle().y;
                float width = rectObj.getRectangle().width;
                float height = rectObj.getRectangle().height;

                if (player.playerPosition.x >= x && player.playerPosition.x <= x + width
                    && player.playerPosition.y >= y && player.playerPosition.y <= y + height) {
                    return true;
                }
            }
        }
        return false;
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
