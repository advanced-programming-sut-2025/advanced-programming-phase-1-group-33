package com.yourgame.model.Item.Tools;

import com.yourgame.model.Map.Farm;
import com.yourgame.model.Map.Map;
import com.yourgame.model.Map.MapElement;
import com.yourgame.model.Map.Tile;
import com.yourgame.model.Skill.Skill;
import com.yourgame.model.UserInfo.Player;
import com.yourgame.model.WeatherAndTime.Weather;

public class Pickaxe extends Tool {
    public Pickaxe() {
        super(ToolType.Pickaxe, 4);
    }

    @Override
    public int getConsumptionEnergy(Player player, Weather weather, boolean success) {
        int energyConsumed = 5 - level;
        if (!success) energyConsumed--;
        if (player.getMiningSkill().isMaxLevel()) energyConsumed--;
        return (int) (Math.max(energyConsumed, 0) * weather.energyCoefficient);
    }


    @Override
    public boolean use(Player player, Map map, Tile tile) {
        MapElement element = tile.getElement();
        if (element == null) {
            if (map instanceof Farm farm && tile.getDirtState() == Tile.DirtState.PLOWED) {
                tile.setDirtState(Tile.DirtState.NORMAL);
                farm.updateTileVisuals(tile.tileX, tile.tileY);
                return true;
            }
            return false;
        }

        int damage = (int) ((1 + 0.3 * player.getMiningSkill().level()) * (1 + 0.4 * level));

        if (element.getType() == MapElement.ElementType.ROCK) {
            if (element.takeDamage(damage)) {
                dropElement(map, element);
            }
            return true;
        }

        return false;
    }
}
