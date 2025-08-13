package com.yourgame.view.GameViews;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.Graphics.MenuAssetManager;
import com.yourgame.controller.GameController.PierreShopMenuController;
import com.yourgame.model.App;
import com.yourgame.model.Map.Store.PierreGeneralStore.PierreGeneralStore;
import com.yourgame.model.Map.Store.PierreGeneralStore.PierreGeneralStoreBackPackUpgrade;
import com.yourgame.model.Map.Store.PierreGeneralStore.PierreGeneralStoreSaplingItem;
import com.yourgame.model.Map.Store.PierreGeneralStore.PierreGeneralStoreSeedsItem;
import com.yourgame.model.Map.Store.ShopItem;
import com.yourgame.model.WeatherAndTime.Season;
import com.yourgame.view.AppViews.GameScreen;

import java.util.ArrayList;


public class PierreShopMenuView extends Window {
    private final PierreShopMenuController controller;
    private final Skin skin;
    GameScreen gameScreen;
    Stage stage;

    private java.util.List<ShopItem> originalInventoryOrder;
    private boolean isSorted = false;

    private java.util.List<ShopItem> orderedItems = new ArrayList<ShopItem>();

    public PierreShopMenuView(Skin skin, Stage stage, GameScreen gameScreen) {
        super("Pierre General Store Menu", skin);
        controller = new PierreShopMenuController();
        controller.setView(this);

        this.skin = skin;
        this.gameScreen = gameScreen;
        this.stage = stage;

        setSize(1200, 800);
        setModal(true);
        setMovable(false);
        center();

        Table contentTable = new Table();
        contentTable.defaults().pad(10f);

        TextButton filterButton = new TextButton("Show Available", MenuAssetManager.getInstance().getSkin(3));
        TextButton buyGoodsButton = new TextButton("Buy", MenuAssetManager.getInstance().getSkin(3));
        TextButton closeButton = new TextButton("Close", MenuAssetManager.getInstance().getSkin(3));

        contentTable.add(createGoodsList(gameScreen.getController().getMapManager().getPierreStore())).colspan(3).center().row();
        contentTable.add(buyGoodsButton);
        contentTable.add(filterButton);
        contentTable.add(closeButton);

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
                PierreGeneralStore store = gameScreen.getController().getMapManager().getPierreStore();
                originalInventoryOrder = new java.util.ArrayList<>(store.getInventory());

                if (!isSorted) {
                    java.util.List<ShopItem> filtered = new java.util.ArrayList<>();
                    for (ShopItem item : originalInventoryOrder) {
                        if (item.getRemainingQuantity() != 0) {
                            filtered.add(item);
                        }
                    }
                    store.getInventory().clear();
                    store.getInventory().addAll(filtered);

                    isSorted = true;
                    filterButton.setText("Show All");
                }
                else {
                    store.getInventory().clear();
                    store.getInventory().addAll(originalInventoryOrder);

                    isSorted = false;
                    filterButton.setText("Show Available");
                }


                // Refresh the goods list
                contentTable.clearChildren();
                contentTable.add(createGoodsList(store)).colspan(3).center().row();
                contentTable.add(buyGoodsButton);
                contentTable.add(filterButton);
                contentTable.add(closeButton).row();
            }
        });

    }

    public ScrollPane createGoodsList(PierreGeneralStore store) {
        Table table = new Table();
        table.top().left();

        table.add(new Label(App.getGameState().getGameTime().getSeason()+" Products",MenuAssetManager.getInstance().getSkin(3),"Bold")).colspan(5).center().row();
        table.add(new Label("",skin));
        table.add(new Label("Product Name", MenuAssetManager.getInstance().getSkin(3),"Bold")).left().padRight(10);
        table.add(new Label("PriceInSeason",MenuAssetManager.getInstance().getSkin(3),"Bold")).padRight(30);
        table.add(new Label("PriceOutOfSeason",MenuAssetManager.getInstance().getSkin(3),"Bold")).padRight(30);
        table.add(new Label("Daily Limit",MenuAssetManager.getInstance().getSkin(3),"Bold")).right().row();

        commonProducts(table, store);

        return switch (App.getGameState().getGameTime().getSeason()){
            case Spring -> spring(table,store);
            case Summer -> summer(table,store);
            case Fall -> fall(table,store);
            case Winter -> winter(table);
        };
    }

    private void commonProducts(Table table, PierreGeneralStore store) {
        for(ShopItem shopItem : store.getInventory()){
            if(shopItem instanceof PierreGeneralStoreSaplingItem || shopItem instanceof PierreGeneralStoreBackPackUpgrade){
                addItem(table, shopItem.getTextureRegion(GameAssetManager.getInstance()), shopItem.getName(), shopItem.getRemainingQuantity(), shopItem.getValue(), -1, shopItem);
            }
        }
    }

    private ScrollPane spring(Table table, PierreGeneralStore store) {
        for(ShopItem shopItem : store.getInventory()){
            if(shopItem instanceof PierreGeneralStoreSeedsItem){
                if(((PierreGeneralStoreSeedsItem) shopItem).getSeason() == Season.Spring){
                    addItem(table, shopItem.getTextureRegion(GameAssetManager.getInstance()), shopItem.getName(), shopItem.getRemainingQuantity(), shopItem.getValue(), ((PierreGeneralStoreSeedsItem) shopItem).getPriceOutOfSeason(), shopItem);
                }
            }
        }
        return new ScrollPane(table, skin);
    }

    private ScrollPane summer(Table table, PierreGeneralStore store) {
        for(ShopItem shopItem : store.getInventory()){
            if(shopItem instanceof PierreGeneralStoreSeedsItem){
                if(((PierreGeneralStoreSeedsItem) shopItem).getSeason() == Season.Summer){
                    addItem(table, shopItem.getTextureRegion(GameAssetManager.getInstance()), shopItem.getName(), shopItem.getRemainingQuantity(), shopItem.getValue(), ((PierreGeneralStoreSeedsItem) shopItem).getPriceOutOfSeason(), shopItem);
                }
            }
        }
        return new ScrollPane(table, skin);
    }

    private ScrollPane fall(Table table, PierreGeneralStore store) {
        for(ShopItem shopItem : store.getInventory()){
            if(shopItem instanceof PierreGeneralStoreSeedsItem){
                if(((PierreGeneralStoreSeedsItem) shopItem).getSeason() == Season.Fall){
                    addItem(table, shopItem.getTextureRegion(GameAssetManager.getInstance()), shopItem.getName(), shopItem.getRemainingQuantity(), shopItem.getValue(), ((PierreGeneralStoreSeedsItem) shopItem).getPriceOutOfSeason(), shopItem);
                }
            }
        }
        return new ScrollPane(table, skin);
    }

    private ScrollPane winter(Table table) {
        return new ScrollPane(table, skin);
    }

    private void addItem(Table table, TextureRegion textureRegion, String name, int remainingQuantity, int price, int priceOut, ShopItem shopItem) {
        String style = (remainingQuantity == 0) ? "default" : "Impact";

        Image icon = new Image(textureRegion);
        Label nameLabel = new Label(name, skin, style);
        Label priceLabel = new Label(price + "g", skin, style);
        Label priceOutLabel = new Label(priceOut != -1 ? priceOut + "g" : "-", skin, style);
        Label remainingQuantityLabel = new Label(remainingQuantity != -1 ? String.valueOf(remainingQuantity) : "Unlimited", skin, style);
        Label addLabel = new Label(remainingQuantity != 0 ? "Add +" : "Unavailable", skin, style);

        addLabel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(shopItem.getRemainingQuantity() == 0){
                    gameScreen.showMessage("error","Product Unavailable",skin,0,200,stage);
                }
                else{
                    gameScreen.playGameSFX("popUp");
                    if(shopItem.getRemainingQuantity() == 1){
                        shopItem.setRemainingQuantity();
                        remainingQuantityLabel.setText("-");
                        addLabel.setText("Unavailable");

                        nameLabel.setStyle(skin.get("default", Label.LabelStyle.class));
                        priceLabel.setStyle(skin.get("default", Label.LabelStyle.class));
                        priceOutLabel.setStyle(skin.get("default", Label.LabelStyle.class));
                        remainingQuantityLabel.setStyle(skin.get("default", Label.LabelStyle.class));
                        addLabel.setStyle(skin.get("default", Label.LabelStyle.class));
                    }
                    else if(shopItem.getRemainingQuantity() != -1){
                        shopItem.setRemainingQuantity();
                        remainingQuantityLabel.setText(shopItem.getRemainingQuantity());
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

    private void addToCart(){

    };
}
