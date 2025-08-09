package com.yourgame.model.Item.Tools;

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
        return 0;
    }

    @Override
    public boolean use(Player player, Map map, Tile tile) {
        int consumedEnergy = switch (getToolStage()) {
            case Primary -> 5;
            case Copper -> 4;
            case Steel -> 3;
            case Gold -> 2;
            case Iridium -> 1;
            default -> 0;
        };
        return consumedEnergy <= consumedEnergy;
    }
}
