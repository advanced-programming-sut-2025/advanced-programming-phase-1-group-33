package com.yourgame.model.ManuFactor.Artisan.InEdibleArtisanProduct.Products;

import com.badlogic.gdx.Gdx;
import com.yourgame.model.Item.Inventory.InventorySlot;
import com.yourgame.model.ManuFactor.Artisan.InEdibleArtisanProduct.InEdibleArtisanProduct;
import com.yourgame.model.ManuFactor.Artisan.InEdibleArtisanProduct.InEdibleArtisanProductType;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;

public class Coal extends InEdibleArtisanProduct {

    public Coal(InEdibleArtisanProductType type) {
        super(type);
    }

    public static int calculateProcessingTime() {
        return 8;
    }

    public static int calculatePrice() {
        return 50;
    }

    public static boolean isIngredientsAppropriate(ArrayList<InventorySlot> selectedItems) {
        if (selectedItems.isEmpty()) return false;

        for(InventorySlot slot : selectedItems) {
            if(!slot.item().getName().equals("Wood"))
                return false;
        }

        int selectedCount = selectedItems.size();
        return selectedCount == 10;
    }

}
