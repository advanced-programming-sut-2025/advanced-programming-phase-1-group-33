
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
    public  abstract PoleType getPoleType();
}