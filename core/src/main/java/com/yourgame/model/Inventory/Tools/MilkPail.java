package com.yourgame.model.Inventory.Tools;

import com.yourgame.model.App;
import com.yourgame.model.IO.Response;

public class MilkPail extends Tool {
    @Override
    protected int getConsumptionEnergy() {
        return 0;
    }

    @Override
    public Response useTool() {

        // Response energyConsumptionResponse = App.getGameState().getCurrentPlayer().consumeEnergy(4);
        // if (!energyConsumptionResponse.getSuccessful())
        //     return energyConsumptionResponse;

        return new Response(true, "");

    }

    public ToolType getToolType() {
        return null;
    }

    public void upgradeTool() {

    }

    public PoleType getPoleType() {
        return null;
    }
}
