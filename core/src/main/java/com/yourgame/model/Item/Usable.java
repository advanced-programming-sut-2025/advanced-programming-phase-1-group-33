package com.yourgame.model.Item;

import com.yourgame.Graphics.Map.MapData;
import com.yourgame.Graphics.Map.TileData;
import com.yourgame.model.UserInfo.Player;

public interface Usable {
    boolean use(Player player, MapData map, TileData tile);
}
