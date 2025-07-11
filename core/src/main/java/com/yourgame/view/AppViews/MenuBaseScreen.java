package com.yourgame.view.AppViews;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.yourgame.Graphics.MenuAssetManager;
import com.yourgame.model.App;

public abstract class MenuBaseScreen implements Screen {
    protected Stage stage;
    protected Image backgroundImage;
    protected Image cursor;
    //protected Skin skin;
    protected Sound clickSound;
    protected Sound popUpSound;
    protected Music backgroundMusic;
    protected static boolean isMusicInitialized = false;

    protected MenuBaseScreen() {
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        //Setting background according to the menu we are in
        backgroundImage = MenuAssetManager.getInstance().getBackgroundImage(App.getCurrentMenu());
        backgroundImage.setFillParent(true);
        stage.addActor(backgroundImage);

        //setting up the background music
        backgroundMusic = MenuAssetManager.getInstance().getMusic();
        if(!isMusicInitialized) {
            playBackgroundMusic();
            isMusicInitialized = true;
        }

        //setting up the common SFXs for menus
        clickSound = MenuAssetManager.getInstance().getSounds("click");
        popUpSound = MenuAssetManager.getInstance().getSounds("popUp");
        //avatarChoose = Gdx.audio.newSound(Gdx.files.internal("sounds/AvatarChoose.mp3"));

        //Setting custom cursor for the game
        cursor = MenuAssetManager.getInstance().getCursor();
        cursor.setSize(32,45);
        stage.addActor(cursor);
        cursor.setTouchable(com.badlogic.gdx.scenes.scene2d.Touchable.disabled);
        cursor.toFront();
        Gdx.graphics.setSystemCursor(Cursor.SystemCursor.None);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(Math.min(delta, 1 / 30f));

        float mouseX = Gdx.input.getX();
        float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
        cursor.setPosition(mouseX - cursor.getWidth() / 2f, mouseY - cursor.getHeight() / 2f);

        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT))
            playMenuSFX("click");

        cursor.toFront();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override public void hide() {}
    @Override public void pause() {}
    @Override public void resume() {}

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

    public void playMenuSFX(String string){
        switch (string) {
            case "click" -> clickSound.play();
            case "popUp" -> popUpSound.play();
        }
    }
}
