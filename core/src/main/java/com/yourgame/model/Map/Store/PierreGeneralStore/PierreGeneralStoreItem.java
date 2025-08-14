package com.yourgame.model.Map.Store.PierreGeneralStore;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.model.Item.Item;

public class PierreGeneralStoreItem extends Item {

    public PierreGeneralStoreItem(String name, ItemType itemType, int value, boolean isStackable) {
        super(name, itemType, value, isStackable);
    }

    @Override
    public TextureRegion getTextureRegion(GameAssetManager assetManager) {
        return new TextureRegion(GameAssetManager.getInstance().getTexture("Game/Food/Recipe.png"));
    }
}
