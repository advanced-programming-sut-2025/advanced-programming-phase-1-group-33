package com.yourgame.model.ManuFactor.Artisan.EdibleArtisanProduct;

import com.yourgame.model.Item.ArtisanIngredient;
import com.yourgame.model.Item.Inventory.InventorySlot;
import com.yourgame.model.ManuFactor.Artisan.ArtisanProduct;

import java.util.ArrayList;

public abstract class EdibleArtisanProduct extends ArtisanProduct {
    private final int energy;
    public EdibleArtisanProduct(EdibleArtisanProductType type, ArrayList<InventorySlot> inventorySlots, int energy) {
        super(type);
        this.energy = energy;
    }

    public int getEnergy() {
        return energy;
    }
}
