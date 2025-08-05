package com.yourgame.view.AppViews;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PointMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.yourgame.Graphics.MenuAssetManager;
import com.yourgame.Main;
import com.yourgame.Graphics.GameAssets.HUDManager;
import com.yourgame.Graphics.GameAssets.clockUIAssetManager;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.model.App;

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
    private TiledMap map;
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

    }

    @Override
    public void show() {
        assetManager.loadAllAssets();

        // Initialize HUD with initial states
        hudManager.createHUD(currentWeather, currentSeason, currentEnergyPhase);

        // Load Tiled map
        map = new TmxMapLoader().load("Game/Map/standard-farm.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);

        // Set up camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.zoom = 0.4f;

        // Load player sprite sheet
        walkAnimations = MenuAssetManager.getInstance().getWalkAnimation(App.getCurrentUser().getAvatar());

        stateTime = 0f;
        playerPosition = getSpawnPoint("spawn-right");
        playerVelocity = new Vector2(0, 0);
        direction = 0;
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        handleInput(delta);
        handleHudUpdates();

        camera.position.set(playerPosition.x, playerPosition.y, 0);
        clampCameraToMap();
        camera.update();

        // Update animation timer
        stateTime += delta;

        // Render map
        mapRenderer.setView(camera);
        mapRenderer.render();

        // Render player
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        TextureRegion currentFrame = walkAnimations[direction].getKeyFrame(stateTime, true);
        batch.draw(currentFrame, playerPosition.x, playerPosition.y);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        HUDStage.getViewport().update(width, height, true);
        camera.setToOrtho(false, width, height);
    }

    @Override
    public void dispose() {
        assetManager.dispose();
        map.dispose();
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

        // If idle, pause animation
        if (playerVelocity.isZero()) {
            stateTime = 0f;
        }

        Vector2 newPos = new Vector2(playerPosition).add(playerVelocity.x * delta, playerVelocity.y * delta);
        if (!isTileBlocked(newPos.x, newPos.y)) {
            playerPosition.set(newPos);
        }

        // --- Inventory Selection Input ---
        handleInventoryInput();
    }

    boolean isTileBlocked(float x, float y) {
        MapObjects objects = map.getLayers().get("Collisions").getObjects();

        for (MapObject object : objects) {
            Shape2D shape;

            if (object instanceof RectangleMapObject) {
                shape = ((RectangleMapObject) object).getRectangle();
            } else if (object instanceof PolygonMapObject) {
                Polygon poly = ((PolygonMapObject) object).getPolygon();
                if (poly.contains(x, y))
                    return true;
                continue;
            } else {
                continue;
            }

            if (shape instanceof Rectangle) {
                Rectangle rect = (Rectangle) shape;
                Rectangle playerBounds = new Rectangle(x, y, MenuAssetManager.PLAYER_WIDTH, MenuAssetManager.PLAYER_HEIGHT);
                if (playerBounds.overlaps(rect)) {
                    return true;
                }
            }
        }

        return false;
    }

    private void clampCameraToMap() {
        float tileWidth = map.getProperties().get("tilewidth", Integer.class);
        float tileHeight = map.getProperties().get("tileheight", Integer.class);
        float mapWidth = map.getProperties().get("width", Integer.class) * tileWidth;
        float mapHeight = map.getProperties().get("height", Integer.class) * tileHeight;

        float cameraHalfWidth = camera.viewportWidth * camera.zoom * 0.5f;
        float cameraHalfHeight = camera.viewportHeight * camera.zoom * 0.5f;

        // Clamp X
        camera.position.x = Math.max(cameraHalfWidth, camera.position.x);
        camera.position.x = Math.min(mapWidth - cameraHalfWidth, camera.position.x);

        // Clamp Y
        camera.position.y = Math.max(cameraHalfHeight, camera.position.y);
        camera.position.y = Math.min(mapHeight - cameraHalfHeight, camera.position.y);
    }

    private Vector2 getSpawnPoint(String spawnName) {
        MapObject object = map.getLayers().get("SpawnPoints").getObjects().get(spawnName);

        if (object instanceof PointMapObject) {
            return ((PointMapObject) object).getPoint();
        }

        if (object instanceof RectangleMapObject) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            return new Vector2(rect.x, rect.y);
        }

        return new Vector2(250, 250);
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
