package com.yourgame.model.Item.Tools;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.model.Animals.FishPackage.Fish;
import com.yourgame.model.Animals.FishPackage.FishItem;
import com.yourgame.model.Map.Map;
import com.yourgame.model.Map.Tile;
import com.yourgame.model.App;
import com.yourgame.model.Skill.Skill;
import com.yourgame.model.UserInfo.Player;
import com.yourgame.model.WeatherAndTime.Season;
import com.yourgame.model.WeatherAndTime.Weather;

import java.util.List;
import java.util.Random;

public class FishingPole extends Tool {
    private final static Random random = new Random();

    public FishingPole(PoleStage type) {
        super(ToolType.FishingPole, 3);
    }

    @Override
    public int getConsumptionEnergy(Player player, Weather weather, boolean success) {
        return success ? 2 : 1;
    }

    @Override
    public boolean use(Player player, Map map, Tile tile) {
        if (!tile.isFishable()) return false;
        Season season = App.getGameState().getGameTime().getSeason();
        List<Fish> fishes = Fish.getFishesBySeason(season);
        Fish randomFish = fishes.get(random.nextInt(fishes.size()));
        map.spawnDroppedItem(new FishItem(randomFish), tile.tileX * Tile.TILE_SIZE, tile.tileY * Tile.TILE_SIZE);
        return true;
    }

    @Override
    public TextureRegion getTextureRegion(GameAssetManager assetManager) {
        String path = "Game/Tool/" + getPoleStage().name() + "_FishingPole.png";
        return new TextureRegion(assetManager.getTexture(path));
    }

    public PoleStage getPoleStage() {
        return PoleStage.values()[level];
    }
}
