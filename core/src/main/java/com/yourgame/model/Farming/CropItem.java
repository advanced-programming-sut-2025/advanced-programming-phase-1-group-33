package com.yourgame.model.Farming;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.model.Item.Item;

public class CropItem extends Item {
    private final CropType cropType;

    public CropItem(CropType cropType) {
        super(cropType.getName(), ItemType.CROP, cropType.getBaseSellPrice(), true);
        this.cropType = cropType;
    }

    @Override
    public TextureRegion getTextureRegion(GameAssetManager assetManager) {
        return null;
    }

    public CropType getCropType() {
        return cropType;
    }
}
