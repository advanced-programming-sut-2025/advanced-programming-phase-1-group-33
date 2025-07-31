package com.yourgame.Graphics.GameAssets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align; // For alignment within the table

public class clockUIAssetManager {
    private Skin ClockWeatherSkin;
    private Texture[] energyBarMode;

    public clockUIAssetManager(String wEATHER_CLOCK_SKIN_PATH,
            String cLOCK_ATLAS_PATH,
            String default_FONT_PATH,
            String EnergyBarPath) {

        this.ClockWeatherSkin = loadClockWeatherSkin(wEATHER_CLOCK_SKIN_PATH, cLOCK_ATLAS_PATH, default_FONT_PATH);
        this.energyBarMode = new Texture[5];

        try {
            for (int i = 0; i < 5; i++) {
                this.energyBarMode[i] = new Texture(Gdx.files.internal(EnergyBarPath + "energy_" + i + ".png"));
            }

        } catch (Exception e) {
            Gdx.app.error("clockUIAssetManager", "Error loading individual PNGs: " + e.getMessage(), e);
        }

    }

    public static Skin loadClockWeatherSkin(String skinPath, String clockAtlas, String defaultFontPath) {
        Skin skin = new Skin();

        skin.addRegions(new TextureAtlas(Gdx.files.internal(clockAtlas))); // تصاویر atlas

        skin.load(Gdx.files.internal(skinPath));

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(defaultFontPath));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 24; // اندازه فونت (بر حسب پیکسل)
        parameter.color = Color.WHITE; // رنگ فونت

        BitmapFont font = generator.generateFont(parameter);
        skin.add("default-24", font); // "default-24" یک نام دلخواه است

        generator.dispose();

        Gdx.app.log("Clock and Weather skin", "Loaded completely");
        return skin;

    }

    public Skin getClockWeatherSkin() {
        return ClockWeatherSkin;
    }

    public void setClockWeatherSkin(Skin weatherClockSkin) {
        this.ClockWeatherSkin = weatherClockSkin;
    }

    public Texture[] getEnergyBarMode() {
        return energyBarMode;
    }

    public void setEnergyBarMode(Texture[] energyBarMode) {
        this.energyBarMode = energyBarMode;
    }

}