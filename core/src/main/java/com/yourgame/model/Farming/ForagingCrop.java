package com.yourgame.model.Farming;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.model.Item.Item;
import com.yourgame.model.ManuFactor.Ingredient;
import com.yourgame.model.WeatherAndTime.Season;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public enum ForagingCrop implements Ingredient {
    // CommonMushroom(Season.Special, 40, 38),
    Common_Mushroom(List.of(Season.Spring, Season.Fall), 40, 38),
    Daffodil(List.of(Season.Spring), 30, 0),
    Dandelion(List.of(Season.Spring), 40, 25),
    Leek(List.of(Season.Spring), 60, 40),
    Morel(List.of(Season.Spring), 150, 20),
    Salmonberry(List.of(Season.Spring), 5, 25),
    Spring_Onion(List.of(Season.Spring), 8, 13),
    Wild_Horseradish(List.of(Season.Spring), 50, 13),
    Fiddlehead_Fern(List.of(Season.Summer), 90, 25),
    Grape(List.of(Season.Summer), 80, 38),
    Red_Mushroom(List.of(Season.Summer), 75, -50),
    Spice_Berry(List.of(Season.Summer), 80, 25),
    Sweet_Pea(List.of(Season.Summer), 50, 0),
    Blackberry(List.of(Season.Fall), 25, 25),
    Chanterelle(List.of(Season.Fall), 160, 75),
    Hazelnut(List.of(Season.Fall), 40, 38),
    Purple_Mushroom(List.of(Season.Fall), 90, 30),
    Wild_Plum(List.of(Season.Fall), 80, 25),
    Crocus(List.of(Season.Winter), 60, 0),
    Crystal_Fruit(List.of(Season.Winter), 150, 63),
    Holly(List.of(Season.Winter), 80, -37),
    Snow_Yam(List.of(Season.Winter), 100, 30),
    Winter_Root(List.of(Season.Winter), 70, 25);

    private final List<Season> seasons;
    private final int baseSellPrice;
    private final int energy;
    private final static HashMap<String, ForagingCrop> stringToForagingCrop = new HashMap<>();

    static {
        for (ForagingCrop value : ForagingCrop.values()) {
            stringToForagingCrop.put(value.name().toLowerCase(), value);
        }
    }

    ForagingCrop(List<Season> seasons, int baseSellPrice, int energy) {
        this.seasons = seasons;
        this.baseSellPrice = baseSellPrice;
        this.energy = energy;
    }

    public List<Season> getSeasons() {
        return seasons;
    }

    public String getName() {
        return name();
    }

    public int getBaseSellPrice() {
        return baseSellPrice;
    }

    public int getEnergy() {
        return energy;
    }


    public static ForagingCrop getForagingCropByName(String name) {
        if (name == null || name.isEmpty())
            return null;
        return stringToForagingCrop.getOrDefault(name.toLowerCase(), null);
    }

    public static ForagingCrop getByName(String name) {
        for (ForagingCrop type : ForagingCrop.values()) {
            if (type.name().equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }

    public static ArrayList<ForagingCrop> getCropsBySeason(Season season) {
        ArrayList<ForagingCrop> crops = new ArrayList<>();
        for (ForagingCrop crop : values()) {
            if (crop.seasons.contains(season)) {
                crops.add(crop);
            }
        }
        return crops;
    }

    @Override
    public Item getItem() {
        return new ForagingCropItem(this);
    }

    public static final class ForagingCropItem extends Item {
        private final ForagingCrop cropType;

        public ForagingCropItem(ForagingCrop cropType) {
            super(cropType.name(), ItemType.INGREDIENT, cropType.baseSellPrice, true);
            this.cropType = cropType;
        }

        @Override
        public TextureRegion getTextureRegion(GameAssetManager assetManager) {
            return new TextureRegion(assetManager.getTexture("Game/Foraging/" + cropType.getName() + ".png"));
        }
    }
}
