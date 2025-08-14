package com.yourgame.Mining;


import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.model.Item.Item;

public class MiningItem extends Item {
    private final MiningItemType type;

    public MiningItem(MiningItemType type) {
        super(type.toString(), ItemType.MINING, 0, true);
        this.type = type;
    }


    @Override
    public TextureRegion getTextureRegion(GameAssetManager assetManager) {
        return null;
    }
}
