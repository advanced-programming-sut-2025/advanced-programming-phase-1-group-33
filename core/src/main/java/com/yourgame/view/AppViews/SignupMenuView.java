package com.yourgame.view.AppViews;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.yourgame.Graphics.MenuAssetManager;
import com.yourgame.controller.AppController.SignUpMenuController;
import com.yourgame.model.enums.Gender;
import com.yourgame.model.enums.SecurityQuestion;

public class SignupMenuView extends MenuBaseScreen {

    private SignUpMenuController controller;
    private final Skin skin_Sepehr = MenuAssetManager.getInstance().getSkin(1);
    private final Skin skin_Nz = MenuAssetManager.getInstance().getSkin(3);

    public SignupMenuView() {
        this.controller = new SignUpMenuController();
        this.controller.setView(this);
    }

    @Override
    public void show() {
        final Label usernameLabel = new Label("Username ", skin_Nz, "Bold");
        final Label passwordLabel = new Label("Password ", skin_Nz, "Bold");
        final Label emailLabel = new Label("Email ", skin_Nz, "Bold");
        final Label genderLabel = new Label("Gender ", skin_Nz, "Bold");
        final Label securityQuestionLabel = new Label("Security\nQuestion ", skin_Nz, "Bold");
        final Label answerLabel = new Label("", skin_Nz, "Bold");

        final TextField usernameField = new TextField("",skin_Nz,"default");
        usernameField.setMessageText(" Please enter your username...");
        final TextField passwordField = new TextField("",skin_Nz,"default");
        passwordField.setMessageText(" Please enter your password...");
        final TextField emailField = new TextField("",skin_Nz,"default");
        emailField.setMessageText(" Please enter your email...");
        final SelectBox<Gender> genderSelectBox = new SelectBox<>(skin_Nz);
        genderSelectBox.setItems(Gender.values());
        final SelectBox<SecurityQuestion> securityQuestionSelectBox = new SelectBox<>(skin_Nz);
        securityQuestionSelectBox.setItems(SecurityQuestion.values());
        final TextField securityAnswerField = new TextField("", skin_Nz,"default");
        securityAnswerField.setMessageText(" Please enter your answer...");

        final TextButton backButton = MenuAssetManager.getInstance().getButtons("back");
        final TextButton submitButton = MenuAssetManager.getInstance().getButtons("submit");

        Gdx.input.setInputProcessor(stage);
        Table table = new Table();
        table.setFillParent(true);
        table.center();
        table.add(usernameLabel).padRight(20).padBottom(20);
        table.add(usernameField).width(600).padBottom(20);
        table.row();
        table.add(passwordLabel).padRight(20).padBottom(20);
        table.add(passwordField).width(600).padBottom(20);
        table.row();
        table.add(emailLabel).padRight(20).padBottom(20);
        table.add(emailField).width(600).padBottom(20);
        table.row();
        table.add(genderLabel).padRight(20).padBottom(20);
        table.add(genderSelectBox).width(600).padBottom(20);
        table.row();
        table.add(securityQuestionLabel).padRight(20);
        table.add(securityQuestionSelectBox).width(600);
        table.row();
        table.add(answerLabel).padBottom(60);
        table.add(securityAnswerField).width(600).padBottom(60);
        stage.addActor(table);
        backButton.setPosition(460,10);
        stage.addActor(backButton);
        submitButton.setPosition(230,10);
        stage.addActor(submitButton);

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor){
                controller.handleBackButton();
            }
        });

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
