package com.yourgame.model.Item.Inventory.Tools;

import com.yourgame.Graphics.Map.MapData;
import com.yourgame.Graphics.Map.TileData;
import com.yourgame.model.UserInfo.Player;

public class Hoe extends Tool {
    public Hoe() {
        super(ToolType.Hoe, 4);
    }

    @Override
    public int getConsumptionEnergy() {
        return 0;
    }

    @Override
    public void use(Player player, MapData map, TileData tile) {
        int consumedEnergy = switch (getToolStage()) {
            case Primary -> 5;
            case Copper -> 4;
            case Steel -> 3;
            case Gold -> 2;
            case Iridium -> 1;
            default -> 0;
        };
    }
}
