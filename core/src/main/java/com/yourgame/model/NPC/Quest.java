package com.yourgame.model.NPC;

import com.yourgame.model.Item.Item;

import java.util.Map;

public record Quest(
    String questId,
    String title,
    String description,
    NPCType questGiver,
    Map<Item, Integer> requiredItems,
    int rewardGold,
    int friendshipPoints
) {}
