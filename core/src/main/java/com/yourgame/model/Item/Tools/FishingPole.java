package com.yourgame.model.Item.Tools;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.model.Map.Map;
import com.yourgame.model.Map.Tile;
import com.yourgame.model.App;
import com.yourgame.model.Skill.Skill;
import com.yourgame.model.UserInfo.Player;
import com.yourgame.model.WeatherAndTime.Weather;

public class FishingPole extends Tool {
    public FishingPole(PoleStage type) {
        super(ToolType.FishingPole, 3);
    }

    @Override
    public int getConsumptionEnergy(Player player, Weather weather, boolean success) {
        return 0;
    }

    @Override
    public boolean use(Player player, Map map, Tile tile) {
        Weather weather = App.getGameState().getGameTime().getWeather();
        int multiple = switch (weather) {
            case Rainy -> 2;
            case Snowy -> 3;
            default -> 1;
        };
        int consumedEnergy;
        if (App.getGameState().getCurrentPlayer().getFishingSkill().isMaxLevel()) {
            consumedEnergy = switch (getPoleStage()) {
                case Training, Bamboo -> 7 * multiple;
                case Fiberglass -> 5 * multiple;
                case Iridium -> 3 * multiple;
            };
        } else {
            consumedEnergy = switch (getPoleStage()) {
                case Training, Bamboo -> 8 * multiple;
                case Fiberglass -> 6 * multiple;
                case Iridium -> 4 * multiple;


            };
        }
        return false;
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
