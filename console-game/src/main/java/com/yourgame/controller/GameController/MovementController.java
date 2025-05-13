package com.yourgame.controller.GameController;

import com.yourgame.model.GameState;
import com.yourgame.model.UserInfo.Player;
import com.yourgame.model.Map.Coordinate;
import com.yourgame.model.Command;
public class MovementController extends GameMenuCtrl{
    private GameState gameState;

    public MovementController(GameState gameState) {
        this.gameState = gameState;
    }

    public void handleWalk(Command cmd) {
        Player player = gameState.getPlayer(1);
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
        return true;

        }
}