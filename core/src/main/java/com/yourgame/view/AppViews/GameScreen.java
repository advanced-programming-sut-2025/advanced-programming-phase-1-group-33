package com.yourgame.view.AppViews;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.yourgame.Graphics.MenuAssetManager;
import com.yourgame.Graphics.GameAssets.HUDManager;
import com.yourgame.Graphics.GameAssets.clockUIAssetManager;
import com.yourgame.controller.GameController.GameController;
import com.yourgame.model.Animals.FishPackage.Fish;
import com.yourgame.model.Food.FoodAnimation;
import com.yourgame.model.Item.Inventory.Inventory;
import com.yourgame.model.Item.Tools.PoleStage;
import com.yourgame.model.ManuFactor.Artisan.ArtisanMachine;
import com.yourgame.model.Item.Tools.Tool;
import com.yourgame.model.Map.*;
import com.yourgame.model.App;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.model.Map.Store.Store;
import com.yourgame.model.NPC.*;
import com.yourgame.model.UserInfo.Player;
import com.yourgame.model.UserInfo.PlayerState;
import com.yourgame.model.WeatherAndTime.ThunderManager;
import com.yourgame.view.GameViews.*;
import com.yourgame.view.GameViews.ArtisanMenuView.BeeHouseMenuView;
import com.yourgame.view.GameViews.ArtisanMenuView.CharcoalKilnMenuView;
import com.yourgame.view.GameViews.MainMenuView;
import com.yourgame.view.GameViews.ShopMenu.CarpenterMenuView;
import com.yourgame.view.GameViews.ShopMenu.PierreShopMenuView;
import com.yourgame.view.GameViews.ShopMenu.PierreShopSellMenuView;
import com.yourgame.view.GameViews.ShopMenu.StardropSaloonMenuView;

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
    private float faintingTimer;
    private final NPCManager npcManager;

    private OrthogonalTiledMapRenderer mapRenderer;
    private OrthographicCamera camera;

    private SpriteBatch batch;

    // MENUS
    private Image menuIcon;
    public boolean paused = false;
    private InputMultiplexer multiplexer;

    // Fields for Day/Night Cycle
    private Texture nightOverlayTexture;
    private Color ambientLightColor;

    private final ThunderManager thunderManager;

    private RefrigeratorView refrigeratorView;
    private DialogueView dialogueView;

    public GameScreen() {
        super();

        controller = new GameController();
        controller.setView(this);
        player = controller.getPlayer();
        player.addPlayerStuffToObserver(); // Toff Mali Khales :(
        stateTime = 0f;
        npcManager = new NPCManager(controller.getMapManager());

        mapRenderer = new OrthogonalTiledMapRenderer(controller.getCurrentMap().getTiledMap());

        assetManager = GameAssetManager.getInstance();
        clockUI = assetManager.getClockManager();

        thunderManager = App.getGameState().getThunderManager();

        // HUD‌ manager
        hudManager = new HUDManager(HUDStage, clockUI, assetManager, player);

        menuIcon = new Image(new TextureRegion(assetManager.getMenuIcon()));
        menuStage = new Stage(new ScreenViewport());

        refrigeratorView = new RefrigeratorView(player, this);

        for(ArtisanMachine artisanMachine : player.getArtisanMachineManager().getArtisanMachines()) {
            App.getGameState().getGameTime().addHourObserver(artisanMachine);
        }
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

        this.multiplexer = new InputMultiplexer();
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
            if (player.isFaintedToday() && player.getPlayerState() != PlayerState.FAINTING) {
                player.setPlayerState(PlayerState.FAINTING);
                faintingTimer = 0f; // Start the fainting timer
            }

            if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                openMenu("stardrop");
            }

            if(Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
                if(player.playerPosition.x > 850 && player.playerPosition.x < 880
                && player.playerPosition.y > 1080 && player.playerPosition.y < 1090) {
                    playGameSFX("popUp");
                    openMenu("beeHouse");
                }

                else if(player.playerPosition.x > 1460 && player.playerPosition.x < 1480
                    && player.playerPosition.y > 860 && player.playerPosition.y < 870) {
                    playGameSFX("popUp");
                    openMenu("charcoalKiln");
                }
            }

            // Update animation timer
            //stateTime += delta;

            if (player.getPlayerState() == PlayerState.FAINTING) {
                faintingTimer += delta;
                Animation<TextureRegion> faintAnimation = MenuAssetManager.getInstance().getFaintAnimation();

                if (faintAnimation.isAnimationFinished(faintingTimer)) {
                    App.getGameState().getGameTime().advanceDay(1);
                    changeMap(player.getFarmHouse(), "spawn-bed");
                    player.setPlayerState(PlayerState.IDLE);
                }
            } else {
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

                stateTime += delta;

                npcManager.update(delta, controller.getMapManager().getTown(), player);
            }
        }

        handleHudUpdates(delta);
        updateDayNightCycle();

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
        // Render player
        renderPlayer();
        // Thunder
        thunderManager.render(batch);
        // Food Animation
        if (foodAnimation != null) foodAnimation.render(batch);
        if (controller.getCurrentMap().getName().equals("town")) npcManager.render(batch);
        batch.end();

        //check for fainting
        checkFainting();

        // Render Day & Night
        renderOverlay();

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

    public void renderPlayer() {
        TextureRegion currentFrame;
        if (player.getPlayerState() == PlayerState.FAINTING) {
            currentFrame = MenuAssetManager.getInstance().getFaintAnimation().getKeyFrame(faintingTimer, false);
        } else {
            currentFrame = player.walkAnimations[player.direction].getKeyFrame(stateTime, true);
        }
        batch.draw(currentFrame, player.playerPosition.x, player.playerPosition.y);

        // render tool
        if (player.getBackpack().getInventory().getSelectedItem() instanceof Tool tool) {
            batch.draw(tool.getTextureRegion(GameAssetManager.getInstance()), player.playerPosition.x + 6, player.playerPosition.y + 7, 16, 16);
        }
    }

    public void showDialogue(NPC npc, Dialogue dialogue) {
        if (dialogueView != null && dialogueView.hasParent()) {
            return;
        }

        paused = true;

        dialogueView = new DialogueView(npc, dialogue.text(), this);
        dialogueView.setPosition(Gdx.graphics.getWidth() / 2f, 200, Align.center);

        menuStage.addActor(dialogueView);
        Gdx.input.setInputProcessor(menuStage);
        hudManager.showInventory(false);
    }

    public void closeDialogue() {
        if (dialogueView != null) {
            dialogueView.remove();
            dialogueView = null;
        }
        paused = false;
        Gdx.input.setInputProcessor(multiplexer);
        hudManager.showInventory(true);
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
    }

    private void handleInput(float delta) {
        if (player.getPlayerState() == PlayerState.FAINTING) return;

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
            player.setPlayerState(PlayerState.IDLE);
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

            player.setPlayerState(PlayerState.WALKING);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.G)) {
            NPC clickedNpc = npcManager.getNpcAt(camera);
            if (clickedNpc != null && clickedNpc.isPlayerInRange()) {
                Gdx.app.log("GameScreen", "Clicked NPC");
                paused = true;
                Gdx.input.setInputProcessor(new InputMultiplexer(HUDStage, menuStage));
                menuStage.addActor(new GiftMenuView(MenuAssetManager.getInstance().getSkin(3),menuStage,this,clickedNpc));
            }
        }

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            NPC clickedNpc = npcManager.getNpcAt(camera);
            if (clickedNpc != null && clickedNpc.isPlayerInRange()) {
                Dialogue dialogue = clickedNpc.getDialogue(player);
                showDialogue(clickedNpc, dialogue);
            }

            controller.handleInteraction();
            if (controller.getCurrentMap() instanceof Store store) {
                if (store.isPlayerInBuyZone(player)) {
                    openMenu(store.getName());
                }
                else if (store.isPlayerInSellZone(player)) {
                    openMenu("sell");
                }
            }
            else if (controller.getCurrentMap().getName().contains("-house")) {
                MapObject obj = controller.getCurrentMap().getTiledMap().getLayers().get("Interactables")
                    .getObjects().get("fridge");
                if (obj != null) {
                    openMenu("refrigerator");
                }
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            if (!paused) {
                openMenu("main");
            } else {
                closeMenu();
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
            paused = true;
            Gdx.input.setInputProcessor(new InputMultiplexer(HUDStage, menuStage));
            menuStage.addActor(new JournalMenuView(menuStage, this, player));
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            paused = true;
            Gdx.input.setInputProcessor(new InputMultiplexer(HUDStage, menuStage));
            menuStage.addActor(new MapMenuView(menuStage, this, player, controller.getCurrentMap()));
        }

        handleCheatCode();

        handleInventoryInput();
    }

    public void handleCheatCode() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            player.consumeEnergy(10);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.H)) {
            if (!(controller.getCurrentMap() instanceof Farm)) return;
            App.getGameState().getThunderManager().triggerThunderStrike(controller.getCurrentMap());
        }

        // Example: Update weather (cycle through enums with 'W' key)
        if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            // TODO: Need the CheatCode Be Implemented
        }

        // Update day
        if (Gdx.input.isKeyJustPressed(Input.Keys.T)) {
            App.getGameState().getGameTime().advanceDay(1);
        }

        // Update hour
        if (Gdx.input.isKeyJustPressed(Input.Keys.Y)) {
            App.getGameState().getGameTime().advanceMinutes(60);
        }

        // Energy
        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            player.setEnergy(player.getMaxEnergy());
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.U)) {
            player.faint();
        }
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
        } else if (teleport.dest().contains("greenhouse")) {
            if (player.getGreenhouse() == null) return;
            newMap = player.getGreenhouse();
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
        if (currentMap == null || camera == null) {
            return;
        }

        // 1. Get map dimensions in pixels
        float mapWidth = currentMap.getTiledMap().getProperties().get("width", Integer.class) * Tile.TILE_SIZE;
        float mapHeight = currentMap.getTiledMap().getProperties().get("height", Integer.class) * Tile.TILE_SIZE;

        // 2. Get the camera's viewport size in world units (taking zoom into account)
        float cameraHalfWidth = camera.viewportWidth * camera.zoom * 0.5f;
        float cameraHalfHeight = camera.viewportHeight * camera.zoom * 0.5f;

        // 3. Start with the camera centered on the player
        camera.position.set(player.playerPosition.x, player.playerPosition.y, 0);

        // 4. Calculate the boundaries for the camera's center position
        float cameraLeftBoundary = cameraHalfWidth;
        float cameraRightBoundary = mapWidth - cameraHalfWidth;
        float cameraBottomBoundary = cameraHalfHeight;
        float cameraTopBoundary = mapHeight - cameraHalfHeight;

        // 5. On small maps, the right boundary can be less than the left.
        // If the map is smaller than the camera's view, we must lock the camera's center.
        if (mapWidth < camera.viewportWidth * camera.zoom) {
            camera.position.x = mapWidth / 2f;
        } else {
            // Otherwise, clamp the camera's position within the valid range.
            camera.position.x = Math.max(cameraLeftBoundary, Math.min(camera.position.x, cameraRightBoundary));
        }

        if (mapHeight < camera.viewportHeight * camera.zoom) {
            camera.position.y = mapHeight / 2f;
        } else {
            camera.position.y = Math.max(cameraBottomBoundary, Math.min(camera.position.y, cameraTopBoundary));
        }

        // 6. Apply the changes
        camera.update();
    }

    /**
     * Handles updates to the HUD elements based on game state or input.
     */
    private void handleHudUpdates(float delta) {
        hudManager.updateInventory(player.getBackpack().getInventory());
        hudManager.updateBuffs(player.getBuffManager().getActiveBuffs());
        hudManager.updateTime(delta);
        hudManager.updateWeather();
        hudManager.updateSeason();
        hudManager.updateEnergyBar();
        hudManager.updateCoin();
    }

    private void checkFainting() {
        if (player.getEnergy() <= 0) {
            player.faint();
        }

//        if (App.getGameState().getGameTime().getHour() >= 8) {
//            isFainting = true;
//        }
    }

    private void openMenu(String name) {
        paused = true;
        Gdx.input.setInputProcessor(new InputMultiplexer(HUDStage, menuStage));

        switch (name) {
            case "main" -> menuStage.addActor(new MainMenuView(MenuAssetManager.getInstance().getSkin(3),
                menuStage, this));
            case "pierre-store" -> menuStage.addActor(new PierreShopMenuView(MenuAssetManager.getInstance().getSkin(3),
                menuStage, this));
            case "refrigerator" -> menuStage.addActor(refrigeratorView);
            case "sell" -> menuStage.addActor(new PierreShopSellMenuView(MenuAssetManager.getInstance().getSkin(3),
                menuStage, this));
            case "stardrop" -> menuStage.addActor(new StardropSaloonMenuView(MenuAssetManager.getInstance().getSkin(3),
                menuStage,this));
            case "CarpenterShop" -> menuStage.addActor(new CarpenterMenuView(this, menuStage, player));
            case "beeHouse" -> menuStage.addActor(new BeeHouseMenuView(MenuAssetManager.getInstance().getSkin(3),
                menuStage, this));
            case "charcoalKiln" -> menuStage.addActor(new CharcoalKilnMenuView(MenuAssetManager.getInstance().getSkin(3),
                menuStage, this));
        }
    }

    public void startFishingGame(Fish fish, PoleStage fishingPole){
        paused = true;
        Gdx.input.setInputProcessor(new InputMultiplexer(HUDStage, menuStage));

        menuStage.addActor(new FishingGameMenuView(MenuAssetManager.getInstance().getSkin(3),menuStage,this,
        fish,fishingPole));
    }

    public void closeMenu() {
        paused = false;
        Gdx.input.setInputProcessor(multiplexer);
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

    public GameController getController() {
        return controller;
    }

    public HUDManager getHUDManager() {
        return hudManager;
    }

    public GameController getGameController() {
        return controller;
    }
}
