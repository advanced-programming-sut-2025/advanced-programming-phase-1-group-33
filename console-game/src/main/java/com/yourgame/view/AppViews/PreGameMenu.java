package com.yourgame.view.AppViews;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.yourgame.controller.AppController.PreGameController;
import com.yourgame.model.App;
import com.yourgame.model.UserInfo.Player;
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
                return getNewGame(input, scanner);
            case LOAD_GAME:
                return getLoadGame(input);
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

private Response getNewGame(String input, Scanner scanner) {
    // Split input assuming format: "New_GAM user1 user2 user3"
    String stringUsers= PreGameMenuCommands.New_GAME.getGroup(input, "usernames");
    String[] tokens = stringUsers.trim().split("\\s+");
    // tokens[0] is the command; subsequent tokens are usernames.
    if (tokens.length < 1 || tokens.length > 3) {
        return new Response(false, "Please enter between 1 to 3 usernames for new game.");
    }
    // Extract usernames (tokens[1] to tokens[tokens.length-1])
    List<String> usernames = Arrays.asList(tokens);
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
    ArrayList<Player> players = new ArrayList<>();
    try {
        for (String username : usernames) {
            players.add(new Player(userDAO.loadUser(username)));
        }
    } catch (SQLException e) {
        return new Response(false, "Error loading users: " + e.getMessage());
    }

    System.out.println("players: ");
    for(Player p : players){
        System.out.println(p.getUsername());
    }
    // Change the current menu to the game menu.
    App.setCurrentMenu(MenuTypes.GameMenu);
    int[] startXForMap = {0 ,150 ,0 ,150 };
    int[] startYForMap = {0 , 0 , 125 ,125};
    Pattern pattern = Pattern.compile("^\\s*game\\s+map\\s+(?<mapNumber>\\d+)$");
    for(int i=0 ; i<players.size()  ; i++){
        String command;
        while((command = scanner.nextLine()) != null ){
            Matcher matcher = pattern.matcher(command);
            if(matcher.matches()){
                String Map = matcher.group(1);
                int mapNumber = Integer.parseInt(matcher.group(1));
                Response response  = controller.selectMapForCreateNewGame(mapNumber, players.get(i) ,
                        startXForMap[i] , startYForMap[i]);
                System.out.println(response);
                if(response.getSuccessful()){

                    break;
                }


            }
            else {
                System.out.println("invalid command , please try again!");
            }


        }



    }
    System.out.println(controller.createNewGame(players));


    return new Response(true, "New game started! Entering Game...");
}}
