package com.yourgame.view.GameViews.ShopMenu;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.yourgame.view.AppViews.GameScreen;

public class StardropSaloonMenuView extends Window {

    public StardropSaloonMenuView(Skin skin, Stage stage, GameScreen gameScreen) {
        super("The Stardrop Saloon Menu", skin);

        setSize(1400, 800);
        setModal(true);
        setMovable(false);
        center();

        stage.addActor(this);
    }
}
