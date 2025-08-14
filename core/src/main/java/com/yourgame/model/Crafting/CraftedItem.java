package com.yourgame.model.Crafting;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.model.Item.Item;

public class CraftedItem extends Item {
    private final CraftedItemType type;

    public CraftedItem(CraftedItemType type) {
        super(type.getName(), ItemType.CRAFTED, type.getPrice(), true);
        this.type = type;
    }

    @Override
    public TextureRegion getTextureRegion(GameAssetManager assetManager) {
        String path = "Game/Craft/" + type.getName() + ".png";
        return new TextureRegion(assetManager.getTexture(path));
    }
}
