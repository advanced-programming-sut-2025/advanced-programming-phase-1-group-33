package com.yourgame.view.AppViews;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Color;
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
import com.yourgame.Graphics.GameAssets.HUDManager;
import com.yourgame.Graphics.GameAssets.clockUIAssetManager;
import com.yourgame.controller.GameController.GameController;
import com.yourgame.model.Food.Food;
import com.yourgame.model.Food.FoodAnimation;
import com.yourgame.model.Map.*;
import com.yourgame.model.App;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.model.UserInfo.Player;
import com.yourgame.model.WeatherAndTime.ThunderManager;
import com.yourgame.view.GameViews.JournalMenuView;
import com.yourgame.view.GameViews.MainMenuView;
import com.yourgame.view.GameViews.MapMenuView;
import com.yourgame.view.GameViews.PierreShopMenuView;

import java.awt.*;

import static com.yourgame.model.UserInfo.Player.*;

public class GameScreen extends GameBaseScreen {
    private GameAssetManager assetManager;

    // ==HUD==
    private clockUIAssetManager clockUI;
    private ImageButton clockImg;
    private HUDManager hudManager;

    // ==GAME==
    private final GameController controller;
    private final Player player;
    private float stateTime;

    private OrthogonalTiledMapRenderer mapRenderer;
    private OrthographicCamera camera;

    private SpriteBatch batch;

    // MENUS
    private Image menuIcon;
    public boolean paused = false;

    // Fields for Day/Night Cycle
    private Texture nightOverlayTexture;
    private Color ambientLightColor;

    // Pierre's shop
    private PierreShopMenuView pierreShopMenuView;


    private final ThunderManager thunderManager;

