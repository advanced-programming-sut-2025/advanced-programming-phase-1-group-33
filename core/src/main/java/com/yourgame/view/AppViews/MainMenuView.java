package com.yourgame.view.AppViews;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.yourgame.Graphics.MenuAssetManager;
import com.yourgame.controller.AppController.MainMenuController;
import com.yourgame.model.App;

public class MainMenuView extends MenuBaseScreen {

    private Image redLine;
    private ImageButton signupButton = MenuAssetManager.getInstance().getButtons("signup");
    private ImageButton exitButton = MenuAssetManager.getInstance().getButtons("exit");
    private ImageButton loginButton = MenuAssetManager.getInstance().getButtons("login");
    private ImageButton logoutButton = MenuAssetManager.getInstance().getButtons("logout");
    private ImageButton profileButton = MenuAssetManager.getInstance().getButtons("profile");
    private ImageButton playButton = MenuAssetManager.getInstance().getButtons("play");
    private final MainMenuController controller;

    public MainMenuView(){
        this.controller = new MainMenuController();
    }

    @Override
    public void show() {
        Table table = createTable();
        stage.addActor(table);

        redLine = MenuAssetManager.getInstance().getRedLine();
        redLine.setScale(0.1f);
        redLine.setPosition(20,730);
        redLine.setVisible(false);
        stage.addActor(redLine);

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

            table.add(exitButton).padRight(30);
            table.add(loginButton).padRight(30);
            table.add(signupButton).padRight(30);
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

            table.add(exitButton).padRight(30);
            table.add(logoutButton).padRight(30);
            table.add(profileButton).padRight(30);
            table.add(playButton).padRight(30);
        }
        return table;
    }
}

