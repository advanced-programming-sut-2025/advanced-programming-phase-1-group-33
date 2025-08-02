package com.yourgame.model.Inventory.Tools;


import com.yourgame.model.Skill.Ability;
import com.yourgame.model.WeatherAndTime.Weather;
import com.yourgame.model.App;
import com.yourgame.model.IO.Response;

public class Axe extends Tool {
    private ToolType type = ToolType.Primary;

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
    public int getConsumptionEnergy() {
        return 0;
    }

    @Override
    public  Response useTool() {
        Weather weather = App.getGameState().getGameTime().getWeather();
        int multiple = switch (weather) {
            case Rainy -> 2;
            case Snowy -> 3;
            default -> 1;
        };
        int consumedEnergy;
        if(App.getGameState().getCurrentPlayer().getAbility().getFarmingLevel() == Ability.getMaxLevel()){
            consumedEnergy = switch (type) {
                case Primary -> 4 * multiple;
                case Coppery -> 3 * multiple;
                case Metal -> 2 * multiple;
                case Golden -> 1;
                default -> 0;
            };
        }
        else {
            consumedEnergy = switch (type) {
                case Primary -> 5 * multiple;
                case Coppery -> 4 * multiple;
                case Metal -> 3  * multiple;
                case Golden -> 2 * multiple;
                case Iridium -> 1;
                default -> 0;
            };
        }
        Response energyConsumptionResult = App.getGameState().getCurrentPlayer().consumeEnergy(consumedEnergy);
        if (!energyConsumptionResult.getSuccessful()){
            return energyConsumptionResult;
        }

        return energyConsumptionResult;



    }
    public ToolType getToolType() {
        return type;
    }
    public PoleType getPoleType() {
        return null;
    }

}
