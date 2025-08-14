package com.yourgame.controller.AppController;

import com.yourgame.Main;
import com.yourgame.model.App;
import com.yourgame.model.enums.Commands.MenuTypes;
import com.yourgame.view.AppViews.LobbyMenuView;
import com.yourgame.view.AppViews.PreGameMenuView;

public class LobbyMenuController {
    private LobbyMenuView view;

    public void setView(LobbyMenuView view) {
        this.view = view;
    }

    public void handleBackButton() {
        App.setCurrentMenu(MenuTypes.PreGameMenu);
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new PreGameMenuView());
    }
}
