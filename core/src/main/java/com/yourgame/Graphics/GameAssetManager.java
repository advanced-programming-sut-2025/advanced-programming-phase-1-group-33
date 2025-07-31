package com.yourgame.Graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader.SkinParameter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.yourgame.Graphics.GameAssets.clockUIAssetManager;
import com.yourgame.model.WeatherAndTime.Weather;

public class GameAssetManager extends AssetManager {

    public final String Default_FONT_PATH = "Fonts/default-12.fnt";

    public final String WEATHER_CLOCK_SKIN_PATH = "Game/Clock/Skin/Clock.json";
    public final String CLOCK_ATLAS_PATH = "Game/Clock/Skin/Clock.atlas";
    public final String EnergyBarPath = "Game/Clock/Energy_Bar/";
    public final String InventoryBarDirectoryPath = "Game/Clock/Inventroy/";
    private clockUIAssetManager clockManager;

    public GameAssetManager() {
        this.clockManager = new clockUIAssetManager(WEATHER_CLOCK_SKIN_PATH, CLOCK_ATLAS_PATH, Default_FONT_PATH,
                EnergyBarPath, InventoryBarDirectoryPath);
    }

    public clockUIAssetManager getClockManager() {
        return clockManager;
    }

    public void setClockManager(clockUIAssetManager clockManager) {
        this.clockManager = clockManager;
    }

    public void loadAllAssets() {
    }

    public Skin getWeatherClockSkin() {
        return get(WEATHER_CLOCK_SKIN_PATH, Skin.class);
    }

}