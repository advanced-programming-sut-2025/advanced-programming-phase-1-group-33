package com.yourgame.model.NPC;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.model.WeatherAndTime.Season;
import com.yourgame.model.WeatherAndTime.Weather;

import java.util.List;

public class NPC {
    private final NPCType type;
    public Vector2 position;
    private int friendshipLevel; // e.g., 0-2500, where every 250 points is one heart
    private Quest activeQuest;

    public NPC(NPCType type, float startX, float startY) {
        this.type = type;
        this.position = new Vector2(startX, startY);
        this.friendshipLevel = 0;
        this.activeQuest = null;
    }

    public void update(float delta) {
        // TODO: Implement NPC movement based on a daily schedule
    }

    /**
     * The core logic for selecting the most appropriate dialogue.
     * It iterates through all possible dialogues and picks the best match.
     */
    public String getDialogue(Season currentSeason, Weather currentWeather) {
        List<Dialogue> possibleDialogues = type.getDialogues();
        Dialogue bestMatch = null;

        for (Dialogue dialogue : possibleDialogues) {
            boolean seasonMatch = dialogue.seasons().isEmpty() || dialogue.seasons().contains(currentSeason);
            boolean weatherMatch = dialogue.weathers().isEmpty() || dialogue.weathers().contains(currentWeather);
            boolean friendshipMatch = friendshipLevel >= dialogue.minFriendShipLevel() * 250;

            if (seasonMatch && weatherMatch && friendshipMatch) {
                // This is a potential dialogue, but we might find a more specific one
                if (bestMatch == null || dialogue.minFriendShipLevel() > bestMatch.minFriendShipLevel()) {
                    bestMatch = dialogue;
                }
            }
        }

        return bestMatch != null ? bestMatch.text() : "...";
    }

    public TextureRegion getTexture(GameAssetManager assetManager) {
        return new TextureRegion(assetManager.getTexture("Game/NPC/" + type.getName() + ".png"));
    }

    // Getters and Setters
    public NPCType getType() { return type; }
    public int getFriendshipLevel() { return friendshipLevel; }
    public void addFriendship(int amount) { this.friendshipLevel += amount; }
    public void setQuest(Quest quest) { this.activeQuest = quest; }
    public Quest getQuest() { return activeQuest; }
}
