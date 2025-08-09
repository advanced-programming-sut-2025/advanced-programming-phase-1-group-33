package com.yourgame.view.GameViews;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.view.AppViews.GameScreen;

public class CookingMenuView extends Window {
    public CookingMenuView(Skin skin, Stage stage, GameScreen gameScreen) {
        super("Cooking", skin);

        setSize(1200, 900);
        setModal(true);
        setMovable(false);
        pad(20f);

        Label label = new Label("Your Cookings Go Here", skin);
        TextButton backButton = GameAssetManager.getInstance().getButton("Back");

        add(label).row();
        add(backButton).row();

        setPosition((stage.getWidth() - getWidth())/2f, (stage.getHeight() - getHeight())/2f);

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage.clear();
                stage.addActor(new MainMenuView(skin, stage, gameScreen));
            }
        });
    }
}
