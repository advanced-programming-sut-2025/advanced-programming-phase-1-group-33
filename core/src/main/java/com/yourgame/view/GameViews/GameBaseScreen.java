package com.yourgame.view.GameViews;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.Graphics.MenuAssetManager;

public abstract class GameBaseScreen implements Screen {
    protected Stage HUDStage;
    protected Stage menuStage;
    protected Sound clickSound;
    protected Sound popUpSound;
    protected Sound errorSound;
    protected Music backgroundMusic;
    protected Image cursor;
    protected SpriteBatch batch;

    public GameBaseScreen() {
        this.HUDStage = new Stage(new ScreenViewport());
        this.menuStage = new Stage(new ScreenViewport());

        Gdx.input.setInputProcessor(HUDStage);

        //Setting the SFXs needed in the game
        clickSound = MenuAssetManager.getInstance().getSounds("click");
        popUpSound = MenuAssetManager.getInstance().getSounds("popUp");
        errorSound = MenuAssetManager.getInstance().getSounds("error");


        //Setting the musix playing in the background
        backgroundMusic = GameAssetManager.getInstance().getBackgroundMusic();
        backgroundMusic.setLooping(true);
        backgroundMusic.play();

        //Setting the game cursor
        cursor = MenuAssetManager.getInstance().getCursor();
        cursor.setSize(32, 45);
        HUDStage.addActor(cursor);
        cursor.setTouchable(com.badlogic.gdx.scenes.scene2d.Touchable.disabled);
        cursor.toFront();
        Gdx.graphics.setSystemCursor(Cursor.SystemCursor.None);

        //Setting the sprite batch
        batch = new SpriteBatch();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        // Clear screen
        HUDStage.act(Math.min(delta, 1 / 30f));
        menuStage.act(Math.min(delta, 1 / 30f));

        float mouseX = Gdx.input.getX();
        float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
        cursor.setPosition(mouseX - cursor.getWidth() / 2f, mouseY - cursor.getHeight() / 2f);

        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT))
            playGameSFX("click");

        cursor.toFront();
        menuStage.draw();
        HUDStage.draw();
    }

    @Override
    public void resize(int width, int height) {}

    @Override public void pause() {}
    @Override public void resume() {}

    @Override
    public void hide() {
        // Stop music when the screen is hidden
        backgroundMusic.stop();
    }

    @Override
    public void dispose() {
        HUDStage.dispose();
        backgroundMusic.dispose(); // Dispose the music
        clickSound.dispose(); // Dispose the click sound
        popUpSound.dispose(); //Dispose the popUp sound
    }

    public void playGameSFX(String string) {
        switch (string) {
            case "click" -> clickSound.play();
            case "popUp" -> popUpSound.play();
            case "error" -> errorSound.play(5f);
        }
    }

    public void showMessage(String soundName,String message, Skin skin, float x, float y, Stage stage) {
        playGameSFX(soundName);
        Dialog dialog = new Dialog("", skin);
        dialog.text(message);
        dialog.show(stage);
        dialog.setPosition((stage.getWidth()-dialog.getWidth())/2f + x, y);
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {dialog.hide();}
        }, 2);
    }
}
