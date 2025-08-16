package com.yourgame.model.Animals;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.yourgame.model.Animals.AnimalPackage.Animal;
import com.yourgame.model.App;
import com.yourgame.model.Map.Elements.FarmBuilding;
import com.yourgame.model.Map.Map;
import com.yourgame.model.UserInfo.Player;
import com.yourgame.model.WeatherAndTime.TimeObserver;
import com.yourgame.model.WeatherAndTime.TimeSystem;
import com.yourgame.model.WeatherAndTime.Weather;

import java.util.ArrayList;
import java.util.List;

public class AnimalManager implements TimeObserver {
    private final List<Animal> activeAnimals;
    private final Player player;

    public AnimalManager(Player player) {
        this.activeAnimals = new ArrayList<>();
        this.player = player;
    }

    @Override
    public void onTimeChanged(TimeSystem timeSystem) {
        activeAnimals.clear();

        if (timeSystem.getWeather() != Weather.Sunny) {
            return;
        }

        // Let animals out from the coop
        FarmBuilding coop = player.getCoop();
        if (coop != null) {
            // TODO: Check if the coop door is open
            for (Animal animal : coop.getAnimals()) {
                animal.position.set(coop.getPixelBounds().x + 32, coop.getPixelBounds().y - 16);
                activeAnimals.add(animal);
            }
        }

        // Let animals out from the barn
        FarmBuilding barn = player.getBarn();
        if (barn != null) {
            // TODO: Check if the barn door is open
            for (Animal animal : barn.getAnimals()) {
                animal.position.set(barn.getPixelBounds().x + 32, barn.getPixelBounds().y - 16);
                activeAnimals.add(animal);
            }
        }
    }

    public void update(float delta, Map map) {
        for (Animal animal : activeAnimals) {
            animal.update(delta, map);
        }
    }

    public void render(SpriteBatch batch) {
        for (Animal animal : activeAnimals) {
            batch.draw(animal.getTextureFrame(), animal.position.x, animal.position.y);
        }
    }
}
