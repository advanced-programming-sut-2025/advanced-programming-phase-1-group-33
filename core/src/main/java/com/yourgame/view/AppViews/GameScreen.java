package com.yourgame.view.AppViews;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.yourgame.Main;
import com.yourgame.Graphics.GameAssets.ClockGraphicalAssests;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.Graphics.MenuAssetManager;

public class GameScreen implements Screen {
    private Main game;
    private GameAssetManager assetManager; // Declare the asset manager

    private Stage stage; // Now declared directly in GameScreen
    private ClockGraphicalAssests clockUI;

    private ImageButton clockImg;
    private Image cursor; // Added for custom cursor if desired

    public GameScreen() {
        this.game = Main.getMain();
        this.assetManager = new GameAssetManager();
        this.clockUI = assetManager.getClockManager();

        // Initialize stage and input processor directly
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Optional: If you want the custom cursor from MenuBaseScreen
        cursor = MenuAssetManager.getInstance().getCursor();
        cursor.setSize(32,45);
        stage.addActor(cursor);
        cursor.setTouchable(com.badlogic.gdx.scenes.scene2d.Touchable.disabled);
        cursor.toFront();
        Gdx.graphics.setSystemCursor(Cursor.SystemCursor.None);
    }

    @Override
    public void show() {
        assetManager.loadAllAssets();

        Table table = new Table();
        Skin clockSkin = clockUI.getClockWeatherSkin();
        clockImg = new ImageButton(clockSkin, "MainClockButton");
        table.add(clockImg).center();
        stage.addActor(table);

        System.out.println("loladed"); // Typo here: "loladed" should be "loaded"
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1); // Clear the screen
        stage.act(Math.min(delta, 1 / 30f)); // Update actors

        // Optional: If you want the custom cursor from MenuBaseScreen
        float mouseX = Gdx.input.getX();
        float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
        cursor.setPosition(mouseX - cursor.getWidth() / 2f, mouseY - cursor.getHeight() / 2f);
        cursor.toFront();

        stage.draw(); // Draw the stage
        System.out.println("rendering");
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
        // Implement pause logic if needed
    }

    @Override
    public void resume() {
        // Implement resume logic if needed
    }

    @Override
    public void hide() {
        // Implement hide logic if needed
    }

    @Override
    public void dispose() {
        stage.dispose();
        // Dispose other assets if they are not managed by GameAssetManager's dispose method
        assetManager.dispose(); // Assuming GameAssetManager has a dispose method
    }
}
