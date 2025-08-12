package com.yourgame.model.WeatherAndTime;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * Represents a single, temporary thunder strike animation on the screen.
 */
public class ThunderStrike {
    private final Animation<TextureRegion> animation;
    private final Vector2 position;
    private float stateTime;
    private boolean effectApplied; // To ensure we only destroy the element once

    public ThunderStrike(Animation<TextureRegion> animation, float x, float y) {
        this.animation = animation;
        // Center the animation on the tile
        this.position = new Vector2(x - (animation.getKeyFrame(0).getRegionWidth() / 2f) + 8, y);
        this.stateTime = 0f;
        this.effectApplied = false;
    }

    public void update(float delta) {
        stateTime += delta;
    }

    public TextureRegion getKeyFrame() {
        return animation.getKeyFrame(stateTime, false); // false = don't loop
    }

    public Vector2 getPosition() {
        return position;
    }

    public boolean isAnimationFinished() {
        return animation.isAnimationFinished(stateTime);
    }

    public boolean hasEffectApplied() {
        return effectApplied;
    }

    public void setEffectApplied(boolean effectApplied) {
        this.effectApplied = effectApplied;
    }
}
