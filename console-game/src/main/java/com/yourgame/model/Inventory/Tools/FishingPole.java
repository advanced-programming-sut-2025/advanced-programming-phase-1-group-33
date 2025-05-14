package com.yourgame.model.Inventory.Tools;

public class FishingPole extends Tool {
    private PoleType type = PoleType.Training;

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
    protected void useTool() {

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
