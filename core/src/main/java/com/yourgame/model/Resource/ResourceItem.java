package com.yourgame.model.Resource;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.model.Item.Item;

public class ResourceItem extends Item {
    private final ResourceType type;

    public ResourceItem(ResourceType type) {
        super(type.getName(), ItemType.RESOURCE, type.getBasePrice(), true);
        this.type = type;
    }

    @Override
    public TextureRegion getTextureRegion(GameAssetManager assetManager) {
        String path = "Game/Resource/" + type.name() + ".png";
        return new TextureRegion(assetManager.getTexture(path));
    }
}
