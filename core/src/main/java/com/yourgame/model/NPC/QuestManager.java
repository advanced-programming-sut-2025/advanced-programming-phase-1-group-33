package com.yourgame.model.NPC;

import com.yourgame.model.Item.Inventory.Inventory;
import com.yourgame.model.Item.Item;

import java.util.HashMap;
import java.util.Map;

public class QuestManager {
    private final Map<String, Quest> activeQuests = new HashMap<>();
    private final Map<String, Quest> completedQuests = new HashMap<>();

    public void acceptQuest(Quest quest) {
        if (quest != null && !activeQuests.containsKey(quest.questId())) {
            activeQuests.put(quest.questId(), quest);
            // TODO: Show a "New Quest" notification to the player
        }
    }

    public boolean canCompleteQuest(Quest quest, Inventory playerInventory) {
        if (!activeQuests.containsKey(quest.questId())) return false;

        for (Map.Entry<Item, Integer> entry : quest.requiredItems().entrySet()) {
            if (playerInventory.getItemQuantity(entry.getKey()) < entry.getValue()) {
                return false;
            }
        }
        return true;
    }

    public void completeQuest(Quest quest, Inventory playerInventory) {
        if (!canCompleteQuest(quest, playerInventory)) return;

        // Remove items from inventory
        for (Map.Entry<Item, Integer> entry : quest.requiredItems().entrySet()) {
            playerInventory.reduceItemQuantity(entry.getKey(), entry.getValue());
        }

        // Move quest from active to completed
        activeQuests.remove(quest.questId());
        completedQuests.put(quest.questId(), quest);

        // TODO: Give rewards to the player (gold, friendship points)
    }

    public boolean isQuestActive(String questId) {
        return activeQuests.containsKey(questId);
    }

    public boolean isQuestCompleted(String questId) {
        return completedQuests.containsKey(questId);
    }

    public Quest getActiveQuestByGiver(NPCType npcType) {
        for (Quest quest : activeQuests.values()) {
            if (quest.questGiver() == npcType) {
                return quest;
            }
        }
        return null;
    }
}
