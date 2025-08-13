package com.yourgame.model.Animals.FishPackage;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.model.Item.Item;

public class FishItem extends Item {
    private final Fish fish;

    public FishItem(Fish fish) {
        super(fish.getName(), ItemType.FISH, fish.getPrice(), true);
        this.fish = fish;
    }

    @Override
    public TextureRegion getTextureRegion(GameAssetManager assetManager) {
        String path = "Game/Animal/" + fish.getName() + "/" + fish.getName() + ".png";
        return new TextureRegion(assetManager.getTexture(path));
    }

    public Fish getFish() {
        return fish;
    }
}
