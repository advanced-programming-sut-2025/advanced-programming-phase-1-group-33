package com.yourgame.model.Farming;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.model.Item.Item;
import com.yourgame.model.Item.Usable;
import com.yourgame.model.ManuFactor.Ingredient;
import com.yourgame.model.Map.Farm;
import com.yourgame.model.Map.Map;
import com.yourgame.model.Map.Tile;
import com.yourgame.model.UserInfo.Player;

public enum Fertilizer implements Ingredient {
    Growth_Fertilizer,
    Water_Fertilizer;

    public static Fertilizer getFertilizerByName(String fertilizerName) {
        if (fertilizerName.equalsIgnoreCase("GrowthFertilizer"))
            return Growth_Fertilizer;
        else
            return Water_Fertilizer;
    }

    public static Fertilizer getByName(String name) {
        for (Fertilizer type : Fertilizer.values()) {
            if (type.name().equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }

    public static final class FertilizerItem extends Item implements Usable {
        private final Fertilizer fertilizerType;

        public FertilizerItem(Fertilizer fertilizer) {
            super(fertilizer.name(), ItemType.INGREDIENT, fertilizer == Growth_Fertilizer ? 100 : 150, true);
            fertilizerType = fertilizer;
        }


        @Override
        public TextureRegion getTextureRegion(GameAssetManager assetManager) {
            String path = "Game/Crop/" + fertilizerType.name() + ".png";
            return new TextureRegion(assetManager.getTexture(path));
        }

        @Override
        public boolean use(Player player, Map map, Tile tile) {
            if (!(map instanceof Farm farm)) return false;
            return switch (tile.getDirtState()) {
                case PLOWED, WATERED -> {
                    tile.setFertilizer(fertilizerType);
                    farm.updateTileVisuals(tile.tileX, tile.tileY);
                    player.getBackpack().getInventory().reduceItemQuantity(this, 1);
                    yield true;
                }
                default -> false;
            };
        }
    }
}
