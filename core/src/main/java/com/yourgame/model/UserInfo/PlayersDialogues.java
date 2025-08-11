package com.yourgame.model.UserInfo;

public class PlayersDialogues {
    private final Player sender;

    private final String dialogue;
    private final Player receiver;

    public PlayersDialogues(Player sender, Player receiver, String dialogue) {
        this.receiver = receiver;
        this.sender = sender;
        this.dialogue = dialogue;
    }

    public Player getReceiver() {
        return receiver;
    }

    public Player getSender() {
        return sender;
    }
}
