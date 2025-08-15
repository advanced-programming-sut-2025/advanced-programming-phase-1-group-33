package com.yourgame.model.ManuFactor.Artisan.EdibleArtisanProduct.Products;

import com.yourgame.model.Farming.Vegetables;
import com.yourgame.model.Item.ArtisanIngredient;
import com.yourgame.model.Item.Inventory.InventorySlot;
import com.yourgame.model.ManuFactor.Artisan.EdibleArtisanProduct.EdibleArtisanProduct;
import com.yourgame.model.ManuFactor.Artisan.EdibleArtisanProduct.EdibleArtisanProductType;

import java.util.ArrayList;

public class Honey extends EdibleArtisanProduct {
    private static final int PROCESSING_TIME_HOURS = 96;
    private static final int ENERGY = 75;
    private static final int PRICE = 350;

    public Honey(EdibleArtisanProductType type, ArrayList<InventorySlot> inventorySlots) {
        super(type, inventorySlots, ENERGY);
    }

    public int calculateEnergy() {
        return ENERGY;
    }

    public static int calculateValue() {
        return PRICE;
    }

    public static int calculateProcessingTime() {
        return PROCESSING_TIME_HOURS;
    }

    public static boolean isIngredientsAppropriate(ArrayList<InventorySlot> inventorySlots) {
        if(inventorySlots.isEmpty())
            return true;
        return false;
    }
}
