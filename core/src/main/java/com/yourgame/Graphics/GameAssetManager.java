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
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
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

    private final Texture menuIcon;
    private final Texture farmingSkillIcon;
    private final Texture fishingSkillIcon;
    private final Texture foragingSkillIcon;
    private final Texture miningSkillIcon;

    private final TextButton closeButton;
    private final TextButton inventoryMenuButton;
    private final TextButton cookingMenuButton;
    private final TextButton journalButton;
    private final TextButton socialButton;
    private final TextButton mapButton;
    private final TextButton settingMenuButton;
    private final TextButton skillButton;
    private final TextButton craftingButton;
    private final TextButton backButton;

    // A cache for textures that are loaded dynamically by path.
    private final HashMap<String, Texture> textureCache = new HashMap<>();

    private GameAssetManager() {
        this.clockManager = new clockUIAssetManager(WEATHER_CLOCK_SKIN_PATH, CLOCK_ATLAS_PATH, Default_FONT_PATH,
                EnergyBarPath, InventoryBarDirectoryPath);

        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("Musics/02. Cloud Country.mp3"));

        menuIcon = new Texture(Gdx.files.internal("Game/Game Menu/menu-icon.png"));
        farmingSkillIcon = new Texture(Gdx.files.internal("Game/Game Menu/Skills Menu/FarmingSkillIcon.png"));
        fishingSkillIcon = new Texture(Gdx.files.internal("Game/Game Menu/Skills Menu/FishingSkillIcon.png"));
        miningSkillIcon = new Texture(Gdx.files.internal("Game/Game Menu/Skills Menu/MiningSkillIcon.png"));
        foragingSkillIcon = new Texture(Gdx.files.internal("Game/Game Menu/Skills Menu/ForagingSkillIcon.png"));

        closeButton = new TextButton("Close" , MenuAssetManager.getInstance().getSkin(3));
        inventoryMenuButton = new TextButton("Inventory" , MenuAssetManager.getInstance().getSkin(3));
        cookingMenuButton = new TextButton("Cooking" , MenuAssetManager.getInstance().getSkin(3));
        journalButton = new TextButton("Journal" , MenuAssetManager.getInstance().getSkin(3));
        socialButton = new TextButton("Social" , MenuAssetManager.getInstance().getSkin(3));
        mapButton = new TextButton("Map" , MenuAssetManager.getInstance().getSkin(3));
        settingMenuButton = new TextButton("Settings", MenuAssetManager.getInstance().getSkin(3));
        skillButton = new TextButton("Skills", MenuAssetManager.getInstance().getSkin(3));
        craftingButton = new TextButton("Crafting", MenuAssetManager.getInstance().getSkin(3));
        backButton = new TextButton("Back", MenuAssetManager.getInstance().getSkin(3));
    }

    public clockUIAssetManager getClockManager() {
        return clockManager;
    }

    public void setClockManager(clockUIAssetManager clockManager) {
        this.clockManager = clockManager;
    }

    public void loadAllAssets() {
    }

    public Texture getMenuIcon() {
        return menuIcon;
    }

    public Texture getSkillMenuIcons(String name) {
        return switch (name) {
            case "farming" -> farmingSkillIcon;
            case "fishing" -> fishingSkillIcon;
            case "foraging" -> foragingSkillIcon;
            case "mining" -> miningSkillIcon;
            default -> null;
        };
    }

    public TextButton getButton(String name) {
        return switch (name) {
            case "Inventory" -> inventoryMenuButton;
            case "Cooking" -> cookingMenuButton;
            case "Journal" -> journalButton;
            case "Social" -> socialButton;
            case "Map" -> mapButton;
            case "Settings" -> settingMenuButton;
            case "Close" -> closeButton;
            case "Skills" -> skillButton;
            case "Crafting" -> craftingButton;
            case "Back" -> backButton;
            default -> null;
        };
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
