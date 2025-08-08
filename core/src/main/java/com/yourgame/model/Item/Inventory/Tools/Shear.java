package com.yourgame.model.Item.Inventory.Tools;

import com.yourgame.Graphics.Map.MapData;
import com.yourgame.Graphics.Map.TileData;
import com.yourgame.model.App;
import com.yourgame.model.IO.Response;
import com.yourgame.model.UserInfo.Player;

public class Shear extends Tool {
    public Shear() {
        super(ToolType.Shear, 0);
    }

    @Override
    protected int getConsumptionEnergy() {
        return 0;
    }

    @Override
    public void use(Player player, MapData map, TileData tile) {

    }
}
