package com.yourgame.model.Item;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.model.Map.MapElement;
import com.yourgame.model.Map.Tile;
import com.yourgame.model.App;
import com.yourgame.model.WeatherAndTime.Season;
import com.yourgame.model.WeatherAndTime.TimeSystem;

import java.awt.*;
import java.util.List;

public class Tree extends MapElement implements Growable {
    private final TreeType type;
    private int levelOfGrowth;
    private TimeSystem lastGrowthTime;
    private TimeSystem lastHarvestTime;
    private TimeSystem lastWaterTime;
    private final Fertilizer fertilizer;
    private final int numberOfDaysCanBeAliveWithoutWater;

    public Tree(TreeType type, TimeSystem timeOfPlanting, Fertilizer fertilizer, int worldX, int worldY) {
        super(ElementType.TREE, new Rectangle(worldX, worldY, 48, 80), 6);
        this.type = type;
        this.lastGrowthTime = timeOfPlanting.clone();
        this.lastWaterTime = timeOfPlanting.clone();
        this.fertilizer = fertilizer;

        if (fertilizer == Fertilizer.GrowthFertilizer) levelOfGrowth = 1;
        else levelOfGrowth = 0;

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

    public void grow(TimeSystem today) {
        if (isComplete())
            return;

        int timeForGrow = type.getTimeForGrow(levelOfGrowth);

        if (lastGrowthTime.getDay() + timeForGrow == today.getDay()) {
            levelOfGrowth++;
            lastGrowthTime = today;
        }

    }

    public boolean canGrowAgain() {
        return true;
    }

    public void setMature() {
        levelOfGrowth = type.getStages().size();
    }

    public boolean isComplete() {
        return levelOfGrowth >= type.getStages().size();
    }

    public boolean harvest() {
        if (!isComplete())
            return false;

        TimeSystem today = App.getGameState().getGameTime().clone();
        int timeForGrow = type.getHarvestCycle();

        if (lastHarvestTime == null || lastHarvestTime.getDay() + timeForGrow <= today.getDay()) {
            lastHarvestTime = today;
            return true;
        }
        return false;
    }

    public void watering() {
        lastWaterTime = App.getGameState().getGameTime().clone();
    }

    public boolean canBeAlive(TimeSystem today) {
        return today.getDay() <= lastWaterTime.getDay() + numberOfDaysCanBeAliveWithoutWater;
    }

    public int getNumberOfDaysToComplete() {
        int passedDays = 0;
        for (int i = 0; i < levelOfGrowth; i++) {
            passedDays += type.getTimeForGrow(i);
        }
        passedDays += App.getGameState().getGameTime().getDay() - lastGrowthTime.getDay();
        return type.getTotalHarvestTime() - passedDays;
    }

    public int getCurrentStage() {
        return levelOfGrowth;
    }

    public boolean hasWateredToday() {
        return App.getGameState().getGameTime().getDay() == lastWaterTime.getDay();
    }

    public boolean hasFertilized() {
        return fertilizer != null;
    }

    public Fertilizer getFertilizer() {
        return fertilizer;
    }

    public String getNameOfProduct() {
        return type.getFruit().name();
    }

    public String getName() {
        return type.name();
    }

    public boolean hasFruit() {
        //return isComplete() && type.getSeason() == App.getGameState().getGameTime().getSeason();
        return false;
    }

    @Override
    public TextureRegion getTexture(GameAssetManager assetManager, Season currentSeason) {
        String treeName = type.name().replace("Tree", ""); // "AppleTree" -> "Apple"
        String basePath = "Game/Tree/" + treeName + "/" + treeName; // "Tree/Apple/Apple"
        String path;

        // Stage 5 is the final, mature stage with seasonal variations
        if (isComplete()) {
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
        } else if (levelOfGrowth >= 0) {
            path = basePath + "_Stage_" + (levelOfGrowth + 1) + ".png";
        } else {
            path = basePath + "_Sapling.png";
        }

        Texture texture = assetManager.getTexture(path);
        return (texture != null) ? new TextureRegion(texture) : null;
    }

    @Override
    public MapElement clone(int tileX, int tileY) {
        int scale = Tile.TILE_SIZE;
        Tree tree = new Tree(type, lastGrowthTime, fertilizer, tileX * scale, tileY * scale);
        tree.levelOfGrowth = levelOfGrowth;
        tree.lastGrowthTime = lastGrowthTime;
        tree.lastWaterTime = lastWaterTime;
        tree.lastHarvestTime = lastHarvestTime;
        return tree;
    }

    @Override
    public java.util.List<Item> drop() {
        return List.of();
    }
}
