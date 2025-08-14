package com.yourgame.model.ManuFactor.Artisan.EdibleArtisanProduct;

import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.model.Food.Cooking.Ingredient;
import com.yourgame.model.Item.Usable;
import com.yourgame.model.ManuFactor.Artisan.ArtisanProduct;
import com.yourgame.model.ManuFactor.Artisan.ArtisanProductType;
import com.yourgame.model.Map.Map;
import com.yourgame.model.Map.Tile;
import com.yourgame.model.UserInfo.Player;

import java.util.ArrayList;

public class EdibleArtisanProduct extends ArtisanProduct implements Usable {
    private int energy;

    public EdibleArtisanProduct(ArtisanProductType type, int price, ArrayList<Ingredient> ingredients) {
        super(type,price,ingredients);
    }

    @Override
    public boolean use(Player player, Map map, Tile tile) {
        // Prevent starting a new animation if one is already playing
        if (GameAssetManager.getInstance().getEdibleArtisanAnimation() != null) {
            return false;
        }

        if (player.getEnergy() < player.getMaxEnergy()) {
            GameAssetManager.getInstance().setEdibleArtisanAnimation(new EdibleArtisanAnimation(this, player, 0.6f));
            return true;
        } else {
            GameAssetManager.getInstance().getSound("Sounds/food_error.mp3").play();
            return false;
        }
    }

    public int getEnergy() {
        return energy;
    }
}
