package com.yourgame.model.Map.Elements;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.model.Animals.AnimalPackage.Animal;
import com.yourgame.model.App;
import com.yourgame.model.Item.Item;
import com.yourgame.model.Map.MapElement;
import com.yourgame.model.WeatherAndTime.Season;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FarmBuilding extends MapElement {
    private final BuildingType buildingType;
    private final List<Animal> animalsInside;

    public FarmBuilding(BuildingType buildingType, int tileX, int tileY) {
        super(
            ElementType.BUILDING,
            new Rectangle(
                tileX * 16,
                tileY * 16,
                buildingType.getTileWidth() * 16,
                buildingType.getTileHeight() * 16
            ),
            Float.MAX_VALUE // Buildings are indestructible
        );
        this.buildingType = buildingType;
        this.animalsInside = new ArrayList<>();
    }

    public BuildingType getBuildingType() {
        return buildingType;
    }

    @Override
    public TextureRegion getTexture(GameAssetManager assetManager, Season currentSeason) {
        String path = "Game/Animal/" + buildingType.getName() + ".png";
        return new TextureRegion(assetManager.getTexture(path));
    }

    @Override
    public MapElement clone(int tileX, int tileY) {
        return new FarmBuilding(this.buildingType, tileX, tileY);
    }

    @Override
    public List<Item> drop() { return List.of(); }

    public boolean addAnimal(Animal animal) {
        int capacity = 4;
        if (animalsInside.size() < capacity) {
            animalsInside.add(animal);
            App.getGameState().getGameTime().addDayObserver(animal);
            return true;
        }
        return false;
    }

    public List<Animal> getAnimals() {
        return animalsInside;
    }
}
