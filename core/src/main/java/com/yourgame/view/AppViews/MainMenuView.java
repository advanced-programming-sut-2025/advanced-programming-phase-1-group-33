package com.yourgame.view.AppViews;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.yourgame.Graphics.MenuAssetManager;
import com.yourgame.controller.AppController.MainMenuController;
import com.yourgame.model.App;

public class MainMenuView extends MenuBaseScreen {
    //TODO : fix music button listener
    private Image redLine;
    private final TextButton signupButton = MenuAssetManager.getInstance().getButtons("signup");
    private final TextButton exitButton = MenuAssetManager.getInstance().getButtons("exit");
    private final TextButton loginButton = MenuAssetManager.getInstance().getButtons("login");
    private final TextButton logoutButton = MenuAssetManager.getInstance().getButtons("logout");
    private final TextButton profileButton = MenuAssetManager.getInstance().getButtons("profile");
    private final TextButton playButton = MenuAssetManager.getInstance().getButtons("play");
    private final MainMenuController controller;

    public MainMenuView(){
        this.controller = new MainMenuController();
        this.controller.setView(this);
    }

    @Override
    public void show() {
        redLine = MenuAssetManager.getInstance().getRedLine();
        redLine.setScale(0.1f);
        redLine.setPosition(20,730);
        redLine.setVisible(false);
        stage.addActor(redLine);

        Table table = createTable();
        stage.addActor(table);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
            // make music enable/disable
            if(Gdx.input.getX() >= 28 && Gdx.input.getX() <= 66 && Gdx.input.getY() >= 37 && Gdx.input.getY() <= 72){
                App.setIsMusicMuted(!App.isIsMusicMuted());
                if(App.isIsMusicMuted()){
                    stopBackgroundMusic();
                    redLine.setVisible(true);
                }
                else{
                    playBackgroundMusic();
                    redLine.setVisible(false);
                }
            }
        }
    }

    private Table createTable(){
        if(App.isIsMusicMuted())
            redLine.setVisible(true);

        Table table = new Table();
        if(App.getCurrentUser() == null){
            table.setFillParent(true);
            table.padTop(400);

            exitButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent changeEvent, Actor actor) {
                    controller.handleExit();
                }
            });
            loginButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent changeEvent, Actor actor) {
                    controller.handleGoingToLoginMenu();
                }
            });
            signupButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent changeEvent, Actor actor) {
                    controller.handleGoingToSignupMenu();
                }
            });

            table.add(exitButton).padRight(20).padLeft(5);
            table.add(loginButton).padRight(20);
            table.add(signupButton);
        }
        else{
            table.setFillParent(true);
            table.padTop(400);

            exitButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent changeEvent, Actor actor) {
                    controller.handleExit();
                }
            });
            logoutButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent changeEvent, Actor actor) {
                    controller.handleLogout();
                }
            });
            profileButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent changeEvent, Actor actor) {
                    controller.handleGoingToProfileMenu();
                }
            });
            playButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent changeEvent, Actor actor) {
                    controller.handleGoingToPreGameMenu();
                }
            });

            table.add(exitButton).padRight(20);
            table.add(logoutButton).padRight(20);
            table.add(profileButton).padRight(20);
            table.add(playButton).padRight(20);
        }
        return table;
    }
}

