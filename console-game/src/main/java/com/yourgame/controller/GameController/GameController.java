package com.yourgame.controller.GameController;

import com.yourgame.model.GameState;
import com.yourgame.view.ConsoleView;

import java.util.Scanner;

import com.yourgame.controller.CommandParser;
import com.yourgame.model.Command;
public class GameController {
    private GameState gameState;
    private ConsoleView consoleView;
    private boolean isRunning;

    public GameController() {
        this.gameState = new GameState();
        this.consoleView = new ConsoleView(System.out);
        this.isRunning = true;
    }

    public GameController(GameState gameState, ConsoleView consoleView) {
        this.gameState = gameState;
        this.consoleView = consoleView;
        this.isRunning = true;
    }
    public void startGame() {
        consoleView.displayWelcomeMessage();
        while (isRunning) {
            String userInput = getUserInput();
            processInput(userInput);
        }
    }

    private String getUserInput() {
        Scanner scanner = new Scanner(System.in);
        consoleView.promptForInput();
        return scanner.nextLine();
    }

    private void processInput(String input) {
        CommandParser commandParser = new CommandParser();
        Command command = commandParser.parse(input);
        
        if (command.isExitCommand()) {
            isRunning = false;
            consoleView.displayGoodbyeMessage();
        } else {
            // Delegate to the appropriate controller based on command
            // This part will be implemented as the game expands
        }
    }
}