package com.yourgame.view.GameViews.ShopMenu;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.model.Item.Inventory.InventorySlot;
import com.yourgame.model.UserInfo.Player;
import com.yourgame.view.AppViews.GameScreen;

import java.util.List;

public class PierreShopSellMenuView extends Window {

    private final Skin skin;
    private final GameScreen gameScreen;
    private final Stage stage;

    private final Table inventoryGrid;
    private final Label itemName, itemPrice, itemQuantity, finalPrice;
    private final Image itemIcon;

    private final TextButton sellButton;
    private final TextButton closeButton;

    private final List<InventorySlot> inventory;
    private InventorySlot selectedItem;
    private int quantitySelected = 0;

    private final int GRID_COLUMNS = 10; // adjust as needed

    public PierreShopSellMenuView(Skin skin, Stage stage, GameScreen gameScreen) {
        super("Sell Menu", skin);
        this.skin = skin;
        this.stage = stage;
        this.gameScreen = gameScreen;

        inventory = gameScreen.getPlayer().getBackpack().getInventory().getSlots();

        // --- Inventory Grid ---
        inventoryGrid = new Table();
        populateInventoryGrid();

        // --- Item Details ---
        itemIcon = new Image();
        itemName = new Label("Name: ", skin);
        itemPrice = new Label("Price: ", skin);
        itemQuantity = new Label("Quantity: ", skin);
        finalPrice = new Label("Final Price: ", skin);

        sellButton = new TextButton("Sell", skin);
        closeButton = new TextButton("Close", skin);

        sellButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(selectedItem == null || selectedItem.quantity() == 0) {
                    gameScreen.showMessage("error","No Item Selected!",skin,0,200,stage);
                }
                else {
                    updateInventory(selectedItem);
                    updateItemDisplay(selectedItem);
                    populateInventoryGrid(); // refresh grid
                }
            }
        });

        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage.clear();
                gameScreen.closeMenu();
                remove();
            }
        });

        // --- Layout ---
        Table mainTable = new Table();
        mainTable.defaults().pad(5);
        mainTable.add(inventoryGrid).top().colspan(2).row();
        mainTable.add(itemName).left().padLeft(20);
        mainTable.add(itemIcon).size(72,72).row();
        mainTable.add(itemPrice).left().padLeft(20).row();
        mainTable.add(itemQuantity).left().padLeft(20).row();
        mainTable.add(finalPrice).left().padLeft(20).row();
        mainTable.add(sellButton).left().padLeft(20);
        mainTable.add(closeButton).left().padLeft(20);

        this.add(mainTable);
        this.pack();
        stage.addActor(this);

        setSize(1000, 600);
        setPosition((stage.getWidth() - getWidth()) / 2f,
            (stage.getHeight() - getHeight()) / 2f);
    }

    private void populateInventoryGrid() {
        inventoryGrid.clear();
        int col = 0;

        for (InventorySlot inventorySlot : inventory) {
            Stack slot = new Stack();
            Image icon = new Image(inventorySlot.item().getTextureRegion(GameAssetManager.getInstance()));
            Label qty = new Label(String.valueOf(inventorySlot.quantity()), skin);

            Table overlay = new Table();
            overlay.add(qty).bottom().right().pad(4);
            slot.add(icon);
            slot.add(overlay);

            // Click listener for selection
            slot.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (selectedItem != inventorySlot) {
                        // New item selected , reset quantity
                        selectedItem = inventorySlot;
                        quantitySelected = 1;
                    } else {
                        // Same item clicked , increase quantity, but not above stock
                        if (quantitySelected < selectedItem.quantity()) {
                            quantitySelected++;
                        }
                    }
                    updateItemDisplay(selectedItem);
                }
            });

            inventoryGrid.add(slot).size(64,64).pad(2);
            col++;
            if (col >= GRID_COLUMNS) {
                col = 0;
                inventoryGrid.row();
            }
        }
    }

    private void updateItemDisplay(InventorySlot inventorySlot) {
        if (inventorySlot == null){
            itemName.setText("Name: ");
            itemPrice.setText("Price: ");
            itemQuantity.setText("Quantity Selected: ");
            finalPrice.setText("Final Price: ");
            itemIcon.setDrawable(null);
            return;
        }

        itemName.setText("Name: " + inventorySlot.item().getName());
        itemPrice.setText("Price: " + inventorySlot.item().getValue() + "g");
        itemQuantity.setText("Quantity Selected: " + quantitySelected + " Out of " + inventorySlot.quantity());
        finalPrice.setText("Final Price: " + (inventorySlot.item().getValue() * quantitySelected) + "g");

        itemIcon.setDrawable(new TextureRegionDrawable(
            inventorySlot.item().getTextureRegion(GameAssetManager.getInstance())
        ));
    }

    private void updateInventory(InventorySlot inventorySlot) {
        if(inventorySlot == null) return;

        Player player = gameScreen.getPlayer();
        player.setGold(player.getGold() + selectedItem.item().getValue() * quantitySelected);
        gameScreen.getHUDManager().updateCoin();
        selectedItem.reduceQuantity(quantitySelected);
        quantitySelected = 0;

        if(selectedItem.quantity() == 0){
            inventory.remove(selectedItem);
            selectedItem = null;
        }
    }

}
