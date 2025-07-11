package com.yourgame.view.AppViews;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.yourgame.Main;
import com.yourgame.Graphics.GameAssets.ClockGraphicalAssests;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.Graphics.MenuAssetManager;

public class GameScreen extends MenuBaseScreen {
    private Main game;
    private GameAssetManager assetManager; // Declare the asset manager
    private Stage uiStage; // Stage for UI elements
    private ClockGraphicalAssests clockUI; // Your clock UI component
    
    private ImageButton clockImg; 


    public GameScreen() {
        this.game = Main.getMain();
        // this.controller = new GameScreenController();
        this.assetManager = new GameAssetManager();
        this.clockUI = assetManager.getClockManager(); 
    }

    @Override
    public void show() {
        assetManager.loadAllAssets();

        Table table = new Table();
        Skin clockSkin = clockUI.getClockWeatherSkin();
        clockImg = new ImageButton(clockSkin, "MainClockButton"); 
        table.add(clockImg).center();  
        stage.addActor(table);

        System.out.println("loladed");
    }

    @Override
    public void render(float delta) {
        System.out.println("rendering");
        stage.draw();
    }

}
