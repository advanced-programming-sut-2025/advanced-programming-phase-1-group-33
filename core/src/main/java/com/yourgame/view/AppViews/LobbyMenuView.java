package com.yourgame.view.AppViews;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.yourgame.Graphics.MenuAssetManager;
import com.yourgame.controller.AppController.LobbyMenuController;
import com.yourgame.controller.AppController.PreGameMenuController;

public class LobbyMenuView extends MenuBaseScreen {
    private final TextButton backButton = MenuAssetManager.getInstance().getButtons("back");
    private final LobbyMenuController controller;

    public LobbyMenuView() {
        this.controller = new LobbyMenuController();
        this.controller.setView(this);
    }

    @Override
    public void show() {
        Table table = new Table();
        table.setFillParent(true);
        table.bottom();

        table.add(backButton).width(200).center();

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor){
                controller.handleBackButton();
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
