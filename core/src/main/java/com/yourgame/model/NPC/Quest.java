package com.yourgame.model.NPC;

import com.yourgame.model.Item.Item;

import java.util.Map;

public record Quest(
    String questId,
    String description,
    NPCType questGiver,
    Map<Item, Integer> requiredItems,
    Dialogue startDialogue,
    Dialogue endDialogue,
    int rewardGold,
    int friendshipPoints
) {}
