package com.yourgame.view.AppViews;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.yourgame.Graphics.MenuAssetManager;
import com.yourgame.controller.AppController.MainMenuController;
import com.yourgame.model.App;

public class MainMenuView extends MenuBaseScreen {
    private Image redLine;
    private final MainMenuController controller;

    public MainMenuView(){
        this.controller = new MainMenuController();
    }

    @Override
    public void show() {
        //super.show();
        Image buttons = new Image(MenuAssetManager.getInstance().getButtons(App.getCurrentMenu()));
        buttons.setPosition(450,100);
        stage.addActor(buttons);

        redLine = new Image(MenuAssetManager.getInstance().getRedLine());
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
            // going to signup menu
            if(Gdx.input.getX() >= 474 && Gdx.input.getX() <= 584 && Gdx.input.getY() >= 590 && Gdx.input.getY() <= 690)
                controller.handleGoingToSignupMenu();
            //going to login menu
            if(Gdx.input.getX() >= 607 && Gdx.input.getX() <= 715 && Gdx.input.getY() >= 590 && Gdx.input.getY() <= 690)
                controller.handleGoingToLoginMenu();
            //going to pregame menu
            if(Gdx.input.getX() >= 739 && Gdx.input.getX() <= 848 && Gdx.input.getY() >= 590 && Gdx.input.getY() <= 690)
                controller.handleGoingToPreGameMenu();
        }
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}

