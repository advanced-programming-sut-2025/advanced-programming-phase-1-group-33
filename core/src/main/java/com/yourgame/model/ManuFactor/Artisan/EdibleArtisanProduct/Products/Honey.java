package com.yourgame.model.ManuFactor.Artisan.EdibleArtisanProduct.Products;

import com.yourgame.model.ManuFactor.Artisan.ArtisanProductType;
import com.yourgame.model.ManuFactor.Artisan.EdibleArtisanProduct.EdibleArtisanProduct;

import java.util.ArrayList;

public class Honey extends EdibleArtisanProduct {
    public Honey(ArtisanProductType type) {
        super(type,type.getSellPrice());
    }
}
