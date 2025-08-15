package com.yourgame.model.ManuFactor.Artisan;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.model.Item.Item;
import com.yourgame.model.ManuFactor.Artisan.EdibleArtisanProduct.EdibleArtisanProductType;

public class ArtisanProduct extends Item {
    ArtisanProductType type;

    public ArtisanProduct(ArtisanProductType type) {
        super(type.getName(), ItemType.ARTISAN, type.getSellPrice(), true);
        this.type = type;
    }

    @Override
    public TextureRegion getTextureRegion(GameAssetManager assetManager) {
        String path = "Game/Artisan/" + type.getName() + ".png";
        return new TextureRegion(assetManager.getTexture(path));
    }

    public ArtisanProductType getType() {
        return type;
    }
}
