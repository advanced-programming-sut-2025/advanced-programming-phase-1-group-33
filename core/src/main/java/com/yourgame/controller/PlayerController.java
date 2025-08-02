package com.yourgame.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.yourgame.model.Player;

/**
 * Handles player-specific logic like input, movement, animation, and rendering.
 * Acts as the 'Controller' in a Model-View-Controller architecture.
 */
public class PlayerController {

    private final Player player; // The data model
    private final Texture playerSheet;
    private final Animation<TextureRegion>[] walkAnimations;
    private float stateTime = 0f;

    private static final int PLAYER_FRAME_WIDTH = 16;
    private static final int PLAYER_FRAME_HEIGHT = 32;
    private static final float MOVE_SPEED = 150f;

    public PlayerController(Player player) {
        this.player = player;

        // It's good practice to load assets via an AssetManager, but direct loading is fine for now.
        playerSheet = new Texture(Gdx.files.internal("Game/Player/player.png")); 
        TextureRegion[][] frames = TextureRegion.split(playerSheet, PLAYER_FRAME_WIDTH, PLAYER_FRAME_HEIGHT);

        walkAnimations = new Animation[4]; // 0:Down, 1:Right, 2:Up, 3:Left
        walkAnimations[0] = new Animation<>(0.2f, frames[0]); // Down
        walkAnimations[1] = new Animation<>(0.2f, frames[1]); // Right
        walkAnimations[2] = new Animation<>(0.2f, frames[2]); // Up
        walkAnimations[3] = new Animation<>(0.2f, frames[3]); // Left
    }

    /**
     * Reads keyboard input and sets the player's velocity and direction.
     */
    private void handleInput() {
        Vector2 velocity = player.getVelocity();
        velocity.setZero();

        float currentSpeed = MOVE_SPEED;
        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
            currentSpeed *= 1.5f; // Sprinting
        }

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            velocity.y = currentSpeed;
            player.setDirection(2); // Up
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            velocity.y = -currentSpeed;
            player.setDirection(0); // Down
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            velocity.x = currentSpeed;
            player.setDirection(1); // Right
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            velocity.x = -currentSpeed;
            player.setDirection(3); // Left
        }
    }

    /**
     * Updates the player's state. Call this every frame from GameScreen.
     * @param delta Time since last frame.
     * @param collisionObjects The collision layer objects from the TiledMap.
     */
    public void update(float delta, MapObjects collisionObjects) {
        handleInput();
        stateTime += delta;

        Vector2 pos = player.getPosition();
        Vector2 vel = player.getVelocity();

        // If not moving, reset animation to the first frame to appear standing still
        if (vel.isZero()) {
            stateTime = 0f;
        }

        // Calculate potential new position
        Vector2 newPos = new Vector2(pos).mulAdd(vel, delta);
        
        // --- Collision Detection ---
        if (!isColliding(newPos, collisionObjects)) {
            player.setPosition(newPos);
        }
    }

    /**
     * Checks if the player at a given position would collide with any map objects.
     * @param position The position to check.
     * @param collisionObjects The map objects to check against.
     * @return True if a collision would occur, false otherwise.
     */
    private boolean isColliding(Vector2 position, MapObjects collisionObjects) {
        // A smaller rectangle for the player's feet is often better for collision
        Rectangle playerBounds = new Rectangle(
            position.x, 
            position.y, 
            PLAYER_FRAME_WIDTH, 
            PLAYER_FRAME_HEIGHT / 2.0f 
        );

        for (MapObject object : collisionObjects) {
             if (object instanceof RectangleMapObject) {
                if (playerBounds.overlaps(((RectangleMapObject) object).getRectangle())) {
                    return true; // Collision detected
                }
            }
            // You can add checks for other shapes like Polygons or Circles here
        }
        return false; // No collision
    }

    /**
     * Renders the player character.
     * @param batch The SpriteBatch to draw with.
     */
    public void render(SpriteBatch batch) {
        Animation<TextureRegion> animation = walkAnimations[player.getDirection()];
        TextureRegion currentFrame = animation.getKeyFrame(stateTime, true); // true loops the animation

        batch.draw(currentFrame, player.getPosition().x, player.getPosition().y);
    }

    /**
     * Dispose of assets managed by this controller.
     */
    public void dispose() {
        playerSheet.dispose();
    }
}
