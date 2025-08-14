package com.yourgame.controller.AppController;

import com.yourgame.Main;
import com.yourgame.model.App;
import com.yourgame.model.GameState;
import com.yourgame.model.UserInfo.Player;
import com.yourgame.model.enums.Commands.MenuTypes;
import com.yourgame.view.AppViews.GameScreen;
import com.yourgame.view.AppViews.LobbyMenuView;
import com.yourgame.view.AppViews.PreGameMenuView;

import java.util.List;

public class PreGameMenuController {
    private PreGameMenuView view;

    public void setView(PreGameMenuView view) {
        this.view = view;
    }

    public void handlePlayButton() {
        //This plays the game immediately
        view.stopBackgroundMusic();
        Player player = new Player(App.getCurrentUser());
        App.setGameState(new GameState(List.of(player)));
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new GameScreen());
        /////////////////////////////////////////////

    }

    public void handleLobbyButton() {
        App.setCurrentMenu(MenuTypes.LobbyMenu);
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new LobbyMenuView());
    }

    public void handleExitButton() {

    }
}
