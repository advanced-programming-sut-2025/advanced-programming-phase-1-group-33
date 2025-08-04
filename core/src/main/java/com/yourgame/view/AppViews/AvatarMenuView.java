package com.yourgame.view.AppViews;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.yourgame.Graphics.MenuAssetManager;
import com.yourgame.controller.AppController.AvatarMenuController;
import com.yourgame.model.enums.Avatar;

public class AvatarMenuView extends MenuBaseScreen{
    private final AvatarMenuController controller;

    Skin skin_Nz = MenuAssetManager.getInstance().getSkin(3);
    final Image redArrow = MenuAssetManager.getInstance().getRedArrow();

    public AvatarMenuView(){
        controller = new AvatarMenuController();
    }

    @Override
    public void show(){

        final TextButton backButton = MenuAssetManager.getInstance().getButtons("back");
        final TextButton submitButton = MenuAssetManager.getInstance().getButtons("submit");

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

        Table buttonTable = new Table();
        buttonTable.setFillParent(true);
        buttonTable.bottom().right();
        buttonTable.add(submitButton).padRight(20).padBottom(5).width(250).height(90);
        buttonTable.row();
        buttonTable.add(backButton).padRight(20).padBottom(20).width(200).height(90);
        stage.addActor(buttonTable);
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
