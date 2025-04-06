package com.yourgame.controller;

import com.yourgame.view.MenuView;
import com.yourgame.model.GameState;

public class MenuController {

    private final GameState gameState;  // Keeps track of the current game state.
    private final MenuView menuView;    // Responsible for displaying menus to the user.

    // Constructor
    public MenuController(GameState gameState, MenuView menuView) {
        this.gameState = gameState;
        this.menuView = menuView;
    }

    // Handles the "enter menu" command.
    public void handleMenuEnter(String menuName) {
        switch (menuName.toLowerCase()) {
            case "main":
                showMainMenu();
                break;
            case "game":
                showGameMenu();
                break;
            case "profile":
                showProfileMenu();
                break;
            // Add more cases for other menus as necessary
            default:
                menuView.displayError("Invalid menu name: " + menuName);
        }
    }

    // Handles the "exit menu" command (goes back to the previous menu or closes the game).
    public void handleMenuExit() {
        menuView.displayMessage("Exiting current menu...");
        // Code to navigate to the previous menu or close the game
    }

    // Show the main menu
    private void showMainMenu() {
        menuView.displayMainMenu();  // Call the view to display the main menu.
        // Add further logic for interacting with the main menu
    }

    // Show the game menu
    private void showGameMenu() {
        menuView.displayGameMenu();  // Call the view to display the game menu.
        // Add further logic for interacting with the game menu
    }

    // Show the profile menu
    private void showProfileMenu() {
        menuView.displayProfileMenu();  // Call the view to display the profile menu.
        // Add further logic for interacting with the profile menu
    }

    // Example method to handle menu interactions
    public void handleMenuAction(String action) {
        switch (action.toLowerCase()) {
            case "start game":
                // Start the game or transition to another menu
                gameState.startGame();
                menuView.displayMessage("Game started.");
                break;
            case "exit game":
                // Exit the game or show exit confirmation
                menuView.displayMessage("Exiting game...");
                break;
            default:
                menuView.displayError("Unknown action: " + action);
        }
    }
}
