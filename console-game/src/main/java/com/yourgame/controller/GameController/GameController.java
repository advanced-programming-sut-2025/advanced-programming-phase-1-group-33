package com.yourgame.controller.GameController;

import com.yourgame.model.GameState;
import com.yourgame.model.Player;
import com.yourgame.model.User;
import com.yourgame.model.IO.Request;
import com.yourgame.model.IO.Response;
import com.yourgame.model.Map.Coordinate;
import com.yourgame.model.Map.GameMap;
import com.yourgame.model.Map.Portal;
import com.yourgame.model.Map.Tile;
import com.yourgame.model.enums.TileType;
import com.yourgame.model.enums.Weather;
import com.yourgame.view.ConsoleView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
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
            throw new IllegalStateException(
                    "Cannot initialize GameController: No user logged in or game state available.");
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
        return new Response(true, "Your energy is " + gameState.getCurrentPlayer().getEnergy() + "/"
                + gameState.getCurrentPlayer().getMaxEnergy());
    }

    public Response setCurrentPlayerEnergy(Request request) {
        int energy = Integer.parseInt(request.body.get("value"));
        gameState.getCurrentPlayer().setEnergy(energy);
        return new Response(true, "Your energy is Setted" + gameState.getCurrentPlayer().getEnergy() + "/"
                + gameState.getCurrentPlayer().getMaxEnergy());
    }

    public Response setCurrentPlayerEnergyUnlimited() {
        // TODO Auto-generated method stub
        gameState.getCurrentPlayer().setUnlimitedEnergy(true);
        return new Response(true, "Your energy is unlimited");
    }

    public Response NextTurn() {
        gameState.nextTurn();
        return new Response(true, "You are  playing as " + gameState.getCurrentPlayer().getUsername());

    }

    public Response getTime() {
        return new Response(true, "Current time is " + gameState.getGameTime().getTime());

    }

    public Response getDate() {
        return new Response(true, "Current Date is " + gameState.getGameTime().getDate());
    }

    public Response getDateTime() {
        return new Response(true, "Current DateTime is " + gameState.getGameTime().getDateTime());
    }

    public Response getDayOfWeek() {
        // TODO Auto-generated method stub
        return new Response(true, "Current Day of the week is " + gameState.getGameTime().getDayOfWeek());

    }

    public Response getSeason() {
        // TODO Auto-generated method stub
        return new Response(true, "Current Season is " + gameState.getGameTime().getSeason());
    }

    public Response getAdvancedDate(Request request) {
        // TODO Auto-generated method stub

        int amountOfDays = Integer.parseInt(request.body.get("amount"));

        for (int i = 0; i < amountOfDays; i++) {
            gameState.getGameTime().advanceDay();
        }
        return new Response(true, "How tf you cheated at the timeee dudeee!!" + amountOfDays + "Days");
    }

    public Response getAdvancedTime(Request request) {
        int amountOfHours = Integer.parseInt(request.body.get("amount"));

        for (int i = 0; i < amountOfHours; i++) {
            gameState.getGameTime().advanceHour();
        }
        return new Response(true, "How tf you cheated at the timeee dudeee!!" + amountOfHours + " hours");
    }

    public Response getWeather() {
        return new Response(true, "Current Weather is " + gameState.getWeather().getCurrentWeather());
    }

    public Response cheatWeather(Request request) {
        String weatherInput = request.body.get("Type");
        Weather weather;
        try {
            weather = Weather.fromString(weatherInput);
        } catch (IllegalArgumentException e) {
            return new Response(true, e.getMessage());
        }
        gameState.getWeather().setCurrentWeather(weather);
        return new Response(true, "Weather set to " + weather.toString());
    }

    public Response cheatThor(Request request) {
        // TODO Auto-generated method stub
        return new Response(false, "To Do We need to create the Thor After Map ");
    }

    public Response getWeatherForcast() {
        return new Response(true, "Tomorrow Weather is " + gameState.getWeather().getTomorrowWeather());
    }

    public Response getBuildGreenHouse() {
        // TODO Auto-generated method stub
        return new Response(false, "To Do We need to create Map after that we can have green house");
    }

    public Response PrintMap() {
        // Get the current player from the game state
        Player currentPlayer = gameState.getCurrentPlayer();
        // Retrieve the map using the player's current map id
        String mapId = currentPlayer.getCurrentMapId();
        GameMap map = gameState.getMapManager().getMap(mapId);
        if (map == null) {
            return new Response(false, "Map not found for the current player. Current map Id: " + mapId);
        }
        // Render the map based on its current state
        String renderedMap = map.renderMap(gameState.getPlayers());
        return new Response(true, renderedMap);
    }

    public Response getWalk(Request request) {
        // Obtain current player and destination.
        Player currentPlayer = gameState.getCurrentPlayer();
        int destX = Integer.parseInt(request.body.get("x"));
        int destY = Integer.parseInt(request.body.get("y"));
        Coordinate destination = new Coordinate(destX, destY);

        // Retrieve the current map.
        GameMap currentMap = gameState.getMapManager().getMap(currentPlayer.getCurrentMapId());
        if (currentMap == null) {
            return new Response(false, "Current map not found.");
        }

        // Find a path using BFS with 8–neighbor moves.
        List<Coordinate> path = findPath(currentPlayer.getCurrentCoordinate(), destination, currentMap);
        if (path == null || path.isEmpty()) {
            return new Response(false, "No valid path found to the destination.");
        }

        // Check that path does not cross another player's farm.
        // (For illustration, we assume that if any tile in the path is marked as
        // non–walkable or
        // belongs to a farm whose map id is not equal to currentPlayer's own farm id,
        // then reject.)
        for (Coordinate coord : path) {
            // Example: if the tile is on a farm map and it is not the current player’s
            // farm:
            if (!currentPlayer.getCurrentMapId().equals("farmMap") // adjust condition as needed
                    && isOtherPlayersFarm(coord, currentMap)) {
                return new Response(false, "You cannot enter another player's farm.");
            }
        }

        // Simulate walking along the path using energy.
        // Energy cost per step is 1/20 unit.
        // At the start of each new turn (except the first), there is an extra penalty
        // of 10 energy (equivalent to 10/20 units).
        // Each turn, the player has 50 energy, unless unlimited.
        double perStepCost = 1.0 / 20; // energy cost for one tile move.
        double turnPenalty = 10.0 / 20; // additional cost when changing turn.
        double turnEnergy = 50;
        int turnCount = 1;

        // If player has unlimited energy, then simply move and update coordinate.
        if (currentPlayer.isUnlimitedEnergy()) {
            currentPlayer.setCurrentCoordinate(destination);
            // Also check if destination is a portal.
            Tile destTile = currentMap.getTileAt(destination);
            if (destTile.getType() == TileType.PORTAL && destTile.getContent() instanceof Portal) {
                Portal portal = (Portal) destTile.getContent();
                currentPlayer.setCurrentMapId(portal.getDestinationMapId());
                currentPlayer.setCurrentCoordinate(new Coordinate(portal.getDestRow(), portal.getDestCol()));
                return new Response(true, "Teleported to " + portal.getDestinationMapId());
            }
            return new Response(true, "Moved successfully (unlimited energy).");
        }

        // Otherwise, simulate movement along the path.
        for (int i = 1; i < path.size(); i++) { // start from 1 as 0 is the starting coordinate.
            // Check if current turn energy is enough for the next step.
            if (turnEnergy < perStepCost) {
                // Not enough energy to move to next tile: auto-change turn.
                turnCount++;
                turnEnergy = 50 - turnPenalty; // new turn cost penalty.
            }
            turnEnergy -= perStepCost;
        }

        // Calculate total energy cost using the formula: (distance + 10 *
        // numberOfTurns) / 20.
        int distance = path.size() - 1; // number of steps
        double totalCost = (distance + 10 * turnCount) / 20.0;

        // Optionally, if totalCost exceeds the available energy across turns, you could
        // stop early.
        // For this demo, we assume the simulation always completes the path.

        // Update the player's coordinate.
        currentPlayer.setCurrentCoordinate(destination);

        // Check for a portal at destination.
        Tile destTile = currentMap.getTileAt(destination);
        if (destTile.getType() == TileType.PORTAL && destTile.getContent() instanceof Portal) {
            Portal portal = (Portal) destTile.getContent();
            currentPlayer.setCurrentMapId(portal.getDestinationMapId());
            currentPlayer.setCurrentCoordinate(new Coordinate(portal.getDestRow(), portal.getDestCol()));
            return new Response(true,
                    "Teleported to " + portal.getDestinationMapId() + " after " + turnCount + " turn(s).");
        }

        // If the move consumed all energy in the current turn, automatically advance
        // the turn.
        if (turnEnergy <= 0) {
            gameState.nextTurn();
            return new Response(true, "Moved successfully, but your turn ended after " + turnCount
                    + " turn(s). Now playing as " + gameState.getCurrentPlayer().getUsername());
        }

        return new Response(true, "Moved successfully in " + turnCount + " turn(s).");
    }

    // Helper method: findPath using BFS with 8–directions.
    private List<Coordinate> findPath(Coordinate start, Coordinate goal, GameMap map) {
        Queue<Coordinate> queue = new LinkedList<>();
        Map<Coordinate, Coordinate> cameFrom = new HashMap<>();
        queue.add(start);
        cameFrom.put(start, null);

        // Define 8 neighboring moves.
        int[] dx = { -1, -1, -1, 0, 0, 1, 1, 1 };
        int[] dy = { -1, 0, 1, -1, 1, -1, 0, 1 };

        while (!queue.isEmpty()) {
            Coordinate current = queue.poll();
            if (current.equals(goal)) {
                // Found destination; reconstruct path.
                List<Coordinate> path = new ArrayList<>();
                for (Coordinate node = current; node != null; node = cameFrom.get(node)) {
                    path.add(0, node);
                }
                return path;
            }
            // Explore neighbors.
            for (int i = 0; i < 8; i++) {
                int newX = current.getX() + dx[i];
                int newY = current.getY() + dy[i];
                Coordinate neighbor = new Coordinate(newX, newY);
                if (!isWithinMap(neighbor, map))
                    continue;
                // Check if the tile is walkable.
                Tile tile = map.getTileAt(neighbor);
                if (!tile.getType().isWalkable())
                    continue;
                if (cameFrom.containsKey(neighbor))
                    continue;
                cameFrom.put(neighbor, current);
                queue.add(neighbor);
            }
        }
        return null; // no path found.
    }

    // Check if a coordinate is within the bounds of the map.
    private boolean isWithinMap(Coordinate coord, GameMap map) {
        Tile[][] tiles = map.getTiles();
        int rows = tiles.length;
        int cols = tiles[0].length;
        return coord.getX() >= 0 && coord.getX() < rows && coord.getY() >= 0 && coord.getY() < cols;
    }

    // Example helper to check if the tile belongs to another player's farm.
    // (You will need to implement logic based on your game's rules, for example:
    private boolean isOtherPlayersFarm(Coordinate coord, GameMap map) {
        // For instance, if tile type is BUILDING and the coordinate is within a defined
        // farm boundary,
        // then return true.
        // This is a stub and should be replaced with your actual logic.
        Tile tile = map.getTileAt(coord);
        return tile.getType() == TileType.BUILDING;
    }
}