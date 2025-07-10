package com.yourgame.controller.AppController;

import com.yourgame.Main;
import com.yourgame.model.App;
import com.yourgame.model.IO.Request;
import com.yourgame.model.IO.Response;
import com.yourgame.model.enums.Commands.MenuTypes;
import com.yourgame.model.enums.Commands.MainMenuCommands;
import com.yourgame.view.AppViews.LoginMenuView;
import com.yourgame.view.AppViews.PreGameMenuView;
import com.yourgame.view.AppViews.SignupMenuView;

public class MainMenuController {
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
        Main.getMain().setScreen(new PreGameMenuView());
    }
}
