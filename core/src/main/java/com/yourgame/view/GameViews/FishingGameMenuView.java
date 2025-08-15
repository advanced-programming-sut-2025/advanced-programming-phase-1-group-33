package com.yourgame.view.GameViews;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.model.Animals.FishPackage.Fish;
import com.yourgame.model.Animals.FishPackage.FishItem;
import com.yourgame.model.Item.Inventory.InventorySlot;
import com.yourgame.model.Item.Item;
import com.yourgame.model.Item.Tools.PoleStage;
import com.yourgame.model.WeatherAndTime.Season;
import com.yourgame.view.AppViews.GameScreen;

import java.util.ArrayList;

public class FishingGameMenuView extends Window {
    private final Skin skin;
    private final GameScreen gameScreen;
    private final Stage stage;
    private final Fish fish;
    private final PoleStage poleStage;

    private static final float TRACK_HEIGHT = 520f;       // play area vertical size
    private static final float TRACK_TOP_PADDING = -150f;  // top space inside the window
    private static final float TRACK_LEFT = 200f;         // x position of the track elements
    private static final float BAR_HEIGHT = 120f;         // green bar height
    private static final float BAR_WIDTH  = 64f;
    private static final float FISH_SIZE  = 64f;

    private static final float barRiseAccel = 200f;   // acceleration upward when holding mouse
    private static final float barFallGravity = 150f; // gravity pulling bar down
    private static final float barMaxSpeed = 300f;    // cap for bar movement speed

    private static final float progressFillRate = 0.1f;
    private static final float progressDrainRate = 0.05f;

    private final Image progressImg;
    private final Image greenBarImg;
    private final Image fishImg;
    private final Label perfectLabel;
    private final Label exitHint;

    private float greenBarY;          // bottom of green bar (local to window coords)
    private float greenBarVelY;       // velocity for smooth gravity feel
    private float fishY;              // center Y of fish
    private float progress01 = 0.2f;  // start with 20% progress to feel snappy
    private boolean held = false;     // mouse pressed
    private boolean perfectSoFar = true;
    private float fishAItimer = 0f;   // 0.5s step timer
    private FishMove lastMove = FishMove.STAY;

    private enum FishStyle { MIXED, SMOOTH, SINKER, FLOATER, DART }
    private enum FishMove { UP, DOWN, STAY }
    private final FishStyle style;
    private float fishStep = 40f;

    private boolean caught = false;

    public FishingGameMenuView(Skin skin, Stage stage, GameScreen gameScreen, Fish fish, PoleStage fishingPole) {
        super("Fishing Game", skin);
        this.stage = stage;
        this.gameScreen = gameScreen;
        this.skin = skin;
        this.fish = fish;
        this.poleStage = fishingPole;

        setSize(1200, 800);
        setModal(true);
        setMovable(false);
        pad(20f);
        setPosition((stage.getWidth() - getWidth()) / 2f, (stage.getHeight() - getHeight()) / 2f);

        this.style = chooseStyleForFish(fish);

        progressImg = new Image(GameAssetManager.getInstance().getFishingState(0));
        progressImg.setScale(1.5f);
        greenBarImg = new Image(GameAssetManager.getInstance().getScrollBar());
        fishImg = new Image(GameAssetManager.getInstance().getFish());

        perfectLabel = new Label("Perfect!", skin, "BoldImpact");
        exitHint = new Label("Hold mouse: move up • Release: fall • Q: quit", skin);

        add(perfectLabel).top().padTop(20).padLeft(10f).expandX();
        add(exitHint).top().right().padTop(20).padRight(10f);
        row().padTop(200);

        WidgetGroup playArea = new WidgetGroup();
        playArea.setSize(getWidth(), TRACK_HEIGHT + TRACK_TOP_PADDING * 2f);
        add(playArea);
        add(progressImg).padRight(100);

        playArea.addActor(greenBarImg);
        playArea.addActor(fishImg);

        greenBarY = TRACK_TOP_PADDING + TRACK_HEIGHT * 0.3f;
        fishY = TRACK_TOP_PADDING + TRACK_HEIGHT * 0.55f;
        applyPositions();

        addListeners();

        TextButton backButton = new TextButton("Back", skin);
        backButton.addListener(new ClickListener(){
            @Override public void clicked(InputEvent event, float x, float y){
                closeWithoutCatch();
            }
        });
        row();
        add(backButton).padTop(10f);

        stage.addActor(this);
    }

