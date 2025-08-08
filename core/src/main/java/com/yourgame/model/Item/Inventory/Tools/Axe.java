package com.yourgame.model.Item.Inventory.Tools;

import com.yourgame.Graphics.Map.MapData;
import com.yourgame.Graphics.Map.TileData;
import com.yourgame.model.Skill.Ability;
import com.yourgame.model.UserInfo.Player;
import com.yourgame.model.WeatherAndTime.Weather;
import com.yourgame.model.App;

public class Axe extends Tool {
    public Axe() {
        super(ToolType.Axe, 4);
    }

    @Override
    public int getConsumptionEnergy() {
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
        if(App.getGameState().getCurrentPlayer().getAbility().getFarmingLevel() == Ability.getMaxLevel()){
            consumedEnergy = switch (getToolStage()) {
                case Primary -> 4 * multiple;
                case Coppery -> 3 * multiple;
                case Metal -> 2 * multiple;
                case Golden -> 1;
                default -> 0;
            };
        }
        else {
            consumedEnergy = switch (getToolStage()) {
                case Primary -> 5 * multiple;
                case Coppery -> 4 * multiple;
                case Metal -> 3  * multiple;
                case Golden -> 2 * multiple;
                case Iridium -> 1;
                default -> 0;
            };
        }
    }
}
