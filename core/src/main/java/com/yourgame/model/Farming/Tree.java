package com.yourgame.model.Farming;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.model.Item.Item;
import com.yourgame.model.Map.MapElement;
import com.yourgame.model.Map.Tile;
import com.yourgame.model.WeatherAndTime.Season;
import com.yourgame.model.WeatherAndTime.TimeSystem;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Tree extends Plant {
    private final TreeType treeType;
    private final int numberOfDaysCanBeAliveWithoutWater;

    public Tree(TreeType treeType, Fertilizer fertilizer, int worldX, int worldY) {
        super(ElementType.TREE, new Rectangle(worldX, worldY, 48, 80), 6, fertilizer);
        this.treeType = treeType;

        if (treeType == TreeType.OakTree || treeType == TreeType.MapleTree || treeType == TreeType.PineTree) {
            numberOfDaysCanBeAliveWithoutWater = Integer.MAX_VALUE;
        } else if (fertilizer == Fertilizer.Water_Fertilizer) {
            numberOfDaysCanBeAliveWithoutWater = 3;
        } else {
            numberOfDaysCanBeAliveWithoutWater = 2;
        }
    }

    public TreeType getTreeType() {
        return treeType;
    }

    public void setMature() {
        currentStage = treeType.getStages().size();
    }

    @Override
    public boolean isMature() {
        return currentStage >= treeType.getStages().size();
    }

    @Override
    public TextureRegion getTexture(GameAssetManager assetManager, Season currentSeason) {
        String treeName = treeType.name().replace("Tree", ""); // "AppleTree" -> "Apple"
        String basePath = "Game/Tree/" + treeName + "/" + treeName; // "Tree/Apple/Apple"
        String path;

        // Stage 5 is the final, mature stage with seasonal variations
        if (isMature()) {
            // Check if it should have fruit
            if (hasProduct()) {
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
        Tree tree = new Tree(treeType, fertilizer, tileX * scale, tileY * scale);
        tree.daysSinceLastHarvest = daysSinceLastHarvest;
        tree.daysSinceLastStage = daysSinceLastStage;
        tree.currentStage = currentStage;
        tree.wateredToday = wateredToday;
        return tree;
    }

    @Override
    public List<Item> harvest() {
        if (hasProduct()) return List.of(new Fruit.FruitItem(treeType.getFruit()));
        return List.of();
    }

    @Override
    public List<Item> drop() {
        List<Item> items = new ArrayList<>();
        for (int i = 0; i < 5; i++) items.add(new Wood.WoodItem());
        return items;
    }

    @Override
    public void onTimeChanged(TimeSystem timeSystem) {
        if (isMature()) {
            if (hasProduct) return;
            daysSinceLastHarvest++;
            if (daysSinceLastHarvest >= treeType.getHarvestCycle()) {
                hasProduct = true;
                daysSinceLastHarvest = 0;
            }
        } else if (wateredToday) {
            daysSinceLastStage++;
            if (daysSinceLastStage >= treeType.getStages().get(currentStage)) {
                currentStage++;
                daysSinceLastStage = 0;
            }
        }
        wateredToday = false;
    }
}
