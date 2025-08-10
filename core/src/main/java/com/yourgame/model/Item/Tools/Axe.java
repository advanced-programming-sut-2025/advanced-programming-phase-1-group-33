package com.yourgame.model.Item.Tools;

import com.yourgame.model.Item.Item;
import com.yourgame.model.Map.Map;
import com.yourgame.model.Map.MapElement;
import com.yourgame.model.Map.Tile;
import com.yourgame.model.Skill.Ability;
import com.yourgame.model.UserInfo.Player;
import com.yourgame.model.WeatherAndTime.Weather;

import java.awt.*;
import java.util.List;

public class Axe extends Tool {
    public Axe() {
        super(ToolType.Axe, 4);
    }

    @Override
    public int getConsumptionEnergy(Player player, Weather weather, boolean success) {
        int energyConsumed = 5 - level;
        if (!success) energyConsumed--;
        if (player.getAbility().getForagingLevel() == Ability.MAX_LEVEL) energyConsumed--;
        return (int) (Math.max(energyConsumed, 0) * weather.energyCoefficient);
    }

    @Override
    public boolean use(Player player, Map map, Tile tile) {
        MapElement element = tile.getElement();
        if (element == null) return false;

        int damage = (int) ((1 + 0.3 * player.getAbility().getForagingLevel()) * (1 + 0.4 * level));

        if (element.getType() == MapElement.ElementType.TREE || element.getType() == MapElement.ElementType.WOOD) {
            if (element.takeDamage(damage)) {
                dropElement(map, element);
            }
            return true;
        }

        return false;
    }
}
