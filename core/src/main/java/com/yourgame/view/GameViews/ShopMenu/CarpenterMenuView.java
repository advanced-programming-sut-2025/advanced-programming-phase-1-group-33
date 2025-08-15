package com.yourgame.view.GameViews.ShopMenu;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.Graphics.MenuAssetManager;
import com.yourgame.model.Farming.Wood;
import com.yourgame.model.UserInfo.Player;
import com.yourgame.view.AppViews.GameScreen;
import com.yourgame.view.GameViews.MainMenuView;

public class CarpenterMenuView extends Window {
    public CarpenterMenuView(GameScreen gameScreen, Stage stage, Player player) {
        super("Carpenter", MenuAssetManager.getInstance().getSkin(3));
        Skin skin = MenuAssetManager.getInstance().getSkin(3);

        // --- Window Setup ---
        setModal(true);
        setMovable(true);
        padTop(40f); // Make space for the title

        // --- Main Layout Table ---
        Table mainTable = new Table();
        mainTable.pad(20f);

        // --- Title ---
        mainTable.add(new Label("Upgrade Farm Buildings", skin)).colspan(2).center().padBottom(20);
        mainTable.row();

        // --- Close Button ---
        TextButton closeButton = new TextButton("X", MenuAssetManager.getInstance().getSkin(1));
        this.getTitleTable().add(closeButton).size(30, 30).padRight(5);
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                remove();
                gameScreen.closeMenu();
            }
        });

        if (player.getGreenhouse() != null) {
            Label completeLabel = new Label("Greenhouse Completed", skin);
            mainTable.add(completeLabel).colspan(2).align(Align.left).padBottom(10);
            this.pack();
            this.setPosition(stage.getWidth() / 2f, stage.getHeight() / 2f, Align.center);
            return;
        }

        // --- Greenhouse Section ---
        Label greenhouseLabel = new Label("Build Greenhouse", skin);
        mainTable.add(greenhouseLabel).colspan(2).align(Align.left).padBottom(10);
        mainTable.row();

        // Use a nested table for requirements to keep them grouped
        Table requirementsTable = new Table();

        // Wood Requirement
        requirementsTable.add(new Image(new Wood.WoodItem().getTextureRegion(GameAssetManager.getInstance()))).size(32, 32).padRight(10);
        Label woodLabel = new Label("500 Wood", skin);
        requirementsTable.add(woodLabel).align(Align.left);

        // Gold Requirement
        requirementsTable.add(new Image(new TextureRegion(GameAssetManager.getInstance().getTexture("Game/Clock/Gold.png")))).size(32, 32).padLeft(20).padRight(10);
        Label goldLabel = new Label("1000 Gold", skin);
        requirementsTable.add(goldLabel).align(Align.left);

        mainTable.add(requirementsTable).align(Align.left);

        // --- Build Button ---
        TextButton buildButton = new TextButton("Build", MenuAssetManager.getInstance().getSkin(1));
        mainTable.add(buildButton).width(100).height(40).padLeft(20).align(Align.right);
        mainTable.row();

        // --- Add a separator ---
        mainTable.add(new Image(GameAssetManager.getInstance().getTexture("Backgrounds/Background7.png"))).colspan(2).growX().height(2).padTop(20);
        mainTable.row();

        this.add(mainTable);
        this.pack();
        this.setPosition(stage.getWidth() / 2f, stage.getHeight() / 2f, Align.center);

        buildButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (player.areGreenhouseRequirementsMet()) {
                    player.buildGreenhouse();
                    remove();
                    gameScreen.closeMenu();
                } else {
                    gameScreen.showMessage("error", "The requirements aren't met.", skin, 0, 200, stage);
                }
            }
        });
    }
}
