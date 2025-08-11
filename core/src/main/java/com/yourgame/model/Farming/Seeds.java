package com.yourgame.model.Farming;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.model.App;
import com.yourgame.model.Item.Item;
import com.yourgame.model.Item.Usable;
import com.yourgame.model.ManuFactor.Ingredient;
import com.yourgame.model.Map.Farm;
import com.yourgame.model.Map.Map;
import com.yourgame.model.Map.Tile;
import com.yourgame.model.UserInfo.Player;
import com.yourgame.model.WeatherAndTime.Season;

public enum Seeds implements Ingredient {
    // Remove the CropType argument from here
    Jazz_Seeds(Season.Spring),
    Carrot_Seeds(Season.Spring),
    Cauliflower_Seeds(Season.Spring),
    Coffee_Bean(Season.Spring),
    Garlic_Seeds(Season.Spring),
    Bean_Starter(Season.Spring),
    Kale_Seeds(Season.Spring),
    Parsnip_Seeds(Season.Spring),
    Potato_Seeds(Season.Spring),
    Rhubarb_Seeds(Season.Spring),
    Strawberry_Seeds(Season.Spring),
    Tulip_Bulb(Season.Spring),
    Rice_Shoot(Season.Spring),
    Blueberry_Seeds(Season.Summer),
    Corn_Seeds(Season.Summer),
    Hops_Starter(Season.Summer),
    Pepper_Seeds(Season.Summer),
    Melon_Seeds(Season.Summer),
    Poppy_Seeds(Season.Summer),
    Radish_Seeds(Season.Summer),
    Red_Cabbage_Seeds(Season.Summer),
    Starfruit_Seeds(Season.Summer),
    Spangle_Seeds(Season.Summer),
    Summer_Squash_Seeds(Season.Summer),
    Sunflower_Seeds(Season.Summer),
    Tomato_Seeds(Season.Summer),
    Wheat_Seeds(Season.Summer),
    Amaranth_Seeds(Season.Fall),
    Artichoke_Seeds(Season.Fall),
    Beet_Seeds(Season.Fall),
    Bok_Choy_Seeds(Season.Fall),
    Broccoli_Seeds(Season.Fall),
    Cranberry_Seeds(Season.Fall),
    Eggplant_Seeds(Season.Fall),
    Fairy_Seeds(Season.Fall),
    Grape_Starter(Season.Fall),
    Pumpkin_Seeds(Season.Fall),
    Yam_Seeds(Season.Fall),
    Rare_Seed(Season.Fall),
    Powdermelon_Seeds(Season.Winter),
    // AncientSeeds(Season.Special),
    // MixedSeeds(Season.Special);
    Ancient_Seeds(Season.Winter),
    Mixed_Seeds(Season.Winter);

    private final Season season;

    Seeds(Season season) {
        this.season = season;
    }

    public String getName() {
        return name();
    }

    public Season getSeason() {
        return season;
    }

    public static Seeds getSeedByName(String name) {
        if (name == null || name.isEmpty())
            return null;
        return Seeds.valueOf(name);
    }

    @Override
    public Item getItem() {
        return new SeedItem(this);
    }

    public static final class SeedItem extends Item implements Usable {
        private final Seeds seed;

        public SeedItem(Seeds seed) {
            super(seed.name(), ItemType.INGREDIENT, 5, true);
            this.seed = seed;
        }

        @Override
        public TextureRegion getTextureRegion(GameAssetManager assetManager) {
            String path = "Game/Crop/" + CropType.getCropForSeed(seed).getName() + "/" + this.getName() + ".png";
            return new TextureRegion(assetManager.getTexture(path));
        }

        @Override
        public boolean use(Player player, Map map, Tile tile) {
            if (!(map instanceof Farm)) return false;
            if (tile.getElement() != null) return false;
            return switch (tile.getDirtState()) {
                case NON_FARMABLE, NORMAL -> false;
                case PLOWED, WATERED -> {
                    int x = tile.tileX * Tile.TILE_SIZE;
                    int y = tile.tileY * Tile.TILE_SIZE;
                    Crop crop = new Crop(CropType.getCropForSeed(seed), tile.getFertilizer(), x, y);
                    crop.setWateredToday(tile.isWatered());

                    map.addElement(crop);
                    App.getGameState().getGameTime().addObserver(crop);

                    player.getBackpack().getInventory().reduceItemQuantity(this, 1);

                    yield true;
                }
            };
        }
    }
}
