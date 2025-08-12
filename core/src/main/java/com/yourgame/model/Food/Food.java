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

    @Override
    public TextureRegion getTextureRegion(GameAssetManager assetManager) {
        String path = "Game/Food/" + foodType.name() + ".png";
        return new TextureRegion(assetManager.getTexture(path));
    }

    @Override
    public boolean use(Player player, Map map, Tile tile) {
        if (player.getEnergy() < Player.MAX_ENERGY) {
            player.addEnergy(foodType.getEnergy());
            player.getBackpack().getInventory().reduceItemQuantity(this, 1);
            return true;
        } else {
            return false;
        }
        /*
        * TODO
        * food animation, applying buff, error sound when player is full
        * */
    }
}
