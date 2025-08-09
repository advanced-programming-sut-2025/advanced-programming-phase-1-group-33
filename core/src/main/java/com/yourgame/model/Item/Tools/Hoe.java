package com.yourgame.model.Item.Tools;

import com.yourgame.model.Map.Farm;
import com.yourgame.model.Map.Map;
import com.yourgame.model.Map.Tile;
import com.yourgame.model.UserInfo.Player;
import com.yourgame.model.WeatherAndTime.Weather;

public class Hoe extends Tool {
    public Hoe() {
        super(ToolType.Hoe, 4);
    }

    @Override
    public int getConsumptionEnergy(Player player, Weather weather, boolean success) {
        return (int) ((5 - level) * weather.energyCoefficient);
    }

    @Override
    public boolean use(Player player, Map map, Tile tile) {
        if (!(map instanceof Farm)) return false;
        if (tile.getDirtState() != Tile.DirtState.NORMAL) return false;
        tile.setDirtState(Tile.DirtState.PLOWED);
        ((Farm) map).updateTileVisuals(tile.tileX, tile.tileY);
        return true;
    }
}
