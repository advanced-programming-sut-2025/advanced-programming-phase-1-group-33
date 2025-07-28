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
    private clockUIAssetManager clockUI; // Assuming ClockUI is a class you have
    private AssetManager assetManager; // Assuming AssetManager is a class you have

    // Constructor to initialize with necessary dependencies
    public HUDManager(Stage stage, clockUIAssetManager clockUI, AssetManager assetManager) {
        this.hudStage = stage;
        this.clockUI = clockUI;
        this.assetManager = assetManager;
    }

    // Method to create and add the info bar (clock, weather, season) to the HUD stage
    public void createInfoBar(String weatherType, String seasonType) {
        // Create a Table to hold the clock UI element.
        Table infoBarTable = new Table();
        infoBarTable.setFillParent(true);
        infoBarTable.top().right();

        Skin clockSkin = clockUI.getClockWeatherSkin(); // Get the skin from ClockUI

        // Main clock button
        ImageButton clockImg = new ImageButton(clockSkin, "MainClockButton");

        // Weather and season buttons based on provided types
        // You'll need to ensure these style names exist in your JSON Skin
        ImageButton weatherTypeButton = new ImageButton(clockSkin, weatherType + "Button"); // e.g., "SunnyButton"
        ImageButton seasonButton = new ImageButton(clockSkin, seasonType + "Button");     // e.g., "SpringButton"

        // Create a Stack to overlay the day/season buttons on the main clock
        Stack clockAndIndicatorsStack = new Stack();
        clockAndIndicatorsStack.add(clockImg);

        Table overlayButtonsTable = new Table();
        overlayButtonsTable.setFillParent(true); // Make the table fill the stack's area

        // Add weather button
        overlayButtonsTable.add(weatherTypeButton)
                           .padBottom(60).padLeft(80) // Adjust padding as needed
                           .align(Align.topLeft).expandX().size(35);

        // Add season button
        overlayButtonsTable.add(seasonButton)
                           .padBottom(60).padLeft(15) // Adjust padding as needed
                           .align(Align.topLeft).expandX().size(35);

        clockAndIndicatorsStack.add(overlayButtonsTable);

        infoBarTable.add(clockAndIndicatorsStack).size(196, 196).pad(10).top().right();

        // Add the info bar table to the HUD stage
        hudStage.addActor(infoBarTable);
    }


    public enum weatherType{
        Sunny, 
        Rainny,
        Snowy, 
        Stormy
        ;
    }
    public enum seasonType{
        // TODO: need to be implemented in future. 
        Sunny, 
        Rainny,
        Snowy, 
        Stormy
        ;
    }
    
    // You can add more methods here to manage other HUD elements
    // public void createInventoryPanel(...) { ... }
    // public void updateClock(float time) { ... }
}
