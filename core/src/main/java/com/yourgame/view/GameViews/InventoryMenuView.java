package com.yourgame.view.GameViews;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.yourgame.view.AppViews.GameScreen;

public class InventoryMenuView extends Window {
    public InventoryMenuView(Skin skin, Stage stage, GameScreen gameScreen) {
        super("Inventory", skin);
        setSize(800, 600);
        setModal(true);
        setMovable(false);
        pad(20);

        Label label = new Label("Your Items Go Here", skin);
        TextButton backBtn = new TextButton("Back", skin);

        add(label).row();
        add(backBtn).row();

        backBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage.clear();
                stage.addActor(new MainMenuView(skin, stage, gameScreen));
            }
        });
    }
}

