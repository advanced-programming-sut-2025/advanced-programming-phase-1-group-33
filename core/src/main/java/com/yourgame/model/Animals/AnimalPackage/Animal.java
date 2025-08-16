package com.yourgame.model.Animals.AnimalPackage;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.controller.GameController.PathFinder;
import com.yourgame.model.Animals.Hay;
import com.yourgame.model.Item.Item;
import com.yourgame.model.Map.Elements.BuildingType;
import com.yourgame.model.Map.Map;
import com.yourgame.model.Map.Tile;
import com.yourgame.model.NPC.NPC;
import com.yourgame.model.WeatherAndTime.TimeObserver;
import com.yourgame.model.WeatherAndTime.TimeSystem;

import java.util.List;
import java.util.Random;

public class Animal implements TimeObserver {
    private static final float SPEED = 30f;
    private static final Random random = new Random();

    private final AnimalType type;
    private String name; // Can be set by the player
    private int friendship;
    private boolean wasFed;
    private boolean wasPet;
    private int daysUntilProductReady;

    public final Vector2 position;
    private boolean isMoving;
    private int direction; // 0=Down, 1=Right, 2=Up, 3=Left
    private float stateTimer;
    private float animationTime;
    private final Animation<TextureRegion>[] walkAnimations;
    private List<Vector2> currentPath;
    private int pathIndex;
    private Vector2 currentDestination;

    public Animal(AnimalType type, float startX, float startY) {
        this.type = type;
        this.name = type.getName(); // Default name
        this.friendship = 0;
        this.wasFed = false;
        this.wasPet = false;
        this.daysUntilProductReady = type.getDaysToProduce();

        this.position = new Vector2(startX, startY);
        this.isMoving = false;
        this.direction = 0;
        this.stateTimer = random.nextFloat() * 4 + 2; // Idle for 2-6 seconds initially
        this.animationTime = 0f;

        String path = "Game/Animal/" + type.getName() + "Walking.png";
        int frameWidth = (type.getHome() == BuildingType.BARN) ? 32 : 16;
        int frameHeight = (type.getHome() == BuildingType.BARN) ? 32 : 16;

        TextureRegion[][] frames = TextureRegion.split(GameAssetManager.getInstance().getTexture(path), frameWidth, frameHeight);
        this.walkAnimations = new Animation[4];
        walkAnimations[0] = new Animation<>(0.2f, frames[0]);
        walkAnimations[1] = new Animation<>(0.2f, frames[1]);
        walkAnimations[2] = new Animation<>(0.2f, frames[2]);
        walkAnimations[3] = new Animation<>(0.2f, frames[3]);
    }

    public void update(float delta, Map map) {
        stateTimer -= delta;
        animationTime += delta;

        if (!isMoving) {
            if (stateTimer <= 0) {
                findNewPath(map);
            }
        } else {
            followPath(delta);
        }
    }

    private void findNewPath(Map map) {
        // Pick a random spot within a 5-tile radius
        float newX = position.x + (random.nextFloat() - 0.5f) * (Tile.TILE_SIZE * 10);
        float newY = position.y + (random.nextFloat() - 0.5f) * (Tile.TILE_SIZE * 10);
        Vector2 destination = new Vector2(newX, newY);

        // Use the same Pathfinder as the NPCs
        currentPath = PathFinder.findPath(map, position, destination);
        if (currentPath != null && !currentPath.isEmpty()) {
            pathIndex = 0;
            isMoving = true;
        } else {
            // Couldn't find a path, so just idle for a bit longer
            stateTimer = random.nextFloat() * 2 + 1;
        }
    }

    private void followPath(float delta) {
        if (currentPath == null || pathIndex >= currentPath.size()) {
            isMoving = false;
            stateTimer = random.nextFloat() * 5 + 3; // Idle for 3-8 seconds
            return;
        }

        Vector2 target = currentPath.get(pathIndex);
        Vector2 moveDirection = new Vector2(target).sub(position).nor();

        position.x += moveDirection.x * SPEED * delta;
        position.y += moveDirection.y * SPEED * delta;

        // Update visual direction
        if (Math.abs(moveDirection.x) > Math.abs(moveDirection.y)) {
            direction = (moveDirection.x > 0) ? 1 : 3;
        } else {
            direction = (moveDirection.y > 0) ? 2 : 0;
        }

        if (position.dst(target) < 2f) {
            pathIndex++;
        }
    }

    public TextureRegion getTextureFrame() {
        if (isMoving) {
            return walkAnimations[direction].getKeyFrame(animationTime, true);
        } else {
            return walkAnimations[0].getKeyFrame(0);
        }
    }

    @Override
    public void onTimeChanged(TimeSystem timeSystem) {
        if (wasPet) {
            friendship += 15;
        }
        if (wasFed) {
            daysUntilProductReady--;
        } else {
            friendship -= 20;
        }

        wasPet = false;
        wasFed = false;

        friendship = Math.max(0, Math.min(1000, friendship));
    }

    public boolean canProduceProduct() {
        return daysUntilProductReady <= 0;
    }

    public void collectProduct() {
        if (canProduceProduct()) {
            daysUntilProductReady = type.getDaysToProduce();
        }
    }

    public boolean pet() {
        if (!wasPet) {
            this.wasPet = true;
            return true;
        }
        return false;
    }

    public boolean feed(Item heldItem) {
        if (!wasFed && heldItem instanceof Hay) {
            this.wasFed = true;
            return true;
        }
        return false;
    }

    public AnimalType getType() { return type; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
