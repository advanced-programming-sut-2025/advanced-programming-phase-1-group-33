package com.yourgame.model.ManuFactor.Artisan.EdibleArtisanProduct.Products;

import com.yourgame.model.Item.ArtisanIngredient;
import com.yourgame.model.Farming.Vegetables;
import com.yourgame.model.ManuFactor.Artisan.EdibleArtisanProduct.EdibleArtisanProduct;
import com.yourgame.model.ManuFactor.Artisan.EdibleArtisanProduct.EdibleArtisanProductType;

import java.util.ArrayList;

public class Juice extends EdibleArtisanProduct {

    private static final int PROCESSING_TIME_DAYS = 96;

    public Juice(EdibleArtisanProductType type, ArrayList<ArtisanIngredient> artisanIngredients) {
        super(type, artisanIngredients, calculateEnergy(artisanIngredients));
    }

    public static int calculateEnergy(ArrayList<ArtisanIngredient> artisanIngredients) {
        int totalEnergy = 0;
        for (ArtisanIngredient ingredient : artisanIngredients) {
            totalEnergy += ingredient.getBaseEnergy() * 2;
        }
        return totalEnergy;
    }

    public static int calculateValue(ArrayList<ArtisanIngredient> artisanIngredients) {
        int totalValue = 0;
        for (ArtisanIngredient ingredient : artisanIngredients) {
            totalValue += Math.round(ingredient.getBasePrice() * 2.25f);
        }
        return totalValue;
    }

    public static int calculateProcessingTime() {
        return PROCESSING_TIME_DAYS;
    }

    public static boolean isIngredientsAppropriate(ArrayList<ArtisanIngredient> artisanIngredients) {
        for (ArtisanIngredient ingredient : artisanIngredients) {
            if (!(ingredient instanceof Vegetables)) {
                return false;
            }
        }
        return true;
    }
}
