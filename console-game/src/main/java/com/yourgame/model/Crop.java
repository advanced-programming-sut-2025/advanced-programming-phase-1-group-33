package com.yourgame.model;

import com.yourgame.model.data.CropData;
import com.yourgame.model.enums.Quality;
import com.yourgame.model.enums.ItemType;

public class Crop extends Item {
    private CropData cropData;
    private int currentStage;
    private int daysInCurrentStage;
    private Quality quality;
    
    public Crop(String id, String name, String description, int sellPrice, boolean isStackable,
                CropData cropData, int currentStage, int daysInCurrentStage, Quality quality) {
        super(id, name, description, ItemType.CROP, sellPrice);
        this.cropData = cropData;
        this.currentStage = currentStage;
        this.daysInCurrentStage = daysInCurrentStage;
        this.quality = quality;
    }
    
    public boolean isHarvestable() {
        // Implement harvest logic.
        // For example, assume the crop is harvestable when it reaches its final stage.
        return currentStage >= cropData.getStages().size() - 1;
    }
    
    public void grow() {
        daysInCurrentStage++;
        int threshold = cropData.getStages().get(currentStage);
        if (daysInCurrentStage >= threshold && !isHarvestable()) {
            currentStage++;
            daysInCurrentStage = 0;
        }
    }
    
    public CropData getCropData() {
        return cropData;
    }
    
    public int getCurrentStage() {
        return currentStage;
    }
    
    public int getDaysInCurrentStage() {
        return daysInCurrentStage;
    }
    
    public Quality getQuality() {
        return quality;
    }
    
    @Override
    public void use() {
        System.out.println("Harvesting the crop: " + getName());
        // Optionally, add further logic for using/harvesting the crop.
    }
}