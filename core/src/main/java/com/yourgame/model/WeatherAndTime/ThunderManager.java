package com.yourgame.model.WeatherAndTime;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.model.Map.Farm;
import com.yourgame.model.Map.Map;
import com.yourgame.model.Map.MapElement;
import com.yourgame.model.Map.Tile;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class ThunderManager {
    private final List<ThunderStrike> activeStrikes;
    private final Animation<TextureRegion> strikeAnimation;
    private final Sound thunderSound;
    private final Random random;

    public ThunderManager() {
        this.activeStrikes = new ArrayList<>();
        this.random = new Random();

        // Load your thunder animation spritesheet and sound
        Texture texture = GameAssetManager.getInstance().getTexture("Game/thunder.png");
        TextureRegion[][] frames = TextureRegion.split(texture, 192, 192);
        this.strikeAnimation = new Animation<>(0.1f, frames[0]);
        this.thunderSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/thunder.mp3"));
    }

    /**
     * This is the main update loop for the thunder system. Call this from GameScreen.
     */
    public void update(float delta) {
        // Update all active animations
        Iterator<ThunderStrike> iterator = activeStrikes.iterator();
        while (iterator.hasNext()) {
            ThunderStrike strike = iterator.next();
            strike.update(delta);

            // Apply the destruction effect mid-animation for better timing
            if (!strike.hasEffectApplied() && strike.isAnimationFinished()) { // Or a specific time
                // Logic to destroy the element is handled when the strike is created
                strike.setEffectApplied(true);
            }

            // Remove finished animations
            if (strike.isAnimationFinished()) {
                iterator.remove();
            }
        }
    }

    public void triggerThunderStrike(Map map) {
        if (!(map instanceof Farm)) return;

        List<Tile> validTargets = new ArrayList<>();

        // Find all tiles that have a destructible element on them
        for (int y = 0; y < map.getMapHeight(); y++) {
            for (int x = 0; x < map.getMapWidth(); x++) {
                Tile tile = map.getTile(x, y);
                if (tile != null && tile.getElement() != null) {
                    // You can add more checks here, e.g., don't strike trees right next to the house
                    validTargets.add(tile);
                }
            }
        }

        if (validTargets.isEmpty()) return;

        // Strike up to 3 random tiles
        thunderSound.play();
        int strikeCount = Math.min(3, validTargets.size());
        for (int i = 0; i < strikeCount; i++) {
            Tile targetTile = validTargets.remove(random.nextInt(validTargets.size()));
            MapElement elementToDestroy = targetTile.getElement();

            if (elementToDestroy != null) {
                // Create the visual animation
                activeStrikes.add(new ThunderStrike(strikeAnimation, targetTile.tileX * Tile.TILE_SIZE, targetTile.tileY * Tile.TILE_SIZE));

                // Immediately destroy the element in the game data
                map.removeElement(elementToDestroy);
            }
        }
    }

    /**
     * Draws all active thunder animations. Call this from GameScreen.
     * @param batch The SpriteBatch to draw with.
     */
    public void render(SpriteBatch batch) {
        for (ThunderStrike strike : activeStrikes) {
            batch.draw(strike.getKeyFrame(), strike.getPosition().x, strike.getPosition().y);
        }
    }

    public void dispose() {
        thunderSound.dispose();
    }
}
