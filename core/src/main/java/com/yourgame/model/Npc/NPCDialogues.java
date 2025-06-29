package com.yourgame.model.Npc;

import java.util.ArrayList;

public enum NPCDialogues {

    AbigailDialogues(new ArrayList<>() {{
        add("Dad wants me to help at the store again... I'd rather be out enjoying the flowers."); // Summer - sunny
        add("Rain makes everything feel so dramatic. I kind of like it."); // Rainy
        add("Days like this make me want to grab my sword and find adventure... too bad there’s nowhere exciting around here."); // Spring and sunny
        add("The moon looks so cool tonight. Let's sit under it for a while."); // Night and Fall
        add("Snow days are the best excuse to sleep in and snack all day."); // Snowy
        add("I found a place near the river that looks magical at night... want me to show you sometime?"); // Night and high
        add("Oh hey! Wanna hang out for a bit?"); // general
    }}),

    SebastianDialogues(new ArrayList<>() {{
        add("Sun’s out... guess I’ll stay in the basement until it goes away."); // Summer - Sunny
        add("I could listen to this rain for hours. You too?"); // Rainy
        add("It’s too bright. I’ll be in my room with the blinds closed."); // Spring and sunny
        add("This wind makes me want to ride out of town for a bit. Want to come?"); // Night and Fall
        add("The snow makes everything feel quieter. I kinda like that."); // Snowy
        add("The night's quiet... but it's better with you around."); // Night and high
        add("I’ve been debugging this script for hours. It's kinda frustrating, honestly."); // general
    }}),

    HarveyDialogues(new ArrayList<>() {{
        add("Ah, fresh air! Perfect time to start new healthy habits."); // Summer - Sunny
        add("If you're going out, take an umbrella. You don’t want to catch a cold, do you?"); //Rainy
        add("Please don’t skip breakfast. It really is the most important meal"); // Spring and sunny
        add("Clear skies like this are rare. Makes me wish I’d gone into aviation."); // Fall and Night
        add("I packed extra vitamins today. Winter tends to wear people down."); // Snowy
        add("Would you like to join me for tea sometime? It’s calming after long workdays."); // Night and high
        add("Good to see you! Stay healthy. okay?"); //general
    }}),

    LeahDialogues(new ArrayList<>() {{
        add("I found the perfect log for a new sculpture today."); // summer - Sunny
        add("Rain gives everything a shine I can’t explain. I love sketching on days like this."); //Rainy
        add("I'm heading out to the forest to collect clay. It's peaceful out there."); // Spring and sunny
        add("The falling leaves remind me of home. I think I’ll start a new wood carving today."); // Fall and night
        add("Come by later if you want to warm up by the fire. I’ll be painting."); // Snowy
        add("You inspire me... more than you know. I think I’ll start a portrait"); // Night and high
        add("Hey! I was just out gathering some inspiration.");// general
    }}),

    RobinDialogues(new ArrayList<>() {{
        add("Perfect day to get things built!"); // summer - Sunny
        add("Rain delays construction, but at least I can catch up on blueprints"); //Rainy
        add("Working in this heat’s tough, but I love what I do."); // Spring and sunny
        add("I drew up some designs for your house today. Just in case."); // Fall and night
        add("Building indoors today. Too cold for roofing, even for me."); // Snowy
        add("The shop's closed,but it's nice to see you!"); //Night and high
        add("Hi there! Need any building work done soon?"); //general
    }});

    private final ArrayList<String> dialogues;
    NPCDialogues(ArrayList<String> dialogues) {
        this.dialogues = dialogues;
    }

    public ArrayList<String> getDialogues() {
        return dialogues;
    }
}
