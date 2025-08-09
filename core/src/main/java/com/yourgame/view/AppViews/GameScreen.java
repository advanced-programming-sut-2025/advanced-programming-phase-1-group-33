package com.yourgame.view.AppViews;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.yourgame.Graphics.Map.*;
import com.yourgame.Graphics.MenuAssetManager;
import com.yourgame.Main;
import com.yourgame.Graphics.GameAssets.HUDManager;
import com.yourgame.Graphics.GameAssets.clockUIAssetManager;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.model.App;
import com.yourgame.model.Item.Tools.Axe;
import com.yourgame.model.Item.Tools.Pickaxe;
import com.yourgame.model.UserInfo.Player;
import com.yourgame.model.WeatherAndTime.Season;
import com.yourgame.model.WeatherAndTime.Weather;

import java.util.List;

public class GameScreen extends GameBaseScreen {
    private final Main game;
    private final GameAssetManager assetManager;

    // ==HUD==
    private clockUIAssetManager clockUI;
    private ImageButton clockImg;
    private HUDManager hudManager;
    private int currentEnergyPhase;
    private HUDManager.weatherTypeButton currentWeather; // New variable for dynamic weather
    private HUDManager.seasonTypeButton currentSeason;

    // ==GAME==
    private MapManager mapManager;
    private Player player;
    private MapData currentMap;

    private OrthogonalTiledMapRenderer mapRenderer;
    private OrthographicCamera camera;

    private Animation<TextureRegion>[] walkAnimations;
    private float stateTime;
    private Vector2 playerPosition;
    private Vector2 playerVelocity;
    private int direction; // 0=Down, 1=Right, 2=Up, 3=Left

    private static final float SPEED = 150f;

    public GameScreen() {
        this.game = Main.getMain();
        this.assetManager = GameAssetManager.getInstance();
        this.clockUI = assetManager.getClockManager();
        this.hudManager = new HUDManager(HUDStage, clockUI, assetManager);
        this.currentEnergyPhase = 4;
        this.currentWeather = HUDManager.weatherTypeButton.Sunny; // Initial weather
        this.currentSeason = HUDManager.seasonTypeButton.Spring;

        // Load background music and SFX directly here or through AssetManager
        backgroundMusic = MenuAssetManager.getInstance().getMusic(); // Or
                                                                     // Gdx.audio.newMusic(Gdx.files.internal("path/to/your/game_music.mp3"));
        clickSound = MenuAssetManager.getInstance().getSounds("click"); // Example SFX

        player = Player.guest();
        mapManager = new MapManager(List.of(player));
        mapRenderer = new OrthogonalTiledMapRenderer(mapManager.getPlayersCurrentMap(player).getTiledMap());
        currentMap = mapManager.getPlayersCurrentMap(player);
    }

    @Override
    public void show() {
        assetManager.loadAllAssets();

        // Initialize HUD with initial states
        hudManager.createHUD(currentWeather, currentSeason, currentEnergyPhase);

        // Set up camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.zoom = 0.4f;

        // Load player sprite sheet
        walkAnimations = MenuAssetManager.getInstance().getWalkAnimation(App.getCurrentUser().getAvatar());

        stateTime = 0f;
        playerPosition = currentMap.getSpawnPoint();
        playerVelocity = new Vector2();
        direction = 0;
    }

    @Override
    public void render(float delta) {
        handleInput(delta);
        checkForTeleport();
        handleHudUpdates();

        // Clear screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.position.set(playerPosition.x, playerPosition.y, 0);
        clampCameraToMap();
        camera.update();

        // Update animation timer
        stateTime += delta;

        // Render map
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mapRenderer.setView(camera);
        mapRenderer.render();

        // Render player and Map Elements
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        Season gameSeason = Season.Spring;
        for (MapElement element : currentMap.getMapElements()) {
            TextureRegion texture = element.getTexture(assetManager, gameSeason);
            if (texture != null) {
                java.awt.Rectangle bounds = element.getPixelBounds();
                batch.draw(texture, bounds.x, bounds.y, bounds.width, bounds.height);
            }
        }

        TextureRegion currentFrame = walkAnimations[direction].getKeyFrame(stateTime, true);
        batch.draw(currentFrame, playerPosition.x, playerPosition.y);
        batch.end();

        super.render(delta);
    }

