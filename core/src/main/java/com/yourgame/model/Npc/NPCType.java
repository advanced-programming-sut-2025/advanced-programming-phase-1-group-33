package com.yourgame.model.Npc;
import java.util.ArrayList;

import com.yourgame.model.enums.SymbolType;

public enum NPCType {

    Abigail("Abigail", "Gamer",
            NPCDialogues.AbigailDialogues.getDialogues(), SymbolType.ABIGAIL),
    Sebastian("Sebastian", "Freelancer",
            NPCDialogues.SebastianDialogues.getDialogues(), SymbolType.SEBASTIAN),
    Harvey("Harvey", "Town doctor",
            NPCDialogues.HarveyDialogues.getDialogues(), SymbolType.HARVEY),
    Leah("Leah", "Artist",
            NPCDialogues.LeahDialogues.getDialogues(), SymbolType.LEAH),
    Robin("Robin", "Carpenter",
            NPCDialogues.RobinDialogues.getDialogues(), SymbolType.ROBIN);

    private final String name;
    private final String job;
    private final ArrayList<String> dialogues;
    private final SymbolType symbol;

    NPCType(String name, String job, ArrayList<String> dialogues, SymbolType symbol) {
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

    public SymbolType getSymbol() {
        return symbol;
    }
}
