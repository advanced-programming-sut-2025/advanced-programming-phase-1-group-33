package com.yourgame.view.GameViews;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.yourgame.Graphics.MenuAssetManager;
import com.yourgame.controller.GameController.PierreShopMenuController;
import com.yourgame.model.App;
import com.yourgame.view.AppViews.GameScreen;

import java.awt.*;

public class PierreShopMenuView extends Window {
    private final PierreShopMenuController controller;
    private final Skin skin;

    public PierreShopMenuView(Skin skin, Stage stage, GameScreen gameScreen) {
        super("Pierre General Store Menu", skin);
        controller = new PierreShopMenuController();
        controller.setView(this);

        this.skin = skin;

        setSize(1000, 800);
        setModal(true);
        setMovable(false);
        center();

        Table contentTable = new Table();
        contentTable.defaults().pad(10f);

        TextButton buyGoodsButton = new TextButton("Buy", MenuAssetManager.getInstance().getSkin(3));
        TextButton closeButton = new TextButton("Close", MenuAssetManager.getInstance().getSkin(3));

        contentTable.add(createGoodsList()).colspan(2).center().row();
        contentTable.add(buyGoodsButton);
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
    }

    public ScrollPane createGoodsList(){
        Table table = new Table();
        table.top().left();
        table.add(new Label(App.getGameState().getGameTime().getSeason()+" Products",MenuAssetManager.getInstance().getSkin(3),"Bold")).colspan(4).center().row();
        table.add(new Label("   Product Name", MenuAssetManager.getInstance().getSkin(3),"Bold")).left().padRight(10);
        table.add(new Label("Price(InSeason/OutOfSeason)",MenuAssetManager.getInstance().getSkin(3),"Bold")).padRight(30);
        table.add(new Label("Daily Limit",MenuAssetManager.getInstance().getSkin(3),"Bold")).right().row();

        return switch (App.getGameState().getGameTime().getSeason()){
            case Spring -> spring(table);
            case Summer -> summer(table);
            case Fall -> fall(table);
            case Winter -> winter(table);
        };
    }

    private ScrollPane spring(Table table) {

//        addItem(table, "- Parsnip Seeds", "Plant these in the spring. Takes 4 days to mature.","5", 20);
//        addItem(table, "- Bean Starter", "Plant these in the spring. Takes 10 days to mature, but keeps producing after that.","5", 60);
//        addItem(table, "- Cauliflower Seeds", "Plant these in the spring. Takes 12 days to produce.", "5",80);
//        addItem(table, "- Potato Seeds", "Plant these in the spring. Takes 6 days to mature.", "5",50);
//        addItem(table, "- Tulip Bulb", "Plant in spring. Takes 6 days to produce a flower.", "5",20);
//        addItem(table, "- Kale Seeds", "Plant these in the spring. Takes 6 days to mature.", "5",70);
//        addItem(table, "- Jazz Seeds", "Plant in spring. Takes 7 days to produce a blue flower.", "5",30);
//        addItem(table, "- Garlic Seeds", "Plant these in the spring. Takes 4 days to mature.", "5",40);
//        addItem(table, "- Rice Shoot", "Plant in spring. Takes 8 days to mature. Faster near water.", "5",40);


        return new ScrollPane(table, skin);
    }

    private ScrollPane summer(Table table) {
        addItem(table, "Melon Seeds", "Plant these in the summer. Takes 12 days to mature.","5", 80);
        addItem(table, "Tomato Seeds", "Plant in summer. Takes 11 days to mature, produces after harvest.","5", 50);
        addItem(table, "Blueberry Seeds", "Plant in summer. Takes 13 days to mature, produces after harvest.","5", 80);
        addItem(table, "Pepper Seeds", "Plant in summer. Takes 5 days to mature, produces after harvest.","5", 40);
        addItem(table, "Wheat Seeds", "Plant in summer or fall. Takes 4 days to mature.", "5",10);
        addItem(table, "Radish Seeds", "Plant in summer. Takes 6 days to mature.", "5",40);
        addItem(table, "Poppy Seeds", "Plant in summer. Produces a red flower in 7 days.", "5",100);
        addItem(table, "Spangle Seeds", "Plant in summer. Takes 8 days to produce a flower.", "5",50);
        addItem(table, "Hops Starter", "Plant in summer. Takes 11 days to grow, keeps producing.", "5",60);
        addItem(table, "Corn Seeds", "Plant in summer or fall. Takes 14 days to mature, produces after harvest.", "5",150);
        addItem(table, "Sunflower Seeds", "Plant in summer or fall. Takes 8 days to mature.", "5",200);
        addItem(table, "Red Cabbage Seeds", "Plant in summer. Takes 9 days to mature.", "5",100);

        return new ScrollPane(table, skin);
    }

    private ScrollPane fall(Table table) {

//        addItem(table, "Eggplant Seeds", "Plant in fall. Takes 5 days to mature, produces after harvest.", 20);
//        addItem(table, "Corn Seeds", "Plant in summer or fall. Takes 14 days to mature, produces after harvest.", 150);
//        addItem(table, "Pumpkin Seeds", "Plant in fall. Takes 13 days to mature.", 100);
//        addItem(table, "Bok Choy Seeds", "Plant in fall. Takes 4 days to mature.", 50);
//        addItem(table, "Yam Seeds", "Plant in fall. Takes 10 days to mature.", 60);
//        addItem(table, "Cranberry Seeds", "Plant in fall. Takes 7 days to mature, produces after harvest.", 240);
//        addItem(table, "Sunflower Seeds", "Plant in summer or fall. Takes 8 days to mature.", 200);
//        addItem(table, "Fairy Seeds", "Plant in fall. Takes 12 days to produce a mysterious flower.", 200);
//        addItem(table, "Amaranth Seeds", "Plant in fall. Takes 7 days to grow.", 70);
//        addItem(table, "Grape Starter", "Plant in fall. Takes 10 days to grow, keeps producing.", 60);
//        addItem(table, "Wheat Seeds", "Plant in summer or fall. Takes 4 days to mature.", 10);
//        addItem(table, "Artichoke Seeds", "Plant in fall. Takes 8 days to mature.", 30);

        return new ScrollPane(table, skin);
    }

    private ScrollPane winter(Table table){
        return new ScrollPane(table, skin);
    }

    private void addItem(Table table, String name, String description, String dailyLimit, int price) {
        Label nameLabel = new Label(name, skin);
        Label priceLabel = new Label(price + "g", skin);
        Label dailyLimitLabel = new Label(dailyLimit, skin);
        Label addLabel = new Label("+", skin);

        // Tooltip for description
        Tooltip<Label> tooltip = new Tooltip<>(new Label(description, skin));
        tooltip.setInstant(true);
        nameLabel.addListener(tooltip);

        addLabel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //controller.buyItem(name, price);
            }
        });

        table.add(nameLabel).left().padRight(20);
        table.add(priceLabel).padRight(30);
        table.add(dailyLimit).padRight(40);
        table.add(addLabel).right().row();
    }
}
