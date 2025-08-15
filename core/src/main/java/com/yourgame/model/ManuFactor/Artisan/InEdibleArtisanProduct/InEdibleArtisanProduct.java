package com.yourgame.model.ManuFactor.Artisan.InEdibleArtisanProduct;

import com.yourgame.model.ManuFactor.Artisan.ArtisanProduct;
import com.yourgame.model.ManuFactor.Artisan.EdibleArtisanProduct.EdibleArtisanProductType;

public abstract class InEdibleArtisanProduct extends ArtisanProduct {

    public InEdibleArtisanProduct(EdibleArtisanProductType type) {
        super(type);
    }
}
