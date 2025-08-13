package com.yourgame.model.NPC;

import com.yourgame.model.WeatherAndTime.Season;
import com.yourgame.model.WeatherAndTime.Weather;

import java.util.List;

public enum NPCType {
    Abigail("Gamer",
    List.of(
        new Dialogue("Dad wants me to help at the store again... I'd rather be out enjoying the flowers.", List.of(Season.Summer), List.of(Weather.Sunny), 0, false),
        new Dialogue("Rain makes everything feel so dramatic. I kind of like it.", List.of(), List.of(Weather.Rainy), 0, false),
        new Dialogue("Days like this make me want to grab my sword and find adventure...", List.of(Season.Spring), List.of(Weather.Sunny), 0, false),
        new Dialogue("I found a place near the river that looks magical at night... want me to show you sometime?", List.of(), List.of(), 4, false),
        new Dialogue("Oh hey! Wanna hang out for a bit?")
    )),
    Sebastian("Freelancer",
    List.of(
        new Dialogue("Sun’s out... guess I’ll stay in the basement until it goes away.", List.of(Season.Summer), List.of(Weather.Sunny), 0, false),
        new Dialogue("I could listen to this rain for hours. You too?", List.of(), List.of(Weather.Rainy), 0, false),
        new Dialogue("The night's quiet... but it's better with you around.", List.of(), List.of(), 4, false),
        new Dialogue("I’ve been debugging this script for hours. It's kinda frustrating, honestly.")
    )),
    Harvey("Town doctor",
    List.of(
        new Dialogue("Ah, fresh air! Perfect time to start new healthy habits.", List.of(Season.Summer), List.of(Weather.Sunny), 0, false),
        new Dialogue("If you're going out, take an umbrella. You don't want to catch a cold, do you?", List.of(), List.of(Weather.Rainy), 0, false),
        new Dialogue("Please don't skip breakfast. It really is the most important meal", List.of(Season.Spring), List.of(Weather.Sunny), 0, false),
        new Dialogue("Clear skies like this are rare. Makes me wish I'd gone into aviation.", List.of(Season.Fall), List.of(), 0, false),
        new Dialogue("I packed extra vitamins today. Winter tends to wear people down.", List.of(Season.Winter), List.of(Weather.Snowy), 2, false),
        new Dialogue("Would you like to join me for tea sometime? It’s calming after long workdays."),
        new Dialogue("Good to see you! Stay healthy. okay?")
    )),
    Leah("Artist",
    List.of(
        new Dialogue("I found the perfect log for a new sculpture today.", List.of(Season.Summer), List.of(Weather.Sunny), 0, false),
        new Dialogue("Rain gives everything a shine I can't explain. I love sketching on days like this.", List.of(), List.of(Weather.Rainy), 0, false),
        new Dialogue("I'm heading out to the forest to collect clay. It's peaceful out there.", List.of(Season.Spring), List.of(Weather.Sunny), 0, false),
        new Dialogue("The falling leaves remind me of home. I think I'll start a new wood carving today.", List.of(Season.Fall), List.of(), 0, false),
        new Dialogue("Come by later if you want to warm up by the fire. I'll be painting.", List.of(), List.of(Weather.Snowy), 0, false),
        new Dialogue("You inspire me... more than you know. I think I’ll start a portrait", List.of(), List.of(), 4, false),
        new Dialogue("Hey! I was just out gathering some inspiration.")
    )),
    Robin("Carpenter",
    List.of(
        new Dialogue("Perfect day to get things built!", List.of(Season.Summer), List.of(Weather.Sunny), 0, false),
        new Dialogue("Rain delays construction, but at least I can catch up on blueprints", List.of(), List.of(Weather.Rainy), 0, false),
        new Dialogue("Working in this heat's tough, but I love what I do.", List.of(Season.Spring), List.of(Weather.Sunny), 0, false),
        new Dialogue("I drew up some designs for your house today. Just in case.", List.of(Season.Fall), List.of(), 0, false),
        new Dialogue("Building indoors today. Too cold for roofing, even for me.", List.of(), List.of(Weather.Snowy), 0, false),
        new Dialogue("Hi there! Need any building work done soon?")
    ));

    private final String job;
    private final List<Dialogue> dialogues;

    NPCType(String job, List<Dialogue> dialogues) {
        this.job = job;
        this.dialogues = dialogues;
    }

    public String getName() {
        return name();
    }

    public String getJob() {
        return job;
    }

    public List<Dialogue> getDialogues() {
        return dialogues;
    }

    public int getMaxFriendShipLevel() {
        return 799;
    }
}
