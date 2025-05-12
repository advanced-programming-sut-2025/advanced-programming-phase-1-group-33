package com.yourgame.controller.GameController;

import com.yourgame.model.GameState;
import com.yourgame.model.User;
import com.yourgame.model.IO.Request;
import com.yourgame.model.IO.Response;
import com.yourgame.model.enums.Weather;
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
        // TODO Auto-generated method stub
        return new Response(true, "Tomorrow Weather is " + gameState.getWeather().getTomorrowWeather());
    }

    public Response getBuildGreenHouse() {
        // TODO Auto-generated method stub
        return new Response(false, "To Do We need to create Map after that we can have green house");
    }
}