package com.yourgame.model.Item;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.model.enums.ResourceType;

public class Resource extends Item {
    ResourceType type;

    public Resource(String name, int sellPrice, boolean isStackable) {
        super(name, ItemType.RESOURCE, sellPrice, isStackable);
        this.type = ResourceType.Metal;
    }


    @Override
    public TextureRegion getTextureRegion(GameAssetManager assetManager) {
        return null;
    }
}
