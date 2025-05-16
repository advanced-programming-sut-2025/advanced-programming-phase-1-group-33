package com.yourgame.model.UserInfo;

public class DialoguesBetweenPlayers {
    private final Player sender;

    private final Player receiver;
    private final String dialogue;

    public DialoguesBetweenPlayers(Player sender, Player receiver, String dialogue) {
        this.sender = sender;
        this.receiver = receiver;
        this.dialogue = dialogue;
    }

    public Player getReceiver() {
        return receiver;
    }

    public Player getSender() {
        return sender;
    }

    @Override
    public String toString() {
        return sender.getUsername() + " : " + dialogue;
    }

}
