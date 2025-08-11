package com.yourgame.model.Item;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.model.ManuFactor.Ingredient;

public class Stone implements Ingredient {
    public static final class StoneItem extends Item {
        public StoneItem() {
            super("Stone", ItemType.INGREDIENT, 3, true);
        }

        @Override
        public TextureRegion getTextureRegion(GameAssetManager assetManager) {
            return new TextureRegion(assetManager.getTexture("Game/Mineral/stone.png"));
        }
    }

    @Override
    public Item getItem() {
        return new StoneItem();
    }
}
