package com.yourgame.model.Farming;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.model.Item.Item;
import com.yourgame.model.Map.MapElement;
import com.yourgame.model.Map.Tile;
import com.yourgame.model.App;
import com.yourgame.model.WeatherAndTime.Season;
import com.yourgame.model.WeatherAndTime.TimeSystem;

import java.awt.*;
import java.util.List;

public class Tree extends Plant {
    private final TreeType type;
    private TimeSystem lastHarvestTime;
    private final int numberOfDaysCanBeAliveWithoutWater;
    private boolean hasFruit;

    public Tree(TreeType type, Fertilizer fertilizer, int worldX, int worldY) {
        super(ElementType.TREE, new Rectangle(worldX, worldY, 48, 80), 6, fertilizer);
        this.type = type;
        this.hasFruit = false;

        if (fertilizer == Fertilizer.GrowthFertilizer) {
            currentStage = 1;
        } else {
            currentStage = 0;
        }

        if (type == TreeType.OakTree || type == TreeType.MapleTree || type == TreeType.PineTree) {
            numberOfDaysCanBeAliveWithoutWater = Integer.MAX_VALUE;
        } else if (fertilizer == Fertilizer.WaterFertilizer) {
            numberOfDaysCanBeAliveWithoutWater = 3;
        } else {
            numberOfDaysCanBeAliveWithoutWater = 2;
        }
    }

    public TreeType getTreeType() {
        return type;
    }

    public void setMature() {
        currentStage = type.getStages().size();
    }

    public boolean hasFruit() {
        return hasFruit;
    }

    @Override
    public boolean isMature() {
        return currentStage >= type.getStages().size();
    }

    @Override
    public TextureRegion getTexture(GameAssetManager assetManager, Season currentSeason) {
        String treeName = type.name().replace("Tree", ""); // "AppleTree" -> "Apple"
        String basePath = "Game/Tree/" + treeName + "/" + treeName; // "Tree/Apple/Apple"
        String path;

        // Stage 5 is the final, mature stage with seasonal variations
        if (isMature()) {
            // Check if it should have fruit
            if (hasFruit()) {
                path = basePath + "_Stage_5_Fruit.png";
                Texture texture = assetManager.getTexture(path);
                return new TextureRegion(texture);
            } else {
                // Get the seasonal spritesheet
                path = basePath + "_Stage_5.png";
                Texture seasonalSheet = assetManager.getTexture(path);

                if (seasonalSheet == null) return null;

                // The sheet is 384x160, with each season being 96x160.
                int regionWidth = 96;
                int regionHeight = 160;
                int seasonIndex = switch (currentSeason) {
                    case Spring -> 0;
                    case Summer -> 1;
                    case Fall -> 2;
                    case Winter -> 3;
                    default -> 0; // Default to Spring

                };

                int regionX = seasonIndex * regionWidth;
                return new TextureRegion(seasonalSheet, regionX, 0, regionWidth, regionHeight);
            }
        } else if (currentStage >= 0) {
            path = basePath + "_Stage_" + (currentStage + 1) + ".png";
        } else {
            path = basePath + "_Sapling.png";
        }

        Texture texture = assetManager.getTexture(path);
        return (texture != null) ? new TextureRegion(texture) : null;
    }

    @Override
    public MapElement clone(int tileX, int tileY) {
        int scale = Tile.TILE_SIZE;
        Tree tree = new Tree(type, fertilizer, tileX * scale, tileY * scale);
        tree.currentStage = currentStage;
        tree.lastHarvestTime = lastHarvestTime;
        return tree;
    }

    @Override
    public List<Item> drop() {
        if (health <= 0) return List.of(new Wood.WoodItem());
        else if (hasFruit) return List.of(type.getFruit().getItem());
        return List.of();
    }

    @Override
    public void onTimeChanged(TimeSystem timeSystem) {
        if (isMature()) {
            daysSinceLastHarvest++;
            if (daysSinceLastHarvest >= type.getHarvestCycle()) {
                hasFruit = true;
            }
        } else if (wateredToday) {
            daysSinceLastStage++;
            if (daysSinceLastStage >= type.getStages().get(currentStage)) {
                currentStage++;
                daysSinceLastStage = 0;
            }
        }
        wateredToday = false;
    }
}
