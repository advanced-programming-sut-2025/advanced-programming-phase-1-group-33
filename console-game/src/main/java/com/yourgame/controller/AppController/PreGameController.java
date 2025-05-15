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
import com.yourgame.model.WeatherAndTime.TimeSystem;
import com.yourgame.model.WeatherAndTime.Weather;
import com.yourgame.model.enums.TileType;
import com.yourgame.model.enums.Commands.MenuTypes;

public class PreGameController {

    // public Result handleCommand(String command, Scanner scanner) {
    // PreGameMenuCommands mainCommand = PreGameMenuCommands.parse(command);
    // if (mainCommand == null) {
    // return Result.failure("Invalid command!");
    // }
    //
    // switch (mainCommand) {
    // case GAME_NEW:
    // return createNewGame(command, scanner);
    // case LOAD_GAME:
    // return LOAD_GAME(command);
    // case GAME_MAP:
    // return gameMap(command);
    // case EXIT_GAME:
    // return handleExit(command);
    // case GO_BACK:
    // return handleGoBack();
    // default:
    // return Result.failure("Command not implemented yet.");
    // }
    // }
    //
    // private Result handleExit(String command) {
    // // TODO Auto-generated method stub
    // return Result.success("Unimplemented method 'handleExit'");
    // }
    //
    // private Result gameMap(String command) {
    // // TODO Auto-generated method stub
    // return Result.success("Game Map was not handled correctly ");
    // }
    //
    // private Result handleGoBack() {
    // return Result.success("Unimplemented method 'GO BACK'");
    // }
    //
    // private Result LOAD_GAME(String command) {
    // // TODO Auto-generated method stub
    // GameState gameState = new GameState();
    // ConsoleView consoleView = new ConsoleView(System.out);
    // GameController gameController = new GameController(gameState, consoleView);
    //
    // return Result.success("Unimplemented method 'LOAD_GAME'");
    // }
    //

    public Response selectMapForCreateNewGame(int  mapInput, Player player , int sx , int sy) {
        if(mapInput > 4 || mapInput < 1){
            return new Response(false, "please select between available maps");
        }

        switch (mapInput){
            case 1:
                player.setFarm(FarmFactory.makeFarm1(sx  ,sy));
                break;
            case 2:
                player.setFarm(FarmFactory.makeFarm2(sx , sy));
                break;
            case 3:
                player.setFarm(FarmFactory.makeFarm3(sx ,sy));
                break;
            case 4:
                player.setFarm(FarmFactory.makeFarm4(sx  ,sy));
                break;

        }


        return new Response(true, "map selected successfully");
    }

    public Response createNewGame(ArrayList<Player> players) {
        // Create components for your game state
        ArrayList<Farm> maps = new ArrayList<>();
        for(Player p : players){
            maps.add(p.getFarm());

        }

        Map m = new Map(maps);
        m.buildMap(players, TileType.GRASS);
        GameState x = new GameState(players, maps , App.getCurrentUser() , m);


        App.setGameState(x);
        App.getGameState().setCurrentPlayer(players.getFirst());
        return new Response(true, "new game created successfully");
    }
}
