package com.yourgame.view.AppViews;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.yourgame.Graphics.MenuAssetManager;
import com.yourgame.controller.AppController.LoginMenuController;
import com.yourgame.model.Result;

public class LoginMenuView extends MenuBaseScreen{

    private final LoginMenuController controller;
    private final Skin skin_Nz = MenuAssetManager.getInstance().getSkin(3);

    final TextField usernameField = new TextField("",skin_Nz,"default");
    final TextField passwordField = new TextField("",skin_Nz,"default");

    final TextField securityAnswerField = new TextField("", skin_Nz,"default");
    final Label securityAnswerLabel = new Label("Answer", skin_Nz, "Bold");
    final TextButton findButton = MenuAssetManager.getInstance().getButtons("find");

    boolean isForgetPasswordButtonClicked = false;

    public LoginMenuView() {
        controller = new LoginMenuController();
        controller.setView(this);
    }

    @Override
    public void show() {
        Table table = new Table();
        table.setFillParent(true);
        table.setPosition(-170,20);

        usernameField.setMessageText(" Please enter your username...");
        passwordField.setMessageText(" Please enter your password...");

        final Label usernameLabel = new Label("Username ", skin_Nz, "Bold");
        final Label passwordLabel = new Label("Password ", skin_Nz, "Bold");

        final TextButton backButton = MenuAssetManager.getInstance().getButtons("back");
        final TextButton loginButton = MenuAssetManager.getInstance().getButtons("login2");
        final TextButton forgetButton = MenuAssetManager.getInstance().getButtons("forget");

        table.add(usernameLabel).padRight(20).padBottom(20);
        table.add(usernameField).width(600).padBottom(20);
        table.row();
        table.add(passwordLabel).padRight(20).padBottom(20);
        table.add(passwordField).width(600).padBottom(20);
        table.row();
        stage.addActor(table);
        backButton.setPosition(1000,20);
        stage.addActor(backButton);
        loginButton.setPosition(1000,140);
        stage.addActor(loginButton);
        forgetButton.setPosition(900,330);
        stage.addActor(forgetButton);

        securityAnswerField.setVisible(false);
        securityAnswerField.setWidth(600);
        securityAnswerField.setPosition(270,350);
        stage.addActor(securityAnswerField);

        securityAnswerLabel.setVisible(false);
        securityAnswerLabel.setPosition(110,360);
        stage.addActor(securityAnswerLabel);

        findButton.setVisible(false);
        findButton.setPosition(940,340);
        stage.addActor(findButton);

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor){
                controller.handleBackButton();
            }
        });

        loginButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor){
                Result result = controller.handleLoginButton();
                playMenuSFX("popUp");
                showMessage(result.message(), skin_Nz, -70, 20);
            }
        });

        forgetButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor){
                Result result = controller.handleForgetPasswordButton(usernameField.getText());
                if(!isForgetPasswordButtonClicked) {
                    playMenuSFX("popUp");
                    if (!result.success()) {
                        showMessage(result.message(), skin_Nz, -70, 20);
                    }
                    else {
                        isForgetPasswordButtonClicked = true;
                        usernameField.setDisabled(true);
                        securityAnswerField.setVisible(true);
                        securityAnswerLabel.setVisible(true);
                        findButton.setVisible(true);
                        table.setPosition(table.getX(), table.getY() + 150);
                        forgetButton.setPosition(forgetButton.getX(), forgetButton.getY() + 150);
                        securityAnswerField.setMessageText(result.message());
                    }
                }
            }
        });

        findButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor){
                playMenuSFX("popUP");
                Result result = controller.handleFindButton(securityAnswerField.getText());
                if(!result.success()){
                    showMessage(result.message(), skin_Nz, -70, 20);
                }
                else{
                    showMessage("Your Password is : " + result.message(), skin_Nz, -70, 20);
                    passwordField.setText(result.message());
                }
            }
        });

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public String getUserInfo(String type){
        switch(type){
            case "username" -> {return usernameField.getText();}
            case "password" -> {return passwordField.getText();}
            default -> {return null;}
        }
    }
}
