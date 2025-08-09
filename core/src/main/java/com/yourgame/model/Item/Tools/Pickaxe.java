package com.yourgame.model.Item.Tools;

import com.yourgame.Graphics.Map.MapData;
import com.yourgame.Graphics.Map.MapElement;
import com.yourgame.Graphics.Map.TileData;
import com.yourgame.model.App;
import com.yourgame.model.Skill.Ability;
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
        if (player.getAbility().getForagingLevel() == Ability.MAX_LEVEL) energyConsumed--;
        return (int) (Math.max(energyConsumed, 0) * weather.energyCoefficient);
    }


    @Override
    public boolean use(Player player, MapData map, TileData tile) {
        MapElement element = tile.getElement();
        if (element == null) return false;

        int damage = (int) ((1 + 0.3 * player.getAbility().getMiningLevel()) * (1 + 0.4 * level));

        if (element.getType() == MapElement.ElementType.ROCK) {
            if (element.takeDamage(damage)) {
                // TODO:
                // add the dropped items to the player inventory
                element.drop();
                map.removeElement(element);
            }
            return true;
        }

        return false;
    }
}
