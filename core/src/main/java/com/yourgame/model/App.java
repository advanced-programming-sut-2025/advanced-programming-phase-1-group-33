package com.yourgame.model;
import com.yourgame.model.UserInfo.User;
import com.yourgame.model.enums.Commands.MenuTypes;
import com.yourgame.model.enums.SecurityQuestion;
import com.yourgame.persistence.UserDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class App {
    private static MenuTypes currentMenuTypes = MenuTypes.MainMenu;
    private static User currentUser =  null ; //new User("","","","", Gender.Male,SecurityQuestion.BirthDate,"");
    public static ArrayList<SecurityQuestion> securityQuestions = new ArrayList<>();
    private static List<User> users;
    private static UserDAO userDAO;
    private static GameState gameState;
    private static boolean isMusicMuted = false;

    static {
        try {
            userDAO = new UserDAO("jdbc:sqlite:StardewValley.db");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize UserDAO", e);
        }
    }

    public static GameState getGameState() {
        return gameState;
    }

    public static void setGameState(GameState gameState) {
        App.gameState = gameState;
    }

    public static UserDAO getUserDAO() {
        return userDAO;
    }

    public static void setUserDAO(UserDAO userDAO) {
        App.userDAO = userDAO;
    }

    public static MenuTypes getCurrentMenu() {
        return currentMenuTypes;
    }

    public static void setCurrentMenu(MenuTypes currentMenuTypes) {
        App.currentMenuTypes = currentMenuTypes;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        App.currentUser = currentUser;
    }

    public static List<User> getUsers() {
        return users;
    }

    public static void setUsers(List<User> users) {
        App.users = users;
    }

    public static boolean isMusicMuted() {
        return isMusicMuted;
    }

    public static void setIsMusicMuted(boolean isMusicMuted) {
        App.isMusicMuted = isMusicMuted;
    }
}
