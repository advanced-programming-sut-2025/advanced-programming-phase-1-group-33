package com.yourgame.Graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.yourgame.model.enums.Commands.MenuTypes;

public class MenuAssetManager {
    private static MenuAssetManager instance;
    public static MenuAssetManager getInstance() {  //SingleTone
        if (instance == null) {
            instance = new MenuAssetManager();
        }
        return instance;
    }

    private MenuAssetManager() {

    }

    public Texture getBackgroundImage(MenuTypes type) {
        switch (type) {
            case SignupMenu -> {return new Texture(Gdx.files.internal("Backgrounds/Background1.jpg"));}
            case MainMenu -> {return new Texture(Gdx.files.internal("Backgrounds/Background1.jpg"));}
            default -> {return null;}
        }
    }

    public Texture getButtons(MenuTypes type) {
            switch (type) {
                case MainMenu -> {return new Texture(Gdx.files.internal("Textures/Main Menu titles.png"));}
                default -> {return null;}
            }
    }

    public Texture getCursor() {return new Texture(Gdx.files.internal("Textures/Cursor.png"));}
    public Texture getRedLine() {return new Texture(Gdx.files.internal("Textures/RedLine.png"));}
}
