package com.yourgame.controller.GameController;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.Graphics.MenuAssetManager;
import com.yourgame.model.App;
import com.yourgame.model.Farming.Plant;
import com.yourgame.model.GameState;
import com.yourgame.model.Item.Item;
import com.yourgame.model.Item.Tools.Tool;
import com.yourgame.model.Item.Usable;
import com.yourgame.model.Map.*;
import com.yourgame.model.Map.Elements.DroppedItem;
import com.yourgame.model.UserInfo.Player;
import com.yourgame.model.WeatherAndTime.Season;
import com.yourgame.model.WeatherAndTime.Weather;

import java.util.Iterator;
import java.util.List;

import static com.yourgame.model.Map.Tile.TILE_SIZE;
import static com.yourgame.model.UserInfo.Player.PLAYER_HEIGHT;
import static com.yourgame.model.UserInfo.Player.PLAYER_WIDTH;

public class GameController {
    private static final float ITEM_PULL_RADIUS = 48f; // The distance at which items start moving towards the player.
    private static final float ITEM_PICKUP_RADIUS = 12f; // The distance at which items are collected.
    private static final float ITEM_MOVE_SPEED = 180f;

    private MapManager mapManager;
    private Player player;
    private Map currentMap;

    private Sound popUpSound;

    public GameController() {
        player = Player.guest();
        mapManager = new MapManager(List.of(player));
        currentMap = mapManager.getPlayersCurrentMap(player);
        player.playerPosition = currentMap.getSpawnPoint();

        popUpSound = MenuAssetManager.getInstance().getSounds("popUp");
    }

    public MapManager getMapManager() {
        return mapManager;
    }

    public Map getCurrentMap() {
        return currentMap;
    }

    public void setCurrentMap(Map currentMap) {
        this.currentMap = currentMap;
    }

    public Player getPlayer() {
        return player;
    }

    public void renderMapObjects(GameAssetManager assetManager, SpriteBatch batch) {
        Season gameSeason = App.getGameState().getGameTime().getSeason();
        for (MapElement element : currentMap.getMapElements()) {
            TextureRegion texture = element.getTexture(assetManager, gameSeason);
            if (texture != null) {
                java.awt.Rectangle bounds = element.getPixelBounds();
                batch.draw(texture, bounds.x, bounds.y, bounds.width, bounds.height);
            }

            if (element.getHealth() <= 0) currentMap.removeElement(element);
        }

        // Render dropped items
        for (DroppedItem droppedItem : currentMap.getDroppedItems()) {
            TextureRegion texture = droppedItem.getTexture(assetManager, gameSeason);
            if (texture != null) {
                batch.draw(texture, droppedItem.getPosition().x, droppedItem.getPosition().y);
            }
        }
    }

    public void updateDroppedItems(float delta) {
        // Use an iterator to safely remove items from the list while looping through it
        Iterator<DroppedItem> iterator = currentMap.getDroppedItems().iterator();
        while (iterator.hasNext()) {
            DroppedItem droppedItem = iterator.next();

            float distance = player.playerPosition.dst(droppedItem.getPosition());

            // 1. Check for immediate pickup
            if (distance < ITEM_PICKUP_RADIUS) {
                // Try to add the item to the player's inventory
                if (player.getBackpack().getInventory().addItem(droppedItem.getItem(), 1)) {
                    // If adding was successful, remove the item from the map
                    iterator.remove();
                    popUpSound.play();
                    continue;
                }
            }
            // 2. Check for magnetic pull
            else if (distance < ITEM_PULL_RADIUS) {
                droppedItem.moveTo(player.playerPosition, ITEM_MOVE_SPEED);
            }
            // 3. If the item is out of range, ensure it's not moving
            else {
                droppedItem.stopMovement();
            }

            // Update the item's position based on its velocity
            droppedItem.update(delta);
        }
    }

    public Tile getTileInFront() {
        // Calculate the center of the player's collision box
        float playerCenterX = player.playerPosition.x + (PLAYER_WIDTH / 2f);
        float playerCenterY = player.playerPosition.y + (PLAYER_HEIGHT / 4f); // Center of the feet/lower body

        // Determine the tile the player is currently on
        int tileX = (int) (playerCenterX / TILE_SIZE);
        int tileY = (int) (playerCenterY / TILE_SIZE);

        // Get the tile in front based on direction
        return switch (player.direction) {
            case 0 -> currentMap.getTile(tileX, tileY - 1); // Down
            case 1 -> currentMap.getTile(tileX + 1, tileY); // Right
            case 2 -> currentMap.getTile(tileX, tileY + 1); // Up
            case 3 -> currentMap.getTile(tileX - 1, tileY); // Left
            default -> currentMap.getTile(tileX, tileY);   // Should not happen
        };
    }

    public boolean isBlocked(float x, float y) {
        // Define a smaller collision box at the player's feet for better feel
        float boxX = x + 2; // small horizontal offset
        float boxY = y;
        float boxWidth = PLAYER_WIDTH - 4;
        float boxHeight = PLAYER_HEIGHT / 2f; // Check only the lower half of the player

        // Check the four corners of the player's collision box
        if (currentMap.isTileBlocked(boxX, boxY))
            return true;
        if (currentMap.isTileBlocked(boxX + boxWidth, boxY))
            return true;
        if (currentMap.isTileBlocked(boxX, boxY + boxHeight))
            return true;
        if (currentMap.isTileBlocked(boxX + boxWidth, boxY + boxHeight))
            return true;

        return false;
    }

    /**
     * This method is called when the player hits the left mouse button.
     * */
    public void handleInteraction() {
        Tile tile = getTileInFront();

        // Harvest Tree or Crop
//        MapElement element = tile.getElement();
//        if (element instanceof Plant plant && plant.hasProduct()) {
//            for (Item item : plant.harvest()) {
//                currentMap.spawnDroppedItem(item, tile.tileX * TILE_SIZE, tile.tileY * TILE_SIZE);
//                plant.setHasProduct(false);
//                return;
//            }
//        }

        // Use item
        Item item = player.getBackpack().getInventory().getSelectedItem();
        if (item instanceof Usable usable) {
            boolean success = usable.use(player, currentMap, tile);
            if (item instanceof Tool tool)
                player.consumeEnergy(tool.getConsumptionEnergy(player, Weather.Sunny, success));
        }
    }
}
