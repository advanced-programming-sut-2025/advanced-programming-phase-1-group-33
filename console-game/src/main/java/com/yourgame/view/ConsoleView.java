package com.yourgame.view;

import java.io.PrintStream;

public class ConsoleView {
    private PrintStream output;

    public ConsoleView(PrintStream output) {
        this.output = output;
    }

    public void displayMessage(String message) {
        output.println(message);
    }

    public void displayError(String errorMessage) {
        output.println("Error: " + errorMessage);
    }

    public void displayWelcomeMessage() {
        displayMessage("Welcome to the Console Game!");
    }

    public void displayGoodbyeMessage() {
        displayMessage("Thank you for playing! Goodbye!");
    }

    public void displayMenu(String menu) {
        displayMessage("Menu: " + menu);
    }

    public void displayPlayerStatus(String status) {
        displayMessage("Player Status: " + status);
    }

    public void displayInventory(String inventory) {
        displayMessage("Inventory: " + inventory);
    }

    public void displayMap(String map) {
        displayMessage("Map: " + map);
    }

    public void promptForInput() {displayMessage("input: ");
    }
}
