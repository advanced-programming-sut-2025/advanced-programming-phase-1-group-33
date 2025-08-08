package com.yourgame.model.Item.Tools;

import com.yourgame.Graphics.Map.MapData;
import com.yourgame.Graphics.Map.TileData;
import com.yourgame.model.App;
import com.yourgame.model.Skill.Ability;
import com.yourgame.model.UserInfo.Player;
import com.yourgame.model.WeatherAndTime.Weather;

public class WateringCan extends Tool {
    private int capacity = 40;
    private int waterCapacity = capacity;

    public WateringCan() {
        super(ToolType.WateringCan, 4);

    }

    @Override
    protected int getConsumptionEnergy() {
        return 0;
    }

    @Override
    public void use(Player player, MapData map, TileData tile) {
        Weather weather = App.getGameState().getGameTime().getWeather();
        int multiple = switch (weather) {
            case Rainy -> 2;
            case Snowy -> 3;
            default -> 1;
        };
        int consumedEnergy;
        if (App.getGameState().getCurrentPlayer().getAbility().getFarmingLevel() == Ability.getMaxLevel()) {
            consumedEnergy = switch (getToolStage()) {
                case Primary -> 4 * multiple;
                case Copper -> 3 * multiple;
                case Steel -> 2 * multiple;
                case Gold -> 1;
                default -> 0;
            };
        } else {
            consumedEnergy = switch (getToolStage()) {
                case Primary -> 5 * multiple;
                case Copper -> 4 * multiple;
                case Steel -> 3 * multiple;
                case Gold -> 2 * multiple;
                case Iridium -> 1;
                default -> 0;
            };
        }
        waterCapacity--;
    }

    @Override
    public boolean upgradeTool() {
        if (super.upgradeTool()) {
            capacity += 15;
            return true;
        }
        return false;
    }

    public int getWaterCapacity() {
        return waterCapacity;
    }

    public void setWaterCapacity(int waterCapacity) {
        this.waterCapacity = waterCapacity;
    }

    public void makeFull() {
        waterCapacity = capacity;
    }

}
