package com.yourgame.Graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader.SkinParameter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.yourgame.Graphics.GameAssets.clockUIAssetManager;
import com.yourgame.model.WeatherAndTime.Weather;

import java.util.HashMap;

public class GameAssetManager extends AssetManager {

    private static GameAssetManager instance;
    public static GameAssetManager getInstance() {
        if (instance == null) {
            instance = new GameAssetManager();
        }
        return instance;
    }

    public final String Default_FONT_PATH = "Fonts/default-12.fnt";
    public final String WEATHER_CLOCK_SKIN_PATH = "Game/Clock/Skin/Clock.json";
    public final String CLOCK_ATLAS_PATH = "Game/Clock/Skin/Clock.atlas";
    public final String EnergyBarPath = "Game/Clock/Energy_Bar/";
    public final String InventoryBarDirectoryPath = "Game/Clock/Inventory/";

    private clockUIAssetManager clockManager;

    private Music backgroundMusic;

    // A cache for textures that are loaded dynamically by path.
    private final HashMap<String, Texture> textureCache = new HashMap<>();

    public GameAssetManager() {
        this.clockManager = new clockUIAssetManager(WEATHER_CLOCK_SKIN_PATH, CLOCK_ATLAS_PATH, Default_FONT_PATH,
                EnergyBarPath, InventoryBarDirectoryPath);

        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("Musics/02. Cloud Country.mp3"));
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

    public Music getBackgroundMusic() {
        return backgroundMusic;
    }

    /**
     * Gets a Texture from a given path.
     * If the texture is not already loaded, it will be loaded synchronously and cached.
     *
     * @param path The internal path to the texture file.
     * @return The loaded Texture.
     */
    public Texture getTexture(String path) {
        if (textureCache.containsKey(path)) {
            return textureCache.get(path);
        }

        if (Gdx.files.internal(path).exists()) {
            Texture texture = new Texture(Gdx.files.internal(path));
            textureCache.put(path, texture);
            return texture;
        } else {
            Gdx.app.error("AssetManager", "Texture not found at path: " + path);
            return null;
        }
    }

    public void dispose() {
        for (Texture texture : textureCache.values()) {
            texture.dispose();
        }
        textureCache.clear();
    }
}
