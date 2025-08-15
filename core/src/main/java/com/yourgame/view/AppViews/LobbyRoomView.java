package com.yourgame.view.AppViews;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.yourgame.Graphics.MenuAssetManager;
import com.yourgame.controller.AppController.LobbyRoomController;
import com.yourgame.view.AppViews.MenuBaseScreen;
import com.yourgame.model.App;

import java.util.List;

public class LobbyRoomView extends MenuBaseScreen {
    private final LobbyRoomController controller;
    private final TextButton exitButton = MenuAssetManager.getInstance().getButtons("exit");
    private final TextButton startGameButton = MenuAssetManager.getInstance().getButtons("play"); // دکمه شروع بازی
    private final Skin skin; // Declaring the skin variable

    private final Table mainTable;
    private final Table playerListTable;

    public LobbyRoomView() {
        this.controller = new LobbyRoomController();
        this.controller.setView(this);
        this.skin = MenuAssetManager.getInstance().getSkin(3); // Initializing the skin variable

        this.mainTable = new Table();
        this.mainTable.setFillParent(true);
        this.mainTable.top().pad(20);

        this.playerListTable = new Table(skin);
        this.playerListTable.top().pad(10);

        setupUI();
        setupListeners();
    }

    private void setupUI() {
        Label titleLabel = new Label("Lobby Room", skin, "Bold");
        mainTable.add(titleLabel).padBottom(20).row();

        Label lobbyInfoLabel = new Label(
                "Lobby: " + App.getCurrentLobby().getName() + " (ID: " + App.getCurrentLobby().getId() + ")", skin,
                "default");
        mainTable.add(lobbyInfoLabel).padBottom(20).row();

        mainTable.add(playerListTable).expand().fillX().row();

        // Buttons table
        Table buttonsTable = new Table();
        buttonsTable.defaults().pad(10).width(200);
        buttonsTable.add(exitButton);
        buttonsTable.add(startGameButton); // این دکمه بعداً بر اساس ادمین بودن کاربر قابل مشاهده می‌شود
        mainTable.add(buttonsTable).padTop(20).row();

        stage.addActor(mainTable);
    }

    private void setupListeners() {
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // این متد باید توسط کنترلر پیاده‌سازی شود
                controller.handleExitLobby();
            }
        });

        startGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // این متد باید توسط کنترلر پیاده‌سازی شود
                controller.handleStartGame();
            }
        });
    }

    /**
     * این متد لیست بازیکنان را در UI به‌روزرسانی می‌کند.
     */
    public void updatePlayerList(List<String> players) {
        playerListTable.clear();
        Label playersTitle = new Label("Players:", skin, "default");
        playerListTable.add(playersTitle).align(Align.left).padBottom(10).row();

        if (players.isEmpty()) {
            playerListTable.add(new Label("No players in lobby.", skin)).row();
        } else {
            for (String player : players) {
                Label playerLabel = new Label(player, skin, "default");
                playerListTable.add(playerLabel).align(Align.left).row();
            }
        }
    }

    // متدها و فیلدهای دیگر به صورت موقت در اینجا اضافه شده‌اند تا کد compile شود
    public void showMessage(String message, float x, float y) {
        // پیاده سازی نمایش پیام
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }
}