    public GameScreen() {
        super();

        controller = new GameController();
        player = controller.getPlayer();
        App.getGameState().getGameTime().addHourObserver(player.getBuffManager()); // Toff Mali Khales :(
        stateTime = 0f;

        mapRenderer = new OrthogonalTiledMapRenderer(controller.getCurrentMap().getTiledMap());

        assetManager = GameAssetManager.getInstance();
        clockUI = assetManager.getClockManager();

        thunderManager = App.getGameState().getThunderManager();

        // HUD‌ manager
        hudManager = new HUDManager(HUDStage, clockUI, assetManager, player);

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

        // placing the menu Icon
        Table menuTable = new Table();
        menuTable.setFillParent(true);
        menuTable.left().top();
        menuTable.add(menuIcon).width(80).height(80).pad(20);
        HUDStage.addActor(menuTable);

        menuIcon.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                openMenu("main");
            }
        });

        batch = new SpriteBatch();

        // Create a 1x1 black pixel texture for the overlay
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.BLACK); // Use black for a neutral darkness
        pixmap.fill();
        nightOverlayTexture = new Texture(pixmap);
        pixmap.dispose();

        // This color object will be modified to change the overlay's transparency
        ambientLightColor = new Color(0, 0, 0, 0);

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
        FoodAnimation foodAnimation = GameAssetManager.getInstance().getFoodAnimation();

        if (!paused) {
            controller.updateSelectedTile(camera);
            handleInput(delta);
            checkForTeleport();
            handleHudUpdates(delta);
            controller.updateDroppedItems(delta);
            updateDayNightCycle();
            thunderManager.update(delta);

            if (foodAnimation != null) {
                boolean isFinished = foodAnimation.update(delta);
                if (isFinished) {
                    GameAssetManager.getInstance().setFoodAnimation(null);
                }
            }

            //handle pierre's shop menu
            if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                openMenu("pierreShop");
            }

            // Update animation timer
            stateTime += delta;
        }

        // Clear screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.position.set(player.playerPosition.x, player.playerPosition.y, 0);
        clampCameraToMap();
        camera.update();

        // Render map
        mapRenderer.setView(camera);
        mapRenderer.render();

        // == Render On-Map ==
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        // Map Elements
        controller.renderMapObjects(assetManager, batch);
        // Player
        TextureRegion currentFrame = player.walkAnimations[player.direction].getKeyFrame(stateTime, true);
        batch.draw(currentFrame, player.playerPosition.x, player.playerPosition.y);
        // Thunder
        thunderManager.render(batch);
        // Food Animation
        if (foodAnimation != null) foodAnimation.render(batch);
        batch.end();

        // Render Day & Night
        renderOverlay();

        hudManager.updateInventory(player.getBackpack().getInventory());
        hudManager.updateBuffs(player.getBuffManager().getActiveBuffs());
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
        thunderManager.dispose();
        batch.dispose();
    }

    private void renderOverlay() {
        batch.setProjectionMatrix(HUDStage.getCamera().combined);
        batch.begin();
        batch.setColor(ambientLightColor);
        batch.draw(nightOverlayTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.setColor(Color.WHITE);
        batch.end();
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
                openMenu("main");
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
        player.playerVelocity.setZero();

        float speed = SPEED;
        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))
            speed *= 1.5f;

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.playerVelocity.x = -speed;
            player.direction = 3;
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.playerVelocity.x = speed;
            player.direction = 1;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            player.playerVelocity.y = speed;
            player.direction = 2;
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            player.playerVelocity.y = -speed;
            player.direction = 0;
        }

        if (player.playerVelocity.isZero()) {
            stateTime = 0f; // Pause animation when idle
        } else {
            // Store original position
            Vector2 oldPos = new Vector2(player.playerPosition);

            // Move on X axis and check for collisions
            player.playerPosition.x += player.playerVelocity.x * delta;
            if (controller.isBlocked(player.playerPosition.x, oldPos.y)) {
                player.playerPosition.x = oldPos.x; // Revert if collision
            }

            // Move on Y axis and check for collisions
            player.playerPosition.y += player.playerVelocity.y * delta;
            if (controller.isBlocked(player.playerPosition.x, player.playerPosition.y)) {
                player.playerPosition.y = oldPos.y; // Revert if collision
            }
        }

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            controller.handleInteraction();
            if (controller.getCurrentMap() instanceof Store store) {
                if (store.isPlayerInTradeZone(player)) {
                    Gdx.app.log("Store", "Player is in trade zone");
                }
            }
        }

        // --- Inventory Selection Input ---
        handleInventoryInput();
    }

    public void changeMap(Map newMap, String spawnName) {
        if (newMap == null)
            return;

        controller.setCurrentMap(newMap);
        mapRenderer.setMap(newMap.getTiledMap());
        player.playerPosition.set(newMap.getSpawnPoint(spawnName));
        // Reset camera to new position immediately
        camera.position.set(player.playerPosition.x, player.playerPosition.y, 0);
        clampCameraToMap();
        camera.update();
    }

    private void checkForTeleport() {
        // Check the tile at the center of the player's feet
        float checkX = player.playerPosition.x + (MenuAssetManager.PLAYER_WIDTH / 2f);
        float checkY = player.playerPosition.y + 4; // A bit above the bottom edge

        Teleport teleport = controller.getCurrentMap().getTeleport(checkX, checkY);
        if (teleport == null)
            return;

        // Find the correct map from the MapManager based on the destination string
        Map newMap;
        if (teleport.dest().equalsIgnoreCase("town")) {
            newMap = controller.getMapManager().getTown();
        } else if (teleport.dest().contains("farm")) {
            newMap = controller.getMapManager().getFarm(player);
        } else if (teleport.dest().contains("house")) {
            newMap = controller.getMapManager().getHouse(player);
        } else {
            // If it's not a special map, assume it's a building
            newMap = controller.getMapManager().getBuilding(teleport.dest());
        }

        if (newMap != null) {
            changeMap(newMap, teleport.spawnName());
        }
    }

    private void clampCameraToMap() {
        Map currentMap = controller.getCurrentMap();
        float mapWidth = currentMap.getTiledMap().getProperties().get("width", Integer.class) * Tile.TILE_SIZE;
        float mapHeight = currentMap.getTiledMap().getProperties().get("height", Integer.class) * Tile.TILE_SIZE;

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

        if (Gdx.input.isKeyJustPressed(Input.Keys.H)) {
            if (!(controller.getCurrentMap() instanceof Farm)) return;
            App.getGameState().getThunderManager().triggerThunderStrike(controller.getCurrentMap());
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
            App.getGameState().getGameTime().advanceDay(1);
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

    private void openMenu(String name) {
        paused = true;
        Gdx.input.setInputProcessor(new InputMultiplexer(HUDStage, menuStage));

        switch (name) {
            case "main" -> menuStage.addActor(new MainMenuView(MenuAssetManager.getInstance().getSkin(3),
                menuStage, this));
            case "pierreShop" -> menuStage.addActor(new PierreShopMenuView(MenuAssetManager.getInstance().getSkin(3),
                menuStage, this));

        }
    }

    public void closeMenu() {
        paused = false;
        Gdx.input.setInputProcessor(HUDStage);
    }

    private void updateDayNightCycle() {
        int hour = App.getGameState().getGameTime().getHour();
        int minute = App.getGameState().getGameTime().getMinutes();

        float alpha = 0f; // Default to fully transparent (daytime)

        if (hour < 6 || hour >= 20) { // Deep night (8 PM to 6 AM)
            alpha = 0.7f; // Very dark
        } else if (hour >= 17 && hour < 20) { // Evening (5 PM to 8 PM)
            // Smoothly fades to dark
            float totalMinutesInEvening = 3 * 60;
            float elapsedMinutes = (hour - 17) * 60 + minute;
            alpha = (elapsedMinutes / totalMinutesInEvening) * 0.7f;
        }

        // Set the alpha (transparency) of our color object
        ambientLightColor.a = alpha;
    }

    public Player getPlayer() {
        return player;
    }
}
