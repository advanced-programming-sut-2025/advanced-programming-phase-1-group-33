package com.yourgame.model;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.yourgame.model.Map.Coordinate;
import com.yourgame.model.Weather.TimeCondition;

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

    // Getter and Setter for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and Setter for currentLocation
    public Coordinate getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Coordinate currentLocation) {
        this.currentLocation = currentLocation;
    }

    // Getter and Setter for schedule
    public Map<TimeCondition, Coordinate> getSchedule() {
        return schedule;
    }

    public void setSchedule(Map<TimeCondition, Coordinate> schedule) {
        this.schedule = schedule;
    }

    // Getter and Setter for dialogue map
    public Map<DialogueTrigger, String> getDialogueMap() {
        return dialogue;
    }

    public void setDialogueMap(Map<DialogueTrigger, String> dialogue) {
        this.dialogue = dialogue;
    }

    // Schema method: getDialogue()
    public String getDialogue(DialogueTrigger trigger) {
        return dialogue.get(trigger);
    }

    // Getter and Setter for likedGifts
    public Set<Item> getLikedGifts() {
        return likedGifts;
    }

    public void setLikedGifts(Set<Item> likedGifts) {
        this.likedGifts = likedGifts;
    }

    // Getter and Setter for dislikedGifts
    public Set<Item> getDislikedGifts() {
        return dislikedGifts;
    }

    public void setDislikedGifts(Set<Item> dislikedGifts) {
        this.dislikedGifts = dislikedGifts;
    }

    // Getter and Setter for questsAvailable
    public List<Quest> getQuestsAvailable() {
        return questsAvailable;
    }

    public void setQuestsAvailable(List<Quest> questsAvailable) {
        this.questsAvailable = questsAvailable;
    }

    // Schema method: checkGiftPreference()
    // Returns Boolean: true if liked, false if disliked, or null for a neutral gift.
    public Boolean checkGiftPreference(Item gift) {
        if (likedGifts.contains(gift)) {
            return true;
        } else if (dislikedGifts.contains(gift)) {
            return false;
        }
        return null; // Neutral gift
    }
}
