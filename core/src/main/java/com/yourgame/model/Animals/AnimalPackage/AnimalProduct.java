package com.yourgame.model.Animals.AnimalPackage;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.model.Item.Item;

public class AnimalProduct extends Item {
    private final AnimalProductType animalProductType;
    private final Animal animal;

    public AnimalProduct(AnimalProductType animalProductType,Animal animal) {
        super(animalProductType.getName(), ItemType.ANIMAL_PRODUCT, animalProductType.getPrice(), true);
        this.animalProductType = animalProductType;
        this.animal = animal;
    }

    @Override
    public TextureRegion getTextureRegion(GameAssetManager assetManager) {
        String path = "Game/Animal/" + animalProductType.getName() + "/" + animalProductType.getName() + ".png";
        return new TextureRegion(assetManager.getTexture(path));
    }

    public AnimalProductType getAnimalProductType() {
        return animalProductType;
    }

    public Animal getAnimal() {
        return animal;
    }
}
