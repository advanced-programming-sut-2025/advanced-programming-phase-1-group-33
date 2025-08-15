package com.yourgame.model.Animals.AnimalPackage;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.model.Item.Item;

public class AnimalProduct extends Item {
    private final AnimalProductType animalProductType;
    private final AnimalType animalType;

    public AnimalProduct(AnimalProductType animalProductType, AnimalType animalType) {
        super(animalProductType.getName(), ItemType.ANIMAL_PRODUCT, animalProductType.getPrice(), true);
        this.animalProductType = animalProductType;
        this.animalType = animalType;
    }

    @Override
    public TextureRegion getTextureRegion(GameAssetManager assetManager) {
        String path = "Game/Animal/Product/" + animalProductType.getName() + ".png";
        return new TextureRegion(assetManager.getTexture(path));
    }

    public AnimalProductType getAnimalProductType() {
        return animalProductType;
    }

    public AnimalType getAnimal() {
        return animalType;
    }
}