    private FishStyle chooseStyleForFish(Fish fish){
        int pick = Math.abs(fish.getName().hashCode()) % 5;
        return switch (pick){
            case 1 -> FishStyle.SMOOTH;
            case 2 -> FishStyle.SINKER;
            case 3 -> FishStyle.FLOATER;
            case 4 -> FishStyle.DART;
            default -> FishStyle.MIXED;
        };
    }

    private void addListeners() {
        // We’ll use input listeners on this window
        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                held = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                held = false;
            }

            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.Q) {
                    closeWithoutCatch();
                    return true;
                }
                return false;
            }
        });

        Gdx.input.setInputProcessor(stage);
        stage.setKeyboardFocus(this);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        float accel = held ? barRiseAccel : -barFallGravity;
        greenBarVelY += accel * delta;
        greenBarVelY = MathUtils.clamp(greenBarVelY, -barMaxSpeed, barMaxSpeed);
        greenBarY += greenBarVelY * delta;

        float barMin = TRACK_TOP_PADDING;
        float barMax = TRACK_TOP_PADDING + TRACK_HEIGHT - BAR_HEIGHT;
        if (greenBarY < barMin) { greenBarY = barMin; greenBarVelY = 0; }
        if (greenBarY > barMax) { greenBarY = barMax; greenBarVelY = 0; }

        fishAItimer += delta;
        while (fishAItimer >= 0.5f) {
            fishAItimer -= 0.5f;
            stepFishAI();
        }

        float fishMin = TRACK_TOP_PADDING + FISH_SIZE * 0.5f;
        float fishMax = TRACK_TOP_PADDING + TRACK_HEIGHT - FISH_SIZE * 0.5f;
        fishY = MathUtils.clamp(fishY, fishMin, fishMax);

        boolean inside = isFishInsideBar();
        if (inside) {
            progress01 += progressFillRate * delta;
        } else {
            progress01 -= progressDrainRate * delta;
            perfectSoFar = false;
        }
        progress01 = MathUtils.clamp(progress01, 0f, 1f);

        // Update progress image state
        updateProgressImage();

        // Perfect indicator
        perfectLabel.setVisible(perfectSoFar);

        // Apply positions to actors
        applyPositions();

        if (!caught && progress01 >= 1f - 1e-6f) {
            caught = true;
            onCatch();
        } else if (!caught && progress01 <= 1e-6f) {
            caught = true;
            closeWithoutCatch();
        }
    }

    private void applyPositions() {
        greenBarImg.setBounds(TRACK_LEFT, greenBarY, BAR_WIDTH, BAR_HEIGHT);
        fishImg.setBounds(TRACK_LEFT + 100f, fishY - FISH_SIZE * 0.5f, FISH_SIZE, FISH_SIZE);
    }

    private boolean isFishInsideBar() {
        float barBottom = greenBarY - 50;
        float barTop = greenBarY + BAR_HEIGHT + 50;
        float fy = fishY; // fish center
        Gdx.app.log(fy + " " + barBottom + " " + barTop, "");
        return fy >= barBottom && fy <= barTop;
    }

    private void updateProgressImage() {
        int state = MathUtils.clamp(Math.round(progress01 * 16f), 0, 16);
        progressImg.setDrawable(new TextureRegionDrawable(
            GameAssetManager.getInstance().getFishingState(state)));
    }

    private void stepFishAI() {
        FishMove move = pickMove(style, lastMove);
        lastMove = move;

        float step = fishStep;
        if (style == FishStyle.DART) step = 72f; // harder jumps (~144 px/s effective)
        if (style == FishStyle.SINKER && move == FishMove.DOWN) step *= 1.35f;
        if (style == FishStyle.FLOATER && move == FishMove.UP)   step *= 1.35f;

        switch (move) {
            case UP:   fishY += step; break;
            case DOWN: fishY -= step; break;
            case STAY: /* no-op */    break;
        }
    }

    private FishMove pickMove(FishStyle style, FishMove last) {
        switch (style) {
            case MIXED:
                return randomOf(FishMove.UP, FishMove.DOWN, FishMove.STAY);
            case SMOOTH:
                if (MathUtils.random() < 0.6f) return last;
                return randomOf(FishMove.UP, FishMove.DOWN, FishMove.STAY);
            case SINKER:
                return weightedRandom(0.25f, 0.5f, 0.25f); // up, down, stay
            case FLOATER:
                return weightedRandom(0.5f, 0.25f, 0.25f);
            case DART:
                return randomOf(FishMove.UP, FishMove.DOWN, FishMove.STAY);
            default:
                return FishMove.STAY;
        }
    }

    private FishMove randomOf(FishMove a, FishMove b, FishMove c) {
        int r = MathUtils.random(2);
        return r==0 ? a : (r==1 ? b : c);
    }

    private FishMove weightedRandom(float up, float down, float stay) {
        float r = MathUtils.random();
        if (r < up) return FishMove.UP;
        if (r < up + down) return FishMove.DOWN;
        return FishMove.STAY;
    }

    private void onCatch() {
        if(perfectSoFar){
            gameScreen.getPlayer().setFishingLevel((int)(gameScreen.getPlayer().getFishingLevel()*2.4));
            gameScreen.showMessage("Victory","Perfect Catch!", skin,0,200,stage);
        }
        else{
            gameScreen.showMessage("popUp","Caught!", skin,0,200,stage);
        }

        boolean hasSonar = false;
        for (InventorySlot slot : gameScreen.getPlayer().getBackpack().getInventory().getSlots()) {
            if (slot != null && slot.item().getName().equals("SonarBobber")) {
                hasSonar = true;
                break;
            }
        }

        if (hasSonar) {
            Dialog sonarDialog = new Dialog("Use Sonar Bobber?", skin) {
                @Override
                protected void result(Object object) {
                    boolean useSonar = object != null && object.equals(Boolean.TRUE);
                    if (useSonar) {
                        gameScreen.showMessage("popUp", showFishDetails(), skin, 0, 200, stage);
                    }
                    addToInventory();
                }
            };
            sonarDialog.text("Do you want to use the Sonar Bobber to reveal fish details?");
            sonarDialog.button("Yes", true);
            sonarDialog.button("No", false);
            sonarDialog.show(stage);
            stage.setKeyboardFocus(sonarDialog);
        }
    }

    private void addToInventory(){
        boolean added = gameScreen.getPlayer().getBackpack().getInventory().addItem(new FishItem(fish),1);
        if (!added) {
            gameScreen.showMessage("error", "Not enough space in inventory!", skin, 0, 200, stage);
        }
        // Close minigame
        gameScreen.closeMenu();
        remove();
    }

    private String showFishDetails() {
        StringBuilder fishInfo = new StringBuilder();
        fishInfo.append("Name : " + fish.getName() + "\n");
        fishInfo.append("Price : " + fish.getPrice() + "\n");
        fishInfo.append("Season : " + fish.getSeason() + "\n");
        fishInfo.append("Rarity : " + getRarity(fish));
        return fishInfo.toString();
    }

    private void closeWithoutCatch() {
        // Escaped
        gameScreen.showMessage("error", "The fish got away...", skin, 0, 200, stage);
        gameScreen.closeMenu();
        remove();
    }

    private String getRarity(Fish fish){
        if(fish.equals(Fish.CrimsonFish) || (fish.equals(Fish.GlacierFish))
            || fish.equals(Fish.Angler) || (fish.equals(Fish.Legend))){
            return "Legendary";
        }
        return "Common";
    }
}
