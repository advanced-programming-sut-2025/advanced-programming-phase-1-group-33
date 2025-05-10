package com.yourgame.view.AppViews;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.yourgame.controller.AppController.PreGameController;
import com.yourgame.model.App;
import com.yourgame.model.GameState;
import com.yourgame.model.NPC;
import com.yourgame.model.Player;
import com.yourgame.model.IO.Response;
import com.yourgame.model.Map.GameMap;
import com.yourgame.model.Shop.Shop;
import com.yourgame.model.Weather.TimeSystem;
import com.yourgame.model.Weather.WeatherSystem;
import com.yourgame.model.enums.Commands.MenuTypes;
import com.yourgame.model.enums.Commands.PreGameMenuCommands;

public class PreGameMenu implements AppMenu {
    PreGameController controller = new PreGameController();

    @Override
    public Response handleMenu(String input, Scanner scanner) {
        PreGameMenuCommands command = PreGameMenuCommands.parse(input);
        switch (command) {
            case New_GAM:
                return getNewGame(input);
            case LOAD_GAME:
                return getLoadGame(input);
            case GAME_MAP:
                return getGameMap(input);
            case GO_Back:
                App.setCurrentMenu(MenuTypes.MainMenu);
                return new Response(true, "Going Back to MainMenu");
            default:
                return getInvalidCommand();
        }

    }

    private Response getGameMap(String input) {
        // Todo
        return new Response(true, "by default map one is selected");
    }

    private Response getLoadGame(String input) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getLoadGame'");
    }

    private Response getNewGame(String input) {
        controller.createNewGame(input);
        // Change the current menu to your game menu
        App.setCurrentMenu(MenuTypes.GameMenu);
        return new Response(true, "New game started! Entering Game...");
    }
}
