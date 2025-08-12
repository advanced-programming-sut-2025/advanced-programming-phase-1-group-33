package com.yourgame.model.Food;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.model.Item.Item;
import com.yourgame.model.Item.Usable;
import com.yourgame.model.Map.Map;
import com.yourgame.model.Map.Tile;
import com.yourgame.model.UserInfo.Player;

public class Food extends Item implements Usable {
    private final FoodType foodType;

    public Food(FoodType foodType) {
        super(foodType.name(), ItemType.INGREDIENT, foodType.getSellPrice(), true);
        this.foodType = foodType;
    }

    public FoodType getFoodType() {
        return foodType;
    }

    @Override
    public TextureRegion getTextureRegion(GameAssetManager assetManager) {
        String path = "Game/Food/" + foodType.name() + ".png";
        return new TextureRegion(assetManager.getTexture(path));
    }

    @Override
    public boolean use(Player player, Map map, Tile tile) {
        // Prevent starting a new animation if one is already playing
        if (GameAssetManager.getInstance().getFoodAnimation() != null) {
            return false;
        }

        if (player.getEnergy() < Player.MAX_ENERGY) {
            GameAssetManager.getInstance()
                .setFoodAnimation(new FoodAnimation(this, player, 0.6f));
            return true;
        } else {
            // TODO: Play an error sound because the player is full
            return false;
        }
        /*
        * TODO
        * food animation, applying buff, error sound when player is full
        * */
    }
}
