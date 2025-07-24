package com.yourgame.view.AppViews;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
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
import com.yourgame.model.App;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.Graphics.MenuAssetManager;

public class GameScreen implements Screen {
    private Main game;
    private GameAssetManager assetManager;
    private Stage HUDStage;
    private ClockGraphicalAssests clockUI;

    private ImageButton clockImg;
    private Image cursor;

    // Music related variables
    private Music backgroundMusic;
    private Sound clickSound; // Example SFX, if you want it in game screen
    private static boolean isMusicInitialized = false; // Replicated from MenuBaseScreen

    public GameScreen() {
        this.game = Main.getMain();
        this.assetManager = new GameAssetManager();
        this.clockUI = assetManager.getClockManager();

        this.HUDStage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(HUDStage);

        cursor = MenuAssetManager.getInstance().getCursor();
        cursor.setSize(32,45);
        HUDStage.addActor(cursor);
        cursor.setTouchable(com.badlogic.gdx.scenes.scene2d.Touchable.disabled);
        cursor.toFront();
        Gdx.graphics.setSystemCursor(Cursor.SystemCursor.None);

        // Load background music and SFX directly here or through AssetManager
        // If MenuAssetManager has a getMusic() for general game music, use that.
        // Otherwise, load it specifically for GameScreen.
        // For demonstration, assuming you might have a dedicated game music asset or load from MenuAssetManager.
        backgroundMusic = MenuAssetManager.getInstance().getMusic(); // Or Gdx.audio.newMusic(Gdx.files.internal("path/to/your/game_music.mp3"));
        clickSound = MenuAssetManager.getInstance().getSounds("click"); // Example SFX

        if(!isMusicInitialized) {
            playBackgroundMusic();
            isMusicInitialized = true;
        }
    }

    @Override
    public void show() {
        assetManager.loadAllAssets();

        Table table = new Table();
        Skin clockSkin = clockUI.getClockWeatherSkin();
        clockImg = new ImageButton(clockSkin, "MainClockButton");
        table.add(clockImg).center();
        HUDStage.addActor(table);

        System.out.println("loaded");
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        HUDStage.act(Math.min(delta, 1 / 30f));

        float mouseX = Gdx.input.getX();
        float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
        cursor.setPosition(mouseX - cursor.getWidth() / 2f, mouseY - cursor.getHeight() / 2f);
        cursor.toFront();

        HUDStage.draw();
        System.out.println("rendering");
    }

    @Override
    public void resize(int width, int height) {
        HUDStage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
        // Pause music if the game is paused
        if (backgroundMusic.isPlaying()) {
            backgroundMusic.pause();
        }
    }

    @Override
    public void resume() {
        // Resume music if the game resumes and it was playing before
        if (!App.isIsMusicMuted() && !backgroundMusic.isPlaying()) {
            backgroundMusic.play();
        }
    }

    @Override
    public void hide() {
        // Stop music when the screen is hidden
        stopBackgroundMusic();
    }

    @Override
    public void dispose() {
        HUDStage.dispose();
        assetManager.dispose();
        backgroundMusic.dispose(); // Dispose the music
        if (clickSound != null) { // Dispose SFX if loaded
            clickSound.dispose();
        }
    }

    // Methods for music control, similar to MenuBaseScreen
    public void playBackgroundMusic() {
        if(App.isIsMusicMuted()) {
            return;
        }

        backgroundMusic.setLooping(true);
        backgroundMusic.play();
    }

    public void stopBackgroundMusic() {
        backgroundMusic.stop();
    }

    // Optional: if you want SFX in GameScreen
    public void playGameSFX(String string){
        switch (string) {
            case "click" -> clickSound.play();
            // Add other game SFX cases here
        }
    }

}
