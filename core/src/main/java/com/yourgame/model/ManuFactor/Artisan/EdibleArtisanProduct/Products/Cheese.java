package com.yourgame.model.ManuFactor.Artisan.EdibleArtisanProduct.Products;

import com.yourgame.model.ManuFactor.Artisan.ArtisanProductType;
import com.yourgame.model.ManuFactor.Artisan.EdibleArtisanProduct.EdibleArtisanProduct;
import com.yourgame.model.ManuFactor.Ingredient;

import java.util.ArrayList;

public class Cheese extends EdibleArtisanProduct {

    public Cheese(ArtisanProductType type, ArrayList<Ingredient> ingredients) {
        super(type, calculateSellPrice(ingredients));
    }

    private static int calculateSellPrice(ArrayList<Ingredient> ingredients) {

    }
}
