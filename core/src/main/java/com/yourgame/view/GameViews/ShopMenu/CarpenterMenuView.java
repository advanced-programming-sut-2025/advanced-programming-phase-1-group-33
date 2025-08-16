package com.yourgame.view.GameViews.ShopMenu;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.Graphics.MenuAssetManager;
import com.yourgame.model.Item.Item;
import com.yourgame.model.Map.Elements.BuildingType;
import com.yourgame.model.Map.Elements.FarmBuilding;
import com.yourgame.model.Resource.Wood;
import com.yourgame.model.UserInfo.Player;
import com.yourgame.view.AppViews.GameScreen;

public class CarpenterMenuView extends Window {
    private final Skin skin;
    private final Player player;
    private final GameScreen gameScreen;
    private final Image background;

    public CarpenterMenuView(GameScreen gameScreen, Stage stage, Player player) {
        super("Carpenter", MenuAssetManager.getInstance().getSkin(3));
        this.skin = MenuAssetManager.getInstance().getSkin(3);
        this.player = player;
        this.gameScreen = gameScreen;
        this.background = new Image(GameAssetManager.getInstance().getTexture("Backgrounds/Background7.png"));

        // --- Window Setup ---
        setModal(true);
        setMovable(true);
        padTop(40f);

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

        // --- Build Sections ---
        // We check each building. If it's not built, we show the build UI. Otherwise, we show "Completed".
        if (player.getGreenhouse() == null) {
            createBuildingEntry(mainTable, "Build Greenhouse", new Wood(), 500, 1000, null);
        } else {
            mainTable.add(new Label("Greenhouse: Completed", skin)).colspan(2).align(Align.left).pad(10, 0, 10, 0).row();
        }

        mainTable.add(background).colspan(2).growX().height(2).pad(20, 0, 20, 0).row();

        if (player.getCoop() == null) {
            createBuildingEntry(mainTable, "Build Coop", new Wood(), 300, 4000, BuildingType.COOP);
        } else {
            mainTable.add(new Label("Coop: Completed", skin)).colspan(2).align(Align.left).pad(10, 0, 10, 0).row();
        }

        mainTable.add(background).colspan(2).growX().height(2).pad(20, 0, 20, 0).row();

        if (player.getBarn() == null) {
            createBuildingEntry(mainTable, "Build Barn", new Wood(), 400, 6000, BuildingType.BARN);
        } else {
            mainTable.add(new Label("Barn: Completed", skin)).colspan(2).align(Align.left).pad(10, 0, 10, 0).row();
        }

        this.add(mainTable);
        this.pack();
        this.setPosition(stage.getWidth() / 2f, stage.getHeight() / 2f, Align.center);
    }

    private void createBuildingEntry(Table parentTable, String title, Item resource, int resourceAmount, int goldAmount, BuildingType buildingType) {
        parentTable.add(new Label(title, skin)).colspan(2).align(Align.left).padBottom(10).row();

        Table requirementsTable = new Table();
        requirementsTable.add(new Image(resource.getTextureRegion(GameAssetManager.getInstance()))).size(32, 32).padRight(10);
        requirementsTable.add(new Label(resourceAmount + " " + resource.getName(), skin)).align(Align.left);
        requirementsTable.add(new Image(new TextureRegion(GameAssetManager.getInstance().getTexture("Game/Clock/Gold.png")))).size(32, 32).padLeft(20).padRight(10);
        requirementsTable.add(new Label(goldAmount + " Gold", skin)).align(Align.left);
        parentTable.add(requirementsTable).align(Align.left);

        TextButton buildButton = new TextButton("Build", MenuAssetManager.getInstance().getSkin(1));
        parentTable.add(buildButton).width(100).height(40).padLeft(20).align(Align.right).row();

        buildButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (player.getGold() >= goldAmount && player.getBackpack().getInventory().getItemQuantity(resource) >= resourceAmount) {
                    if (buildingType == null) player.buildGreenhouse();
                    else gameScreen.enterPlacementMode(buildingType);
                    remove();
                } else {
                    gameScreen.showMessage("error", "Not enough resources.", skin, 0, 200, getStage());
                }
            }
        });
    }
}
