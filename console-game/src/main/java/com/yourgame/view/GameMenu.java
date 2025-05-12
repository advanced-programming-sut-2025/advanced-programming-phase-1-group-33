package com.yourgame.view;

import java.util.Scanner;

import com.yourgame.controller.AppController.LoginMenuController;
import com.yourgame.controller.GameController.GameController;
import com.yourgame.model.App;
import com.yourgame.model.IO.Request;
import com.yourgame.model.IO.Response;
import com.yourgame.model.enums.Commands.LoginMenuCommands;
import com.yourgame.model.enums.Commands.MenuTypes;
import com.yourgame.model.enums.Commands.GameViewCommands;

import com.yourgame.view.AppViews.AppMenu;

public class GameMenu implements AppMenu {
    private GameController controller;

    public Response handleMenu(String input, Scanner scanner) {

        if (controller == null) {
            // Initialize only when game state exists
            if (App.getCurrentUser() == null || App.getGameState() == null) {
                App.setCurrentMenu(MenuTypes.LoginMenu);
                return new Response(false, "Game not initialized. Please start a new game.");
            }
            controller = new GameController();
        }

        GameViewCommands command = GameViewCommands.parse(input);
        if (command == null) {
            return getInvalidCommand();
        }
        switch (command) {
        case NEXT_TURN:
            return getNextTurn();
        case TIME:
            return getTime();
        case DATE:
            return getDate();
        case DATETIME:
            return getDateTime();
        case DAY_OF_WEEK:
            return getDayOfWeek();
        case SEASON:
            return getSeason();
        case CHEAT_ADVANCE_DATE:
            return getAdvancedDate(input);
        case CHEAT_ADVANCE_TIME:
            return getAdvancedTime(input);
        case CHEAT_THOR:
            return getCheatThor(input);
        case WEATHER:
            return getWeather();
        case CHEAT_WEATHER_SET:
            return getCheatWeather(input);
        case WEATHER_FORECAST: 
            return getWeatherForcast();
        case GREEN_HOUSE_BUILD:
            return getBuildGreenHouse(); 
        case WALK:
            return getWalk(input); 
        case PRINT_MAP:
            return getPrintMap(input); 
        case HELP_READING_MAP:
            return getHelpRedning(); 
        case ENERGY_SHOW:
            return getEnergyShow();
        case ENERGY_SET_VALUE:
            return getEnergySet(input);
        case ENERGY_UNLIMITED:
            return getEnergyUnlimited();
        case Go_Back:
            App.setCurrentMenu(MenuTypes.MainMenu);
            return new Response(true, "Going Back to MainMenu");
        case EXIT_MENU:
            return getExitMenu(input);
        case ENTER_MENU:
            return getEnterMenu(input);
        case SHOW_MENU:
            return getShowMenu(input);
        default:
            return getInvalidCommand();
        }
    }

    private Response getHelpRedning() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getHelpRedning'");
    }


    private Response getPrintMap(String input) {
        // TODO Auto-generated method stub
        return controller.PrintMap(); 
    }


    private Response getWalk(String input) {
        // TODO Auto-generated method stub
        Request request = new Request(input);
        request.body.put("x", GameViewCommands.WALK.getGroup(input, "x"));
        request.body.put("y", GameViewCommands.WALK.getGroup(input, "y"));

        return controller.getWalk(request); 
    }


    private Response getBuildGreenHouse() {
        return controller.getBuildGreenHouse();
    }

    private Response getWeatherForcast() {
        return controller.getWeatherForcast();
    }

    private Response getCheatWeather(String input) {
        Request request = new Request(input);
        request.body.put("Type", GameViewCommands.CHEAT_WEATHER_SET.getGroup(input, "Type"));
        // return controller.getAdvancedTime(request);
        return controller.cheatWeather(request);
    }

    private Response getWeather() {
        // TODO Auto-generated method stub
        return controller.getWeather();
    }

    private Response getCheatThor(String input) {
        Request request = new Request(input);
        request.body.put("X", GameViewCommands.CHEAT_THOR.getGroup(input, "X"));
        request.body.put("Y", GameViewCommands.CHEAT_THOR.getGroup(input, "Y"));
        // TODO Auto-generated methd stub
        // return controller.getAdvancedTime(request);
        return controller.cheatThor(request);
    }

    private Response getAdvancedTime(String input) {
        Request request = new Request(input);
        request.body.put("amount", GameViewCommands.CHEAT_ADVANCE_TIME.getGroup(input, "amount"));
        return controller.getAdvancedTime(request);
    }

    private Response getAdvancedDate(String input) {
        Request request = new Request(input);
        request.body.put("amount", GameViewCommands.CHEAT_ADVANCE_DATE.getGroup(input, "amount"));
        return controller.getAdvancedDate(request);
    }

    private Response getSeason() {
        // TODO Auto-generated method stub
        return controller.getSeason();
    }

    private Response getDayOfWeek() {
        // TODO Auto-generated method stub
        return controller.getDayOfWeek();
    }

    private Response getDateTime() {
        // TODO Auto-generated method stub
        return controller.getDateTime();
    }

    private Response getDate() {
        return controller.getDate();
    }

    private Response getTime() {
        return controller.getTime();
    }

    private Response getNextTurn() {
        return controller.NextTurn();
    }

    private Response getExitFromGame(String input, Scanner scanner) {
        App.setCurrentMenu(MenuTypes.ExitMenu);
        return new Response(true, "Exit from the Game Until We make some menu for it");
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method
        // 'getExitFromGame'");
    }

    private Response getEnergyUnlimited() {
        return controller.setCurrentPlayerEnergyUnlimited();
    }

    private Response getEnergySet(String input) {
        Request request = new Request(input);
        request.body.put("value", GameViewCommands.ENERGY_SET_VALUE.getGroup(input, "value"));
        return controller.setCurrentPlayerEnergy(request);
    }

    private Response getEnergyShow() {
        return controller.getEnergy();
    }

    private static Response getExitMenu(String input) {
        Request request = new Request(input);
        Response response = LoginMenuController.handleExitMenu(request);
        return response;
    }

    private static Response getEnterMenu(String input) {
        Request request = new Request(input);
        request.body.put("menuName", LoginMenuCommands.ENTER_MENU.getGroup(input, "menuName"));
        Response response = LoginMenuController.handleEnterMenu(request);
        return response;
    }

    private static Response getShowMenu(String input) {
        Request request = new Request(input);
        Response response = LoginMenuController.handleShowMenu(request);
        return response;
    }
}