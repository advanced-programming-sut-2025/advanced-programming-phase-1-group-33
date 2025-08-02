package com.yourgame.Graphics.GameAssets;

// Assuming these imports are already present or will be added
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport; // Recommended for HUD Stage
import com.yourgame.model.Tool;

// --- New HUDManager Class (Conceptual - you'll implement this) ---
// This class will encapsulate the creation and management of HUD elements.
public class HUDManager {
    private Stage hudStage;
    private clockUIAssetManager clockUI;
    private AssetManager assetManager;

    // Energy Bar 
    private Texture[] energy_bar_textures;
    private Image energyBarImage;



    // Info Bar
    private ImageButton weatherTypeButton;
    private ImageButton seasonButton;

    private Texture InventoryTexture;

        // Inventory Bar
    private final Texture inventoryTexture;
    private InventorySlot[] inventorySlots;
    private int selectedSlotIndex = 0;
    private Drawable selectionDrawable;


    public HUDManager(Stage stage, clockUIAssetManager clockUI, AssetManager assetManager) {
        this.hudStage = stage;
        this.clockUI = clockUI;
        this.assetManager = assetManager;
        this.energy_bar_textures = clockUI.getEnergyBarMode();
        this.inventoryTexture = clockUI.getInventoryTexture(); // Assumes this texture is loaded
        this.inventorySlots = new InventorySlot[12];
        createSelectionHighlight();
    }



    /**
     * Creates a simple drawable that will be used to highlight the selected inventory slot.
     */
    private void createSelectionHighlight() {
        Pixmap pixmap = new Pixmap(48, 48, Pixmap.Format.RGBA8888); // Create a 48x48 pixel map
        pixmap.setColor(new Color(1, 1, 0, 0.4f)); // Set color to semi-transparent yellow
        pixmap.fill(); // Fill the pixmap with the color
        this.selectionDrawable = new TextureRegionDrawable(new Texture(pixmap));
        pixmap.dispose(); // Dispose of the pixmap to free memory
    }

    // Method to create and add the info bar (clock, weather, season) and energy bar
    // to the HUD stage
    public void createHUD(weatherTypeButton initialWeather, seasonTypeButton initialSeason, int initialEnergyPhase) {
        // Create the clock/info bar table
        Table clockBarTable = createClockBarTable(initialWeather, initialSeason);
        hudStage.addActor(clockBarTable);

        // Create and add the energy bar table
        Table energyBarTable = createEnergyBarTable(initialEnergyPhase);
        hudStage.addActor(energyBarTable);

        Table InventoryBarTable  = createInventoryBarTable();
        hudStage.addActor(InventoryBarTable);

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
     * Uses a Stack to overlay tool icons and a selection highlight on top of the inventory background image.
     */
    public Table createInventoryBarTable() {
        Table inventoryContainerTable = new Table();
        inventoryContainerTable.setFillParent(true);
        inventoryContainerTable.bottom().padBottom(10);

        // Stack allows us to layer actors on top of each other.
        Stack inventoryStack = new Stack();

        // 1. The background image of the inventory bar
        Image inventoryBackground = new Image(inventoryTexture);
        inventoryStack.add(inventoryBackground);

        // 2. A table to hold the 12 individual slots
        Table slotsTable = new Table();
        // This padding aligns the slots inside the background image. You may need to tweak these values.
        slotsTable.pad(0, 4, 0, 4);

        for (int i = 0; i < 12; i++) {
            InventorySlot slot = new InventorySlot(selectionDrawable);
            inventorySlots[i] = slot;
            // Add the slot to the table. Tweak size and padding to fit your background image perfectly.
            slotsTable.add(slot).width(52).height(52).padLeft(11).padRight(11);
        }
        inventoryStack.add(slotsTable);

        inventoryContainerTable.add(inventoryStack);

        // Select the first slot by default
        selectSlot(0);

        return inventoryContainerTable;
    }

    /**
     * Adds a tool to a specific slot in the inventory.
     * @param tool The tool to add.
     * @param index The index of the slot (0-11).
     */
    public void addTool(Tool tool, int index) {
        if (index >= 0 && index < inventorySlots.length) {
            inventorySlots[index].setTool(tool);
        } else {
            Gdx.app.log("HUDManager", "Invalid inventory slot index: " + index);
        }
    }

    /**
     * Selects an inventory slot, updating the visual highlight.
     * @param index The index of the slot to select (0-11).
     */
    public void selectSlot(int index) {
        if (index < 0 || index >= inventorySlots.length) return;

        // Deselect the previously selected slot
        inventorySlots[selectedSlotIndex].setSelected(false);

        // Select the new slot
        selectedSlotIndex = index;
        inventorySlots[selectedSlotIndex].setSelected(true);

        Gdx.app.log("HUDManager", "Selected tool: " + getSelectedToolName());
    }

    /**
     * @return The Tool object in the currently selected slot, or null if the slot is empty.
     */
    public Tool getSelectedTool() {
        return inventorySlots[selectedSlotIndex].getTool();
    }

    /**
     * @return The name of the tool in the selected slot, or "Empty" if there is none.
     */
    public String getSelectedToolName() {
        Tool tool = getSelectedTool();
        return (tool != null) ? tool.getName() : "Empty";
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



    public enum weatherTypeButton {
        Sunny("SunnyButton"),
        Rainny("RainnyButton"),
        Snowy("SnowyButton"),
        Wedding("WeddingdayButton"),
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

        /**
     * Represents a single slot in the inventory UI. It's a Stack that can hold
     * a tool image and a selection highlight.
     */
    public static class InventorySlot extends Stack {
        private Tool tool;
        private final Image toolImage;
        private final Image selectionImage;

        public InventorySlot(Drawable selectionDrawable) {
            this.toolImage = new Image();
            this.toolImage.setAlign(Align.center);

            this.selectionImage = new Image(selectionDrawable);
            this.selectionImage.setVisible(false); // Hide highlight by default

            this.add(selectionImage); // Add highlight behind the tool
            this.add(toolImage);      // Add tool image on top
        }

        public void setTool(Tool newTool) {
            this.tool = newTool;
            if (newTool != null) {
                this.toolImage.setDrawable(new TextureRegionDrawable(newTool.getTextureRegion()));
            } else {
                this.toolImage.setDrawable(null); // Clear the image if tool is null
            }
        }

        public Tool getTool() {
            return tool;
        }

        public void setSelected(boolean isSelected) {
            selectionImage.setVisible(isSelected);
        }
    }

}
