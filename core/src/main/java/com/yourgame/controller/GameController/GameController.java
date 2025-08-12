package com.yourgame.controller.GameController;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.Graphics.MenuAssetManager;
import com.yourgame.model.App;
import com.yourgame.model.Farming.ForagingCrop;
import com.yourgame.model.Farming.ForagingCropElement;
import com.yourgame.model.Farming.Plant;
import com.yourgame.model.Item.Inventory.Inventory;
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
    private Tile selectedTile;

    private Sound popUpSound;

    public GameController() {
        player = Player.guest();
        mapManager = new MapManager(List.of(player));
        currentMap = mapManager.getPlayersCurrentMap(player);
        player.playerPosition = currentMap.getSpawnPoint();
        selectedTile = null;

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
        Iterator<MapElement> iterator = currentMap.getMapElements().iterator();
        while (iterator.hasNext()) {
            MapElement element = iterator.next();
            if (element.getHealth() <= 0) {
                iterator.remove();
                continue;
            }
            TextureRegion texture = element.getTexture(assetManager, gameSeason);
            if (texture != null) {
                java.awt.Rectangle bounds = element.getPixelBounds();
                batch.draw(texture, bounds.x, bounds.y, bounds.width, bounds.height);
            }
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
        Inventory inventory = player.getBackpack().getInventory();

        // Use an iterator to safely remove items from the list while looping through it
        Iterator<DroppedItem> iterator = currentMap.getDroppedItems().iterator();
        while (iterator.hasNext()) {
            DroppedItem droppedItem = iterator.next();
            if (inventory.isInventoryFull() && inventory.getItemQuantity(droppedItem.getItem()) <= 0) continue;

            float distance = player.playerPosition.dst(droppedItem.getPosition());

            // 1. Check for immediate pickup
            if (distance < ITEM_PICKUP_RADIUS) {
                // Try to add the item to the player's inventory
                if (inventory.addItem(droppedItem.getItem(), 1)) {
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
        // We just select the tile with the mouse for Hoe & WateringCan
        Tile selectedTile;
        Item item = player.getBackpack().getInventory().getSelectedItem();
        if (item instanceof Tool tool &&
            (tool.getToolType() == Tool.ToolType.Hoe || tool.getToolType() == Tool.ToolType.WateringCan)) {
            selectedTile = this.selectedTile;
        } else {
            selectedTile = getTileInFront();
        }

        // Harvest Tree or Crop
        MapElement element = selectedTile.getElement();
        if (element instanceof Plant plant && plant.hasProduct()) {
            List<Item> harvestedItems = plant.harvest();
            if (harvestedItems != null && !harvestedItems.isEmpty()) {
                for (Item harvest : harvestedItems) {
                    currentMap.spawnDroppedItem(harvest, selectedTile.tileX * TILE_SIZE, selectedTile.tileY * TILE_SIZE);
                }
                // If the plant is single-harvest, it will be destroyed.
                if (plant.getHealth() <= 0) {
                    currentMap.removeElement(plant);
                    App.getGameState().getGameTime().removePlant(plant);
                }
                plant.setHasProduct(false);
                return;
            }
        }
        // Harvest Foraging
        if (element instanceof ForagingCropElement foraging) {
            List<Item> dropped = foraging.drop();
            for (Item drop : dropped) {
                currentMap.spawnDroppedItem(drop, selectedTile.tileX * TILE_SIZE, selectedTile.tileY * TILE_SIZE);
            }
            currentMap.removeElement(foraging);
            return;
        }

        // Use item
        if (item instanceof Usable usable) {
            boolean success = usable.use(player, currentMap, selectedTile);
            if (item instanceof Tool tool)
                player.consumeEnergy(tool.getConsumptionEnergy(player, Weather.Sunny, success));
        }
    }

    /**
     * This method calculates which tile is under the mouse and checks if it's in range.
     * It should be called every frame from the GameScreen.
     * @param camera The game's camera, used to unproject coordinates.
     */
    public void updateSelectedTile(OrthographicCamera camera) {
        // 1. Convert screen coordinates (mouse position) to world coordinates.
        Vector3 worldCoordinates = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        int mouseTileX = (int) (worldCoordinates.x / TILE_SIZE);
        int mouseTileY = (int) (worldCoordinates.y / TILE_SIZE);

        // 2. Get the player's current tile coordinates.
        int playerTileX = (int) ((player.playerPosition.x + (float) PLAYER_WIDTH / 2) / TILE_SIZE);
        int playerTileY = (int) (player.playerPosition.y / TILE_SIZE);

        // 3. Check if the mouse is within the 3x3 grid around the player.
        boolean inRange = mouseTileX >= playerTileX - 1 && mouseTileX <= playerTileX + 1 &&
            mouseTileY >= playerTileY - 1 && mouseTileY <= playerTileY + 1;

        if (inRange) {
            this.selectedTile = currentMap.getTile(mouseTileX, mouseTileY);
        } else {
            this.selectedTile = null;
        }
    }
}
