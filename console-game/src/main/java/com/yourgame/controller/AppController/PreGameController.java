package com.yourgame.controller.AppController;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.yourgame.model.App;
import com.yourgame.model.GameState;
import com.yourgame.model.NPC;
import com.yourgame.model.Player;
import com.yourgame.model.IO.Response;
import com.yourgame.model.Map.GameMap;
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
    public Response createNewGame(Request request) {
        // Assume you have a Player constructor that can create a player from the
        // current user
        
        Player player = new Player(App.getCurrentUser());
        List<Player> players = new ArrayList<>();
        players.add(player);

        // Create components for your game state
        TimeSystem gameTime = new TimeSystem(8, 1, Season.SPRING, 1); // initialize game time
        WeatherSystem weather = new WeatherSystem(); // initialize weather system
        GameMap map = new GameMap(); // default game map
        List<NPC> npcs = new ArrayList<>(); // any NPCs for the game
        List<Shop> shops = new ArrayList<>(); // and shop list if needed

        // Create a new game state instance
        GameState gameState = new GameState(players,  gameTime, weather, map, npcs, shops);
        gameState.startGame(); // Any initialization logic inside startGame()

        // Optionally, store the gameState somewhere accessible (e.g., in App or a
        // GameController)
        App.setGameState(gameState);
        // Change the current menu to your game menu
        App.setCurrentMenu(MenuTypes.GameMenu);
        return new Response(true, "New game started! Entering game menu...");
    }

}
