
package com.yourgame.model.Item.Inventory.Tools;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.model.Item.Item;
import com.yourgame.model.Item.Usable;

public abstract class Tool extends Item implements Usable {
    public enum ToolType {Axe, Pickaxe, Hoe, WateringCan, FishingPole, Scythe, MilkPale, Shears}

    protected final ToolType toolType;
    protected final int maxLevel;
    protected int level;

    /**
     * @param maxLevel maxLevel is 0-based
     * */
    public Tool(ToolType toolType, int maxLevel) {
        super(toolType.name(), ItemType.TOOL, 0, false);
        this.toolType = toolType;
        this.maxLevel = maxLevel;
    }

    protected abstract int getConsumptionEnergy();

    public ToolType getToolType() {
        return toolType;
    }

    public int getLevel() {
        return level;
    }

    public boolean upgradeTool() {
        if (level < maxLevel) {level++; return true;}
        return false;
    }

    public ToolStage getToolStage() {
        return ToolStage.values()[level];
    }

    public TextureRegion getTextureRegion(GameAssetManager assetManager) {
        String basePath = "Game/Tool/";
        ToolStage stage = getToolStage();
        String level = (stage == ToolStage.Primary) ? "" : (stage.name() + "_");
        String path = basePath + level + name + ".png";
        return new TextureRegion(assetManager.getTexture(path));
    }

    public static Tool getToolByName(String name) {
        return switch (name.toLowerCase()) {
            case "axe" -> new Axe();
            case "fishingpole" -> new FishingPole(PoleStage.Training);
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
