package com.yourgame;

import com.yourgame.controller.GameController;
import com.yourgame.model.GameState;
import com.yourgame.view.ConsoleView;

public class GameMain {
    public static void main(String[] args) {
        // Initialize the game state (Model)
        GameState gameState = new GameState();
        
        // Setup the view
        ConsoleView view = new ConsoleView(System.out);
        
        // Create the main controller with the model and view
        GameController controller = new GameController(gameState, view);
        
        // Start the game loop
        controller.startGame();
    }
}