package com.yourgame.model.ManuFactor.Artisan;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.model.Food.Cooking.Ingredient;
import com.yourgame.model.Item.Item;

import java.util.ArrayList;

public class ArtisanProduct extends Item {
    private final ArtisanProductType type;
    private final ArrayList<Ingredient> ingredients;

    public ArtisanProduct(ArtisanProductType type,int price, ArrayList<Ingredient> ingredients) {
        super(type.getName(), ItemType.ARTISAN, price, true);
        this.type = type;
        this.ingredients = ingredients;
    }

    @Override
    public TextureRegion getTextureRegion(GameAssetManager assetManager) {
        String path = "Game/Artisan/" + type.name() + ".png";
        return new TextureRegion(assetManager.getTexture(path));
    }

    public ArtisanProductType getType() {
        return type;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }
}
