package com.yourgame.Graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.yourgame.model.enums.Commands.MenuTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MenuAssetManager {
    private static final Logger log = LoggerFactory.getLogger(MenuAssetManager.class);
    //SingleTone
    private static MenuAssetManager instance;
    public static MenuAssetManager getInstance() {  //SingleTone
        if (instance == null) {
            instance = new MenuAssetManager();
        }
        return instance;
    }

    Skin skin_1_Sepehr = new Skin(Gdx.files.internal("Skin/Skin_1(Sepeher)/stardew-skin.json"), new TextureAtlas(Gdx.files.internal("Skin/Skin_1(Sepeher)/stardew-skin.atlas")));
    Skin skin_2_Amirhossein = new Skin(Gdx.files.internal("Skin/Skin_2(Amirhossein)/uiskin.json"), new TextureAtlas(Gdx.files.internal("Skin/Skin_2(Amirhossein)/Buttons.atlas")));
    Skin skin_3_Nz = new Skin(Gdx.files.internal("Skin/Skin_3(Nz)/NzSkin.json"), new TextureAtlas(Gdx.files.internal("Skin/Skin_3(Nz)/NzSkin.atlas")));

    private final Image[] backgrounds;
    private final Sound[] sounds;
    private final Music music;
    private final Image cursor;
    private final Image redLine;

    private final TextButton signupButton;
    private final TextButton loginButton;
    private final TextButton exitButton;
    private final TextButton playButton;
    private final TextButton profileButton;
    private final TextButton logoutButton;
    private final TextButton backButton;
    private final TextButton submitButton;

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

        TextButton.TextButtonStyle strawberryButtonStyle = skin_3_Nz.get("Strawberry", TextButton.TextButtonStyle.class);
        strawberryButtonStyle.font = skin_1_Sepehr.getFont("loading");
        signupButton = new TextButton("Signup", strawberryButtonStyle);
        playButton = new TextButton("Play", strawberryButtonStyle);

        TextButton.TextButtonStyle chickenButtonStyle = skin_3_Nz.get("Chicken", TextButton.TextButtonStyle.class);
        chickenButtonStyle.font = skin_1_Sepehr.getFont("loading");
        loginButton = new TextButton("Login", chickenButtonStyle);
        profileButton = new TextButton("Profile", chickenButtonStyle);

        TextButton.TextButtonStyle plantButtonStyle = skin_3_Nz.get("Plant", TextButton.TextButtonStyle.class);
        plantButtonStyle.font = skin_1_Sepehr.getFont("loading");
        exitButton = new TextButton("Exit", plantButtonStyle);

        TextButton.TextButtonStyle earthButtonStyle = skin_3_Nz.get("Earth", TextButton.TextButtonStyle.class);
        earthButtonStyle.font = skin_1_Sepehr.getFont("loading");
        logoutButton = new TextButton("Login", earthButtonStyle);

        TextButton.TextButtonStyle defaultButtonStyle = skin_3_Nz.get("default", TextButton.TextButtonStyle.class);
        defaultButtonStyle.font = skin_1_Sepehr.getFont("loading");
        backButton = new TextButton("Back", defaultButtonStyle);
        backButton.setScale(0.5f);
        submitButton = new TextButton("Submit", defaultButtonStyle);
        submitButton.setScale(0.5f);
    }

    public Image getBackgroundImage(MenuTypes type) {
        switch (type) {
            case MainMenu -> {return backgrounds[0];}
            case SignupMenu,LoginMenu -> {return backgrounds[1];}
            default -> {return null;}
        }
    }

    public TextButton getButtons(String name) {
            switch (name) {
                case "signup" -> {return signupButton;}
                case "login" -> {return loginButton;}
                case "logout" -> {return logoutButton;}
                case "exit" -> {return exitButton;}
                case "play" -> {return playButton;}
                case "profile" -> {return profileButton;}
                case "back" -> {return backButton;}
                case "submit" -> {return submitButton;}
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

    public Skin getSkin(int index) {
        switch (index) {
            case 1 -> {return skin_1_Sepehr;}
            case 2 -> {return skin_2_Amirhossein;}
            case 3 -> {return skin_3_Nz;}
            default -> {return null;}
        }
    }

    public Image getCursor() {return cursor;}
    public Image getRedLine() {return redLine;}
}
