package com.yourgame.view.AppViews;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.yourgame.Graphics.MenuAssetManager;
import com.yourgame.controller.AppController.ProfileMenuController;
import com.yourgame.model.App;
import com.yourgame.model.Result;

public class ProfileMenuView extends MenuBaseScreen {
    private final ProfileMenuController controller;
    private final Skin skin_Nz = MenuAssetManager.getInstance().getSkin(3);

    final TextField usernameField = new TextField("",skin_Nz,"default");
    final TextField newPasswordField = new TextField("",skin_Nz,"default");
    final TextField oldPasswordField = new TextField("",skin_Nz,"default");
    final TextField emailField = new TextField("",skin_Nz,"default");
    final TextField nicknameField = new TextField("",skin_Nz,"default");

    public ProfileMenuView() {
        controller = new ProfileMenuController();
    }

    @Override
    public void show(){
        usernameField.setMessageText(" Your new username...");
        newPasswordField.setMessageText(" Your new password...");
        oldPasswordField.setMessageText(" your old password...");
        emailField.setMessageText(" Your new email...");
        nicknameField.setMessageText(" Your new nickname...");

        final Label changeUsernameLabel = new Label("Change Username ", skin_Nz, "Bold");
        final Label changePasswordLabel = new Label("Change Password ", skin_Nz, "Bold");
        final Label oldPasswordLabel = new Label("", skin_Nz, "Bold");
        final Label changeEmailLabel = new Label("Change Email ", skin_Nz, "Bold");
        final Label changeNicknameLabel = new Label("Change Nickname ", skin_Nz, "Bold");

        final Label currentUsernameLabel = new Label("- Your current username : " + App.getCurrentUser().getUsername(), skin_Nz, "WhiteText");
        final Label currentEmailLabel = new Label("- Your current email : " + App.getCurrentUser().getEmail(), skin_Nz, "WhiteText");
        final Label currentNicknameLabel = new Label("- Your current nickname : " + App.getCurrentUser().getNickname(), skin_Nz, "WhiteText");
        final Label genderLabel = new Label("- Your gender : " + App.getCurrentUser().getGender(), skin_Nz, "WhiteText");
        //TODO : more to add like the top score and ...


        final TextButton changeUsernameButton = MenuAssetManager.getInstance().getButtons("changeUsername");
        final TextButton changePasswordButton = MenuAssetManager.getInstance().getButtons("changePassword");
        final TextButton changeEmailButton = MenuAssetManager.getInstance().getButtons("changeEmail");
        final TextButton changeNicknameButton = MenuAssetManager.getInstance().getButtons("changeNickname");
        final TextButton backButton = MenuAssetManager.getInstance().getButtons("back");
        final TextButton submitButton = MenuAssetManager.getInstance().getButtons("submit");

        Table table = new Table();
        table.setFillParent(true);
        table.center();
        table.setPosition(table.getX(), table.getY() + 160);
        table.add(changeUsernameLabel).padBottom(5);
        table.add(usernameField).width(600).padBottom(5);
        table.add(changeUsernameButton).height(80).padBottom(5).padLeft(20);
        table.row();
        table.add(changePasswordLabel).padBottom(5);
        table.add(newPasswordField).width(600).padBottom(5);
        table.add(changePasswordButton).height(80).padBottom(5).padLeft(20);
        table.row();
        table.add(oldPasswordLabel).padBottom(5);
        table.add(oldPasswordField).width(600).padBottom(5);
        table.row();
        table.add(changeEmailLabel).padBottom(5);
        table.add(emailField).width(600).padBottom(5);
        table.add(changeEmailButton).height(80).padBottom(5).padLeft(20);
        table.row();
        table.add(changeNicknameLabel).padBottom(5);
        table.add(nicknameField).width(600).padBottom(5);
        table.add(changeNicknameButton).height(80).padBottom(5).padLeft(20);
        stage.addActor(table);

        Table detailTable = new Table();
        detailTable.setPosition(430,220);
        detailTable.add(currentUsernameLabel).left().padBottom(5).row();
        detailTable.add(currentEmailLabel).left().padBottom(5).row();
        detailTable.add(currentNicknameLabel).left().padBottom(5).row();
        detailTable.add(genderLabel).left().padBottom(5).row();
        stage.addActor(detailTable);

        backButton.setPosition(1015,20);
        stage.addActor(backButton);
        submitButton.setPosition(1000,140);
        stage.addActor(submitButton);

        changeUsernameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                Result result = controller.handleChangeUsername(usernameField.getText());
                if(result.success()){
                    currentUsernameLabel.setText("- Your current username : " + App.getCurrentUser().getUsername());
                    usernameField.setText("");
                }
                showMessage(result.message(),skin_Nz,-70,20);
            }
        });

        changePasswordButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                Result result = controller.handleChangePassword(newPasswordField.getText(),oldPasswordField.getText());
                if(result.success()){
                    newPasswordField.setText("");
                    oldPasswordField.setText("");
                }
                showMessage(result.message(),skin_Nz,-70,20);
            }
        });

        changeEmailButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                Result result = controller.handleChangeEmail(emailField.getText());
                if(result.success()){
                    currentEmailLabel.setText("- Your current email : " + App.getCurrentUser().getEmail());
                    emailField.setText("");
                }
                showMessage(result.message(),skin_Nz,-70,20);

            }
        });

        changeNicknameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                Result result = controller.handleChangeNickname(nicknameField.getText());
                if(result.success()){
                    currentNicknameLabel.setText("- Your current nickname : " + App.getCurrentUser().getNickname());
                    nicknameField.setText("");
                }
                showMessage(result.message(),skin_Nz,-70,20);
            }
        });

        submitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                controller.handleSubmitButton();
            }
        });

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                controller.handleBackButton();
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
}
