package com.yourgame.model.Item.Tools;

import com.yourgame.model.Map.Map;
import com.yourgame.model.Map.Tile;
import com.yourgame.model.UserInfo.Player;
import com.yourgame.model.WeatherAndTime.Weather;

public class MilkPail extends Tool {
    public MilkPail() {
        super(ToolType.MilkPale, 0);
    }

    @Override
    public int getConsumptionEnergy(Player player, Weather weather, boolean success) {
        return 0;
    }

    @Override
    public boolean use(Player player, Map map, Tile tile) {
        return false;
    }
}
