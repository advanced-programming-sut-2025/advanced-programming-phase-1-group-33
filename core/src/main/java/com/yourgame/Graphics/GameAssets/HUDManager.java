package com.yourgame.Graphics.GameAssets;

// Assuming these imports are already present or will be added
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport; // Recommended for HUD Stage

// --- New HUDManager Class (Conceptual - you'll implement this) ---
// This class will encapsulate the creation and management of HUD elements.
public class HUDManager {
    private Stage hudStage;
    private clockUIAssetManager clockUI;
    private AssetManager assetManager;
    private Texture[] energy_bar_textures;
    private Image energyBarImage;
    private Texture energy_bar_phase;
    private ImageButton weatherTypeButton; // Reference to the weather button
    private ImageButton seasonButton; // Reference to the season button
    private Texture InventoryTexture;

    public HUDManager(Stage stage, clockUIAssetManager clockUI, AssetManager assetManager) {
        this.hudStage = stage;
        this.clockUI = clockUI;
        this.assetManager = assetManager;
        this.energy_bar_textures = clockUI.getEnergyBarMode();
        this.InventoryTexture = clockUI.getInventoryTexture();
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

    public Table createInventoryBarTable() {
        Table inventoryBarTable = new Table();
        inventoryBarTable.setFillParent(true);
        inventoryBarTable.bottom().center().padBottom(10);
        
        ImageButton clockImg = new ImageButton(this.InventoryTexture);

        return inventoryBarTable;
    }

    // Creates the energy bar table and initializes the energyBarImage
    public Table createEnergyBarTable(int initialPhase) {
        Table energy_barTable = new Table();
        energy_barTable.setFillParent(true);
        energy_barTable.bottom().right().padRight(10).padBottom(10);

        // Initialize energyBarImage with the correct texture based on initialPhase
        if (initialPhase >= 0 && initialPhase < energy_bar_textures.length) {
            this.energyBarImage = new Image(this.energy_bar_textures[initialPhase]);
        } else {
            // Fallback or error handling if initialPhase is out of bounds
            Gdx.app.log("HUDManager", "Initial energy phase " + initialPhase + " is out of bounds. Using phase 0.");
            this.energyBarImage = new Image(this.energy_bar_textures[0]); // Default to phase 0
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
            // Set the new texture to the existing Image actor
            energyBarImage.setDrawable(new Image(this.energy_bar_textures[newPhase]).getDrawable());
        } else {
            Gdx.app.log("Energy_bar", "Invalid energy bar phase: " + newPhase + ". Must be between 0 and "
                    + (energy_bar_textures.length - 1));
        }
    }

    /**
     * Updates the weather icon on the HUD.
     * 
     * @param newWeather The new weather type enum.
     */
    public void updateWeather(weatherTypeButton newWeather) {
        if (weatherTypeButton != null) {
            Skin skin = clockUI.getClockWeatherSkin();
            weatherTypeButton.setStyle(skin.get(newWeather.getButtonPath(), ImageButton.ImageButtonStyle.class));
        }
    }

    /**
     * Updates the season icon on the HUD.
     * 
     * @param newSeason The new season type enum.
     */
    public void updateSeason(seasonTypeButton newSeason) {
        if (seasonButton != null) {
            Skin skin = clockUI.getClockWeatherSkin();
            seasonButton.setStyle(skin.get(newSeason.getButtonPath(), ImageButton.ImageButtonStyle.class));
        }
    }

    public Table get_energyTable(int Phase) {
        Table energy_barTable = new Table();
        energy_barTable.setFillParent(true);
        energy_barTable.bottom().right().padRight(10).padBottom(10);

        if (0 <= Phase && Phase <= 4) {
            this.energy_bar_phase = this.energy_bar_textures[Phase];
            Image eBarImage = new Image(this.energy_bar_phase);

            energy_barTable.add(eBarImage);
        } else {
            Gdx.app.log("Energy_bar", "this energy bar is not real");
        }
        return energy_barTable;
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

}
