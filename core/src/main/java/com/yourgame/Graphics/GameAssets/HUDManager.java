package com.yourgame.Graphics.GameAssets;

// Assuming these imports are already present or will be added
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.Graphics.MenuAssetManager;
import com.yourgame.model.Food.Buff;
import com.yourgame.model.Item.Inventory.Inventory;
import com.yourgame.model.Item.Inventory.InventorySlot;
import com.yourgame.model.Item.Item;
import com.yourgame.model.App;
import com.yourgame.model.UserInfo.Player;
import com.yourgame.model.WeatherAndTime.TimeObserver;

import java.util.ArrayList;
import java.util.List;

// --- New HUDManager Class (Conceptual - you'll implement this) ---
// This class will encapsulate the creation and management of HUD elements.
public class HUDManager {
    private Stage hudStage;
    private clockUIAssetManager clockUI;
    private GameAssetManager assetManager;
    private final Skin skin_Nz = MenuAssetManager.getInstance().getSkin(3);

    private Player localPlayer;

    // Buff
    private final Table buffsTable;

    // Energy Bar
    private Texture[] energy_bar_textures;
    private Image energyBarImage;

    // Info Bar
    private ImageButton weatherTypeButton;
    private ImageButton seasonButton;

    private Texture InventoryTexture;
    private TextField timeField, dateField, goldField;

    // Inventory Bar
    private final InventorySlotUI[] inventorySlotsUI; // Array of our new UI slots
    private int selectedSlotIndex = 0;
    private final Drawable selectionDrawable;
    private final BitmapFont font;

    // Testing the Time
    private TimeObserver timeObserver;

    public float timeAccumulator = 0f; // Used to track time for updates`

    public HUDManager(Stage stage, clockUIAssetManager clockUI, GameAssetManager assetManager, Player localPlayer) {
        this.hudStage = stage;
        this.clockUI = clockUI;
        this.assetManager = assetManager;

        this.localPlayer = localPlayer;

        this.energy_bar_textures = clockUI.getEnergyBarMode();
        this.inventorySlotsUI = new InventorySlotUI[12];
        this.font = MenuAssetManager.getInstance().getSkin(3).getFont("Text");
        this.selectionDrawable = createSelectionHighlight();
        // Initialize the UI slots array
        for (int i = 0; i < inventorySlotsUI.length; i++) {
            inventorySlotsUI[i] = new InventorySlotUI(selectionDrawable, font);
        }

        // Initialize the table for buffs
        this.buffsTable = new Table();
        buffsTable.setFillParent(true);
        buffsTable.top().right().padTop(10).padRight(210); // padRight should be width of clock + padding
        hudStage.addActor(buffsTable);
    }

    /**
     * Creates a simple drawable that will be used to highlight the selected
     * inventory slot.
     */
    private Drawable createSelectionHighlight() {
        Pixmap pixmap = new Pixmap(56, 56, Pixmap.Format.RGBA8888);
        pixmap.setColor(new Color(1, 1, 0, 0.4f)); // Semi-transparent yellow
        pixmap.fill();
        TextureRegionDrawable drawable = new TextureRegionDrawable(new Texture(pixmap));
        pixmap.dispose();
        return drawable;
    }

    // Method to create and add the info bar (clock, weather, season) and energy bar
    // to the HUD stage
    public void createHUD() {
        hudStage.addActor(createClockBarTable());
        hudStage.addActor(createEnergyBarTable());
        hudStage.addActor(createInventoryBarTable());
    }

    public Table createClockBarTable() {
        Table clockBarTable = new Table();
        clockBarTable.setFillParent(true);
        clockBarTable.top().right();

        Skin clockSkin = clockUI.getClockWeatherSkin();
        ImageButton clockImg = new ImageButton(clockSkin, "MainClockButton");

        // Create the weather and season buttons and store references to them
        String season_BPath = App.getGameState().getGameTime().getSeason().getButtonPath();
        String weather_BPath = App.getGameState().getGameTime().getWeather().getButtonPath();

        this.weatherTypeButton = new ImageButton(clockSkin, weather_BPath);
        this.seasonButton = new ImageButton(clockSkin, season_BPath);

        Stack clockAndIndicatorsStack = new Stack();
        clockAndIndicatorsStack.add(clockImg);

        Table overlayButtonsTable = new Table();
        overlayButtonsTable.setFillParent(true);

        overlayButtonsTable.add(this.weatherTypeButton)
                .padBottom(60).padLeft(80)
                .align(Align.topLeft).expandX().size(35);

        overlayButtonsTable.add(this.seasonButton)
                .padBottom(60).padLeft(15)
                .align(Align.topLeft).expandX().size(35);

        clockAndIndicatorsStack.add(overlayButtonsTable);
        // 2. Create a new table for time-related text fields
        Table timeInfoTable = new Table();
        timeInfoTable.setFillParent(true);
        timeInfoTable.top().right().padTop(10).padRight(10); // adjust padding as needed

        timeField = new TextField(App.getGameState().getGameTime().getTimeString(), skin_Nz, "default");
        dateField = new TextField(App.getGameState().getGameTime().getDateToString(), skin_Nz, "default");
        //goldField = new TextField("todo", skin_Nz, "default");

        timeField.setDisabled(true);
        dateField.setDisabled(true);
        //goldField.setDisabled(true);
        timeField.setMaxLength(5);

        timeInfoTable.add(dateField).padBottom(5).top().right().height(40).padBottom(40).row();
        timeInfoTable.add(timeField).padBottom(5).top().right().height(40).padBottom(40).row();
        //timeInfoTable.add(goldField).top().right().height(40).padBottom(60);

        clockAndIndicatorsStack.add(timeInfoTable);

        clockBarTable.add(clockAndIndicatorsStack).size(196, 196).pad(10).top().right();
        return clockBarTable;
    }

