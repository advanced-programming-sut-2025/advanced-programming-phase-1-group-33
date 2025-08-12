package com.yourgame.model.Farming;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.model.App;
import com.yourgame.model.Item.Item;
import com.yourgame.model.Map.Map;
import com.yourgame.model.Map.MapElement;
import com.yourgame.model.Map.Tile;
import com.yourgame.model.WeatherAndTime.Season;
import com.yourgame.model.WeatherAndTime.TimeSystem;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Tree extends Plant {
    private final TreeType treeType;
    private final Map map;

    public Tree(Map map, TreeType treeType, Fertilizer fertilizer, int worldX, int worldY) {
        super(ElementType.TREE, new Rectangle(worldX, worldY, 16, 16), 6, fertilizer);
        this.treeType = treeType;
        this.map = map;
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
        Tree tree = new Tree(map, treeType, fertilizer, tileX * scale, tileY * scale);
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
        items.add(new TreeSource.TreeSourceItem(treeType.getSource()));
        return items;
    }

    @Override
    public void onTimeChanged(TimeSystem timeSystem) {
        if (health <= 0) return;
        boolean wasMature = isMature();

        // Handle Growth Logic First
        if (!isMature() && wateredToday) {
            daysSinceLastStage++;
            if (daysSinceLastStage >= treeType.getStages().get(currentStage)) {
                currentStage++;
                daysSinceLastStage = 0;
            }
            wateredToday = false;
        }

        if (isMature()) {
            if (treeType.getSeason() == App.getGameState().getGameTime().getSeason()) {
                daysSinceLastHarvest++;
                if (daysSinceLastHarvest >= treeType.getHarvestCycle()) {
                    hasProduct = true;
                    daysSinceLastHarvest = 0;
                }
            } else {
                hasProduct = false;
            }
        }

        if (!wasMature && isMature()) {
            // The tree has just grown up. Update its collision bounds.
            if (map != null) {
                // Tell the map to update our bounds from 1x1 to 3x5
                map.updateElementBounds(this,  3, 5);
            }
        }
    }
}
