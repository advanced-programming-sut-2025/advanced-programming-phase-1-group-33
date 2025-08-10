package com.yourgame.view.AppViews;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.yourgame.Graphics.MenuAssetManager;
import com.yourgame.Main;
import com.yourgame.Graphics.GameAssets.HUDManager;
import com.yourgame.Graphics.GameAssets.clockUIAssetManager;
import com.yourgame.model.Item.Item;
import com.yourgame.model.Item.Tools.Tool;
import com.yourgame.model.Item.Usable;
import com.yourgame.model.Map.*;
import com.yourgame.model.App;
import com.yourgame.model.GameState;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.model.UserInfo.Player;
import com.yourgame.model.WeatherAndTime.Season;
import com.yourgame.view.GameViews.JournalMenuView;
import com.yourgame.view.GameViews.MainMenuView;
import com.yourgame.view.GameViews.MapMenuView;
import com.yourgame.model.WeatherAndTime.Weather;

import java.util.ArrayList;
import java.util.List;

public class GameScreen extends GameBaseScreen {
    private Main game;
    private GameAssetManager assetManager;

    // ==HUD==
    private clockUIAssetManager clockUI;
    private ImageButton clockImg;
    private HUDManager hudManager;

    // ==GAME==
    private MapManager mapManager;
    private Player player;
    private ArrayList<Player> players = new ArrayList<>();
    private Map currentMap;
    private GameState gameState;

    private OrthogonalTiledMapRenderer mapRenderer;
    private OrthographicCamera camera;

    private Texture playerSheet;
    private Animation<TextureRegion>[] walkAnimations;
    private float stateTime;
    private Vector2 playerPosition;
    private Vector2 playerVelocity;
    private int direction; // 0=Down, 1=Right, 2=Up, 3=Left

    private static final int PLAYER_WIDTH = 16;
    private static final int PLAYER_HEIGHT = 32;
    private static final float SPEED = 150f;

    private SpriteBatch batch;

    // MENUS
    private Image menuIcon;
    public boolean paused = false;
    private MainMenuView mainMenuView;

    public GameScreen() {
        this.player = Player.guest();
        this.mapManager = new MapManager(List.of(player));
        this.mapRenderer = new OrthogonalTiledMapRenderer(mapManager.getPlayersCurrentMap(player).getTiledMap());
        this.currentMap = mapManager.getPlayersCurrentMap(player);

        this.game = Main.getMain();
        this.assetManager = new GameAssetManager();
        this.clockUI = assetManager.getClockManager();
        this.gameState = new GameState(players);
        App.setGameState(gameState);

        // HUD‌ manager
        this.hudManager = new HUDManager(HUDStage, clockUI, assetManager, this.player);

        menuIcon = new Image(new TextureRegion(assetManager.getMenuIcon()));
        menuStage = new Stage(new ScreenViewport());
    }

