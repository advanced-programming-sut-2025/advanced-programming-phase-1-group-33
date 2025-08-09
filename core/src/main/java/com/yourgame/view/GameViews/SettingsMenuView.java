package com.yourgame.view.GameViews;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.Main;
import com.yourgame.model.App;
import com.yourgame.model.enums.Commands.MenuTypes;
import com.yourgame.view.AppViews.GameScreen;

public class SettingsMenuView extends Window {
    public SettingsMenuView(Skin skin, Stage stage, GameScreen gameScreen) {
        super("Settings", skin);

        setSize(1200, 900);
        setModal(true);
        setMovable(false);
        pad(20f);

        Label label = new Label("Leave the game : ", skin);
        TextButton leaveButton = new TextButton("Leave", skin);
        TextButton backButton = GameAssetManager.getInstance().getButton("Back");

        add(label).padRight(20);
        add(leaveButton).row();
        add(backButton).center().row();

        setPosition((stage.getWidth() - getWidth())/2f, (stage.getHeight() - getHeight())/2f);

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage.clear();
                stage.addActor(new MainMenuView(skin, stage, gameScreen));
            }
        });

        leaveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showMessageWithButton("Are you sure?", gameScreen, skin, stage);
            }
        });
    }

    private void showMessageWithButton(String message, GameScreen gameScreen, Skin skin, Stage stage) {
        gameScreen.playGameSFX("popUp");
        Dialog dialog = new Dialog("",skin) {
            @Override
            protected void result(Object object) {
                if (object.equals(true)) {
                    App.setCurrentMenu(MenuTypes.MainMenu);
                    Main.getMain().getScreen().dispose();
                    Main.getMain().setScreen(new com.yourgame.view.AppViews.MainMenuView());
                }
            }
        };
        dialog.text(message);
        dialog.button("Yes", true);
        dialog.button("No", false);
        dialog.show(stage);
        dialog.setPosition((stage.getWidth()-dialog.getWidth())/2f , getY());
    }
}
