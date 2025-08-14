package com.yourgame.view.GameViews.ShopMenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.Graphics.MenuAssetManager;
import com.yourgame.model.Food.Cooking.CookingRecipe;
import com.yourgame.model.Item.Inventory.InventorySlot;
import com.yourgame.model.Map.Store.StardropSaloon.StardropSaloon;
import com.yourgame.model.Map.Store.StardropSaloon.StardropSaloonItem;
import com.yourgame.view.AppViews.GameScreen;

import java.util.ArrayList;
import java.util.List;

public class StardropSaloonMenuView extends Window {
    private final Skin skin;
    private final GameScreen gameScreen;
    private final Stage stage;

    private final List<InventorySlot> masterInventory;
    private boolean isFiltered = false;

    private final Table contentTable;
    private final TextButton filterButton;
    private final TextButton buyGoodsButton;
    private final TextButton closeButton;

    private final Table purchasePreviewTable = new Table();
    private InventorySlot currentSelectedItem = null;
    private int currentSelectedQuantity = 0;
    private int currentSelectedPrice = 0;

    public StardropSaloonMenuView(Skin skin, Stage stage, GameScreen gameScreen) {
        super("The Stardrop Saloon Menu", skin);
        this.skin = skin;
        this.stage = stage;
        this.gameScreen = gameScreen;

        setSize(1400, 800);
        setModal(true);
        setMovable(false);
        center();

        StardropSaloon store = gameScreen.getController().getMapManager().getStardropSaloon();
        masterInventory = new ArrayList<>(store.getInventory());

        contentTable = new Table();
        contentTable.defaults().pad(10f);

        filterButton = new TextButton("Show Available", MenuAssetManager.getInstance().getSkin(3));
        buyGoodsButton = new TextButton("Buy", MenuAssetManager.getInstance().getSkin(3));
        closeButton = new TextButton("Close", MenuAssetManager.getInstance().getSkin(3));

        // First display
        refreshGoodsList();

        add(contentTable).expand().center();

        setPosition((stage.getWidth() - getWidth()) / 2f,
            (stage.getHeight() - getHeight()) / 2f);

        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage.clear();
                gameScreen.closeMenu();
                remove();
            }
        });

        filterButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                isFiltered = !isFiltered;
                filterButton.setText(isFiltered ? "Show All" : "Show Available");
                refreshGoodsList();
            }
        });

        buyGoodsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                purchaseSelectedItem();
            }
        });

        stage.addActor(this);
    }

    private void refreshGoodsList() {
        contentTable.clearChildren();
        contentTable.add(createGoodsListFromItems(getFilteredList())).colspan(3).center().row();
        contentTable.add(purchasePreviewTable).colspan(3).expand().center().row();
        contentTable.add(buyGoodsButton);
        contentTable.add(filterButton);
        contentTable.add(closeButton);
    }

    private List<InventorySlot> getFilteredList() {
        if (!isFiltered) {
            return new ArrayList<>(masterInventory);
        }
        List<InventorySlot> filtered = new ArrayList<>();
        for (InventorySlot inventorySlot : masterInventory) {
            if (inventorySlot.quantity() != 0) {
                filtered.add(inventorySlot);
            }
        }
        return filtered;
    }

    public ScrollPane createGoodsListFromItems(List<InventorySlot> items) {
        Table table = new Table();
        table.top().left();

        // Header
        table.add(new Label("Common Products",
            MenuAssetManager.getInstance().getSkin(3), "Bold")).colspan(5).center().row();

        // Column headers
        table.add(new Label("", skin));
        table.add(new Label("Product Name", MenuAssetManager.getInstance().getSkin(3), "Bold")).left().padRight(10);
        table.add(new Label("Price", MenuAssetManager.getInstance().getSkin(3), "Bold")).padRight(30);
        table.add(new Label("Daily Limit", MenuAssetManager.getInstance().getSkin(3), "Bold")).padRight(30);
        table.add(new Label("", skin)).colspan(5).row();

        // Add items
        for (InventorySlot inventorySlot : items) {
            addItem(table, inventorySlot.item().getTextureRegion(GameAssetManager.getInstance()),
                inventorySlot.item().getName(), inventorySlot.quantity(),
                inventorySlot.item().getValue(), inventorySlot);
        }

        return new ScrollPane(table, skin);
    }

    private void addItem(Table table, TextureRegion textureRegion, String name, int remainingQuantity,
                         int price, InventorySlot inventorySlot) {

        String style = (remainingQuantity == 0) ? "default" : "Impact";

        Image icon = new Image(textureRegion);
        Label nameLabel = new Label(name, skin, style);
        Label priceLabel = new Label(price + "g", skin, style);
        Label remainingQuantityLabel = new Label(
            remainingQuantity != -1 ? String.valueOf(remainingQuantity) : "Unlimited", skin, style);
        Label addLabel = new Label(remainingQuantity != 0 ? "Add +" : "Unavailable", skin, style);

        addLabel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(currentSelectedItem == null || (currentSelectedItem != null && currentSelectedItem.item().getName().equals(name))) {
                    if (inventorySlot.quantity() == 0) {
                        gameScreen.showMessage("error", "Product Unavailable", skin,
                            0, 200, stage);
                    } else {
                        gameScreen.playGameSFX("popUp");

                        if (inventorySlot.quantity() != -1) {
                            inventorySlot.reduceQuantity(1);
                        }

                        int newQty = inventorySlot.quantity();
                        if (newQty == 0) {
                            remainingQuantityLabel.setText("0");
                            addLabel.setText("Unavailable");
                            nameLabel.setStyle(skin.get("default", Label.LabelStyle.class));
                            priceLabel.setStyle(skin.get("default", Label.LabelStyle.class));
                            remainingQuantityLabel.setStyle(skin.get("default", Label.LabelStyle.class));
                            addLabel.setStyle(skin.get("default", Label.LabelStyle.class));
                        } else if (newQty != -1) {
                            remainingQuantityLabel.setText(String.valueOf(newQty));
                        }

                        // Update preview table for this product
                        currentSelectedItem = inventorySlot;
                        currentSelectedQuantity++;

                        updatePurchasePreview(
                            textureRegion,
                            inventorySlot.item().getName(),
                            currentSelectedQuantity,
                            price
                        );

                    }
                }
            }
        });

        table.add(icon).padRight(20);
        table.add(nameLabel).padRight(20);
        table.add(priceLabel).padRight(30);
        table.add(remainingQuantityLabel).padRight(70);
        table.add(addLabel).center().padRight(10).padLeft(20).row();
    }

    private void updatePurchasePreview(TextureRegion iconTexture, String name, int quantity, int unitPrice) {
        purchasePreviewTable.clear();
        purchasePreviewTable.add(new Image(iconTexture)).padRight(10);
        purchasePreviewTable.add(new Label(name, skin, "Bold")).padRight(20);
        purchasePreviewTable.add(new Label("x" + quantity, skin, "Bold")).padRight(20);
        purchasePreviewTable.add(new Label(unitPrice + "g (each)", skin, "Bold")).padRight(20);

        // Total price
        int totalPrice = unitPrice * quantity;
        currentSelectedPrice = totalPrice;
        purchasePreviewTable.add(new Label("Total: " + totalPrice + "g", skin, "Bold")).padRight(20);

        // Minus label
        Label minusLabel = new Label("Reduce(-)", skin, "BoldImpact");
        minusLabel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (currentSelectedQuantity > 1) {
                    currentSelectedQuantity--;

                    // Return the item to the item list (restore remaining quantity)
                    if (currentSelectedItem.quantity() != -1) {
                        currentSelectedItem.increaseQuantity(1);
                    }

                    updatePurchasePreview(iconTexture, name, currentSelectedQuantity, unitPrice);
                } else {
                    // Remove completely
                    if (currentSelectedItem.quantity() != -1) {
                        currentSelectedItem.increaseQuantity(1);
                    }
                    currentSelectedQuantity = 0;
                    currentSelectedItem = null;
                    purchasePreviewTable.clear();
                }

                // Refresh goods list so the returned product is visible again
                refreshGoodsList();
            }
        });
        purchasePreviewTable.add(minusLabel).padLeft(10);
    }

    private void purchaseSelectedItem() {
        if (currentSelectedItem == null || currentSelectedQuantity <= 0) {
            gameScreen.showMessage("error", "No product selected", skin, 0, 200, stage);
            return;
        }

        // Check if player has enough money
        if (gameScreen.getPlayer().getGold() < currentSelectedPrice) {
            gameScreen.showMessage("error", "Not enough gold", skin, 0, 200, stage);
            return;
        }

        // Check if already have this recipe / Active recipe
        if (currentSelectedItem.item() instanceof StardropSaloonItem){
            for(CookingRecipe recipe : gameScreen.getPlayer().getCookingRecipeManager().getCookingRecipes()){
                if((recipe.getResult().getName() + " Recipe").equals(currentSelectedItem.item().getName())){
                    if(recipe.getSource().isBought()){
                        gameScreen.showMessage("error", "You already have this recipe!", skin, 0, 200, stage);
                        return;
                    }
                    else{
                        recipe.getSource().setBought(true);
                        gameScreen.getPlayer().setGold(gameScreen.getPlayer().getGold()-currentSelectedPrice);
                        gameScreen.getHUDManager().updateCoin();
                        gameScreen.showMessage("popUp", currentSelectedItem.item().getName() + " activated !", skin, 0, 200, stage);
                        currentSelectedQuantity = 0;
                        currentSelectedItem = null;
                        currentSelectedPrice = 0;
                        purchasePreviewTable.clear();
                        refreshGoodsList();
                        return;
                    }
                }
            }
        }

        if(!gameScreen.getPlayer().getBackpack().getInventory().addItem(currentSelectedItem.item(),currentSelectedQuantity)) {
            gameScreen.showMessage("error", "Not enough space in your backpack", skin, 0, 200, stage);
            return;
        }

        // Deduct money
        gameScreen.getPlayer().setGold(gameScreen.getPlayer().getGold()-currentSelectedPrice);
        gameScreen.getHUDManager().updateCoin();

        // Show Confirmation
        gameScreen.showMessage("popUp", "Purchased " + currentSelectedQuantity + " x " + currentSelectedItem.item().getName(), skin, 0, 200, stage);

        // Reset preview
        currentSelectedQuantity = 0;
        currentSelectedItem = null;
        currentSelectedPrice = 0;
        purchasePreviewTable.clear();

        // Refresh goods list to update availability
        refreshGoodsList();
    }
}
