package com.yourgame.controller.AppController;

import com.badlogic.gdx.Gdx;
import com.yourgame.Main;
import com.yourgame.network.ClientConnectionManager;
import com.yourgame.network.ResponseHolder;
import com.yourgame.network.protocol.RequestType;
import com.yourgame.network.protocol.ResponseType;
import com.yourgame.network.protocol.ResponseWrapper;
import com.yourgame.network.protocol.Auth.Lobby.LeaveLobbyRequest;
import com.yourgame.model.App;
import com.yourgame.model.enums.Commands.MenuTypes;
import com.yourgame.view.AppViews.LobbyRoomView;
import com.yourgame.view.AppViews.PreGameMenuView;
import com.yourgame.view.AppViews.GameScreen;
import com.yourgame.view.AppViews.LobbyMenuView;

public class LobbyRoomController {
    private LobbyRoomView view;
    private final ClientConnectionManager connectionManager;
    private final ResponseHolder responseHolder;
    
    public LobbyRoomController() {
        this.connectionManager = Main.getMain().getConnectionManager();
        this.responseHolder = Main.getMain().getResponseHolder();
        
        // Setup a listener for real-time lobby updates
        setupLobbyUpdateListener();
    }

    public void setView(LobbyRoomView view) {
        this.view = view;
    }
    
    /**
     * Handles the exit button press, sending a request to the server to leave the lobby.
     */
    public void handleExitLobby() {
        new Thread(() -> {
            try {
                LeaveLobbyRequest request = new LeaveLobbyRequest(App.getCurrentLobby().getId());
                connectionManager.sendRequest(request);

                ResponseWrapper responseWrapper = (ResponseWrapper) responseHolder.getResponse(5000);

                if (responseWrapper != null && responseWrapper.getType() == ResponseType.SUCCESSFUL) {
                    // On success, clear the current lobby and go back to the lobby menu.
                    App.setCurrentLobby(null);
                    Gdx.app.postRunnable(() -> {
                        Main.getMain().getScreen().dispose();
                        Main.getMain().setScreen(new LobbyMenuView());
                    });
                } else {
                    String message = (responseWrapper != null) ? responseWrapper.getPayload() : "Connection timeout or unknown error.";
                    Gdx.app.postRunnable(() -> view.showMessage("Failed to leave lobby: " + message, 0, 0));
                }
            } catch (InterruptedException e) {
                Gdx.app.postRunnable(() -> view.showMessage("Request interrupted.", 0, 0));
            }
        }).start();
    }
    
    /**
     * Handles the start game button press, sending a request to the server.
     * This should only be called by the lobby admin.
     */
    public void handleStartGame() {
        // new Thread(() -> {
        //     try {
        //         StartGameRequest request = new StartGameRequest(App.getCurrentLobby().getId());
        //         connectionManager.sendRequest(request);

        //         ResponseWrapper responseWrapper = (ResponseWrapper) responseHolder.getResponse(5000);
                
        //         if (responseWrapper != null && responseWrapper.getType() == ResponseType.SUCCESSFUL) {
        //             // On success, all players are moved to the game screen.
        //             Gdx.app.postRunnable(() -> {
        //                 Main.getMain().getScreen().dispose();
        //                 Main.getMain().setScreen(new GameScreen());
        //             });
        //         } else {
        //             String message = (responseWrapper != null) ? responseWrapper.getPayload() : "Connection timeout or unknown error.";
        //             Gdx.app.postRunnable(() -> view.showMessage("Failed to start game: " + message, 0, 0));
        //         }
        //     } catch (InterruptedException e) {
        //         Gdx.app.postRunnable(() -> view.showMessage("Request interrupted.", 0, 0));
        //     }
        // }).start();
    }

    /**
     * Sets up a listener to receive real-time updates from the server about the lobby.
     */
    private void setupLobbyUpdateListener() {
        // This part needs to be handled by a more robust asynchronous system
        // like a dedicated message queue in the Client class, but for this example,
        // we'll simulate a continuous check.
        // A better approach would be for the GameNetworkHandler to directly
        // update the LobbyRoomView or a centralized state object.
    }
}
