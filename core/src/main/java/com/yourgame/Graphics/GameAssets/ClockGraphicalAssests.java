package com.yourgame.Graphics.GameAssets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align; // For alignment within the table

public class ClockGraphicalAssests {
    private Skin ClockWeatherSkin;

    public ClockGraphicalAssests(String wEATHER_CLOCK_SKIN_PATH, String cLOCK_ATLAS_PATH, String default_FONT_PATH) {
        this.ClockWeatherSkin = loadClockWeatherSkin(wEATHER_CLOCK_SKIN_PATH, cLOCK_ATLAS_PATH, default_FONT_PATH);
    }

    public static Skin loadClockWeatherSkin(String skinPath, String clockAtlas, String defaultFontPath) {
        Skin skin = new Skin();

        skin.addRegions(new TextureAtlas(Gdx.files.internal(clockAtlas))); // تصاویر atlas
        skin.load(Gdx.files.internal(skinPath)); // فایل json برای استایل‌ها و فونت

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(defaultFontPath));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 24; // اندازه فونت (بر حسب پیکسل)
        parameter.color = Color.WHITE; // رنگ فونت
        // parameter.borderWidth = 1; // ضخامت حاشیه
        // parameter.borderColor = Color.BLACK; // رنگ حاشیه
        // parameter.shadowOffsetX = 2; // آفست سایه در X
        // parameter.shadowOffsetY = 2; // آفست سایه در Y
        // parameter.shadowColor = new Color(0, 0, 0, 0.75f); // رنگ سایه (شفافیت 75%)
        // parameter.characters = "abcdefg..."; // کاراکترهای خاصی که می‌خواهید لود شوند
        // (برای کاهش مصرف حافظه)

        // تولید BitmapFont از FreeTypeFontGenerator
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

}