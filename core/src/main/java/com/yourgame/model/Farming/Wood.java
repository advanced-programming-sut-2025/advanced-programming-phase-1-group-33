package com.yourgame.model.Farming;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.model.Item.Item;
import com.yourgame.model.ManuFactor.Ingredient;

public class Wood implements Ingredient {
    /**
     * This class is created to represent an Item version of Wood.
     * Because refactoring Ingredient into Item is not simple.
     * */
    public static final class WoodItem extends Item {
        public WoodItem() {
            super("Wood", ItemType.INGREDIENT, 3, true);
        }

        @Override
        public TextureRegion getTextureRegion(GameAssetManager assetManager) {
            return new TextureRegion(assetManager.getTexture("Game/Tree/Wood.png"));
        }
    }

    @Override
    public Item getItem() {
        return new WoodItem();
    }
}
