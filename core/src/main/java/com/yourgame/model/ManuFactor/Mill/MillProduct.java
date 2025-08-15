package com.yourgame.model.ManuFactor.Mill;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.model.Item.Item;

public class MillProduct extends Item {
    private final MillProductType type;

    public MillProduct(MillProductType type) {
        super(type.getName(), ItemType.MILL, type.getBasePrice(), true);
        this.type = type;
    }

    @Override
    public TextureRegion getTextureRegion(GameAssetManager assetManager) {
        String path = "Game/Mill/" + type.name() + ".png";
        return new TextureRegion(assetManager.getTexture(path));
    }

    public MillProductType getType() {
        return type;
    }
}
