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
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport; // Recommended for HUD Stage

// --- New HUDManager Class (Conceptual - you'll implement this) ---
// This class will encapsulate the creation and management of HUD elements.
public class HUDManager {
    private Stage hudStage;
    private clockUIAssetManager clockUI;
    private AssetManager assetManager;
    private Texture[] energy_bar;
    private Texture energy_bar_phase; 
    // Constructor to initialize with necessary dependencies
    public HUDManager(Stage stage, clockUIAssetManager clockUI, AssetManager assetManager) {
        this.hudStage = stage;
        this.clockUI = clockUI;
        this.assetManager = assetManager;
        this.energy_bar = clockUI.getEnergyBarMode();
    }

    // Method to create and add the info bar (clock, weather, season) to the HUD
    // stage
    public void createInfoBar(String weatherType, String seasonType) {
        Table clockBarTable = clockBarTable(weatherType, seasonType);
        Table Energy_Bar = get_energyTable(0);

        hudStage.addActor(clockBarTable);
        hudStage.addActor(Energy_Bar); 
    }

    public Table clockBarTable(String weatherType,  String seasonType) {
        Table clockBarTable = new Table();
        clockBarTable.setFillParent(true);
        clockBarTable.top().right();

        Skin clockSkin = clockUI.getClockWeatherSkin(); // Get the skin from ClockUI
        ImageButton clockImg = new ImageButton(clockSkin, "MainClockButton");

        ImageButton weatherTypeButton = new ImageButton(clockSkin, weatherType + "Button"); // e.g., "SunnyButton"
        ImageButton seasonButton = new ImageButton(clockSkin, seasonType + "Button"); // e.g., "SpringButton"

        Stack clockAndIndicatorsStack = new Stack();
        clockAndIndicatorsStack.add(clockImg);

        Table overlayButtonsTable = new Table();
        overlayButtonsTable.setFillParent(true); 

        overlayButtonsTable.add(weatherTypeButton)
                .padBottom(60).padLeft(80) // Adjust padding as needed
                .align(Align.topLeft).expandX().size(35);

        overlayButtonsTable.add(seasonButton)
                .padBottom(60).padLeft(15) // Adjust padding as needed
                .align(Align.topLeft).expandX().size(35);

        clockAndIndicatorsStack.add(overlayButtonsTable);

        clockBarTable.add(clockAndIndicatorsStack).size(196, 196).pad(10).top().right();
        return clockBarTable; 
    }



    public Table get_energyTable(int Phase){
        Table energy_barTable = new Table();
        energy_barTable.setFillParent(true);
        energy_barTable.bottom().right().padRight(10).padBottom(10);

        if(0<=Phase && Phase <= 4){
            this.energy_bar_phase = this.energy_bar[Phase]; 
            Image eBarImage = new Image(this.energy_bar_phase); 
    
            energy_barTable.add(eBarImage); 
        }
        else{
            Gdx.app.log("Energy_bar", "this energy bar is not real"); 
        }

        return energy_barTable; 

    }
    
    public enum weatherType {
        Sunny,
        Rainny,
        Snowy,
        Stormy;
    }

    public enum seasonType {
        Sunny,
        Rainny,
        Snowy,
        Stormy;
    }

    // You can add more methods here to manage other HUD elements
    // public void createInventoryPanel(...) { ... }
    // public void updateClock(float time) { ... }
}
