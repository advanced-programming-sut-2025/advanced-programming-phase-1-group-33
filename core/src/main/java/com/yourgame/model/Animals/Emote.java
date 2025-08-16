package com.yourgame.model.Animals;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.yourgame.Graphics.GameAssetManager;

public class Emote {
    public final Vector2 position = new Vector2();
    public final TextureRegion texture;
    private float timer;
    private static final float DURATION = 0.8f; // How long the emote stays on screen (in seconds)
    private static final float RISE_SPEED = 20f; // How fast the emote rises

    public Emote(String emoteName, float startX, float startY) {
        this.texture = new TextureRegion(GameAssetManager.getInstance().getTexture("Game/Animal/" + emoteName + ".png"));
        this.position.set(startX, startY);
        this.timer = DURATION;
    }

    public boolean update(float delta) {
        timer -= delta;
        position.y += RISE_SPEED * delta;
        return timer <= 0;
    }
}
