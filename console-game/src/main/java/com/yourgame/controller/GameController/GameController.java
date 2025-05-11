package com.yourgame.controller.GameController;

import com.yourgame.model.GameState;
import com.yourgame.model.User;
import com.yourgame.model.IO.Request;
import com.yourgame.model.IO.Response;
import com.yourgame.view.ConsoleView;

import java.util.Scanner;

import com.yourgame.controller.CommandParser;
import com.yourgame.model.App;
import com.yourgame.model.Command;
public class GameController {
    private GameState gameState;
    private ConsoleView consoleView;
    private boolean isRunning;

    public GameController() {
        User currentUser = App.getCurrentUser();
        if (currentUser == null || App.getGameState() == null) {
            throw new IllegalStateException("Cannot initialize GameController: No user logged in or game state available.");
        }
        this.gameState = App.getGameState();
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

    public Response getEnergy() {
        return new Response(true, "Your energy is " + gameState.getCurrentPlayer().getEnergy() + "/" + gameState.getCurrentPlayer().getMaxEnergy()); 
    }

    public Response setCurrentPlayerEnergy(Request request) {
        int energy = Integer.parseInt(request.body.get("value")); 
        gameState.getCurrentPlayer().setEnergy(energy); 
        return new Response(true, "Your energy is Setted" + gameState.getCurrentPlayer().getEnergy() + "/" + gameState.getCurrentPlayer().getMaxEnergy()); 
    }

    public Response setCurrentPlayerEnergyUnlimited() {
        // TODO Auto-generated method stub
        gameState.getCurrentPlayer().setUnlimitedEnergy(true);
        return new Response(true, "Your energy is Your energy is set to be unlimited"); 
    }

    public Response NextTurn() {
        gameState.nextTurn();
        return new Response(true, "You are  playing as" + gameState.getCurrentPlayer());

    }
}