    /**
     * Creates the inventory bar with 12 slots.
     * Uses a Stack to overlay tool icons and a selection highlight on top of the
     * inventory background image.
     */
    public Table createInventoryBarTable() {
        Table inventoryContainerTable = new Table();
        inventoryContainerTable.setFillParent(true);
        inventoryContainerTable.bottom().padBottom(10);

        Stack inventoryStack = new Stack();
        inventoryStack.add(new Image(clockUI.getInventoryTexture())); // Background

        Table slotsTable = new Table();
        slotsTable.pad(20, 16, 19, 16);

        for (int i = 0; i < 12; i++) {
            // Add the UI slot to the table. Tweak size and padding to fit your background image.
            slotsTable.add(inventorySlotsUI[i]).width(56).height(56).padLeft(4).padRight(4);
        }
        inventoryStack.add(slotsTable);

        inventoryContainerTable.add(inventoryStack);
        selectSlot(0); // Select the first slot by default
        return inventoryContainerTable;
    }

    /**
     * This is the core method to keep the UI in sync with the player's data.
     * Call this whenever the inventory changes, or every frame.
     * @param playerInventory The player's actual inventory data.
     */
    public void updateInventory(Inventory playerInventory) {
        ArrayList<InventorySlot> dataSlots = playerInventory.getSlots();
        for (int i = 0; i < 12; i++) {
            if (i < dataSlots.size()) {
                // Update the UI slot with data from the corresponding inventory slot
                inventorySlotsUI[i].update(dataSlots.get(i), assetManager);
            } else {
                // This inventory slot is empty, so pass null to clear the UI slot
                inventorySlotsUI[i].update(null, assetManager);
            }
        }
    }

    /**
     * Selects an inventory slot, updating the visual highlight.
     * @param index The index of the slot to select (0-11).
     */
    public void selectSlot(int index) {
        if (index < 0 || index >= inventorySlotsUI.length) return;

        inventorySlotsUI[selectedSlotIndex].setSelected(false); // Deselect the old one
        selectedSlotIndex = index;
        inventorySlotsUI[selectedSlotIndex].setSelected(true); // Select the new one

        Item selectedItem = inventorySlotsUI[selectedSlotIndex].getItem();
        this.localPlayer.getBackpack().getInventory().selectItem(selectedItem);
    }

    public Table createEnergyBarTable() {
        Table energyBarTable = new Table();
        energyBarTable.setFillParent(true);
        energyBarTable.bottom().right().padRight(10).padBottom(10);

        int initialPhase = localPlayer.getEnergyPhase(); // Get phase from the player
        this.energyBarImage = new Image(this.energy_bar_textures[initialPhase]);

        energyBarTable.add(energyBarImage);
        return energyBarTable;
    }

    public void updateEnergyBar() {
        if (energyBarImage == null) {
            Gdx.app.log("HUDManager", "Energy bar Image not initialized. Cannot update.");
            return;
        }

        int newPhase = this.localPlayer.getEnergyPhase();
        if (newPhase >= 0 && newPhase < energy_bar_textures.length) {
            energyBarImage.setDrawable(new Image(this.energy_bar_textures[newPhase]).getDrawable());
        }
    }

    public void updateTime(float delta) {

        timeField.setText(App.getGameState().getGameTime().getTimeString());
        dateField.setText(App.getGameState().getGameTime().getDateToString());

        this.timeAccumulator += delta;
        if (this.timeAccumulator >= 7f) { // every 7 seconds = 10 minutes
            Gdx.app.log("time", App.getGameState().getGameTime().getTimeString());
            Gdx.app.log("time", App.getGameState().getGameTime().getDateToString());
            App.getGameState().getGameTime().advanceMinutes(10);
            this.timeAccumulator = 0f;
        }

    }

    public void updateWeather() {
        String b_path = App.getGameState().getGameTime().getWeather().getButtonPath();

        if (weatherTypeButton != null) {
            Skin skin = clockUI.getClockWeatherSkin();
            weatherTypeButton.setStyle(skin.get(b_path, ImageButton.ImageButtonStyle.class));
        }
    }

    public void updateSeason() {
        String b_path = App.getGameState().getGameTime().getSeason().getButtonPath();

        if (seasonButton != null) {
            Skin skin = clockUI.getClockWeatherSkin();
            seasonButton.setStyle(skin.get(b_path, ImageButton.ImageButtonStyle.class));
        }
    }

    public int getSelectedSlotIndex() {
        return selectedSlotIndex;
    }

    /**
     * This method is called every frame to keep the buff display in sync.
     * @param activeBuffs The current list of active buffs from the BuffManager.
     */
    public void updateBuffs(List<Buff> activeBuffs) {
        // Clear the table to redraw it from scratch
        buffsTable.clearChildren();

        if (activeBuffs.isEmpty()) {
            return; // Nothing to show
        }

        for (Buff buff : activeBuffs) {
            Image buffIcon = new Image(assetManager.getTexture(buff.getIconPath()));
            buffsTable.add(buffIcon).padLeft(5);
        }
    }
}
