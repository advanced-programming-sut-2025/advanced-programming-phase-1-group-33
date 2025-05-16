package com.yourgame.model.Inventory.Tools;

import com.yourgame.model.App;
import com.yourgame.model.GameState;
import com.yourgame.model.IO.Response;
import com.yourgame.model.Skill.Ability;
import com.yourgame.model.WeatherAndTime.Weather;

public class FishingPole extends Tool {
    private PoleType type = PoleType.Training;

    public FishingPole(PoleType type) {
        this.type = type;
    }

    public void upgradeTool() {
        if(type == PoleType.Training) {
            type = PoleType.Bamboo;
        }
        else if(type == PoleType.Bamboo) {
            type = PoleType.Fiberglass;
        }
        else if(type == PoleType.Fiberglass) {
            type = PoleType.Iridium;
        }
    }

    @Override
    protected int getConsumptionEnergy() {
        return 0;
    }

    @Override
    public Response useTool() {
                Weather weather = App.getGameState().getGameTime().getWeather();
        int multiple = switch (weather) {
            case Rainy -> 2;
            case Snowy -> 3;
            default -> 1;
        };
        int consumedEnergy;
        if (App.getGameState().getCurrentPlayer().getAbility().getFarmingLevel() == Ability.getMaxLevel()) {
            consumedEnergy = switch (type) {
                case Training, Bamboo -> 7 * multiple;
                case Fiberglass -> 5 * multiple;
                case Iridium -> 3 * multiple;
            };
        } else {
            consumedEnergy = switch (type) {
                case Training, Bamboo -> 8 * multiple;
                case Fiberglass -> 6 * multiple;
                case Iridium -> 4 * multiple;


            };
        }

        Response energyConsumptionResponse = App.getGameState().getCurrentPlayer().consumeEnergy(consumedEnergy);
        if (!energyConsumptionResponse.getSuccessful())
            return energyConsumptionResponse;

        return new Response(true, "");


    }


    public PoleType getType() {
        return type;
    }

    public void setType(PoleType type) {
        this.type = type;
    }
    public PoleType getPoleType() {
        return type;
    }
    public ToolType getToolType() {
        return null;
    }
}
