package com.yourgame.model.Inventory.Tools;

import com.yourgame.model.App;
import com.yourgame.model.IO.Response;
import com.yourgame.model.Skill.Ability;
import com.yourgame.model.WeatherAndTime.Weather;

public class WateringCan extends Tool {
    private ToolType type = ToolType.Primary;
    private int capacity = 40;
    private int waterCapacity = capacity;

    public void upgradeTool() {
        if (this.type == ToolType.Primary) {
            this.type = ToolType.Coppery;
        } else if (this.type == ToolType.Coppery) {
            this.type = ToolType.Metal;
        } else if (this.type == ToolType.Metal) {
            this.type = ToolType.Golden;
        } else if (this.type == ToolType.Golden) {
            this.type = ToolType.Iridium;
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
                case Primary -> 4 * multiple;
                case Coppery -> 3 * multiple;
                case Metal -> 2 * multiple;
                case Golden -> 1;
                default -> 0;
            };
        } else {
            consumedEnergy = switch (type) {
                case Primary -> 5 * multiple;
                case Coppery -> 4 * multiple;
                case Metal -> 3 * multiple;
                case Golden -> 2 * multiple;
                case Iridium -> 1;
                default -> 0;
            };
        }
        waterCapacity--;

        Response energyConsumptionResponse = App.getGameState().getCurrentPlayer().consumeEnergy(consumedEnergy);
        if (!energyConsumptionResponse.getSuccessful())
            return energyConsumptionResponse;

        return new Response(true, "");
    }

    public ToolType getToolType() {
        return type;
    }

    public PoleType getPoleType() {
        return null;
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
