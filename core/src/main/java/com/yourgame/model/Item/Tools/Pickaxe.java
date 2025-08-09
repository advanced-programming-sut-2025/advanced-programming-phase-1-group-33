package com.yourgame.model.Item.Tools;

import com.yourgame.Graphics.Map.MapData;
import com.yourgame.Graphics.Map.TileData;
import com.yourgame.model.App;
import com.yourgame.model.Skill.Ability;
import com.yourgame.model.UserInfo.Player;
import com.yourgame.model.WeatherAndTime.Weather;

public class Pickaxe extends Tool {
    public Pickaxe() {
        super(ToolType.Pickaxe, 4);
    }

    @Override
    public int getConsumptionEnergy(Player player, Weather weather, boolean success) {
        return 0;
    }


    @Override
    public boolean use(Player player, MapData map, TileData tile) {
        Weather weather = App.getGameState().getGameTime().getWeather();
        int multiple = switch (weather) {
            case Rainy -> 2;
            case Snowy -> 3;
            default -> 1;
        };
        int consumedEnergy;
        if (App.getGameState().getCurrentPlayer().getAbility().getMiningLevel() == Ability.getMaxLevel()) {
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
        return consumedEnergy > 0;
    }
}
