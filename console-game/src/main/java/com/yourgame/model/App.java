package com.yourgame.model;
import com.yourgame.model.enums.Commands.MenuTypes;

import java.util.List;

public class App {
    private static MenuTypes currentMenuTypes = MenuTypes.LoginMenu;
    private static User currentUser;
    private static List<User> users;

    public static MenuTypes getCurrentMenu() {
        return currentMenuTypes;
    }

    public static void setCurrentMenu(MenuTypes currentMenuTypes) {
        App.currentMenuTypes = currentMenuTypes;
    }
    public User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        App.currentUser = currentUser;
    }
    public List<User> getUsers() {
        return users;
    }
    public void setUsers(List<User> users) {
        App.users = users;
    }

}
