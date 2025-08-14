package com.yourgame.view.AppViews;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.yourgame.Graphics.MenuAssetManager;
import com.yourgame.controller.AppController.PreGameMenuController;

public class PreGameMenuView extends MenuBaseScreen {
    private final TextButton exitButton = MenuAssetManager.getInstance().getButtons("exitPreGame");
    private final TextButton playButton = MenuAssetManager.getInstance().getButtons("play");
    private final TextButton lobbyButton = MenuAssetManager.getInstance().getButtons("lobby");
    private final PreGameMenuController controller;

    public PreGameMenuView() {
        this.controller = new PreGameMenuController();
        this.controller.setView(this);
    }

    @Override
    public void show() {
        Table table = new Table();
        table.setFillParent(true);
        table.bottom();

        table.add(playButton).width(200).pad(10);
        table.add(lobbyButton).width(200).pad(10);
        table.add(exitButton).width(200).pad(10).row();

        playButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor){
                controller.handlePlayButton();
            }
        });

        lobbyButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor){
                controller.handleLobbyButton();
            }
        });

        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor){
                controller.handleExitButton();
            }
        });

        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

    }
}


