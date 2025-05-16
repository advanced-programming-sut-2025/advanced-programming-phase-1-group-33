
package com.yourgame.model.Inventory.Tools;

import com.yourgame.model.IO.Response;

public abstract class Tool {
    protected ToolType toolType;
    protected PoleType poleType;
    protected String name;

    protected abstract int getConsumptionEnergy();

    public abstract Response useTool();

    public abstract void upgradeTool();

    public abstract ToolType getToolType();

    public abstract PoleType getPoleType();

    public static Tool getToolByName(String name) {
        return switch (name.toLowerCase()) {
            case "axe" -> new Axe();
            case "fishingpole" -> new FishingPole(PoleType.Training);
            case "hoe" -> new Hoe();
            case "milkpail" -> new MilkPail();
            case "pickaxe" -> new Pickaxe();
            case "scythe" -> new Scythe();
            case "shear" -> new Shear();
            case "wateringcan" -> new WateringCan();
            default -> null;
        };
    }
}