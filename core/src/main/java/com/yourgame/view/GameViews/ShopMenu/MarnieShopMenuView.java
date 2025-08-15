package com.yourgame.view.GameViews.ShopMenu;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.yourgame.Graphics.MenuAssetManager;
import com.yourgame.model.Animals.AnimalPackage.Animal;
import com.yourgame.model.Animals.AnimalPackage.AnimalType;
import com.yourgame.model.Map.Elements.BuildingType;
import com.yourgame.model.Map.Elements.FarmBuilding;
import com.yourgame.model.UserInfo.Player;
import com.yourgame.view.AppViews.GameScreen;

public class MarnieShopMenuView extends Window {
    private final Player player;
    private final GameScreen gameScreen;

    public MarnieShopMenuView(GameScreen gameScreen, Stage stage, Player player) {
        super("Marnie's Ranch", MenuAssetManager.getInstance().getSkin(3));
        this.player = player;
        this.gameScreen = gameScreen;

        // --- Window Setup ---
        setModal(true);
        padTop(40f);

        Table mainTable = new Table();
        mainTable.pad(20f);

        // --- Create an entry for each animal type ---
        for (AnimalType type : AnimalType.values()) {
            createAnimalEntry(mainTable, type);
        }

        this.add(mainTable);
        this.pack();
        this.setPosition(stage.getWidth() / 2f, stage.getHeight() / 2f, Align.center);
    }

    private void createAnimalEntry(Table parentTable, AnimalType animalType) {
        parentTable.add(new Image(animalType.getTexture())).size(32, 32).padRight(10);
        parentTable.add(new Label(animalType.getName(), getSkin())).width(200).align(Align.left);
        parentTable.add(new Label(animalType.getCost() + "g", getSkin())).width(150);

        TextButton buyButton = new TextButton("Buy", MenuAssetManager.getInstance().getSkin(1));
        parentTable.add(buyButton).size(80, 48).pad(10);
        parentTable.row();

        buyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Find the correct building on the player's farm
                FarmBuilding targetBuilding = null;
                if (animalType.getHome() == BuildingType.COOP) {
                    targetBuilding = player.getCoop();
                } else if (animalType.getHome() == BuildingType.BARN) {
                    targetBuilding = player.getBarn();
                }

                // --- Purchase Logic ---
                if (targetBuilding == null) {
                    gameScreen.showMessage("error", "You need to build a " + animalType.getHome().getName() + " first!", getSkin(), 0, 200, getStage());
                } else if (player.getGold() < animalType.getCost()) {
                    gameScreen.showMessage("error", "Not enough gold.", getSkin(), 0, 200, getStage());
                } else {
                    Animal newAnimal = new Animal(animalType);
                    if (targetBuilding.addAnimal(newAnimal)) {
                        player.setGold(player.getGold() - animalType.getCost());
                        gameScreen.showMessage("success", "Your new " + animalType.getName() + " has been sent to the " + targetBuilding.getBuildingType().getName() + "!", getSkin(), 0, 200, getStage());
                    } else {
                        gameScreen.showMessage("error", "Your " + targetBuilding.getBuildingType().getName() + " is full.", getSkin(), 0, 200, getStage());
                    }
                }
            }
        });
    }
}
