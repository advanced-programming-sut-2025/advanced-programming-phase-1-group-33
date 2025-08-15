package com.yourgame.view.GameViews;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.yourgame.model.Animals.FishPackage.Fish;
import com.yourgame.model.Item.Tools.PoleStage;
import com.yourgame.view.AppViews.GameScreen;

public class FishingGameMenuView extends Window {
    private final Skin skin;
    private final GameScreen gameScreen;
    private final Stage stage;

    public FishingGameMenuView(Skin skin, Stage stage, GameScreen gameScreen, Fish fish, PoleStage fishingPole) {
        super("Fishing Game", skin);
        this.stage = stage;
        this.gameScreen = gameScreen;
        this.skin = skin;

        setSize(1400, 900);
        setModal(true);
        setMovable(false);
        pad(20f);

        TextButton backButton = new TextButton("Back", skin);
        add(backButton).center().bottom();


        setPosition((stage.getWidth() - getWidth()) / 2f, (stage.getHeight() - getHeight()) / 2f);

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage.clear();
                gameScreen.closeMenu();
                remove();
            }
        });
    }
}
