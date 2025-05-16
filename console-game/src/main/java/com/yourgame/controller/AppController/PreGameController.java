package com.yourgame.controller.AppController;

import java.util.ArrayList;
import java.util.List;

import com.yourgame.model.App;
import com.yourgame.model.GameState;
import com.yourgame.model.Map.Farm;
import com.yourgame.model.Map.FarmFactory;
import com.yourgame.model.Map.Map;
import com.yourgame.model.UserInfo.Player;
import com.yourgame.model.IO.Response;
import com.yourgame.model.enums.TileType;

public class PreGameController {
    public Response selectMapForCreateNewGame(int mapInput, Player player, int sx, int sy) {
        if (mapInput > 4 || mapInput < 1) {
            return new Response(false, "please select between available maps");
        }

        switch (mapInput) {
            case 1:
                player.setFarm(FarmFactory.makeFarm1(sx, sy));
                break;
            case 2:
                player.setFarm(FarmFactory.makeFarm2(sx, sy));
                break;
            case 3:
                player.setFarm(FarmFactory.makeFarm3(sx, sy));
                break;
            case 4:
                player.setFarm(FarmFactory.makeFarm4(sx, sy));
                break;

        }

        return new Response(true, "map selected successfully");
    }

    public Response createNewGame(ArrayList<Player> players) {
        // Create components for your game state
        ArrayList<Farm> maps = new ArrayList<>();
        for (Player p : players) {
            maps.add(p.getFarm());

        }

        Map m = new Map(maps);
        m.buildMap(players, TileType.GRASS);
        GameState x = new GameState(players, maps, App.getCurrentUser(), m);

        App.setGameState(x);
        App.getGameState().setCurrentPlayer(players.getFirst());
        return new Response(true, "new game created successfully");
    }
}
