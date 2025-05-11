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

public class GameMenu implements AppMenu{
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
        if(command == null){
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
        // throw new UnsupportedOperationException("Unimplemented method 'getExitFromGame'");
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