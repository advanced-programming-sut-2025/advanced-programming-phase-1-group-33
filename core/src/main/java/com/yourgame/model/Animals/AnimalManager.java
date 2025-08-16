package com.yourgame.model.Animals;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.model.Animals.AnimalPackage.Animal;
import com.yourgame.model.App;
import com.yourgame.model.Map.Elements.BuildingType;
import com.yourgame.model.Map.Elements.FarmBuilding;
import com.yourgame.model.Map.Map;
import com.yourgame.model.NPC.NPC;
import com.yourgame.model.UserInfo.Player;
import com.yourgame.model.WeatherAndTime.TimeObserver;
import com.yourgame.model.WeatherAndTime.TimeSystem;
import com.yourgame.model.WeatherAndTime.Weather;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AnimalManager implements TimeObserver {
    private final List<Animal> activeAnimals;
    private final List<Emote> activeEmotes;
    private final Player player;

    public AnimalManager(Player player) {
        this.activeAnimals = new ArrayList<>();
        this.activeEmotes = new ArrayList<>();
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

        activeEmotes.removeIf(emote -> emote.update(delta));
    }

    public void render(SpriteBatch batch) {
        for (Animal animal : activeAnimals) {
            batch.draw(animal.getTextureFrame(), animal.position.x, animal.position.y);
        }

        for (Emote emote : activeEmotes) {
            batch.draw(emote.texture, emote.position.x, emote.position.y);
        }
    }

    /**
     * Checks if a click at the given world coordinates hits an animal.
     * @return The Animal at that position, or null if none.
     */
    public Animal getAnimalAt(float worldX, float worldY) {
        for (Animal animal : activeAnimals) {
            int width = animal.getType().getHome() == BuildingType.BARN ? 32 : 16;
            int height = animal.getType().getHome() == BuildingType.BARN ? 32 : 16;
            if (worldX >= animal.position.x && worldX <= animal.position.x + width &&
                worldY >= animal.position.y && worldY <= animal.position.y + height) {
                return animal;
            }
        }
        return null;
    }

    public Animal getAnimalAt(OrthographicCamera camera) {
        Vector3 worldCoordinates = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        return getAnimalAt(worldCoordinates.x, worldCoordinates.y);
    }

    public void showEmote(Animal animal, String emoteName) {
        float emoteX = animal.position.x;
        float emoteY = animal.position.y + (animal.getType().getHome() == BuildingType.BARN ? 32 : 16);
        activeEmotes.add(new Emote(emoteName, emoteX, emoteY));
    }
}
