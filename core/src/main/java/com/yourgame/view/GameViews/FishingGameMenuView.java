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
import com.yourgame.model.Item.Tools.PoleStage;
import com.yourgame.view.AppViews.GameScreen;

public class FishingGameMenuView extends Window {
    private final Skin skin;
    private final GameScreen gameScreen;
    private final Stage stage;
    private final Fish fish;
    private final PoleStage poleStage;

    private final float TRACK_HEIGHT = 520f;       // play area vertical size
    private final float TRACK_TOP_PADDING = 120f;  // top space inside the window
    private final float TRACK_LEFT = 200f;         // x position of the track elements
    private final float BAR_HEIGHT = 120f;         // green bar height
    private final float BAR_WIDTH  = 64f;
    private final float FISH_SIZE  = 64f;

    private final float barRiseAccel = 200f;   // acceleration upward when holding mouse
    private final float barFallGravity = 150f; // gravity pulling bar down
    private final float barMaxSpeed = 300f;    // cap for bar movement speed

    private final float progressFillRate = 0.3f;  // per second when fish is inside bar
    private final float progressDrainRate = 0.2f; // per second when fish is outside bar

    private Image progressImg;        // 17-state meter (0..16)
    private Image greenBarImg;        // player bar
    private Image fishImg;            // fish icon
    private Label perfectLabel;       // shows "Perfect!" when still perfect
    private Label exitHint;

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

    public FishingGameMenuView(Skin skin, Stage stage, GameScreen gameScreen, Fish fish, PoleStage fishingPole) {
        super("Fishing Game", skin);
        this.stage = stage;
        this.gameScreen = gameScreen;
        this.skin = skin;
        this.fish = fish;
        this.poleStage = fishingPole;

        setSize(1400, 900);
        setModal(true);
        setMovable(false);
        pad(20f);
        setPosition((stage.getWidth() - getWidth()) / 2f, (stage.getHeight() - getHeight()) / 2f);

        this.style = chooseStyleForFish(fish);

        Table root = new Table();
        root.setFillParent(true);
        add(root).expand().fill();

        progressImg = new Image(GameAssetManager.getInstance().getFishingState(0));
        greenBarImg = new Image(GameAssetManager.getInstance().getScrollBar());
        fishImg = new Image(GameAssetManager.getInstance().getFish());

        perfectLabel = new Label("Perfect!", skin, "Bold");
        exitHint = new Label("Hold mouse: move up • Release: fall • Q: quit", skin);

        Table topBar = new Table();
        topBar.add(perfectLabel).left().padLeft(10f).expandX();
        topBar.add(exitHint).right().padRight(10f);
        root.add(topBar).expandX().fillX().top().row();

        WidgetGroup playArea = new WidgetGroup();
        playArea.setSize(getWidth(), TRACK_HEIGHT + TRACK_TOP_PADDING * 2f);
        root.add(playArea).expand().fill().row();

        Table right = new Table();
        right.add(progressImg).width(64f).height(256f).padTop(20f);
        root.add(right).right().padRight(20f).top();

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
        root.row();
        root.add(backButton).left().padTop(10f);

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

        float accel = held ? barRiseAccel : barFallGravity;
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

        if (progress01 >= 1f - 1e-6f) {
            onCatch();
        } else if (progress01 <= 1e-6f) {
            closeWithoutCatch();
        }
    }

    private void applyPositions() {
        greenBarImg.setBounds(TRACK_LEFT, greenBarY, BAR_WIDTH, BAR_HEIGHT);
        fishImg.setBounds(TRACK_LEFT + 200f, fishY - FISH_SIZE * 0.5f, FISH_SIZE, FISH_SIZE);
    }

    private boolean isFishInsideBar() {
        float barBottom = greenBarY;
        float barTop = greenBarY + BAR_HEIGHT;
        float fy = fishY; // fish center
        return fy >= barBottom && fy <= barTop;
    }

    private void updateProgressImage() {
        int state = MathUtils.clamp(Math.round(progress01 * 16f), 0, 16);
        progressImg.setDrawable(new TextureRegionDrawable(
            GameAssetManager.getInstance().getFishingState(state)
        ));
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
                // 60% repeat last, 40% random other
                if (MathUtils.random() < 0.6f) return last;
                return randomOf(FishMove.UP, FishMove.DOWN, FishMove.STAY);
            case SINKER:
                // Slight bias to DOWN
                return weightedRandom(0.25f, 0.5f, 0.25f); // up, down, stay
            case FLOATER:
                // Slight bias to UP
                return weightedRandom(0.5f, 0.25f, 0.25f);
            case DART:
                // Like MIXED but we already use a bigger step
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
        // Perfect catch bonus: if perfectSoFar and fish has at least Silver, upgrade etc.
        // TODO: plug in your reward pipeline here:
        //  - Add fish to inventory
        //  - If perfectSoFar: bump quality (Silver->Gold, Gold->Iridium), XP *= 2.4
        //  - Play SFX, show banner, etc.
        gameScreen.showMessage("popUp", perfectSoFar ? "Perfect Catch!" : "Caught!", skin, 0, 200, stage);

        // Close minigame
        stage.clear();
        gameScreen.closeMenu();
        remove();
    }

    private void closeWithoutCatch() {
        // Escaped
        gameScreen.showMessage("error", "The fish got away...", skin, 0, 200, stage);
        stage.clear();
        gameScreen.closeMenu();
        remove();
    }
}
