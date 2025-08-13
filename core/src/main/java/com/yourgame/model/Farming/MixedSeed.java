package com.yourgame.model.Farming;


import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.model.App;
import com.yourgame.model.Item.Item;
import com.yourgame.model.Item.Usable;
import com.yourgame.model.Map.Farm;
import com.yourgame.model.Map.Map;
import com.yourgame.model.Map.Tile;
import com.yourgame.model.UserInfo.Player;
import com.yourgame.model.WeatherAndTime.Season;

import java.util.*;

public class MixedSeed extends Item implements Usable {
    private static final HashMap<Season, ArrayList<CropType>> seasonSeeds = new HashMap<>();
    private static final Random random = new Random();

    static {
        seasonSeeds.put(Season.Spring, new ArrayList<>(Arrays.asList(CropType.Cauliflower, CropType.Parsnip,
                CropType.Potato, CropType.Blue_Jazz, CropType.Tulip)));
        seasonSeeds.put(Season.Summer, new ArrayList<>(Arrays.asList(CropType.Corn, CropType.Hot_Pepper,
                CropType.Radish, CropType.Wheat, CropType.Poppy, CropType.Sunflower, CropType.Summer_Spangle)));
        seasonSeeds.put(Season.Fall, new ArrayList<>(Arrays.asList(CropType.Artichoke, CropType.Corn,
                CropType.Eggplant, CropType.Pumpkin, CropType.Sunflower, CropType.Fairy_Rose)));
        seasonSeeds.put(Season.Winter, new ArrayList<>(List.of(CropType.PowderMelon)));
    }

    public MixedSeed() {
        super("MixedSeed", ItemType.INGREDIENT, 1, true);
    }

    @Override
    public TextureRegion getTextureRegion(GameAssetManager assetManager) {
        return new TextureRegion(assetManager.getTexture("Game/Crop/Mixed_Seeds.png"));
    }

    @Override
    public boolean use(Player player, Map map, Tile tile) {
        if (!(map instanceof Farm)) return false;
        if (tile.getElement() != null) return false;
        return switch (tile.getDirtState()) {
            case NON_FARMABLE, NORMAL -> false;
            default -> {
                Season currentSeason = App.getGameState().getGameTime().getSeason();
                List<CropType> possibleCrops = seasonSeeds.get(currentSeason);
                CropType type = possibleCrops.get(random.nextInt(possibleCrops.size()));

                int x = tile.tileX * Tile.TILE_SIZE;
                int y = tile.tileY * Tile.TILE_SIZE;
                Crop crop = new Crop(type, tile.getFertilizer(), x, y);
                crop.setWateredToday(tile.isWatered());

                if (!map.addElement(crop)) yield false;

                App.getGameState().getGameTime().addPlant(crop);

                player.getBackpack().getInventory().reduceItemQuantity(this, 1);

                yield true;
            }
        };
    }
}
