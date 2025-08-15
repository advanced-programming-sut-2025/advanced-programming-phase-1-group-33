package com.yourgame.view.GameViews.ArtisanMenuView;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.model.Item.Inventory.InventorySlot;
import com.yourgame.model.ManuFactor.Artisan.EdibleArtisanProduct.Products.Honey;
import com.yourgame.model.ManuFactor.Artisan.InEdibleArtisanProduct.Products.Coal;
import com.yourgame.model.UserInfo.Player;
import com.yourgame.view.AppViews.GameScreen;

import java.util.ArrayList;
import java.util.List;

public class IngredientMenuView extends Window {

    private final Skin skin;
    private final GameScreen gameScreen;
    private final Stage stage;

    private final Table inventoryGrid;
    private final Table chosenItemsTable;

    private final TextButton createButton;
    private final TextButton closeButton;

    private final ArrayList<InventorySlot> inventory;
    private final ArrayList<InventorySlot> selectedItems = new ArrayList<>(); // list of selected slots (one per unit)

    private final int GRID_COLUMNS = 10;

    public IngredientMenuView(Skin skin, Stage stage, GameScreen gameScreen, Window parentWindow) {
        super("Ingredients", skin);
        this.skin = skin;
        this.stage = stage;
        this.gameScreen = gameScreen;

        inventory = new ArrayList<>(gameScreen.getPlayer().getBackpack().getInventory().getSlots());

        inventoryGrid = new Table();
        chosenItemsTable = new Table();

        createButton = new TextButton("Create", skin);
        closeButton = new TextButton("Close", skin);

        createButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                create(parentWindow);
            }
        });

        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                returnSelectedItemsToPreviousMenu(parentWindow);
                remove();
            }
        });

        Table mainTable = new Table();
        mainTable.defaults().pad(5);
        mainTable.add(new ScrollPane(inventoryGrid, skin)).top().colspan(2).expand().fill().row();
        mainTable.add(new ScrollPane(chosenItemsTable, skin)).top().colspan(2).expand().fill().row();
        mainTable.add(createButton).left().padLeft(20);
        mainTable.add(closeButton).left().padLeft(20);

        this.add(mainTable);
        this.pack();
        stage.addActor(this);

        setSize(1000, 600);
        setPosition((stage.getWidth() - getWidth()) / 2f,
            (stage.getHeight() - getHeight()) / 2f);

        populateInventoryGrid();
        refreshChosenItemsTable();
    }

    private void populateInventoryGrid() {
        inventoryGrid.clear();
        int col = 0;

        for (InventorySlot slot : inventory) {
            Stack stack = new Stack();
            Image icon = new Image(slot.item().getTextureRegion(GameAssetManager.getInstance()));

            Label qty = new Label(String.valueOf(slot.quantity()), skin);
            Table overlay = new Table();
            overlay.add(qty).bottom().right().pad(4);

            stack.add(icon);
            stack.add(overlay);

            stack.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (slot.quantity() > countSelected(slot)) {
                        selectedItems.add(slot); // add one unit to selection
                        refreshChosenItemsTable();
                    }
                }
            });

            inventoryGrid.add(stack).size(64, 64).pad(2);
            col++;
            if (col >= GRID_COLUMNS) {
                col = 0;
                inventoryGrid.row();
            }
        }
    }

    private void refreshChosenItemsTable() {
        chosenItemsTable.clear();
        List<InventorySlot> uniqueSlots = new ArrayList<>();

        // Build unique list
        for (InventorySlot slot : selectedItems) {
            if (!uniqueSlots.contains(slot)) uniqueSlots.add(slot);
        }

        for (InventorySlot slot : uniqueSlots) {
            int qty = countSelected(slot);

            Stack stack = new Stack();
            Image icon = new Image(slot.item().getTextureRegion(GameAssetManager.getInstance()));

            Label qtyLabel = new Label(String.valueOf(qty), skin);
            Table overlay = new Table();
            overlay.add(qtyLabel).bottom().right().pad(4);

            stack.add(icon);
            stack.add(overlay);

            // Reduce selection by clicking
            stack.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    removeOneSelected(slot);
                    refreshChosenItemsTable();
                }
            });

            chosenItemsTable.add(stack).size(64, 64).pad(2);
        }
    }

    private int countSelected(InventorySlot slot) {
        int count = 0;
        for (InventorySlot s : selectedItems) {
            if (s == slot) count++;
        }
        return count;
    }

    private void removeOneSelected(InventorySlot slot) {
        for (int i = selectedItems.size() - 1; i >= 0; i--) {
            if (selectedItems.get(i) == slot) {
                selectedItems.remove(i);
                break;
            }
        }
    }

    private void returnSelectedItemsToPreviousMenu(Window parentWindow) {
        if(parentWindow instanceof BeeHouseMenuView){
            ((BeeHouseMenuView) parentWindow).setSelectedItems(selectedItems);
        }

        else if(parentWindow instanceof CharcoalKilnMenuView){
            ((CharcoalKilnMenuView) parentWindow).setSelectedItems(selectedItems);
        }
        // Here you can call a method on gameScreen or parent menu
        // Example: gameScreen.receiveSelectedItems(selectedItems);
        // The list will contain all selected units
    }

    private void create(Window parentWindow) {
        if(parentWindow instanceof CharcoalKilnMenuView){
            if(!Coal.isIngredientsAppropriate(selectedItems)){
                gameScreen.showMessage("error","Not correct Ingredient!",skin,0,200,stage);
                return;
            }

            int woodsToRemove = 10;
            for (InventorySlot slot : inventory) {
                if (slot.item().getName().equals("Wood")) {
                    int remove = Math.min(slot.quantity(), woodsToRemove);
                    slot.reduceQuantity(remove);
                    if(slot.quantity() == 0){
                        gameScreen.getPlayer().getBackpack().getInventory().removeItem(slot.item());
                    }
                    woodsToRemove -= remove;
                    if (woodsToRemove <= 0) break;
                }
            }

            ((CharcoalKilnMenuView) parentWindow).getCharcoalKiln().startProcessing(Coal.calculateProcessingTime());
            ((CharcoalKilnMenuView) parentWindow).resetUI();
        }

        if(parentWindow instanceof BeeHouseMenuView){
            if(!Honey.isIngredientsAppropriate(selectedItems)){
                gameScreen.showMessage("error","Not correct Ingredient!",skin,0,200,stage);
                return;
            }

            ((BeeHouseMenuView) parentWindow).getBeeHouse().startProcessing(Honey.calculateProcessingTime());
            ((BeeHouseMenuView) parentWindow).resetUI();
        }

        remove();
    }
}
