package com.yourgame.model;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.yourgame.model.WeatherAndTime.TimeSystem;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages the overall state of the game world.
 * This class holds references to the core components of the game, like the player,
 * the map, and the time system. It's designed for a real-time game loop.
 */
public class GameState {

    private final Player player;
    private final TimeSystem time;
    private TiledMap currentMap;

    // You can add lists for other dynamic entities here
    // private final List<NPC> npcs = new ArrayList<>();
    // private final List<Animal> animals = new ArrayList<>();

    public GameState(Player player, TiledMap map) {
        this.player = player;
        this.currentMap = map;
        this.time = new TimeSystem();
        // Initialize other game elements (NPCs, etc.) here
    }

    /**
     * Updates the logical state of the game world. This should be called
     * on every frame from the main game loop.
     * @param delta Time in seconds since the last frame.
     */
    public void update(float delta) {
        // This is where time-based game logic goes.
        // For example, advancing the in-game clock.
        // time.update(delta); // Assuming TimeSystem can be updated per frame.

        // You would also update other entities here
        // for (NPC npc : npcs) {
        //     npc.update(delta);
        // }
    }

    /**
     * Prepares the game state for the next in-game day.
     * This method handles resetting player state, growing crops, etc.
     */
    public void prepareForNextDay() {
        // Restore player energy. If fainted, they get less energy back.
        player.restoreEnergy(player.isFainted() ? (player.getMaxEnergy() / 2) : player.getMaxEnergy());
        player.setFainted(false);

        // Here you would add logic from your old 'MakeGameReadyForNextDay' method, such as:
        // - Growing crops and trees
        // - Spawning new forageables
        // - Resetting shop inventories
        // - Updating NPC relationships
        System.out.println("A new day has begun!");
    }


    // --- GETTERS ---

    public Player getPlayer() {
        return player;
    }

    public TimeSystem getGameTime() {
        return time;
    }

    public TiledMap getCurrentMap() {
        return currentMap;
    }

    public void setCurrentMap(TiledMap map) {
        this.currentMap = map;
    }
}
