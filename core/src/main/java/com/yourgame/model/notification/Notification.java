package com.yourgame.model.notification;

import com.yourgame.model.UserInfo.Player;

public class Notification {
    protected final String message;
    protected boolean isChecked;
    protected final Player sender;

    public Notification(String message, Player sender) {
        this.message = message;
        this.isChecked = false;
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public Player getSender() {
        return sender;
    }
}
