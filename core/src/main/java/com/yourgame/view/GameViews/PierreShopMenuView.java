package com.yourgame.view.GameViews;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.Graphics.MenuAssetManager;
import com.yourgame.controller.GameController.PierreShopMenuController;
import com.yourgame.model.App;
import com.yourgame.model.Map.Store.PierreGeneralStore.*;
import com.yourgame.model.Map.Store.ShopItem;
import com.yourgame.model.WeatherAndTime.Season;
import com.yourgame.view.AppViews.GameScreen;

import java.util.ArrayList;
import java.util.List;

public class PierreShopMenuView extends Window {
    private final PierreShopMenuController controller;
    private final Skin skin;
    private final GameScreen gameScreen;
    private final Stage stage;

    private final List<ShopItem> masterInventory; // Always contains full up-to-date inventory
    private boolean isFiltered = false;

    private final Table contentTable;
    private final TextButton filterButton;
    private final TextButton buyGoodsButton;
    private final TextButton closeButton;

    public PierreShopMenuView(Skin skin, Stage stage, GameScreen gameScreen) {
        super("Pierre General Store Menu", skin);
        this.skin = skin;
        this.stage = stage;
        this.gameScreen = gameScreen;

        controller = new PierreShopMenuController();
        controller.setView(this);

        setSize(1200, 800);
        setModal(true);
        setMovable(false);
        center();

        PierreGeneralStore store = gameScreen.getController().getMapManager().getPierreStore();
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
    }

    /** Refreshes the UI with the current filter state */
    private void refreshGoodsList() {
        contentTable.clearChildren();
        contentTable.add(createGoodsListFromItems(getFilteredList())).colspan(3).center().row();
        contentTable.add(buyGoodsButton);
        contentTable.add(filterButton);
        contentTable.add(closeButton);
    }

    /** Returns the filtered list based on current state */
    private List<ShopItem> getFilteredList() {
        if (!isFiltered) {
            return new ArrayList<>(masterInventory);
        }
        List<ShopItem> filtered = new ArrayList<>();
        for (ShopItem item : masterInventory) {
            if (item.getRemainingQuantity() != 0) {
                filtered.add(item);
            }
        }
        return filtered;
    }

    /** Creates goods list UI from provided items */
    public ScrollPane createGoodsListFromItems(List<ShopItem> items) {
        Table table = new Table();
        table.top().left();

        // Header
        table.add(new Label(App.getGameState().getGameTime().getSeason() + " Products",
                MenuAssetManager.getInstance().getSkin(3), "Bold")).colspan(5).center().row();

        // Column headers
        table.add(new Label("", skin));
        table.add(new Label("Product Name", MenuAssetManager.getInstance().getSkin(3), "Bold")).left().padRight(10);
        table.add(new Label("PriceInSeason", MenuAssetManager.getInstance().getSkin(3), "Bold")).padRight(30);
        table.add(new Label("PriceOutOfSeason", MenuAssetManager.getInstance().getSkin(3), "Bold")).padRight(30);
        table.add(new Label("Daily Limit", MenuAssetManager.getInstance().getSkin(3), "Bold")).right().row();

        // Add items by category & season
        for (ShopItem shopItem : items) {
            if (shopItem instanceof PierreGeneralStoreSaplingItem ||
                    shopItem instanceof PierreGeneralStoreBackPackUpgrade) {
                addItem(table, shopItem.getTextureRegion(GameAssetManager.getInstance()),
                        shopItem.getName(), shopItem.getRemainingQuantity(),
                        shopItem.getValue(), -1, shopItem);
            } else if (shopItem instanceof PierreGeneralStoreSeedsItem) {
                Season season = ((PierreGeneralStoreSeedsItem) shopItem).getSeason();
                if (season == App.getGameState().getGameTime().getSeason()) {
                    addItem(table, shopItem.getTextureRegion(GameAssetManager.getInstance()),
                            shopItem.getName(), shopItem.getRemainingQuantity(),
                            shopItem.getValue(),
                            ((PierreGeneralStoreSeedsItem) shopItem).getPriceOutOfSeason(),
                            shopItem);
                }
            }
        }

        return new ScrollPane(table, skin);
    }

    private void addItem(Table table, TextureRegion textureRegion, String name, int remainingQuantity,
                         int price, int priceOut, ShopItem shopItem) {

        String style = (remainingQuantity == 0) ? "default" : "Impact";

        Image icon = new Image(textureRegion);
        Label nameLabel = new Label(name, skin, style);
        Label priceLabel = new Label(price + "g", skin, style);
        Label priceOutLabel = new Label(priceOut != -1 ? priceOut + "g" : "-", skin, style);
        Label remainingQuantityLabel = new Label(
                remainingQuantity != -1 ? String.valueOf(remainingQuantity) : "Unlimited", skin, style);
        Label addLabel = new Label(remainingQuantity != 0 ? "Add +" : "Unavailable", skin, style);

        addLabel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (shopItem.getRemainingQuantity() == 0) {
                    gameScreen.showMessage("error", "Product Unavailable", skin,
                            0, 200, stage);
                } else {
                    gameScreen.playGameSFX("popUp");

                    if(shopItem.getRemainingQuantity() != -1){
                        shopItem.setRemainingQuantity();
                    }

                    int newQty = shopItem.getRemainingQuantity();
                    if (newQty == 0) {
                        remainingQuantityLabel.setText("-");
                        addLabel.setText("Unavailable");
                        nameLabel.setStyle(skin.get("default", Label.LabelStyle.class));
                        priceLabel.setStyle(skin.get("default", Label.LabelStyle.class));
                        priceOutLabel.setStyle(skin.get("default", Label.LabelStyle.class));
                        remainingQuantityLabel.setStyle(skin.get("default", Label.LabelStyle.class));
                        addLabel.setStyle(skin.get("default", Label.LabelStyle.class));
                    } else if (newQty != -1) {
                        remainingQuantityLabel.setText(String.valueOf(newQty));
                    }
                }
            }
        });

        table.add(icon).padRight(20);
        table.add(nameLabel).padRight(20);
        table.add(priceLabel).padRight(30);
        table.add(priceOutLabel).padRight(30);
        table.add(remainingQuantityLabel).padRight(70);
        table.add(addLabel).center().padRight(10).padLeft(20).row();
    }
}
