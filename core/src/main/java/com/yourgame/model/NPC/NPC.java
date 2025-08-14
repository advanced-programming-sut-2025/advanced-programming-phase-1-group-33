package com.yourgame.model.NPC;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.controller.GameController.PathFinder;
import com.yourgame.model.App;
import com.yourgame.model.Item.Inventory.Inventory;
import com.yourgame.model.Map.Map;
import com.yourgame.model.UserInfo.Player;
import com.yourgame.model.WeatherAndTime.Season;
import com.yourgame.model.WeatherAndTime.Weather;

import java.util.List;

public class NPC {
    public enum NPCState { IDLE, WALKING }

    private final NPCType type;
    public Vector2 position;
    private int friendshipLevel;
    private Quest activeQuest;
    private NPCState state;
    private int direction; // 0=Down, 1=Right, 2=Up, 3=Left
    private boolean playerInRange = false;

    // Movement and Pathfinding
    private List<Vector2> currentPath;
    private int pathIndex;
    private Vector2 currentDestination;
    private static final float SPEED = 60f; // Slower than the player

    // Animation
    private final Animation<TextureRegion>[] walkAnimations;
    private float stateTime;

    public NPC(NPCType type, Vector2 startPosition) {
        this.type = type;
        this.position = startPosition;
        this.state = NPCState.IDLE;
        this.direction = 0;
        this.friendshipLevel = 0;
        this.activeQuest = null;

        // Load NPC-specific walking animations
        Texture texture = GameAssetManager.getInstance().getTexture("Game/NPC/" + type.name() + ".png");
        TextureRegion[][] frames = TextureRegion.split(texture, 16, 32);
        this.walkAnimations = new Animation[4];
        walkAnimations[0] = new Animation<>(0.2f, frames[0]); // Down
        walkAnimations[1] = new Animation<>(0.2f, frames[1]); // Right
        walkAnimations[2] = new Animation<>(0.2f, frames[2]); // Up
        walkAnimations[3] = new Animation<>(0.2f, frames[3]); // Left
    }

    public void update(float delta, Map map) {
        stateTime += delta;

        // --- State Machine ---
        switch (state) {
            case IDLE:
                checkSchedule(map);
                break;
            case WALKING:
                followPath(delta);
                break;
        }
    }

    private void checkSchedule(Map map) {
        int currentTime = App.getGameState().getGameTime().getHour() * 100 + App.getGameState().getGameTime().getMinutes();
        Schedule schedule = type.getSchedule();
        Vector2 newDestination = schedule.getDestination(currentTime);

        if (newDestination != null && (currentDestination == null || !currentDestination.equals(newDestination))) {
            currentDestination = newDestination;
            currentPath = PathFinder.findPath(map, position, currentDestination);
            if (currentPath != null && !currentPath.isEmpty()) {
                pathIndex = 0;
                state = NPCState.WALKING;
            }
        }
    }

    private void followPath(float delta) {
        if (currentPath == null || pathIndex >= currentPath.size()) {
            state = NPCState.IDLE;
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
        if (state == NPCState.WALKING) {
            return walkAnimations[direction].getKeyFrame(stateTime, true);
        } else {
            // Return the first frame of the "down" animation for idle
            return walkAnimations[0].getKeyFrame(0);
        }
    }

    public Dialogue getDialogue(Player player) {
        QuestManager questManager = player.getQuestManager();
        Inventory playerInventory = player.getBackpack().getInventory();

        // 1. Check if the player can COMPLETE an active quest for this NPC.
        Quest activeQuest = questManager.getActiveQuestByGiver(this.type);
        if (activeQuest != null && questManager.canCompleteQuest(activeQuest, playerInventory)) {
            questManager.completeQuest(activeQuest, player);
            return activeQuest.endDialogue();
        }

        // 2. Check if this NPC has a new quest to offer the player.
        Quest potentialQuest = findAvailableQuestForPlayer(player);
        if (potentialQuest != null) {
            questManager.acceptQuest(potentialQuest);
            return potentialQuest.startDialogue();
        }

        // 3. If no quest dialogue, find the best regular dialogue.
        return findBestRegularDialogue(player);
    }

    private Quest findAvailableQuestForPlayer(Player player) {
        for (Quest quest : QuestDatabase.QUESTS.values()) {
            // Check if this NPC is the giver and the player hasn't started or finished it
            if (quest.questGiver() == this.type &&
                !player.getQuestManager().isQuestActive(quest.questId()) &&
                !player.getQuestManager().isQuestCompleted(quest.questId())) {

                // TODO: Add more conditions here, e.g.:
                // if (player.getFriendshipWith(this.type) >= quest.minFriendship())
                return quest;
            }
        }
        return null;
    }

    private Dialogue findBestRegularDialogue(Player player) {
        Season currentSeason = App.getGameState().getGameTime().getSeason();
        Weather currentWeather = App.getGameState().getGameTime().getWeather();

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

        return bestMatch != null ? bestMatch : new Dialogue("...");
    }

    // Getters and Setters
    public NPCType getType() { return type; }
    public int getFriendshipLevel() { return friendshipLevel; }
    public void addFriendship(int amount) { this.friendshipLevel += amount; }
    public void setQuest(Quest quest) { this.activeQuest = quest; }
    public Quest getQuest() { return activeQuest; }
    public void setPlayerInRange(boolean playerInRange) { this.playerInRange = playerInRange; }
    public boolean isPlayerInRange() { return playerInRange; }
}
