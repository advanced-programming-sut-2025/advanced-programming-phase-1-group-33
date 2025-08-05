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
    private final TextButton login2Button;
    private final TextButton exitButton;
    private final TextButton playButton;
    private final TextButton profileButton;
    private final TextButton logoutButton;
    private final TextButton backButton;
    private final TextButton submitButton;
    private final TextButton findButton;
    private final TextButton randomPasswordButton;
    private final TextButton forgetPasswordButton;
    private final TextButton changeUsernameButton;
    private final TextButton changeNicknameButton;
    private final TextButton changePasswordButton;
    private final TextButton changeEmailButton;
    private final TextButton changeAvatarButton;
    private final TextButton stayLoggedInButton;

    private MenuAssetManager() {
        backgrounds = new Image[]{
            new Image(new Texture(Gdx.files.internal("Backgrounds/Background1.jpg"))),
            new Image(new Texture(Gdx.files.internal("Backgrounds/Background2.png"))),
            new Image(new Texture(Gdx.files.internal("Backgrounds/Background5.jpg"))),
            new Image(new Texture(Gdx.files.internal("Backgrounds/Background4.jpg"))),
        };

        sounds = new Sound[]{
            Gdx.audio.newSound(Gdx.files.internal("Sounds/UI Click 36.wav")),
            Gdx.audio.newSound(Gdx.files.internal("Sounds/Pop (3).wav")),
            Gdx.audio.newSound(Gdx.files.internal("sounds/AvatarChoose.mp3"))
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
        logoutButton = new TextButton("Logout", earthButtonStyle);

        TextButton.TextButtonStyle defaultButtonStyle = skin_3_Nz.get("default", TextButton.TextButtonStyle.class);
        defaultButtonStyle.font = skin_1_Sepehr.getFont("loading");
        backButton = new TextButton("Back", defaultButtonStyle);
        submitButton = new TextButton("Submit", defaultButtonStyle);
        login2Button = new TextButton("Login", defaultButtonStyle);
        changeAvatarButton = new TextButton("Avatar", defaultButtonStyle);

        TextButton.TextButtonStyle smallButtonStyle = skin_1_Sepehr.get("default", TextButton.TextButtonStyle.class);
        randomPasswordButton = new TextButton("  Generate  \n  Password  ", smallButtonStyle);
        forgetPasswordButton = new TextButton("  Forget  \n  Password  ", smallButtonStyle);
        findButton = new TextButton(" Find ", smallButtonStyle);
        changeUsernameButton = new TextButton("  Change  ", smallButtonStyle);
        changeNicknameButton = new TextButton("  Change  ", smallButtonStyle);
        changePasswordButton = new TextButton("  Change  ", smallButtonStyle);
        changeEmailButton = new TextButton("  Change  ", smallButtonStyle);
        stayLoggedInButton = new TextButton("  Disabled  ", smallButtonStyle);
    }

    public Image getBackgroundImage(MenuTypes type) {
        switch (type) {
            case MainMenu -> {return backgrounds[0];}
            case SignupMenu,LoginMenu -> {return backgrounds[1];}
            case ProfileMenu -> {return backgrounds[2];}
            case AvatarMenu -> {return backgrounds[3];}
            default -> {return null;}
        }
    }

    public TextButton getButtons(String name) {
            switch (name) {
                case "signup" -> {return signupButton;}
                case "login" -> {return loginButton;}
                case "login2" -> {return login2Button;}
                case "logout" -> {return logoutButton;}
                case "exit" -> {return exitButton;}
                case "play" -> {return playButton;}
                case "profile" -> {return profileButton;}
                case "back" -> {return backButton;}
                case "submit" -> {return submitButton;}
                case "find" -> {return findButton;}
                case "random" -> {return randomPasswordButton;}
                case "forget" -> {return forgetPasswordButton;}
                case "changeUsername" -> {return changeUsernameButton;}
                case "changeNickname" -> {return changeNicknameButton;}
                case "changePassword" -> {return changePasswordButton;}
                case "changeEmail" -> {return changeEmailButton;}
                case "avatar" -> {return changeAvatarButton;}
                case "stayLoggedIn" -> {return stayLoggedInButton;}
                default -> {return null;}
            }
    }

    public Music getMusic() {return music;}

    public Sound getSounds(String name){
        switch(name) {
            case "click" -> {return sounds[0];}
            case "popUp" -> {return sounds[1];}
            case "avatarChoose" -> {return sounds[2];}
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
