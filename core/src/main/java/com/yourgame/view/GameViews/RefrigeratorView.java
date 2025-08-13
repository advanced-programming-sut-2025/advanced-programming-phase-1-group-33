package com.yourgame.view.GameViews;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.Graphics.GameAssets.InventorySlotUI;
import com.yourgame.Graphics.MenuAssetManager;
import com.yourgame.model.Food.Food;
import com.yourgame.model.Item.Inventory.Inventory;
import com.yourgame.model.Item.Inventory.InventorySlot;
import com.yourgame.model.UserInfo.Player;
import com.yourgame.view.AppViews.GameScreen;

public class RefrigeratorView extends Window {
    private final Skin skin;
    private final Inventory refrigeratorInventory;
    private final Inventory playerInventory;
    private final GameAssetManager assetManager;

    private final InventorySlotUI[] fridgeSlotsUI;
    private final InventorySlotUI[] playerSlotsUI;

    private final Table mainTable;

    public RefrigeratorView(Player player, GameScreen gameScreen) {
        super("Refrigerator", MenuAssetManager.getInstance().getSkin(3));
        this.skin = MenuAssetManager.getInstance().getSkin(3);

        this.refrigeratorInventory = player.getBackpack().getRefrigeratorInventory();
        this.playerInventory = player.getBackpack().getInventory();
        this.assetManager = GameAssetManager.getInstance();

        this.fridgeSlotsUI = new InventorySlotUI[36];
        this.playerSlotsUI = new InventorySlotUI[36];

        setModal(true);
        padTop(40);

        // --- Main container table ---
        mainTable = new Table();

        // --- Refrigerator Section ---
        mainTable.add(new Label("Refrigerator", skin)).colspan(12).align(Align.left).pad(10);
        mainTable.row();
        Table fridgeTable = createInventoryGrid(fridgeSlotsUI, this::moveItemToPlayer);
        fridgeTable.top();
        mainTable.add(fridgeTable).pad(10);
        mainTable.row();

        // --- Player Inventory Section ---
        mainTable.add(new Label("Inventory", skin)).colspan(12).align(Align.left).pad(10);
        mainTable.row();
        Table playerTable = createInventoryGrid(playerSlotsUI, this::moveItemToFridge);
        playerTable.bottom();
        mainTable.add(playerTable).pad(10);

        this.add(mainTable);
        this.pack();
        this.setPosition(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f, Align.center);

        // Add a close button to the window's title bar
        TextButton closeButton = new TextButton("X", MenuAssetManager.getInstance().getSkin(1));
        this.getTitleTable().add(closeButton).padRight(5);
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameScreen.closeMenu();
                remove();
            }
        });

        updateAllSlots();
    }

    /**
     * A helper method to create a grid of inventory slots.
     * @param uiSlots The array to store the created UI slots.
     * @param clickAction The action to perform when a slot is clicked.
     * @return A populated Table actor.
     */
    private Table createInventoryGrid(InventorySlotUI[] uiSlots, SlotClickAction clickAction) {
        Table container = new Table();

        Stack inventoryStack = new Stack();
        inventoryStack.add(new Image(assetManager.getTexture("Game/Clock/Inventory/Inventory_Full.png")));

        Table table = new Table();
        table.pad(12, 20, 12, 20);
        for (int i = 0; i < uiSlots.length; i++) {
            InventorySlotUI slot = new InventorySlotUI(skin.getFont("Text"));
            uiSlots[i] = slot;
            table.add(slot).width(56).height(56).pad(8, 4, 8, 4);

            if ((i + 1) % 12 == 0) {
                table.row();
            }

            final int index = i;
            slot.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    clickAction.execute(index);
                }
            });
        }
        inventoryStack.add(table);

        container.add(inventoryStack);
        return container;
    }

    public Table getMainTable() {
        return mainTable;
    }

    private void moveItemToPlayer(int fridgeIndex) {
        moveItem(refrigeratorInventory, playerInventory, fridgeIndex);
    }

    private void moveItemToFridge(int playerIndex) {
        moveItem(playerInventory, refrigeratorInventory, playerIndex);
    }

    /**
     * The core logic for moving an item from a source inventory to a destination.
     * @param source The inventory the item is coming from.
     * @param destination The inventory the item is going to.
     * @param sourceIndex The index of the clicked slot in the source inventory.
     */
    private void moveItem(Inventory source, Inventory destination, int sourceIndex) {
        InventorySlot clickedSlot = source.getSlot(sourceIndex);

        if (clickedSlot != null && clickedSlot.item() != null && clickedSlot.item() instanceof Food) {
            // Attempt to add the item to the destination inventory
            boolean success = destination.addItem(clickedSlot.item(), clickedSlot.quantity());
            if (success) {
                // If the entire stack was moved, remove it from the source
                source.removeItem(clickedSlot.item());
                updateAllSlots(); // Refresh the entire UI
            }
        }
    }

    /**
     * Updates all UI slots to reflect the current state of the data inventories.
     */
    public void updateAllSlots() {
        updateGrid(fridgeSlotsUI, refrigeratorInventory);
        updateGrid(playerSlotsUI, playerInventory);
    }

    private void updateGrid(InventorySlotUI[] uiSlots, Inventory inventory) {
        for (int i = 0; i < uiSlots.length; i++) {
            uiSlots[i].update(inventory.getSlot(i), assetManager);
        }
    }

    // Functional interface for handling clicks, makes the code cleaner
    @FunctionalInterface
    private interface SlotClickAction {
        void execute(int index);
    }
}
