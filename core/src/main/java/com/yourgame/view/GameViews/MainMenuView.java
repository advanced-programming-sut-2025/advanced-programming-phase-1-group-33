package com.yourgame.view.GameViews;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.Graphics.MenuAssetManager;
import com.yourgame.view.AppViews.GameScreen;

public class MainMenuView extends Window {
    private final GameAssetManager assetManager = GameAssetManager.getInstance();

    public MainMenuView(Skin skin, Stage stage, GameScreen gameScreen) {
        super("Main Menu", skin);

        setSize(1200, 900);
        setModal(true);
        setMovable(false);
        pad(20f);

        TextButton closeButton = assetManager.getButton("Close");
        TextButton inventoryButton = assetManager.getButton("Inventory");
        TextButton settingsButton = assetManager.getButton("Settings");
        TextButton cookingButton = assetManager.getButton("Cooking");
        TextButton skillButton = assetManager.getButton("Skills");
        TextButton journalButton = assetManager.getButton("Journal");
        TextButton mapButton = assetManager.getButton("Map");
        TextButton socialButton = assetManager.getButton("Social");
        TextButton craftingButton = assetManager.getButton("Crafting");
        TextButton infoButton = assetManager.getButton("Info");

        row().pad(10);
        add(inventoryButton).width(300).padBottom(10);
        add(skillButton).width(300).padBottom(10);
        row();
        add(socialButton).width(300).padBottom(10);
        add(craftingButton).width(300).padBottom(10);
        row();
        add(cookingButton).width(300).padBottom(10);
        add(mapButton).width(300).padBottom(10);
        row();
        add(journalButton).width(300).padBottom(10);
        add(settingsButton).width(300).padBottom(10);
        row();
        add(infoButton).width(300).padBottom(10);
        add(closeButton).width(300).padBottom(10);

        setPosition((stage.getWidth() - getWidth())/2f, (stage.getHeight() - getHeight())/2f);

        // Inventory button logic
        inventoryButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage.clear();
                stage.addActor(new InventoryMenuView(MenuAssetManager.getInstance().getSkin(3), stage , gameScreen));
            }
        });

        // Skill button logic
        skillButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage.clear();
                stage.addActor(new SkillMenuView(MenuAssetManager.getInstance().getSkin(3), stage , gameScreen));
            }
        });

        // Social button logic
        socialButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage.clear();
                stage.addActor(new SocialMenuView(MenuAssetManager.getInstance().getSkin(3), stage , gameScreen));
            }
        });

        // Crafting button logic
        craftingButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage.clear();
                stage.addActor(new CraftingMenuView(MenuAssetManager.getInstance().getSkin(3), stage , gameScreen));
            }
        });

        // Cooking button logic
        cookingButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage.clear();
                stage.addActor(new CookingMenuView(MenuAssetManager.getInstance().getSkin(3), stage , gameScreen));
            }
        });

        // Map button logic
        mapButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage.clear();
                stage.addActor(new MapMenuView(MenuAssetManager.getInstance().getSkin(3), stage, gameScreen));
            }
        });

        // Journal button logic
        journalButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage.clear();
                stage.addActor(new JournalMenuView(stage , gameScreen, gameScreen.getPlayer()));
            }
        });

        // Settings button logic
        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage.clear();
                stage.addActor(new SettingsMenuView(MenuAssetManager.getInstance().getSkin(3), stage , gameScreen));
            }
        });

        // Close button logic
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameScreen.closeMenu();
                remove();
            }
        });

        // Info button logic
        infoButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage.clear();
                stage.addActor(new InfoMenuView(MenuAssetManager.getInstance().getSkin(3), stage , gameScreen));
            }
        });
    }
}

