package com.yourgame.model.ManuFactor.Artisan.EdibleArtisanProduct.Products;

import com.yourgame.model.Item.ArtisanIngredient;
import com.yourgame.model.Farming.Vegetables;
import com.yourgame.model.Item.Inventory.InventorySlot;
import com.yourgame.model.ManuFactor.Artisan.EdibleArtisanProduct.EdibleArtisanProduct;
import com.yourgame.model.ManuFactor.Artisan.EdibleArtisanProduct.EdibleArtisanProductType;

import java.util.ArrayList;

public class Juice extends EdibleArtisanProduct {

    private static final int PROCESSING_TIME_DAYS = 96;

    public Juice(EdibleArtisanProductType type, ArrayList<InventorySlot> inventorySlots) {
        super(type, inventorySlots, calculateEnergy(inventorySlots));
    }

    public static int calculateEnergy(ArrayList<InventorySlot> inventorySlots) {
        return 0;
    }
}
