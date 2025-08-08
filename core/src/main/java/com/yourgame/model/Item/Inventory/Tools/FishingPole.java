package com.yourgame.model.Item.Inventory.Tools;

import com.yourgame.Graphics.Map.MapData;
import com.yourgame.Graphics.Map.TileData;
import com.yourgame.model.App;
import com.yourgame.model.Skill.Ability;
import com.yourgame.model.UserInfo.Player;
import com.yourgame.model.WeatherAndTime.Weather;

public class FishingPole extends Tool {
    public FishingPole(PoleStage type) {
        super(ToolType.FishingPole, 3);
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
            consumedEnergy = switch (getPoleStage()) {
                case Training, Bamboo -> 7 * multiple;
                case Fiberglass -> 5 * multiple;
                case Iridium -> 3 * multiple;
            };
        } else {
            consumedEnergy = switch (getPoleStage()) {
                case Training, Bamboo -> 8 * multiple;
                case Fiberglass -> 6 * multiple;
                case Iridium -> 4 * multiple;


            };
        }
    }

    public PoleStage getPoleStage() {
        return PoleStage.values()[level];
    }
}
