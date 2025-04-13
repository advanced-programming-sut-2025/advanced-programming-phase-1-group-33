package com.yourgame.model.App;
import com.yourgame.model.App.enums.Menu;

import java.util.List;

public class App {
    private static Menu currentMenu = Menu.RegisterationView;
    private static User currentUser;
    private static List<User> users;

    public Menu getCurrentMenu() {
        return currentMenu;
    }

    public static void setCurrentMenu(Menu currentMenu) {
        App.currentMenu = currentMenu;
    }
    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        App.currentUser = currentUser;
    }
    public List<User> getUsers() {
        return users;
    }
    public void setUsers(List<User> users) {
        App.users = users;
    }

}
