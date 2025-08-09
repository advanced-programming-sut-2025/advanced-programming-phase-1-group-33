package com.yourgame.model.Item.Tools;

import com.yourgame.Graphics.Map.MapData;
import com.yourgame.Graphics.Map.TileData;
import com.yourgame.model.UserInfo.Player;
import com.yourgame.model.WeatherAndTime.Weather;

public class Scythe extends Tool {
    public Scythe() {
        super(ToolType.Scythe, 0);
    }

    @Override
    public int getConsumptionEnergy(Player player, Weather weather, boolean success) {
        return 0;
    }

    @Override
    public boolean use(Player player, MapData map, TileData tile) {
        return false;
    }
}
