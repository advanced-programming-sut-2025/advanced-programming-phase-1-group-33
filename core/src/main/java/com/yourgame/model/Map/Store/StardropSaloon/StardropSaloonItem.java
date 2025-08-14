package com.yourgame.model.Map.Store.StardropSaloon;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.model.Item.Item;

public class StardropSaloonItem extends Item {
    public StardropSaloonItem(String name, ItemType itemType, int value, boolean isStackable) {
        super(name, itemType, value, isStackable);
    }

    @Override
    public TextureRegion getTextureRegion(GameAssetManager assetManager) {
       return new TextureRegion(GameAssetManager.getInstance().getTexture("Game/Food/Recipe.png"));
    }
}
