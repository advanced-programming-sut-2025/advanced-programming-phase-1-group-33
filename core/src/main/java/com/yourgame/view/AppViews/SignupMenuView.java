package com.yourgame.view.AppViews;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.yourgame.Graphics.MenuAssetManager;
import com.yourgame.controller.AppController.SignUpMenuController;
import com.yourgame.model.Result;
import com.yourgame.model.enums.Gender;
import com.yourgame.model.enums.SecurityQuestion;

public class SignupMenuView extends MenuBaseScreen {

    private final SignUpMenuController controller;
    private final Skin skin_Nz = MenuAssetManager.getInstance().getSkin(3);

    final TextField usernameField = new TextField("",skin_Nz,"default");
    final TextField passwordField = new TextField("",skin_Nz,"default");
    final TextField confirmPasswordField = new TextField("",skin_Nz,"default");
    final TextField emailField = new TextField("",skin_Nz,"default");
    final TextField nicknameField = new TextField("",skin_Nz,"default");
    final SelectBox<Gender> genderSelectBox = new SelectBox<>(skin_Nz);
    final SelectBox<SecurityQuestion> securityQuestionSelectBox = new SelectBox<>(skin_Nz);
    final TextField securityAnswerField = new TextField("", skin_Nz,"default");

    public SignupMenuView() {
        this.controller = new SignUpMenuController();
        this.controller.setView(this);
    }

    @Override
    public void show() {
        usernameField.setMessageText(" Please enter your username...");
        passwordField.setMessageText(" Please enter your password...");
        confirmPasswordField.setMessageText(" Please re-enter your password...");
        emailField.setMessageText(" Please enter your email...");
        nicknameField.setMessageText(" Please enter your nickname...");
        genderSelectBox.setItems(Gender.values());
        securityQuestionSelectBox.setItems(SecurityQuestion.values());
        securityAnswerField.setMessageText(" Please enter your answer...");


        final Label usernameLabel = new Label("Username ", skin_Nz, "Bold");
        final Label passwordLabel = new Label("Password ", skin_Nz, "Bold");
        final Label passwordConfirmLabel = new Label("", skin_Nz, "Bold");
        final Label emailLabel = new Label("Email ", skin_Nz, "Bold");
        final Label nicknameLabel = new Label("Nickname ", skin_Nz, "Bold");
        final Label genderLabel = new Label("Gender ", skin_Nz, "Bold");
        final Label securityQuestionLabel = new Label("Security\nQuestion ", skin_Nz, "Bold");
        final Label answerLabel = new Label("", skin_Nz, "Bold");

        final TextButton backButton = MenuAssetManager.getInstance().getButtons("back");
        final TextButton submitButton = MenuAssetManager.getInstance().getButtons("submit");
        final TextButton randomPasswordButton = MenuAssetManager.getInstance().getButtons("random");

        Gdx.input.setInputProcessor(stage);
        Table firstTable = new Table();
        firstTable.setFillParent(true);
        firstTable.center();
        firstTable.add(usernameLabel).padRight(20).padBottom(10);
        firstTable.add(usernameField).width(600).padBottom(10);
        firstTable.row();
        firstTable.add(passwordLabel).padRight(20).padBottom(10);
        firstTable.add(passwordField).width(600).padBottom(10);
        firstTable.add(randomPasswordButton).padLeft(20);
        firstTable.row();
        firstTable.add(passwordConfirmLabel).padRight(20).padBottom(10);
        firstTable.add(confirmPasswordField).width(600).padBottom(10);
        firstTable.row();
        firstTable.add(emailLabel).padRight(20).padBottom(10);
        firstTable.add(emailField).width(600).padBottom(10);
        firstTable.row();
        firstTable.add(nicknameLabel).padRight(20).padBottom(10);
        firstTable.add(nicknameField).width(600).padBottom(10);
        firstTable.row();
        firstTable.add(genderLabel).padRight(20).padBottom(10);
        firstTable.add(genderSelectBox).width(600).padBottom(10);
        stage.addActor(firstTable);

        Table secondTable = new Table();
        secondTable.setFillParent(true);
        secondTable.center().padTop(500).padRight(170);
        secondTable.add(securityQuestionLabel).padRight(20);
        secondTable.add(securityQuestionSelectBox).width(600);
        secondTable.row();
        secondTable.add(answerLabel).padBottom(60);
        secondTable.add(securityAnswerField).width(600).padBottom(60);
        secondTable.setVisible(false);
        stage.addActor(secondTable);

        Table buttonTable = new Table();
        buttonTable.setFillParent(true);
        buttonTable.bottom().right();
        buttonTable.add(submitButton).padRight(20).padBottom(5).width(250).height(90);
        buttonTable.row();
        buttonTable.add(backButton).padRight(20).padBottom(20).width(200).height(90);
        stage.addActor(buttonTable);


        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor){
                controller.handleBackButton();
            }
        });

        submitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor){
                Result result;
                if(!secondTable.isVisible()){
                    result = controller.handleSignUpButton();
                    if(!result.success()){
                        if(result.message().contains("exists")){
                            showMessageWithButton(result.message(),result.message().substring(54,result.message().length()));
                        }
                        else {
                            showMessage(result.message(), skin_Nz, 0, 5);
                        }
                    }
                    else {
                        usernameField.setDisabled(true);
                        passwordField.setDisabled(true);
                        confirmPasswordField.setDisabled(true);
                        emailField.setDisabled(true);
                        nicknameField.setDisabled(true);
                        firstTable.setPosition(firstTable.getX(),firstTable.getY() + 100);
                        secondTable.setVisible(true);
                    }
                }
                else{
                    result = controller.handleSecurityAnswer();
                    if(!result.success()){
                        showMessage(result.message(), skin_Nz, 0, 5);
                    }
                }
            }
        });

        randomPasswordButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor){
                playMenuSFX("popUp");
                String generatedPassword = controller.generateRandomPassword();
                passwordField.setText(generatedPassword);
                confirmPasswordField.setText(generatedPassword);
                showMessage("Your password is : " + passwordField.getText() , skin_Nz, 0, 50);
            }
        });

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
            case "confirmPassword" -> {return confirmPasswordField.getText();}
            case "security answer" -> {return securityAnswerField.getText();}
            case "email" -> {return emailField.getText();}
            case "nickname" -> {return nicknameField.getText();}
            default -> {return null;}
        }
    }

    public SecurityQuestion getSecurityQuestion() {
        return securityQuestionSelectBox.getSelected();
    }

    public Gender getGender() {
        return genderSelectBox.getSelected();
    }

    private void showMessageWithButton(String message, String newUsername) {
        playMenuSFX("popUp");
        Dialog dialog = new Dialog("",skin_Nz) {
            @Override
            protected void result(Object object) {
                if (object.equals(true)) {
                    usernameField.setText(newUsername);
                }
            }
        };
        dialog.text(message);
        dialog.button("Accept", true);
        dialog.button("Reject", false);
        dialog.show(stage);
        dialog.setPosition((stage.getWidth()-dialog.getWidth())/2f , 20);
    }
}
