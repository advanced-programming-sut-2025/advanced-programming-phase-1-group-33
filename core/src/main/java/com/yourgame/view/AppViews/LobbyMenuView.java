package com.yourgame.view.AppViews;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.yourgame.Graphics.MenuAssetManager;
import com.yourgame.controller.AppController.LobbyMenuController;
import com.yourgame.network.lobby.Lobby;

import java.util.ArrayList;
import java.util.List;

public class LobbyMenuView extends MenuBaseScreen {
    private final LobbyMenuController controller;
    private final Skin skin;

    // UI elements
    private final Table mainTable;
    private final List<Lobby> lobbyList = new ArrayList<>();
    private final TextButton refreshButton;
    private final TextButton createLobbyButton;
    private final TextButton searchLobbyButton;
    private final TextButton backButton;


    private final ScrollPane scrollPane;
    private final Table lobbyTable;
    public LobbyMenuView() {
        this.controller = new LobbyMenuController();
        this.controller.setView(this);
        this.skin = MenuAssetManager.getInstance().getSkin(3); // استفاده از یکی از skin ها

        // ایجاد دکمه ها
        this.refreshButton = MenuAssetManager.getInstance().getButtons("search"); // استفاده مجدد از دکمه find به عنوان refresh
        this.createLobbyButton = MenuAssetManager.getInstance().getButtons("lobby_but"); // استفاده مجدد از دکمه lobby به عنوان create
        this.searchLobbyButton = MenuAssetManager.getInstance().getButtons("pregame"); // استفاده مجدد از دکمه pregame به عنوان search
        this.backButton = MenuAssetManager.getInstance().getButtons("back");


        this.lobbyTable = new Table(skin);
        this.lobbyTable.bottom().left().pad(10).defaults().pad(5);
        this.scrollPane = new ScrollPane(lobbyTable, skin);
        this.scrollPane.setScrollingDisabled(true, false);

        // جدول اصلی برای قرار دادن همه چیز
        this.mainTable = new Table(skin);
        this.mainTable.setFillParent(true);
        this.mainTable.top().pad(20);

        setupUI();
        setupListeners();


        controller.handleRefreshLobbies();
    }

        private void setupUI() {
        Label titleLabel = new Label("Lobbies", skin, "Bold");
        mainTable.add(titleLabel).padBottom(20).colspan(2).row();

        // جدول دکمه ها
        Table buttonTable = new Table(skin);
        buttonTable.defaults().pad(5).width(150).height(50);
        buttonTable.add(createLobbyButton).padRight(10);
        buttonTable.add(searchLobbyButton).padRight(10);
        buttonTable.add(refreshButton);

        mainTable.add(buttonTable).padBottom(20).row();

        // اضافه کردن ScrollPane
        mainTable.add(scrollPane).width(700).height(400).expandX().row();
        
        // دکمه بازگشت
        mainTable.add(backButton).width(200).height(50).padTop(20).row();

        stage.addActor(mainTable);
    }
    
