package com.yourgame.controller.AppController;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.yourgame.Main;
import com.yourgame.network.ClientConnectionManager;
import com.yourgame.network.ResponseHolder;
import com.yourgame.network.protocol.RequestType;
import com.yourgame.network.protocol.ResponseType;
import com.yourgame.network.protocol.ResponseWrapper;
import com.yourgame.network.protocol.Auth.CreateLobbyRequest;
import com.yourgame.network.protocol.Auth.Lobby.JoinLobbyRequest;
import com.yourgame.network.protocol.Auth.Lobby.RefreshLobbiesResponse;
import com.yourgame.network.protocol.Auth.Lobby.SearchLobbyRequest;
import com.yourgame.network.protocol.Auth.Lobby.SearchLobbyResponse;
import com.yourgame.model.App;
import com.yourgame.model.enums.Commands.MenuTypes;
import com.yourgame.view.AppViews.LobbyMenuView;
import com.yourgame.view.AppViews.PreGameMenuView;
import com.yourgame.view.AppViews.LobbyRoomView;
import com.google.gson.Gson; // اضافه کردن import برای Gson

import java.util.concurrent.atomic.AtomicBoolean;


public class LobbyMenuController {
    private LobbyMenuView view;
    private final ClientConnectionManager connectionManager;
    private final ResponseHolder responseHolder;
    private final Gson gson;

    public LobbyMenuController() {
        this.connectionManager = Main.getMain().getConnectionManager();
        this.responseHolder = Main.getMain().getResponseHolder();
        // فرض می‌کنیم که ClientConnectionManager یک متد برای دسترسی به Gson دارد.
        // اگر ندارید، باید آن را اضافه کنید یا یک نمونه جدید از Gson اینجا ایجاد کنید.
        this.gson = new Gson(); 
    }

    public void setView(LobbyMenuView view) {
        this.view = view;
    }

    public void handleBackButton() {
        App.setCurrentMenu(MenuTypes.PreGameMenu);
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new PreGameMenuView());
    }

    public void handleCreateLobby(String name, boolean isPrivate, String password, boolean isVisible) {
        new Thread(() -> {
            try {
                CreateLobbyRequest request = new CreateLobbyRequest(name, isPrivate, password, isVisible);
                connectionManager.sendRequest(request);

                ResponseWrapper responseWrapper = (ResponseWrapper) responseHolder.getResponse(5000); // Wait for 5 seconds

                if (responseWrapper != null && responseWrapper.getType() == ResponseType.SUCCESSFUL) {
                    Gdx.app.postRunnable(() -> Main.getMain().setScreen(new LobbyRoomView()));
                } else {
                    String message = (responseWrapper != null) ? responseWrapper.getPayload() : "Connection timeout or unknown error.";
                    Gdx.app.postRunnable(() -> view.showMessage("Failed to create lobby: " + message, 0, 0));
                }
            } catch (InterruptedException e) {
                Gdx.app.postRunnable(() -> view.showMessage("Request interrupted.", 0, 0));
            }
        }).start();
    }

    public void handleRefreshLobbies() {
        new Thread(() -> {
            try {
                connectionManager.sendRequest(RequestType.REFRESH_LOBBIES);

                ResponseWrapper responseWrapper = (ResponseWrapper) responseHolder.getResponse(5000);

                if (responseWrapper != null && responseWrapper.getType() == ResponseType.SUCCESSFUL) {
                    RefreshLobbiesResponse response = gson.fromJson(responseWrapper.getPayload(), RefreshLobbiesResponse.class);
                    Gdx.app.postRunnable(() -> view.updateLobbyList(response.getVisibleLobbies()));
                } else {
                    String message = (responseWrapper != null) ? responseWrapper.getPayload() : "Connection timeout or unknown error.";
                    Gdx.app.postRunnable(() -> view.showMessage("Failed to refresh lobbies: " + message, 0, 0));
                }
            } catch (InterruptedException e) {
                Gdx.app.postRunnable(() -> view.showMessage("Request interrupted.", 0, 0));
            }
        }).start();
    }

    public void handleSearchLobby(String lobbyId) {
        new Thread(() -> {
            try {
                SearchLobbyRequest request = new SearchLobbyRequest(lobbyId);
                connectionManager.sendRequest(request);

                ResponseWrapper responseWrapper = (ResponseWrapper) responseHolder.getResponse(5000);

                if (responseWrapper != null && responseWrapper.getType() == ResponseType.SUCCESSFUL) {
                    SearchLobbyResponse response = gson.fromJson(responseWrapper.getPayload(), SearchLobbyResponse.class);
                    String message = (response.getFoundLobby() != null) ? "Lobby found: " + response.getFoundLobby().getName() : response.getMessage();
                    Gdx.app.postRunnable(() -> view.showMessage(message, 0, 0));
                } else {
                    String message = (responseWrapper != null) ? responseWrapper.getPayload() : "Connection timeout or unknown error.";
                    Gdx.app.postRunnable(() -> view.showMessage("Failed to search lobby: " + message, 0, 0));
                }
            } catch (InterruptedException e) {
                Gdx.app.postRunnable(() -> view.showMessage("Request interrupted.", 0, 0));
            }
        }).start();
    }

    public void handleJoinLobby(String lobbyId, String password) {
        new Thread(() -> {
            try {
                JoinLobbyRequest request = new JoinLobbyRequest(lobbyId, password);
                connectionManager.sendRequest(request);

                ResponseWrapper responseWrapper = (ResponseWrapper) responseHolder.getResponse(5000);

                if (responseWrapper != null && responseWrapper.getType() == ResponseType.SUCCESSFUL) {
                    Gdx.app.postRunnable(() -> Main.getMain().setScreen(new LobbyRoomView()));
                } else {
                    String message = (responseWrapper != null) ? responseWrapper.getPayload() : "Connection timeout or unknown error.";
                    Gdx.app.postRunnable(() -> view.showMessage("Failed to join lobby: " + message, 0, 0));
                }
            } catch (InterruptedException e) {
                Gdx.app.postRunnable(() -> view.showMessage("Request interrupted.", 0, 0));
            }
        }).start();
    }
}