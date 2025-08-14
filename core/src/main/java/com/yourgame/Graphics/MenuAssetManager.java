package com.yourgame.Graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.yourgame.model.enums.Avatar;
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
    private final Image[] avatarMenuAvatars;
    private final Texture[] avatarSpriteSheets;
    private final TextureRegion[][][] frames;
    private final Animation[][] walkAnimations;
    private final Animation<TextureRegion>[] faintAnimations;

    private final Sound[] sounds;
    private final Music music;
    private final Image cursor;
    private final Image redLine;

    public static final int PLAYER_WIDTH = 16;
    public static final int PLAYER_HEIGHT = 32;

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
    private final TextButton pregameButton;
    private final TextButton lobbyButton;
    private final TextButton exitPreGameButton;
    private final TextButton randomPasswordButton;
    private final TextButton forgetPasswordButton;
    private final TextButton changeUsernameButton;
    private final TextButton changeNicknameButton;
    private final TextButton changePasswordButton;
    private final TextButton changeEmailButton;
    private final TextButton changeAvatarButton;
    private final TextButton stayLoggedInButton;

    private MenuAssetManager() {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        Texture whiteTexture = new Texture(pixmap);
        skin_3_Nz.add("white", new TextureRegionDrawable(new TextureRegion(whiteTexture)));
        pixmap.dispose();

        backgrounds = new Image[]{
            new Image(new Texture(Gdx.files.internal("Backgrounds/Background1.jpg"))),
            new Image(new Texture(Gdx.files.internal("Backgrounds/Background2.png"))),
            new Image(new Texture(Gdx.files.internal("Backgrounds/Background5.jpg"))),
            new Image(new Texture(Gdx.files.internal("Backgrounds/Background4.jpg"))),
        };

        avatarMenuAvatars = new Image[]{
            new Image(new Texture(Gdx.files.internal("Textures/Avatars/AbigailCharacter.png"))),
            new Image(new Texture(Gdx.files.internal("Textures/Avatars/AbigailPortrait.png"))),
            new Image(new Texture(Gdx.files.internal("Textures/Avatars/HarveyCharacter.png"))),
            new Image(new Texture(Gdx.files.internal("Textures/Avatars/HarveyPortrait.png"))),
            new Image(new Texture(Gdx.files.internal("Textures/Avatars/PierreCharacter.png"))),
            new Image(new Texture(Gdx.files.internal("Textures/Avatars/PierrePortrait.png"))),
            new Image(new Texture(Gdx.files.internal("Textures/Avatars/RobinCharacter.png"))),
            new Image(new Texture(Gdx.files.internal("Textures/Avatars/RobinPortrait.png"))),
            new Image(new Texture(Gdx.files.internal("Textures/Avatars/SamCharacter.png"))),
            new Image(new Texture(Gdx.files.internal("Textures/Avatars/SamPortrait.png")))
        };

        avatarMenuAvatars[0].setScale(3f);
        avatarMenuAvatars[1].setScale(3.2f);
        for(int i=2; i<=9; i++){
            avatarMenuAvatars[i].setScale(3f);
        }

        avatarSpriteSheets = new Texture[]{
            new Texture(Gdx.files.internal("Textures/Avatars/AbigailSpriteSheet.png")),
            new Texture(Gdx.files.internal("Textures/Avatars/HarveySpriteSheet.png")),
            new Texture(Gdx.files.internal("Textures/Avatars/PierreSpriteSheet.png")),
            new Texture(Gdx.files.internal("Textures/Avatars/RobinSpriteSheet.png")),
            new Texture(Gdx.files.internal("Textures/Avatars/SamSpriteSheet.png"))
        };

        frames = new TextureRegion[][][]{
            TextureRegion.split(avatarSpriteSheets[0], PLAYER_WIDTH, PLAYER_HEIGHT),
            TextureRegion.split(avatarSpriteSheets[1], PLAYER_WIDTH, PLAYER_HEIGHT),
            TextureRegion.split(avatarSpriteSheets[2], PLAYER_WIDTH, PLAYER_HEIGHT),
            TextureRegion.split(avatarSpriteSheets[3], PLAYER_WIDTH, PLAYER_HEIGHT),
            TextureRegion.split(avatarSpriteSheets[4], PLAYER_WIDTH, PLAYER_HEIGHT),
        };

        walkAnimations = new Animation[][]{
            new Animation[] { //Abigail
                new Animation<>(0.2f, frames[0][0]), // Down
                new Animation<>(0.2f, frames[0][1]), // Right
                new Animation<>(0.2f, frames[0][2]), // Up
                new Animation<>(0.2f, frames[0][3]) // Left
            },

            new Animation[] { //Harvey
                new Animation<>(0.2f, frames[1][0]), // Down
                new Animation<>(0.2f, frames[1][1]), // Right
                new Animation<>(0.2f, frames[1][2]), // Up
                new Animation<>(0.2f, frames[1][3]) // Left
            },

            new Animation[] { //Pierre
                new Animation<>(0.2f, frames[2][0]), // Down
                new Animation<>(0.2f, frames[2][1]), // Right
                new Animation<>(0.2f, frames[2][2]), // Up
                new Animation<>(0.2f, frames[2][3]) // Left
            },

            new Animation[] { // Robin
                new Animation<>(0.2f, frames[3][0]), // Down
                new Animation<>(0.2f, frames[3][1]), // Right
                new Animation<>(0.2f, frames[3][2]), // Up
                new Animation<>(0.2f, frames[3][3])  // Left
            },

            new Animation[] { // Sam
                new Animation<>(0.2f, frames[4][0]), // Down
                new Animation<>(0.2f, frames[4][1]), // Right
                new Animation<>(0.2f, frames[4][2]), // Up
                new Animation<>(0.2f, frames[4][3])  // Left
            }
        };

        faintAnimations = new Animation[] {
            new Animation<>(0.4f, new Array<>(new TextureRegion[] {
                frames[0][0][0],
                frames[0][6][0],
                frames[0][6][1],
                frames[0][13][1]})) //Abigail
        };

        sounds = new Sound[]{
            Gdx.audio.newSound(Gdx.files.internal("Sounds/UI Click 36.wav")),
            Gdx.audio.newSound(Gdx.files.internal("Sounds/Pop (3).wav")),
            Gdx.audio.newSound(Gdx.files.internal("Sounds/AvatarChoose.mp3")),
            Gdx.audio.newSound(Gdx.files.internal("Sounds/error.mp3"))
        };

        music = Gdx.audio.newMusic(Gdx.files.internal("Musics/01. Stardew Valley Overture.mp3"));

        cursor = new Image(new Texture(Gdx.files.internal("Textures/Cursor.png")));
        redLine = new Image(new Texture(Gdx.files.internal("Textures/RedLine.png")));

        TextButton.TextButtonStyle strawberryButtonStyle = skin_3_Nz.get("Strawberry", TextButton.TextButtonStyle.class);
        strawberryButtonStyle.font = skin_1_Sepehr.getFont("loading");
        signupButton = new TextButton("Signup", strawberryButtonStyle);
        playButton = new TextButton("Play", strawberryButtonStyle);
        pregameButton = new TextButton("PreGame", strawberryButtonStyle);

        TextButton.TextButtonStyle chickenButtonStyle = skin_3_Nz.get("Chicken", TextButton.TextButtonStyle.class);
        chickenButtonStyle.font = skin_1_Sepehr.getFont("loading");
        loginButton = new TextButton("Login", chickenButtonStyle);
        profileButton = new TextButton("Profile", chickenButtonStyle);

        TextButton.TextButtonStyle plantButtonStyle = skin_3_Nz.get("Plant", TextButton.TextButtonStyle.class);
        plantButtonStyle.font = skin_1_Sepehr.getFont("loading");
        exitButton = new TextButton("Exit", plantButtonStyle);
        exitPreGameButton = new TextButton("Exit", plantButtonStyle);

        TextButton.TextButtonStyle earthButtonStyle = skin_3_Nz.get("Earth", TextButton.TextButtonStyle.class);
        earthButtonStyle.font = skin_1_Sepehr.getFont("loading");
        logoutButton = new TextButton("Logout", earthButtonStyle);
        lobbyButton = new TextButton("Lobby", earthButtonStyle);

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
            case SignupMenu,LoginMenu,PreGameMenu -> {return backgrounds[1];}
            case ProfileMenu -> {return backgrounds[2];}
            case AvatarMenu -> {return backgrounds[3];}
            default -> {return null;}
        }
    }
    public Image getAvatarPortrait(Avatar avatar) {
        switch (avatar){
            case Abigail -> {return avatarMenuAvatars[1];}
            case Harvey -> {return avatarMenuAvatars[3];}
            case Pierre -> {return avatarMenuAvatars[5];}
            case Robin -> {return avatarMenuAvatars[7];}
            case Sam -> {return avatarMenuAvatars[9];}
            default -> {return null;}
        }
    }
    public Image getAvatarCharacter(Avatar avatar) {
        switch (avatar){
            case Abigail -> {return avatarMenuAvatars[0];}
            case Harvey -> {return avatarMenuAvatars[2];}
            case Pierre -> {return avatarMenuAvatars[4];}
            case Robin -> {return avatarMenuAvatars[6];}
            case Sam -> {return avatarMenuAvatars[8];}
            default -> {return null;}
        }
    }

    public Animation[] getWalkAnimation(Avatar avatar) {
        return switch (avatar) {
            case Abigail -> walkAnimations[0];
            case Harvey -> walkAnimations[1];
            case Pierre -> walkAnimations[2];
            case Robin -> walkAnimations[3];
            case Sam -> walkAnimations[4];
        };
    }

    public Animation<TextureRegion> getFaintAnimation() {
        return faintAnimations[0];
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
            case "pregame" -> {return pregameButton;}
            case "random" -> {return randomPasswordButton;}
            case "forget" -> {return forgetPasswordButton;}
            case "lobby" -> {return lobbyButton;}
            case "exitPreGame" -> {return exitPreGameButton;}
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
            case "error" -> {return sounds[3];}
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
