package com.yourgame.view.GameViews.ArtisanMenuView;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.model.App;
import com.yourgame.model.Item.Inventory.InventorySlot;
import com.yourgame.model.ManuFactor.Artisan.ArtisanMachine;
import com.yourgame.model.ManuFactor.Artisan.ArtisanMachineType;
import com.yourgame.model.ManuFactor.Artisan.EdibleArtisanProduct.EdibleArtisanProductType;
import com.yourgame.model.ManuFactor.Artisan.EdibleArtisanProduct.Products.Honey;
import com.yourgame.model.ManuFactor.Artisan.InEdibleArtisanProduct.InEdibleArtisanProductType;
import com.yourgame.model.ManuFactor.Artisan.InEdibleArtisanProduct.Products.Coal;
import com.yourgame.model.WeatherAndTime.TimeObserver;
import com.yourgame.model.WeatherAndTime.TimeSystem;
import com.yourgame.view.AppViews.GameScreen;

import java.util.ArrayList;

public class CharcoalKilnMenuView extends Window implements TimeObserver {
    private final Skin skin;
    private final GameScreen gameScreen;
    private final Stage stage;

    private ProgressBar progressBar;
    private TextButton cancelButton;
    private TextButton quickAccessButton;
    private TextButton collectButton;
    private Table progressBarTable;

    private ArtisanMachine charcoalKiln;

    private ArrayList<InventorySlot> selectedItems = new ArrayList<>();

    public CharcoalKilnMenuView(Skin skin, Stage stage, GameScreen gameScreen) {
        super("Charcoal Kiln", skin);
        this.stage = stage;
        this.gameScreen = gameScreen;
        this.skin = skin;

        charcoalKiln = gameScreen.getPlayer()
            .getArtisanMachineManager()
            .getArtisanMachine(ArtisanMachineType.CharcoalKiln);

        App.getGameState().getGameTime().addHourObserver(this);

        setSize(1400, 900);
        setModal(true);
        setMovable(false);
        pad(20f);

        Table table = new Table();
        table.defaults().pad(20f);

        TextButton backButton = GameAssetManager.getInstance().getButton("Close");

        Table recipeTable = new Table();
        recipeTable.top().center();
        initTable(recipeTable);

        ScrollPane scrollPane = new ScrollPane(recipeTable, skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);
        table.add(scrollPane).expandX().fillX().row();

        progressBarTable = new Table();
        progressBarTable.top().center();
        initProgressBarTable(progressBarTable);
        table.add(progressBarTable).expandX().fillX().row();

        table.add(backButton).colspan(5).center();

        add(table);

        setPosition((stage.getWidth() - getWidth()) / 2f, (stage.getHeight() - getHeight()) / 2f);

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameScreen.closeMenu();
                App.getGameState().getGameTime().removeHourObserver(CharcoalKilnMenuView.this);
                remove();
            }
        });

        resetUI();
    }

    @Override
    public void onTimeChanged(TimeSystem timeSystem) {
        updateProgressBar();
    }

    private void updateProgressBar() {
        float totalTime = Coal.calculateProcessingTime();
        progressBar.setValue(totalTime - charcoalKiln.getTimeRemaining());
        resetUI();
    }

    public void resetUI() {
        if (charcoalKiln.isProcessing()) {
            progressBarTable.setVisible(true);
            progressBar.setVisible(true);
            cancelButton.setVisible(true);
            quickAccessButton.setVisible(true);
            collectButton.setVisible(false);
            float totalTime = Coal.calculateProcessingTime();
            progressBar.setValue(totalTime - charcoalKiln.getTimeRemaining());
        } else if (charcoalKiln.getTimeRemaining() <= 0 && charcoalKiln.isMustBeCollected()) {
            progressBarTable.setVisible(true);
            progressBar.setVisible(true);
            cancelButton.setVisible(false);
            quickAccessButton.setVisible(false);
            collectButton.setVisible(true);
        } else {
            progressBarTable.setVisible(false);
        }
    }

    private void createCoal() {
        stage.addActor(new IngredientMenuView(skin, stage, gameScreen, this));
    }

    private void stopProcessing() {
        charcoalKiln.stopProcessing();
        resetUI();
    }

    private void collectCoal() {
        Coal coal = new Coal(InEdibleArtisanProductType.Coal);

        if(!gameScreen.getPlayer().getBackpack().getInventory().addItem(coal,1)){
            gameScreen.showMessage("error","Not enough space!",skin,0,200,stage);
        }

        charcoalKiln.setTimeRemaining(0);
        charcoalKiln.setMustBeCollected(false);
        resetUI();
    }

    private void initTable(Table recipeTable) {
        // Add headers
        recipeTable.add(new Label("Icon", skin, "Bold")).pad(10);
        recipeTable.add(new Label("Product", skin, "Bold")).pad(10);
        recipeTable.add(new Label("Ingredients", skin, "Bold")).pad(10);
        recipeTable.add(new Label("Processing Time", skin, "Bold")).pad(10);
        recipeTable.add(new Label("", skin,"Bold")).pad(10);
        recipeTable.row();

        Label createCoal = new Label("Create", skin, "BoldImpact");
        recipeTable.add(new Image(GameAssetManager.getInstance().getArtisanProductTexture("coal")));
        recipeTable.add(new Label("Coal", skin, "Bold")).pad(10);
        recipeTable.add(new Label(" - ", skin,"Bold")).pad(10);
        recipeTable.add(new Label(Coal.calculateProcessingTime() + " Hours", skin,"Bold")).pad(10);
        recipeTable.add(createCoal).pad(10);

        createCoal.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                createCoal();
            }
        });
    }

    private void initProgressBarTable(Table progressBarTable) {
        progressBar = new ProgressBar(0f, Coal.calculateProcessingTime(), 0.1f, false, skin);
        cancelButton = new TextButton("Cancel", skin);
        quickAccessButton = new TextButton("Quick Access", skin);
        collectButton = new TextButton("Collect", skin);

        cancelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stopProcessing();
            }
        });

        quickAccessButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                charcoalKiln.setTimeRemaining(0);
                onTimeChanged(null);
                resetUI();
            }
        });

        collectButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                collectCoal();
            }
        });

        progressBarTable.row();
        progressBarTable.add(progressBar).colspan(5).fillX().pad(10).row();
        progressBarTable.add(cancelButton).pad(5);
        progressBarTable.add(quickAccessButton).pad(5);
        progressBarTable.add(collectButton).pad(5);

        progressBarTable.setVisible(false);
    }

    public void setSelectedItems(ArrayList<InventorySlot> selectedItems) {
        this.selectedItems.clear();
        this.selectedItems.addAll(selectedItems);
    }

    public ArtisanMachine getCharcoalKiln() {
        return charcoalKiln;
    }
}

