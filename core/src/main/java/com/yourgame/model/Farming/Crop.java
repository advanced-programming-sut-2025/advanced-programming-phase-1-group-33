package com.yourgame.model.Farming;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.model.Item.Item;
import com.yourgame.model.Map.MapElement;
import com.yourgame.model.WeatherAndTime.Season;
import com.yourgame.model.WeatherAndTime.TimeSystem;

import java.awt.*;
import java.util.List;

import static com.yourgame.model.Map.Tile.TILE_SIZE;

public class Crop extends Plant {
    private final CropType cropType;

    public Crop(CropType cropType, Fertilizer fertilizer, int worldX, int worldY) {
        super(ElementType.CROP, new Rectangle(worldX, worldY, TILE_SIZE, TILE_SIZE), 1, fertilizer);
        this.cropType = cropType;
    }

    public CropType getCropType() {
        return cropType;
    }

    public String getNameOfProduct() {
        return cropType.getName();
    }

    public int getSellPrice() {
        return cropType.getBaseSellPrice();
    }

    @Override
    public boolean isMature() {
        return currentStage >= cropType.getNumberOfStages();
    }

    @Override
    public List<Item> harvest() {
        if (hasProduct()) {
            if (getCropType().isOneTime()) health = 0;
            return List.of(new CropItem(cropType));
        }
        return List.of();
    }

    @Override
    public List<Item> drop() {
        return List.of();
    }

    @Override
    public TextureRegion getTexture(GameAssetManager assetManager, Season currentSeason) {
        int stage = currentStage + 1;
        if (!cropType.isOneTime() && !hasProduct) stage++;
        String path = "Game/Crop/" + cropType.getName() + "/" + cropType.getName() + "_Stage_" + stage + ".png";
        return new TextureRegion(assetManager.getTexture(path));
    }

    @Override
    public MapElement clone(int tileX, int tileY) {
        Crop crop = new Crop(cropType, fertilizer, tileX * TILE_SIZE, tileY * TILE_SIZE);
        crop.daysSinceLastHarvest = daysSinceLastHarvest;
        crop.daysSinceLastStage = daysSinceLastStage;
        crop.currentStage = currentStage;
        crop.wateredToday = wateredToday;
        return crop;
    }

    @Override
    public void onTimeChanged(TimeSystem timeSystem) {
        if (health <= 0) return;

        // Handle Growth Logic First
        if (!isMature() && wateredToday) {
            daysSinceLastStage++;
            if (daysSinceLastStage >= cropType.getStages().get(currentStage)) {
                currentStage++;
                daysSinceLastStage = 0;
            }
        }

        if (isMature()) {
            if (cropType.isOneTime()) {
                hasProduct = true;
            } else if (hasProduct) {
                // It has a product, do nothing until harvested.
            } else {
                daysSinceLastHarvest++;
                if (daysSinceLastHarvest >= cropType.getRegrowthTime()) {
                    hasProduct = true;
                    daysSinceLastHarvest = 0;
                }
            }
        }

        if (wateredToday) {
            daysSinceWatered = 0;
        } else {
            daysSinceWatered++;
        }
        wateredToday = false;

        if (daysSinceWatered > maxDryDays) {
            this.health = 0;
        }
    }
}
