package com.yourgame.view.AppViews;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.yourgame.Graphics.MenuAssetManager;
import com.yourgame.controller.AppController.AvatarMenuController;
import com.yourgame.model.App;
import com.yourgame.model.enums.Avatar;

public class AvatarMenuView extends MenuBaseScreen{
    private final AvatarMenuController controller;

    Skin skin_Nz = MenuAssetManager.getInstance().getSkin(3);
    private SelectBox<String> avatarChooseSelectBox;

    public AvatarMenuView(){
        controller = new AvatarMenuController();
    }

    @Override
    public void show(){

        final TextButton backButton = MenuAssetManager.getInstance().getButtons("back");

        final Image SamCharacter = MenuAssetManager.getInstance().getAvatarCharacter(Avatar.Sam);
        final Image HarveyCharacter = MenuAssetManager.getInstance().getAvatarCharacter(Avatar.Harvey);
        final Image RobinCharacter = MenuAssetManager.getInstance().getAvatarCharacter(Avatar.Robin);
        final Image PierreCharacter = MenuAssetManager.getInstance().getAvatarCharacter(Avatar.Pierre);
        final Image AbigailCharacter = MenuAssetManager.getInstance().getAvatarCharacter(Avatar.Abigail);

        final Image SamPortrait = MenuAssetManager.getInstance().getAvatarPortrait(Avatar.Sam);
        final Image HarveyPortrait = MenuAssetManager.getInstance().getAvatarPortrait(Avatar.Harvey);
        final Image RobinPortrait = MenuAssetManager.getInstance().getAvatarPortrait(Avatar.Robin);
        final Image PierrePortrait = MenuAssetManager.getInstance().getAvatarPortrait(Avatar.Pierre);
        final Image AbigailPortrait = MenuAssetManager.getInstance().getAvatarPortrait(Avatar.Abigail);

        final Label SamLabel = new Label("Sam" , skin_Nz, "Bold");
        final Label HarveyLabel = new Label("Harvey", skin_Nz, "Bold");
        final Label RobinLabel = new Label("Robin", skin_Nz, "Bold");
        final Label PierreLabel = new Label("Pierre", skin_Nz, "Bold");
        final Label AbigailLabel = new Label("Abigail", skin_Nz, "Bold");

        avatarChooseSelectBox = new SelectBox<>(skin_Nz);
        avatarChooseSelectBox.setItems("Sam", "Harvey", "Robin", "Pierre", "Abigail");
        avatarChooseSelectBox.setSelected(App.getCurrentUser().getAvatar().getName());
        final Label currentAvatar = new Label("Current Avatar : ", skin_Nz, "Bold");

        Table table = new Table();
        table.setFillParent(true);
        table.row().padRight(200);
        table.add(SamPortrait);
        table.add(HarveyPortrait);
        table.add(RobinPortrait);
        table.add(PierrePortrait);
        table.add(AbigailPortrait);
        table.row().padTop(100).padRight(100);
        table.add(SamCharacter);
        table.add(HarveyCharacter);
        table.add(RobinCharacter);
        table.add(PierreCharacter);
        table.add(AbigailCharacter);
        table.row().padTop(100).padRight(50);
        table.add(SamLabel);
        table.add(HarveyLabel);
        table.add(RobinLabel);
        table.add(PierreLabel);
        table.add(AbigailLabel);
        table.row();
        stage.addActor(table);

        Table chooseTable = new Table();
        chooseTable.setFillParent(true);
        chooseTable.center().padTop(600);
        chooseTable.add(currentAvatar).padRight(100);
        chooseTable.add(avatarChooseSelectBox);
        stage.addActor(chooseTable);

        Table buttonTable = new Table();
        buttonTable.setFillParent(true);
        buttonTable.bottom().right();
        buttonTable.add(backButton).padRight(20).padBottom(20).width(200).height(90);
        stage.addActor(buttonTable);

        avatarChooseSelectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                playMenuSFX("avatarChoose");
                controller.handleAvatarChoose(avatarChooseSelectBox.getSelected());
            }
        });

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
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
