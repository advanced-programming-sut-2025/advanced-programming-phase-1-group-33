package com.yourgame.model.Food;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.model.UserInfo.Player;

public class FoodAnimation {
    private final TextureRegion texture;
    private final Vector2 startPosition;
    private final Food foodItem;
    private final Player player;
    private float timer;
    private final float duration;
    private boolean effectApplied;
    private final Sound eatingSound;

    public final Vector2 position = new Vector2();

    public FoodAnimation(Food foodItem, Player player, float duration) {
        this.texture = foodItem.getTextureRegion(GameAssetManager.getInstance());
        this.startPosition = new Vector2(player.playerPosition.x, player.playerPosition.y + 16);
        this.player = player;
        this.foodItem = foodItem;
        this.duration = duration;
        this.timer = 0;
        this.effectApplied = false;
        this.eatingSound = GameAssetManager.getInstance().getSound("Sounds/eat.wav");
    }

    /**
     * Updates the animation's state.
     * @param delta Time since the last frame.
     * @return True if the animation is finished, false otherwise.
     */
    public boolean update(float delta) {
        timer += delta;
        float progress = Math.min(1f, timer / duration);

        // --- Animation position logic (your code was good) ---
        if (progress < 0.5f) {
            float upProgress = progress * 2;
            position.x = startPosition.x;
            position.y = Interpolation.circleOut.apply(startPosition.y, startPosition.y + 32, upProgress);
        } else {
            float downProgress = (progress - 0.5f) * 2;
            position.x = Interpolation.linear.apply(startPosition.x, player.playerPosition.x, downProgress);
            position.y = Interpolation.circleIn.apply(startPosition.y + 32, player.playerPosition.y + 8, downProgress);
        }

        if (!effectApplied && position.y <= player.playerPosition.y + 16) {
            eatingSound.play();
            player.addEnergy(foodItem.getFoodType().getEnergy());
            player.getBackpack().getInventory().reduceItemQuantity(foodItem, 1);
            effectApplied = true;
        }

        // The animation is finished when the timer runs out.
        return timer >= duration;
    }

    public TextureRegion getTexture() {
        return texture;
    }

    public boolean hasEffectApplied() {
        return effectApplied;
    }

    public void render(SpriteBatch batch) {
        batch.draw(getTexture(), position.x, position.y, 16, 16);
    }
}
