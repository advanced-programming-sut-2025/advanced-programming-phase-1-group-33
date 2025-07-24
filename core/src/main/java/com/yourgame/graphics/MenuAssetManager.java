package com.yourgame.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class MenuAssetManager {
    private static MenuAssetManager instance;
    public static MenuAssetManager getInstance() {
        if (instance == null) {
            instance = new MenuAssetManager();
        }
        return instance;
    }

    private final Skin skin = new Skin(Gdx.files.internal("stardew-skin/skin.json"));

    public Skin getSkin() {
        return skin;
    }
}
