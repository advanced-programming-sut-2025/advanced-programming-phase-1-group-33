package com.yourgame.controller.AppController;

import com.badlogic.gdx.Gdx;
import com.yourgame.Main;
import com.yourgame.model.App;
import com.yourgame.model.GameState;
import com.yourgame.model.UserInfo.HandleStayedLoggedIn;
import com.yourgame.model.UserInfo.Player;
import com.yourgame.model.UserInfo.User;
import com.yourgame.model.enums.Commands.MenuTypes;
import com.yourgame.view.AppViews.*;

import java.io.IOException;
import java.util.List;

public class MainMenuController {
    private MainMenuView view;

    public void setView(MainMenuView view) {
        this.view = view;
    }

    public void handleGoingToSignupMenu(){
        App.setCurrentMenu(MenuTypes.SignupMenu);
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new SignupMenuView());
    }

    public void handleGoingToLoginMenu(){
        App.setCurrentMenu(MenuTypes.LoginMenu);
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new LoginMenuView());
    }

    public void handleGoingToPreGameMenu(){
        App.setCurrentMenu(MenuTypes.PreGameMenu);
        //view.stopBackgroundMusic();
        //Player player = new Player(App.getCurrentUser());
        //App.setGameState(new GameState(List.of(player)));
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new PreGameMenuView());
    }

    public void handleGoingToProfileMenu(){
        App.setCurrentMenu(MenuTypes.ProfileMenu);
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new ProfileMenuView());
    }

    public void handleLogout(){
        HandleStayedLoggedIn.getInstance().getAllUsers().remove(App.getCurrentUser());
        try {
            HandleStayedLoggedIn.getInstance().saveToFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        App.setCurrentUser(null);
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new MainMenuView());
    }

    public void handleExit(){
        Main.getMain().dispose();
        Gdx.app.exit();
    }

    public void handleIsThereALoggedInUser(){
        List<User> users = HandleStayedLoggedIn.getInstance().getAllUsers();
        if(!users.isEmpty())
            App.setCurrentUser(users.getFirst());
    }
}
