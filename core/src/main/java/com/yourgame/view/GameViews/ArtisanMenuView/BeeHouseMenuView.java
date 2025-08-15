package com.yourgame.view.GameViews.ArtisanMenuView;

import com.badlogic.gdx.Gdx;
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
import com.yourgame.model.WeatherAndTime.TimeObserver;
import com.yourgame.model.WeatherAndTime.TimeSystem;
import com.yourgame.view.AppViews.GameScreen;

import java.util.ArrayList;

public class BeeHouseMenuView extends Window implements TimeObserver {
    private final Skin skin;
    private final GameScreen gameScreen;
    private final Stage stage;

    private ProgressBar progressBar;
    private TextButton cancelButton;
    private TextButton quickAccessButton;
    private TextButton collectButton;
    private Table progressBarTable;

    private ArtisanMachine beeHouse;

    private ArrayList<InventorySlot> selectedItems = new ArrayList<>();

    public BeeHouseMenuView(Skin skin, Stage stage, GameScreen gameScreen) {
        super("Bee House", skin);
        this.stage = stage;
        this.gameScreen = gameScreen;
        this.skin = skin;

        beeHouse = gameScreen.getPlayer()
            .getArtisanMachineManager()
            .getArtisanMachine(ArtisanMachineType.BeeHouse);

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
                App.getGameState().getGameTime().removeHourObserver(BeeHouseMenuView.this);
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
        float totalTime = Honey.calculateProcessingTime();
        progressBar.setValue(totalTime - beeHouse.getTimeRemaining());
        resetUI();
    }

    public void resetUI() {
        if (beeHouse.isProcessing()) {
            progressBarTable.setVisible(true);
            progressBar.setVisible(true);
            cancelButton.setVisible(true);
            quickAccessButton.setVisible(true);
            collectButton.setVisible(false);
            float totalTime = Honey.calculateProcessingTime();
            progressBar.setValue(totalTime - beeHouse.getTimeRemaining());
        } else if (beeHouse.getTimeRemaining() <= 0 && beeHouse.isMustBeCollected()) {
            progressBarTable.setVisible(true);
            progressBar.setVisible(true);
            cancelButton.setVisible(false);
            quickAccessButton.setVisible(false);
            collectButton.setVisible(true);
        } else {
            progressBarTable.setVisible(false);
        }
    }

    private void createHoney() {
        stage.addActor(new IngredientMenuView(skin, stage, gameScreen, this));
    }

    private void stopProcessing() {
        beeHouse.stopProcessing();
        resetUI();
    }

    private void collectHoney() {
        Honey honey = new Honey(EdibleArtisanProductType.Honey,null);

        if(!gameScreen.getPlayer().getBackpack().getInventory().addItem(honey,1)){
            gameScreen.showMessage("error","Not enough space!",skin,0,200,stage);
        }

        beeHouse.setTimeRemaining(0);
        beeHouse.setMustBeCollected(false);
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

    private void initProgressBarTable(Table progressBarTable) {
        progressBar = new ProgressBar(0f, Honey.calculateProcessingTime(), 0.1f, false, skin);
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
                beeHouse.setTimeRemaining(0);
                onTimeChanged(null);
                resetUI();
            }
        });

        collectButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                collectHoney();
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

    public ArtisanMachine getBeeHouse() {
        return beeHouse;
    }
}