    @Override
    public void resize(int width, int height) {
        HUDStage.getViewport().update(width, height, true);
        camera.setToOrtho(false, width, height);
    }

    @Override
    public void dispose() {
        assetManager.dispose();
        mapRenderer.dispose();
        batch.dispose();
    }

        /**
     * New method to handle key presses for selecting an inventory slot.
     */
    private void handleInventoryInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) hudManager.selectSlot(0);
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) hudManager.selectSlot(1);
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) hudManager.selectSlot(2);
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_4)) hudManager.selectSlot(3);
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_5)) hudManager.selectSlot(4);
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_6)) hudManager.selectSlot(5);
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_7)) hudManager.selectSlot(6);
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_8)) hudManager.selectSlot(7);
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_9)) hudManager.selectSlot(8);
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_0)) hudManager.selectSlot(9);
        if (Gdx.input.isKeyJustPressed(Input.Keys.MINUS)) hudManager.selectSlot(10);
        if (Gdx.input.isKeyJustPressed(Input.Keys.EQUALS)) hudManager.selectSlot(11);
    }

    private void handleInput(float delta) {
        playerVelocity.setZero();

        float speed = SPEED;
        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))
            speed *= 1.5f;

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            playerVelocity.x = -speed;
            direction = 3;
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            playerVelocity.x = speed;
            direction = 1;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            playerVelocity.y = speed;
            direction = 2;
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            playerVelocity.y = -speed;
            direction = 0;
        }

        if (playerVelocity.isZero()) {
            stateTime = 0f; // Pause animation when idle
        } else {
            // Store original position
            Vector2 oldPos = new Vector2(playerPosition);

            // Move on X axis and check for collisions
            playerPosition.x += playerVelocity.x * delta;
            if (isBlocked(playerPosition.x, oldPos.y)) {
                playerPosition.x = oldPos.x; // Revert if collision
            }

            // Move on Y axis and check for collisions
            playerPosition.y += playerVelocity.y * delta;
            if (isBlocked(playerPosition.x, playerPosition.y)) {
                playerPosition.y = oldPos.y; // Revert if collision
            }
        }

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            TileData tile = getTileInFront();
            boolean success = axe.use(player, currentMap, tile);
            int energy = axe.getConsumptionEnergy(player, Weather.Sunny, success);
            Gdx.app.error("ToolUse", "energy: " + energy);
        }

        // --- Inventory Selection Input ---
        handleInventoryInput();
    }
    Pickaxe axe = new Pickaxe();
    private TileData getTileInFront() {
        int tileX = (int) (playerPosition.x / TileData.TILE_SIZE);
        int tileY = (int) (playerPosition.y / TileData.TILE_SIZE);
        return switch (direction) {
            case 0 -> currentMap.getTile(tileX, tileY - 1);
            case 1 -> currentMap.getTile(tileX + 1, tileY);
            case 2 -> currentMap.getTile(tileX, tileY + 1);
            case 3 -> currentMap.getTile(tileX - 1, tileY);
            default -> currentMap.getTile(tileX, tileY);
        };
    }

    private boolean isBlocked(float x, float y) {
        // Define a smaller collision box at the player's feet for better feel
        float boxX = x + 2; // small horizontal offset
        float boxY = y;
        float boxWidth = MenuAssetManager.PLAYER_WIDTH - 4;
        float boxHeight = MenuAssetManager.PLAYER_HEIGHT / 2f; // Check only the lower half of the player

        // Check the four corners of the player's collision box
        if (currentMap.isTileBlocked(boxX, boxY)) return true;
        if (currentMap.isTileBlocked(boxX + boxWidth, boxY)) return true;
        if (currentMap.isTileBlocked(boxX, boxY + boxHeight)) return true;
        if (currentMap.isTileBlocked(boxX + boxWidth, boxY + boxHeight)) return true;

        return false;
    }

    private void changeMap(MapData newMap, String spawnName) {
        if (newMap == null) return;

        this.currentMap = newMap;
        this.mapRenderer.setMap(currentMap.getTiledMap());
        this.playerPosition.set(currentMap.getSpawnPoint(spawnName));
        // Reset camera to new position immediately
        camera.position.set(playerPosition.x, playerPosition.y, 0);
        clampCameraToMap();
        camera.update();
    }

    private void checkForTeleport() {
        // Check the tile at the center of the player's feet
        float checkX = playerPosition.x + (MenuAssetManager.PLAYER_WIDTH / 2f);
        float checkY = playerPosition.y + 4; // A bit above the bottom edge

        Teleport teleport = currentMap.getTeleport(checkX, checkY);
        if (teleport == null) return;

        // Find the correct map from the MapManager based on the destination string
        MapData newMap;
        if (teleport.dest().equalsIgnoreCase("town")) {
            newMap = mapManager.getTown();
        } else if (teleport.dest().contains("farm")) {
            newMap = mapManager.getFarm(player);
        } else if (teleport.dest().contains("house")) {
            newMap = mapManager.getHouse(player);
        } else {
            // If it's not a special map, assume it's a building
            newMap = mapManager.getBuilding(teleport.dest());
        }

        if (newMap != null) {
            changeMap(newMap, teleport.spawnName());
        }
    }

    private void clampCameraToMap() {
        float tileWidth = currentMap.getTiledMap().getProperties().get("tilewidth", Integer.class);
        float tileHeight = currentMap.getTiledMap().getProperties().get("tileheight", Integer.class);
        float mapWidth = currentMap.getTiledMap().getProperties().get("width", Integer.class) * tileWidth;
        float mapHeight = currentMap.getTiledMap().getProperties().get("height", Integer.class) * tileHeight;

        float cameraHalfWidth = camera.viewportWidth * camera.zoom * 0.5f;
        float cameraHalfHeight = camera.viewportHeight * camera.zoom * 0.5f;

        // Clamp X
        camera.position.x = Math.max(cameraHalfWidth, camera.position.x);
        camera.position.x = Math.min(mapWidth - cameraHalfWidth, camera.position.x);

        // Clamp Y
        camera.position.y = Math.max(cameraHalfHeight, camera.position.y);
        camera.position.y = Math.min(mapHeight - cameraHalfHeight, camera.position.y);
    }

     /**
     * Handles updates to the HUD elements based on game state or input.
     * This is a good practice to separate HUD logic from main rendering.
     */
    private void handleHudUpdates() {
        // Example: Update energy bar
        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            currentEnergyPhase--;
            if (currentEnergyPhase < 0) {
                currentEnergyPhase = 4;
            }
            hudManager.updateEnergyBar(currentEnergyPhase);
        }

        // Example: Update weather (cycle through enums with 'W' key)
        if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            HUDManager.weatherTypeButton[] weathers = HUDManager.weatherTypeButton.values();
            int nextIndex = (currentWeather.ordinal() + 1) % weathers.length;
            currentWeather = weathers[nextIndex];
            hudManager.updateWeather(currentWeather);
        }

        // Example: Update season (cycle through enums with 'R' key)
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            HUDManager.seasonTypeButton[] seasons = HUDManager.seasonTypeButton.values();
            int nextIndex = (currentSeason.ordinal() + 1) % seasons.length;
            currentSeason = seasons[nextIndex];
            hudManager.updateSeason(currentSeason);
        }
    }

}
