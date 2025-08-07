package com.yourgame.model.Inventory.Tools;

import com.yourgame.model.App;
import com.yourgame.model.IO.Response;

public class Hoe extends Tool {
    private ToolType type = ToolType.Primary;
    @Override
    public int getConsumptionEnergy() {
        return 0;
    }

    @Override
    public Response useTool() {
        int consumedEnergy = switch (type) {
            case Primary -> 5;
            case Coppery -> 4;
            case Metal -> 3;
            case Golden -> 2;
            case Iridium -> 1;
            default -> 0;
        };

        // Response energyConsumptionResponse = App.getGameState().getCurrentPlayer().consumeEnergy(consumedEnergy);
        // if (!energyConsumptionResponse.getSuccessful())
        //     return energyConsumptionResponse;

        return new Response(true, "");

    }

    public void upgradeTool() {
        if(this.type == ToolType.Primary) {
            this.type = ToolType.Coppery;
        }
        else if(this.type == ToolType.Coppery) {
            this.type = ToolType.Metal;
        }
        else if(this.type == ToolType.Metal ){
            this.type = ToolType.Golden;
        }
        else if(this.type == ToolType.Golden){
            this.type = ToolType.Iridium;
        }



    }
    public ToolType getToolType() {
        return type;
    }
    public PoleType getPoleType() {
        return null;
    }
}
