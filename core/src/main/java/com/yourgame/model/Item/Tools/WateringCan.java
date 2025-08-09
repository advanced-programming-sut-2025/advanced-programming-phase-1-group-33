package com.yourgame.model.Item.Tools;

import com.yourgame.model.Map.Farm;
import com.yourgame.model.Map.Map;
import com.yourgame.model.Map.Tile;
import com.yourgame.model.App;
import com.yourgame.model.Skill.Ability;
import com.yourgame.model.UserInfo.Player;
import com.yourgame.model.WeatherAndTime.Weather;

public class WateringCan extends Tool {
    private int capacity;
    private int waterLevel;

    public WateringCan() {
        super(ToolType.WateringCan, 4);
        capacity = 40;
        waterLevel = capacity;
    }

    @Override
    public int getConsumptionEnergy(Player player, Weather weather, boolean success) {
        int energyConsumed = 5 - level;
        if (!success) energyConsumed--;
        if (player.getAbility().getFarmingLevel() == Ability.MAX_LEVEL) energyConsumed--;
        return (int) (Math.max(energyConsumed, 0) * weather.energyCoefficient);
    }

    @Override
    public boolean use(Player player, Map map, Tile tile) {
        if (!(map instanceof Farm)) return false;
        if (tile.getDirtState() != Tile.DirtState.PLOWED) return false;
        tile.setDirtState(Tile.DirtState.WATERED);
        ((Farm) map).updateTileVisuals(tile.tileX, tile.tileY);
        return true;
    }

    @Override
    public boolean upgradeTool() {
        if (super.upgradeTool()) {
            capacity = 15 * level + 25;
            return true;
        }
        return false;
    }

    public int getWaterLevel() {
        return waterLevel;
    }

    public void setWaterLevel(int waterLevel) {
        this.waterLevel = waterLevel;
    }

    public void makeFull() {
        waterLevel = capacity;
    }
}
