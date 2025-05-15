package com.yourgame.model.Npc;
import java.util.ArrayList;

public enum NPCType {

    Abigail("Abigail", "Gamer",
            NPCDialogues.AbigailDialogues.getDialogues(), 'a'),
    Sebastian("Sebastian", "Freelancer",
            NPCDialogues.SebastianDialogues.getDialogues(), 's'),
    Harvey("Harvey", "Town doctor",
            NPCDialogues.HarveyDialogues.getDialogues(), 'h'),
    Leah("Leah", "Artist",
            NPCDialogues.LeahDialogues.getDialogues(), 'l'),
    Robin("Robin", "Carpenter",
            NPCDialogues.RobinDialogues.getDialogues(), 'r');

    private final String name;
    private final String job;
    private final ArrayList<String> dialogues;
    private final char symbol;

    NPCType(String name, String job, ArrayList<String> dialogues, char symbol) {
        this.name = name;
        this.job = job;
        this.dialogues = dialogues;
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public String getJob() {
        return job;
    }

    public ArrayList<String> getDialogues() {
        return dialogues;
    }

    public int getMaxFriendShipLevel() {
        return 799;
    }

    public char getSymbol() {
        return symbol;
    }
}