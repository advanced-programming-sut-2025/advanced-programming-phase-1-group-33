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
        table.center();

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
        table.add(passwordField).width(600).padRight(20).padBottom(20);
        table.add(forgetButton).padBottom(20);
        table.row();
        table.add(securityAnswerLabel).padRight(20).padBottom(20);
        securityAnswerField.setVisible(false);
        table.add(securityAnswerField).width(600).padBottom(20);
        securityAnswerLabel.setVisible(false);
        table.add(findButton).padBottom(20);
        findButton.setVisible(false);
        stage.addActor(table);

        Table buttonTable = new Table();
        buttonTable.setFillParent(true);
        buttonTable.bottom().right();
        buttonTable.add(loginButton).padRight(20).padBottom(5).width(200).height(90);
        buttonTable.row();
        buttonTable.add(backButton).padRight(20).padBottom(20).width(200).height(90);
        stage.addActor(buttonTable);


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
                showMessage(result.message(), skin_Nz, 0, 20);
            }
        });

        forgetButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor){
                Result result = controller.handleForgetPasswordButton(usernameField.getText());
                if(!isForgetPasswordButtonClicked) {
                    playMenuSFX("popUp");
                    if (!result.success()) {
                        showMessage(result.message(), skin_Nz, 0, 20);
                    }
                    else {
                        isForgetPasswordButtonClicked = true;
                        usernameField.setDisabled(true);
                        securityAnswerField.setVisible(true);
                        securityAnswerLabel.setVisible(true);
                        findButton.setVisible(true);
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
                    showMessage(result.message(), skin_Nz, 0, 20);
                }
                else{
                    showMessage("Your Password is : " + result.message(), skin_Nz, 0, 20);
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
