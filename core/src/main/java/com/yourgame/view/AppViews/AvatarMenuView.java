package com.yourgame.view.AppViews;

import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.yourgame.Graphics.MenuAssetManager;
import com.yourgame.controller.AppController.AvatarMenuController;

public class AvatarMenuView extends MenuBaseScreen{
    private final AvatarMenuController controller;

    public AvatarMenuView(){
        controller = new AvatarMenuController();
    }

    @Override
    public void show(){
        final TextButton backButton = MenuAssetManager.getInstance().getButtons("back");


        stage.addActor(backButton);
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
