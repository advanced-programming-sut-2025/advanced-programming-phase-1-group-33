package com.yourgame.model;

import java.util.Map;
import java.util.Set;

public class NPC {
    private String name;
    private Coordinate currentLocation;
    private Map<TimeCondition, Coordinate> schedule;
    private Map<DialogueTrigger, String> dialogue;
    private Set<Item> likedGifts;
    private Set<Item> dislikedGifts;
    private List<Quest> questsAvailable;

    public NPC(String name, Coordinate currentLocation, Map<TimeCondition, Coordinate> schedule,
               Map<DialogueTrigger, String> dialogue, Set<Item> likedGifts,
               Set<Item> dislikedGifts, List<Quest> questsAvailable) {
        this.name = name;
        this.currentLocation = currentLocation;
        this.schedule = schedule;
        this.dialogue = dialogue;
        this.likedGifts = likedGifts;
        this.dislikedGifts = dislikedGifts;
        this.questsAvailable = questsAvailable;
    }

    public String getName() {
        return name;
    }

    public Coordinate getCurrentLocation() {
        return currentLocation;
    }

    public Map<TimeCondition, Coordinate> getSchedule() {
        return schedule;
    }

    public String getDialogue(DialogueTrigger trigger) {
        return dialogue.get(trigger);
    }

    public boolean checkGiftPreference(Item gift) {
        if (likedGifts.contains(gift)) {
            return true; // liked gift
        } else if (dislikedGifts.contains(gift)) {
            return false; // disliked gift
        }
        return null; // neutral gift
    }

    public List<Quest> getQuestsAvailable() {
        return questsAvailable;
    }
}