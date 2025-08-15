package com.yourgame.model.ManuFactor.Artisan.EdibleArtisanProduct;

import com.yourgame.model.Item.ArtisanIngredient;
import com.yourgame.model.ManuFactor.Artisan.ArtisanProduct;

import java.util.ArrayList;

public abstract class EdibleArtisanProduct extends ArtisanProduct {
    public EdibleArtisanProduct(EdibleArtisanProductType type, ArrayList<ArtisanIngredient> artisanIngredients) {
        super(type);
    }
}
