package com.yourgame.view.GameViews.ArtisanMenuView;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.model.ManuFactor.Artisan.EdibleArtisanProduct.Products.Honey;
import com.yourgame.view.AppViews.GameScreen;


public class BeeHouseMenuView extends Window {
    private final Skin skin;
    private final GameScreen gameScreen;
    private final Stage stage;

    public BeeHouseMenuView(Skin skin, Stage stage, GameScreen gameScreen) {
        super("Bee House", skin);
        this.stage = stage;
        this.gameScreen = gameScreen;
        this.skin = skin;

        setSize(1400, 900);
        setModal(true);
        setMovable(false);
        pad(20f);

        Table table = new Table();
        table.defaults().pad(20f);

        TextButton backButton = GameAssetManager.getInstance().getButton("Close");

        // Create a Table for the scroll pane content
        Table recipeTable = new Table();
        recipeTable.top().center();
        initTable(recipeTable);


        // Scroll pane
        ScrollPane scrollPane = new ScrollPane(recipeTable, skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);

        table.add(scrollPane).expandX().fillX().row();

        // Process table
        Table processTable = new Table();
        table.add(processTable).expandX().fillX().row();

        table.add(backButton).colspan(5).center();

        add(table);

        setPosition((stage.getWidth() - getWidth()) / 2f, (stage.getHeight() - getHeight()) / 2f);

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage.clear();
                gameScreen.closeMenu();
                remove();
            }
        });
    }

    private void initTable(Table recipeTable) {
        // Add headers
        recipeTable.add(new Label("Icon", skin, "Bold")).pad(10);
        recipeTable.add(new Label("Product", skin, "Bold")).pad(10);
        recipeTable.add(new Label("Ingredients", skin, "Bold")).pad(10);
        recipeTable.add(new Label("Processing Time", skin, "Bold")).pad(10);
        recipeTable.add(new Label("", skin,"Bold")).pad(10);
        recipeTable.row();

        Label createHoney = new Label("Create", skin, "BoldImpact");
        recipeTable.add(new Image(GameAssetManager.getInstance().getArtisanProductTexture("honey")));
        recipeTable.add(new Label("Honey", skin, "Bold")).pad(10);
        recipeTable.add(new Label(" - ", skin,"Bold")).pad(10);
        recipeTable.add(new Label(Honey.calculateProcessingTime() + " Hours", skin,"Bold")).pad(10);
        recipeTable.add(createHoney).pad(10);

        createHoney.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                createHoney();
            }
        });
    }

    private void createHoney() {

    }
}

