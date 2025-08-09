package com.yourgame.model.Item;

import com.yourgame.model.Map.Map;
import com.yourgame.model.Map.Tile;
import com.yourgame.model.UserInfo.Player;

public interface Usable {
    boolean use(Player player, Map map, Tile tile);
}
