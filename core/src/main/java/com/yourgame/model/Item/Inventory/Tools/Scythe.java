package com.yourgame.model.Item.Inventory.Tools;

import com.yourgame.Graphics.Map.MapData;
import com.yourgame.Graphics.Map.TileData;
import com.yourgame.model.App;
import com.yourgame.model.IO.Response;
import com.yourgame.model.UserInfo.Player;

public class Scythe extends Tool {
    public Scythe() {
        super(ToolType.Scythe, 0);
    }

    @Override
    protected int getConsumptionEnergy() {
        return 0;
    }

    @Override
    public void use(Player player, MapData map, TileData tile) {

    }
}
