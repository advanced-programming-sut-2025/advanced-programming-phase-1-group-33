package com.yourgame.controller.AppController;

import java.io.Console;
import java.util.Scanner;

import com.yourgame.controller.GameController.GameController;
import com.yourgame.model.GameState;
import com.yourgame.model.AppModel.Result;
import com.yourgame.model.AppModel.enums.PreGameMenuCommands;
import com.yourgame.view.ConsoleView;
public class PreGameController {

    public Result handleCommand(String command, Scanner scanner) {
        PreGameMenuCommands mainCommand = PreGameMenuCommands.parse(command);
        if (mainCommand == null) {
            return Result.failure("Invalid command!");
        }

        switch (mainCommand) {
            case GAME_NEW:
                return createNewGame(command, scanner);
            case LOAD_GAME:
                return LOAD_GAME(command);
            case GAME_MAP:
                return gameMap(command);
            case EXIT_GAME:
                return handleExit(command);
            case GO_BACK:
                return handleGoBack();
            default:
                return Result.failure("Command not implemented yet.");
        }
    }

    private Result handleExit(String command) {
        // TODO Auto-generated method stub
        return Result.success("Unimplemented method 'handleExit'");
    }

    private Result gameMap(String command) {
        // TODO Auto-generated method stub
        return Result.success("Game Map was not handled correctly ");
    }

    private Result handleGoBack() {
        return Result.success("Unimplemented method 'GO BACK'");
    }

    private Result LOAD_GAME(String command) {
        // TODO Auto-generated method stub
        GameState gameState = new GameState(); 
        ConsoleView consoleView = new ConsoleView(System.out);
        GameController gameController = new GameController(gameState, consoleView); 

        return Result.success("Unimplemented method 'LOAD_GAME'");
    }

    private Result createNewGame(String command, Scanner scanner) {
        GameController gameController = new GameController(); 
        // TODO Auto-generated method stub
        return Result.success("Unimplemented method 'createNewGame'");
    }
    

    
}
