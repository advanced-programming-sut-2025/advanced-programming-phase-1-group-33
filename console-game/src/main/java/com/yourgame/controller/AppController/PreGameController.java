package com.yourgame.controller.AppController;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.yourgame.model.App;
import com.yourgame.model.GameState;
import com.yourgame.model.NPC;
import com.yourgame.model.Player;
import com.yourgame.model.Building.FarmMap;
import com.yourgame.model.IO.Response;
import com.yourgame.model.Map.GameMap;
import com.yourgame.model.Map.MapManager;
import com.yourgame.model.Map.Tile;
import com.yourgame.model.Shop.Shop;
import com.yourgame.model.Weather.TimeSystem;
import com.yourgame.model.Weather.WeatherSystem;
import com.yourgame.model.enums.Season;
import com.yourgame.model.enums.Commands.MenuTypes;
import com.yourgame.model.IO.Request;
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
    public Response createNewGame(List<Player> players) {
        // Create components for your game state
        TimeSystem gameTime = new TimeSystem(8, 1, Season.SPRING, 1); // initialize game time
        WeatherSystem weather = new WeatherSystem(Season.SPRING); // initialize weather system

        // Create map manager and maps
        MapManager mapManager = new MapManager();
        // Initialize tiles array as needed
        Tile[][] beachTiles = mapManager.getDefaultBeachTiles(10 , 10); // create 10x10 tiles for beach map
        // (Fill the tile arrays with proper Tile objects)
        
        GameMap beachMap = new GameMap("Beach",beachTiles, new ArrayList<>());
        mapManager.addMap(beachMap);
        for(Player player: players ){
            Tile[][] farmTiles = mapManager.getDefaultFarmTiles(32 , 32); 
            String playerFarmId = player.getUsername() + "Farm";             
            FarmMap farmMap = new FarmMap(playerFarmId,farmTiles, new ArrayList<>());
            mapManager.addMap(farmMap);
            player.initializeFarm(farmMap); 
        }

        // Create a new game state instance with the mapManager (change constructor if needed)
        GameState gameState = new GameState(players, gameTime, weather, mapManager, new ArrayList<>(), new ArrayList<>());
        gameState.startGame();
        gameState.setCurrentPlayer(players.get(0));

        // Optionally, store the mapManager in GameState or a global App class
        App.setGameState(gameState);
        App.setCurrentMenu(MenuTypes.GameMenu);
        return new Response(true, "New game started! Entering game menu...");
    }
}
