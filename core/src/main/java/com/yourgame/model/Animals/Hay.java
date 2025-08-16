package com.yourgame.model.Animals;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.model.Item.Item;

public class Hay extends Item {
    public Hay() {
        super("Hay", ItemType.INGREDIENT, 10, true);
    }

    @Override
    public TextureRegion getTextureRegion(GameAssetManager assetManager) {
        return new TextureRegion(assetManager.getTexture("Game/Animal/Hay.png"));
    }
}