    @Override
    public void show() {
        assetManager.loadAllAssets();

        // Initialize HUD with initial states
        hudManager.createHUD();

        // Set up camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.zoom = 0.4f;

        // Load player sprite sheet
        playerSheet = new Texture("Game/Player/player.png");
        TextureRegion[][] frames = TextureRegion.split(playerSheet, PLAYER_WIDTH, PLAYER_HEIGHT);

        walkAnimations = new Animation[4]; // down, left, right, up

        walkAnimations[0] = new Animation<>(0.2f, frames[0]); // Down
        walkAnimations[1] = new Animation<>(0.2f, frames[1]); // Right
        walkAnimations[2] = new Animation<>(0.2f, frames[2]); // Up
        walkAnimations[3] = new Animation<>(0.2f, frames[3]); // Left

        stateTime = 0f;
        playerPosition = currentMap.getSpawnPoint();
        playerVelocity = new Vector2();
        direction = 0;

        // placing the menu Icon
        Table menuTable = new Table();
        menuTable.setFillParent(true);
        menuTable.left().top();
        menuTable.add(menuIcon).width(80).height(80).pad(20);
        HUDStage.addActor(menuTable);

        menuIcon.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                openMenu();
            }
        });

        batch = new SpriteBatch();

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(HUDStage);
        multiplexer.addProcessor(new InputAdapter() {
            @Override
            public boolean scrolled(float amountX, float amountY) {
                int direction = (int) amountY;

                int currentSlot = hudManager.getSelectedSlotIndex();

                int newIndex = currentSlot + direction;

                if (newIndex < 0) {
                    newIndex = 11; // Wrap from 0 to 11
                } else if (newIndex > 11) {
                    newIndex = 0; // Wrap from 11 to 0
                }

                hudManager.selectSlot(newIndex);

                return true;
            }
        });
        Gdx.input.setInputProcessor(multiplexer);
    }

    @Override
    public void render(float delta) {
        if (!paused) {
            handleInput(delta);
            checkForTeleport();
            handleHudUpdates(delta);

            // Update animation timer
            stateTime += delta;
        }

        // Clear screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.position.set(playerPosition.x, playerPosition.y, 0);
        clampCameraToMap();
        camera.update();

        // Render map
        mapRenderer.setView(camera);
        mapRenderer.render();

        // Render player
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        TextureRegion currentFrame = walkAnimations[direction].getKeyFrame(stateTime, true);

        Season gameSeason = App.getGameState().getGameTime().getSeason();
        for (MapElement element : currentMap.getMapElements()) {
            TextureRegion texture = element.getTexture(assetManager, gameSeason);
            if (texture != null) {
                java.awt.Rectangle bounds = element.getPixelBounds();
                batch.draw(texture, bounds.x, bounds.y, bounds.width, bounds.height);
            }
        }

        batch.draw(currentFrame, playerPosition.x, playerPosition.y);
        batch.end();

        hudManager.updateInventory(player.getBackpack().getInventory());
        super.render(delta);
    }

    @Override
    public void resize(int width, int height) {
        HUDStage.getViewport().update(width, height, true);
        camera.setToOrtho(false, width, height);
    }

    @Override
    public void pause() {
        // Pause music if the game is paused
        if (backgroundMusic.isPlaying()) {
            backgroundMusic.pause();
        }
    }

    @Override
    public void resume() {
        // Resume music if the game resumes and it was playing before
        if (!App.isMusicMuted() && !backgroundMusic.isPlaying()) {
            backgroundMusic.play();
        }
    }

    @Override
    public void hide() {
        // Stop music when the screen is hidden
        stopBackgroundMusic();
    }

    @Override
    public void dispose() {
        HUDStage.dispose();
        assetManager.dispose();
        backgroundMusic.dispose(); // Dispose the music
        if (clickSound != null) { // Dispose SFX if loaded
            clickSound.dispose();
        }
        mapRenderer.dispose();
        playerSheet.dispose();
        batch.dispose();
    }

    // Methods for music control, similar to MenuBaseScreen
    public void playBackgroundMusic() {
        if (App.isMusicMuted()) {
            return;
        }

        backgroundMusic.setLooping(true);
        backgroundMusic.play();
    }

    public void stopBackgroundMusic() {
        backgroundMusic.stop();
    }

    // Optional: if you want SFX in GameScreen
    public void playGameSFX(String string) {
        switch (string) {
            case "click" -> clickSound.play();
            // Add other game SFX cases here
        }
    }

    /**
     * New method to handle key presses for selecting an inventory slot.
     */
    private void handleInventoryInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1))
            hudManager.selectSlot(0);
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2))
            hudManager.selectSlot(1);
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3))
            hudManager.selectSlot(2);
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_4))
            hudManager.selectSlot(3);
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_5))
            hudManager.selectSlot(4);
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_6))
            hudManager.selectSlot(5);
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_7))
            hudManager.selectSlot(6);
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_8))
            hudManager.selectSlot(7);
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_9))
            hudManager.selectSlot(8);
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_0))
            hudManager.selectSlot(9);
        if (Gdx.input.isKeyJustPressed(Input.Keys.MINUS))
            hudManager.selectSlot(10);
        if (Gdx.input.isKeyJustPressed(Input.Keys.EQUALS))
            hudManager.selectSlot(11);
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            if (paused == false) {
                openMenu();
            } else {
                closeMenu();
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
            paused = true;
            Gdx.input.setInputProcessor(new InputMultiplexer(HUDStage, menuStage));
            menuStage.addActor(new JournalMenuView(MenuAssetManager.getInstance().getSkin(3), menuStage, this));
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            paused = true;
            Gdx.input.setInputProcessor(new InputMultiplexer(HUDStage, menuStage));
            menuStage.addActor(new MapMenuView(MenuAssetManager.getInstance().getSkin(3), menuStage, this));
        }
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
            Item item = player.getBackpack().getInventory().getSelectedItem();
            if (item instanceof Usable usable) {
                boolean success = usable.use(player, currentMap, getTileInFront());
                if (item instanceof Tool tool)
                    player.consumeEnergy(tool.getConsumptionEnergy(player, Weather.Sunny, success));
            }
        }

        // --- Inventory Selection Input ---
        handleInventoryInput();
    }

    private Tile getTileInFront() {
        // Calculate the center of the player's collision box
        float playerCenterX = playerPosition.x + (PLAYER_WIDTH / 2f);
        float playerCenterY = playerPosition.y + (PLAYER_HEIGHT / 4f); // Center of the feet/lower body

        // Determine the tile the player is currently on
        int tileX = (int) (playerCenterX / Tile.TILE_SIZE);
        int tileY = (int) (playerCenterY / Tile.TILE_SIZE);

        // Get the tile in front based on direction
        return switch (direction) {
            case 0 -> currentMap.getTile(tileX, tileY - 1); // Down
            case 1 -> currentMap.getTile(tileX + 1, tileY); // Right
            case 2 -> currentMap.getTile(tileX, tileY + 1); // Up
            case 3 -> currentMap.getTile(tileX - 1, tileY); // Left
            default -> currentMap.getTile(tileX, tileY);   // Should not happen
        };
    }

    private boolean isBlocked(float x, float y) {
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

    private void changeMap(Map newMap, String spawnName) {
        if (newMap == null)
            return;

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
        if (teleport == null)
            return;

        // Find the correct map from the MapManager based on the destination string
        Map newMap;
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
    private void handleHudUpdates(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            player.consumeEnergy(10);
        }

        // Example: Update weather (cycle through enums with 'W' key)
        if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            // TODO: Need the CheetCode Be Implemented
            // HUDManager.weatherTypeButton[] weathers =
            // HUDManager.weatherTypeButton.values();
            // int nextIndex = (currentWeather.ordinal() + 1) % weathers.length;
            // currentWeather = weathers[nextIndex];
            // hudManager.updateWeather(currentWeather);
        }

        // Example: Update season (cycle through enums with 'R' key)
        if (Gdx.input.isKeyJustPressed(Input.Keys.T)) {
            // TODO: need the cheat Code Be implemented
            App.getGameState().getGameTime().advanceMinutes(20);
            // HUDManager.seasonTypeButton[] seasons = HUDManager.seasonTypeButton.values();
            // int nextIndex = (currentSeason.ordinal() + 1) % seasons.length;
            // currentSeason = seasons[nextIndex];
            // hudManager.updateSeason(currentSeason);
        }

        hudManager.updateTime(delta);
        hudManager.updateWeather();
        hudManager.updateSeason();
        hudManager.updateEnergyBar();
    }

    private void openMenu() {
        paused = true;
        Gdx.input.setInputProcessor(new InputMultiplexer(HUDStage, menuStage));
        menuStage.addActor(mainMenuView = new MainMenuView(MenuAssetManager.getInstance().getSkin(3), menuStage, this));
    }

    public void closeMenu() {
        paused = false;
        Gdx.input.setInputProcessor(HUDStage);
    }
}
