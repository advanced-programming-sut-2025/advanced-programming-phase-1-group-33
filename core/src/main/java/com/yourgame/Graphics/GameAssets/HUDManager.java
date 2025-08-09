package com.yourgame.Graphics.GameAssets;

// Assuming these imports are already present or will be added
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
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
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.Graphics.MenuAssetManager;
import com.yourgame.model.Item.Inventory.Inventory;
import com.yourgame.model.Item.Inventory.InventorySlot;
import com.yourgame.model.Item.Tools.Tool;

import java.util.ArrayList;

// --- New HUDManager Class (Conceptual - you'll implement this) ---
// This class will encapsulate the creation and management of HUD elements.
public class HUDManager {
    private Stage hudStage;
    private clockUIAssetManager clockUI;
    private GameAssetManager assetManager;

    // Energy Bar
    private Texture[] energy_bar_textures;
    private Image energyBarImage;

    // Info Bar
    private ImageButton weatherTypeButton;
    private ImageButton seasonButton;

    private Texture InventoryTexture;

    // Inventory Bar
    private final InventorySlotUI[] inventorySlotsUI; // Array of our new UI slots
    private int selectedSlotIndex = 0;
    private final Drawable selectionDrawable;
    private final BitmapFont font;

    public HUDManager(Stage stage, clockUIAssetManager clockUI, GameAssetManager assetManager) {
        this.hudStage = stage;
        this.clockUI = clockUI;
        this.assetManager = assetManager;
        this.energy_bar_textures = clockUI.getEnergyBarMode();
        this.inventorySlotsUI = new InventorySlotUI[12];
        this.font = MenuAssetManager.getInstance().getSkin(3).getFont("Text");
        this.selectionDrawable = createSelectionHighlight();
        // Initialize the UI slots array
        for (int i = 0; i < inventorySlotsUI.length; i++) {
            inventorySlotsUI[i] = new InventorySlotUI(selectionDrawable, font);
        }
    }

    /**
     * Creates a simple drawable that will be used to highlight the selected inventory slot.
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
    public void createHUD(weatherTypeButton initialWeather, seasonTypeButton initialSeason, int initialEnergyPhase) {
        hudStage.addActor(createClockBarTable(initialWeather, initialSeason));
        hudStage.addActor(createEnergyBarTable(initialEnergyPhase));
        hudStage.addActor(createInventoryBarTable());

    }

    public Table createClockBarTable(weatherTypeButton weatherType, seasonTypeButton seasonType) {
        Table clockBarTable = new Table();
        clockBarTable.setFillParent(true);
        clockBarTable.top().right();

        Skin clockSkin = clockUI.getClockWeatherSkin();
        ImageButton clockImg = new ImageButton(clockSkin, "MainClockButton");

        // Create the weather and season buttons and store references to them
        this.weatherTypeButton = new ImageButton(clockSkin, weatherType.getButtonPath());
        this.seasonButton = new ImageButton(clockSkin, seasonType.getButtonPath());

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

        clockBarTable.add(clockAndIndicatorsStack).size(196, 196).pad(10).top().right();
        return clockBarTable;
    }

    /**
     * Creates the inventory bar with 12 slots.
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
    }

    // Energy Bar HAndler
    public Table createEnergyBarTable(int initialPhase) {
        Table energy_barTable = new Table();
        energy_barTable.setFillParent(true);
        energy_barTable.bottom().right().padRight(10).padBottom(10);

        if (initialPhase >= 0 && initialPhase < energy_bar_textures.length) {
            this.energyBarImage = new Image(this.energy_bar_textures[initialPhase]);
        } else {
            Gdx.app.log("HUDManager", "Initial energy phase " + initialPhase + " is out of bounds. Using phase 0.");
            this.energyBarImage = new Image(this.energy_bar_textures[0]);
        }

        energy_barTable.add(energyBarImage);
        return energy_barTable;
    }

    public void updateEnergyBar(int newPhase) {
        if (energyBarImage == null) {
            Gdx.app.log("HUDManager", "Energy bar Image not initialized. Cannot update.");
            return;
        }
        if (newPhase >= 0 && newPhase < energy_bar_textures.length) {
            energyBarImage.setDrawable(new Image(this.energy_bar_textures[newPhase]).getDrawable());
        } else {
            Gdx.app.log("Energy_bar", "Invalid energy bar phase: " + newPhase);
        }
    }



    public void updateWeather(weatherTypeButton newWeather) {
        if (weatherTypeButton != null) {
            Skin skin = clockUI.getClockWeatherSkin();
            weatherTypeButton.setStyle(skin.get(newWeather.getButtonPath(), ImageButton.ImageButtonStyle.class));
        }
    }

    public void updateSeason(seasonTypeButton newSeason) {
        if (seasonButton != null) {
            Skin skin = clockUI.getClockWeatherSkin();
            seasonButton.setStyle(skin.get(newSeason.getButtonPath(), ImageButton.ImageButtonStyle.class));
        }
    }

    public int getSelectedSlotIndex() {
        return selectedSlotIndex;
    }

    public enum weatherTypeButton {
        Sunny("SunnyButton"),
        Rainy("RainyButton"),
        Snowy("SnowyButton"),
        Wedding("WeddingButton"),
        Stormy("StormyButton");

        private final String pathToButton;

        weatherTypeButton(String pathToButton) {
            this.pathToButton = pathToButton;
        }

        public String getButtonPath() {
            return pathToButton;
        }
    }

    public enum seasonTypeButton {

        Spring("SpringButton"),
        Summer("SummerButton"),
        Fall("FallButton"),
        Winter("WinterButton");

        private final String pathToButton;

        seasonTypeButton(String pathToButton) {
            this.pathToButton = pathToButton;
        }

        public String getButtonPath() {
            return pathToButton;
        }
    }
}
