package com.yourgame.Graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.yourgame.model.enums.Commands.MenuTypes;

public class MenuAssetManager {
    //SingleTone
    private static MenuAssetManager instance;
    public static MenuAssetManager getInstance() {  //SingleTone
        if (instance == null) {
            instance = new MenuAssetManager();
        }
        return instance;
    }

    Skin buttonsSkin = new Skin(Gdx.files.internal("Skin/uiskin.json"), new TextureAtlas(Gdx.files.internal("Skin/Buttons.atlas")));

    private final Image[] backgrounds;
    private final Sound[] sounds;
    private final Music music;
    private final Image cursor;
    private final Image redLine;

    private final ImageButton signupButton;
    private final ImageButton loginButton;
    private final ImageButton exitButton;
    private final ImageButton playButton;
    private final ImageButton profileButton;
    private final ImageButton logoutButton;

    private MenuAssetManager() {
        backgrounds = new Image[]{
            new Image(new Texture(Gdx.files.internal("Backgrounds/Background1.jpg"))),
            new Image(new Texture(Gdx.files.internal("Backgrounds/Background2.png"))),
        };

        sounds = new Sound[]{
            Gdx.audio.newSound(Gdx.files.internal("Sounds/UI Click 36.wav")),
            Gdx.audio.newSound(Gdx.files.internal("Sounds/Pop (3).wav"))
        };

        music = Gdx.audio.newMusic(Gdx.files.internal("Musics/01. Stardew Valley Overture.mp3"));

        cursor = new Image(new Texture(Gdx.files.internal("Textures/Cursor.png")));
        redLine = new Image(new Texture(Gdx.files.internal("Textures/RedLine.png")));

        signupButton = new ImageButton(buttonsSkin, "SignupButton");
        loginButton = new ImageButton(buttonsSkin, "LoginButton");
        exitButton = new ImageButton(buttonsSkin, "ExitButton");
        playButton = new ImageButton(buttonsSkin, "PlayButton");
        profileButton = new ImageButton(buttonsSkin, "ProfileButton");
        logoutButton = new ImageButton(buttonsSkin, "LogoutButton");
    }

    public Image getBackgroundImage(MenuTypes type) {
        switch (type) {
            case SignupMenu,MainMenu -> {return backgrounds[0];}
            default -> {return null;}
        }
    }

    public ImageButton getButtons(String name) {
            switch (name) {
                case "signup" -> {return signupButton;}
                case "login" -> {return loginButton;}
                case "logout" -> {return logoutButton;}
                case "exit" -> {return exitButton;}
                case "play" -> {return playButton;}
                case "profile" -> {return profileButton;}
                default -> {return null;}
            }
    }

    public Music getMusic() {return music;}

    public Sound getSounds(String name){
        switch(name) {
            case "click" -> {return sounds[0];}
            case "popUp" -> {return sounds[1];}
            default -> {return null;}
        }
    }

    public Image getCursor() {return cursor;}
    public Image getRedLine() {return redLine;}
}
