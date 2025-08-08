package com.yourgame.model.Item.Inventory.Tools;

import com.yourgame.Graphics.Map.MapData;
import com.yourgame.Graphics.Map.TileData;
import com.yourgame.model.UserInfo.Player;

public class MilkPail extends Tool {
    public MilkPail() {
        super(ToolType.MilkPale, 0);
    }

    @Override
    protected int getConsumptionEnergy() {
        return 0;
    }

    @Override
    public void use(Player player, MapData map, TileData tile) {

    }
}
