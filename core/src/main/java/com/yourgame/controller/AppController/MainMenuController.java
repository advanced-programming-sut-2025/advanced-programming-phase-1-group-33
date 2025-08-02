package com.yourgame.controller.AppController;

import com.badlogic.gdx.Gdx;
import com.yourgame.Main;
import com.yourgame.model.App;
import com.yourgame.model.enums.Commands.MenuTypes;
import com.yourgame.view.AppViews.*;

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
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new GameScreen());
    }

    public void handleGoingToProfileMenu(){
        App.setCurrentMenu(MenuTypes.ProfileMenu);
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new ProfileMenuView());
    }

    public void handleLogout(){
        App.setCurrentUser(null);
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new MainMenuView());
    }

    public void handleExit(){
        Main.getMain().dispose();
        Gdx.app.exit();
    }
}