    private void setupListeners() {
        // دکمه بازگشت
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                controller.handleBackButton();
            }
        });

        // دکمه رفرش
        refreshButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                controller.handleRefreshLobbies();
            }
        });

        // دکمه ایجاد لابی
        createLobbyButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showCreateLobbyDialog();
            }
        });

        // دکمه جستجوی لابی
        searchLobbyButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showSearchLobbyDialog();
            }
        });
    }

    /**
     * دیالوگ برای ایجاد لابی جدید را نمایش می دهد.
     */
    private void showCreateLobbyDialog() {
        Dialog dialog = new Dialog("Create New Lobby", skin);
        dialog.setModal(true);
        dialog.setKeepWithinStage(false);

        TextField lobbyNameField = new TextField("", skin);
        lobbyNameField.setMessageText("Lobby Name");
        CheckBox isPrivateCheckBox = new CheckBox("Private", skin);
        TextField passwordField = new TextField("", skin);
        passwordField.setMessageText("Password (if private)");
        passwordField.setVisible(false); // در ابتدا پنهان است
        CheckBox isVisibleCheckBox = new CheckBox("Visible", skin);
        isVisibleCheckBox.setChecked(true);

        isPrivateCheckBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                passwordField.setVisible(isPrivateCheckBox.isChecked());
            }
        });

        dialog.getContentTable().defaults().pad(5).width(200);
        dialog.getContentTable().add(new Label("Lobby Name:", skin));
        dialog.getContentTable().add(lobbyNameField).row();
        dialog.getContentTable().add(isPrivateCheckBox).colspan(2).row();
        dialog.getContentTable().add(new Label("Password:", skin));
        dialog.getContentTable().add(passwordField).row();
        dialog.getContentTable().add(isVisibleCheckBox).colspan(2).row();

        TextButton createButton = new TextButton("Create", skin);
        TextButton cancelButton = new TextButton("Cancel", skin);

        dialog.getButtonTable().add(createButton).pad(10);
        dialog.getButtonTable().add(cancelButton).pad(10);

        createButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (!lobbyNameField.getText().isEmpty()) {
                    controller.handleCreateLobby(
                        lobbyNameField.getText(), 
                        isPrivateCheckBox.isChecked(), 
                        passwordField.getText(), 
                        isVisibleCheckBox.isChecked()
                    );
                    dialog.hide();
                } else {
                    showMessage("Lobby name cannot be empty.", skin, 0, 0);
                }
            }
        });

        cancelButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dialog.hide();
            }
        });

        dialog.show(stage);
    }
    
    /**
     * دیالوگ برای جستجوی لابی را نمایش می دهد.
     */
    private void showSearchLobbyDialog() {
        Dialog dialog = new Dialog("Search Lobby", skin);
        dialog.setModal(true);

        TextField lobbyIdField = new TextField("", skin);
        lobbyIdField.setMessageText("Lobby ID");

        dialog.getContentTable().defaults().pad(5).width(200);
        dialog.getContentTable().add(new Label("Lobby ID:", skin));
        dialog.getContentTable().add(lobbyIdField).row();

        TextButton searchButton = new TextButton("Search", skin);
        TextButton cancelButton = new TextButton("Cancel", skin);

        dialog.getButtonTable().add(searchButton).pad(10);
        dialog.getButtonTable().add(cancelButton).pad(10);

        searchButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (!lobbyIdField.getText().isEmpty()) {
                    controller.handleSearchLobby(lobbyIdField.getText());
                    dialog.hide();
                }
            }
        });

        cancelButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dialog.hide();
            }
        });

        dialog.show(stage);
    }

    /**
     * لیست لابی ها را در UI به روزرسانی می کند.
     */
    public void updateLobbyList(List<Lobby> lobbies) {
        this.lobbyList.clear();
        this.lobbyList.addAll(lobbies);
        
        lobbyTable.clear(); // پاک کردن لابی های قبلی
        
        if (lobbies.isEmpty()) {
            lobbyTable.add(new Label("No public lobbies available.", skin)).center().pad(20);
        } else {
            for (Lobby lobby : lobbies) {
                addLobbyItem(lobby);
            }
        }
    }

    private void addLobbyItem(Lobby lobby) {
        Table lobbyItem = new Table(skin);
        lobbyItem.setBackground(skin.getDrawable("default-round")); // یک پس زمینه برای آیتم لابی
        lobbyItem.defaults().pad(5);

        Label nameLabel = new Label(lobby.getName(), skin, "default");
        Label playersLabel = new Label(lobby.getPlayers().size() + "/4", skin, "default");
        Label statusLabel = new Label(lobby.isPrivate() ? "Private" : "Public", skin, "default");

        TextButton joinButton = new TextButton("Join", skin);

        lobbyItem.add(nameLabel).width(200).align(Align.left);
        lobbyItem.add(playersLabel).width(50).align(Align.center);
        lobbyItem.add(statusLabel).width(100).align(Align.center);
        lobbyItem.add(joinButton).width(80).padLeft(20);
        
        joinButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (lobby.isPrivate()) {
                    showJoinLobbyDialog(lobby.getId());
                } else {
                    controller.handleJoinLobby(lobby.getId(), null);
                }
            }
        });

        lobbyTable.add(lobbyItem).expandX().fillX().row();
    }
    
    /**
     * دیالوگ ورود رمز عبور برای پیوستن به لابی خصوصی را نمایش می دهد.
     */
    private void showJoinLobbyDialog(String lobbyId) {
        Dialog dialog = new Dialog("Enter Password", skin);
        dialog.setModal(true);

        TextField passwordField = new TextField("", skin);
        passwordField.setMessageText("Password");
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');

        dialog.getContentTable().defaults().pad(5).width(200);
        dialog.getContentTable().add(new Label("Password:", skin));
        dialog.getContentTable().add(passwordField).row();

        TextButton joinButton = new TextButton("Join", skin);
        TextButton cancelButton = new TextButton("Cancel", skin);

        dialog.getButtonTable().add(joinButton).pad(10);
        dialog.getButtonTable().add(cancelButton).pad(10);

        joinButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                controller.handleJoinLobby(lobbyId, passwordField.getText());
                dialog.hide();
            }
        });
        
        cancelButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dialog.hide();
            }
        });

        dialog.show(stage);
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
