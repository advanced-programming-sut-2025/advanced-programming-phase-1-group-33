package com.yourgame.controller;

import com.yourgame.model.GameState;
import com.yourgame.model.Player;
import com.yourgame.model.Map;
import com.yourgame.model.Coordinate;

public class MovementController {
    private GameState gameState;

    public MovementController(GameState gameState) {
        this.gameState = gameState;
    }

    public void handleWalk(Command cmd) {
        Player player = gameState.getPlayer();
        Coordinate newLocation = calculateNewLocation(player.getCurrentLocation(), cmd.getArguments());

        if (isValidMove(newLocation)) {
            player.setCurrentLocation(newLocation);
            player.changeEnergy(-1); // Assuming moving costs 1 energy
            // Additional logic for updating the game state can be added here
        } else {
            // Handle invalid move (e.g., display error message)
        }
    }

    private Coordinate calculateNewLocation(Coordinate currentLocation, String[] arguments) {
        // Logic to calculate new location based on current location and command arguments
        return new Coordinate(currentLocation.getX(), currentLocation.getY()); // Placeholder
    }

    private boolean isValidMove(Coordinate newLocation) {
        Map map = gameState.getMap();
        return map.isOccupied(newLocation) == false; // Example check
    }
}