package com.yourgame.view.AppViews;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.yourgame.controller.AppController.PreGameController;
import com.yourgame.model.App;
import com.yourgame.model.Player;
import com.yourgame.model.IO.Response;
import com.yourgame.model.enums.Commands.MenuTypes;
import com.yourgame.model.enums.Commands.PreGameMenuCommands;
import com.yourgame.persistence.UserDAO;
public class PreGameMenu implements AppMenu {
    PreGameController controller = new PreGameController();

    @Override
    public Response handleMenu(String input, Scanner scanner) {
        PreGameMenuCommands command = PreGameMenuCommands.parse(input);
        switch (command) {
            case New_GAME:
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
    // Split input assuming format: "New_GAM user1 user2 user3"
    String stringUsers= PreGameMenuCommands.New_GAME.getGroup(input, "usernames");
    String[] tokens = stringUsers.trim().split("\\s+");
    // tokens[0] is the command; subsequent tokens are usernames.
    if (tokens.length < 2 || tokens.length > 4) {
        return new Response(false, "Please enter between 1 to 3 usernames for new game.");
    }
    // Extract usernames (tokens[1] to tokens[tokens.length-1])
    java.util.List<String> usernames = Arrays.asList(Arrays.copyOfRange(tokens, 1, tokens.length));
    UserDAO userDAO = App.getUserDAO(); 
    try {
        for (String username : usernames) {
            if (userDAO.loadUser(username) == null) {
                return new Response(false, "User '" + username + "' does not exist. Cannot start game.");
            }
        }
    } catch (SQLException e) {
        return new Response(false, "Database error: " + e.getMessage());
    }

        // Load each user and create a Player object for each
    List<Player> players = new ArrayList<>();
    try {
        for (String username : usernames) {
            players.add(new Player(userDAO.loadUser(username)));
        }
    } catch (SQLException e) {
        return new Response(false, "Error loading users: " + e.getMessage());
    }
    
    controller.createNewGame(players);
    // Change the current menu to the game menu.
    App.setCurrentMenu(MenuTypes.GameMenu);
    return new Response(true, "New game started! Entering Game...");
}}
