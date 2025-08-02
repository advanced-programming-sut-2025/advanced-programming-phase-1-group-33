package com.yourgame.model.notification;

import com.yourgame.model.UserInfo.Player;

public class MarriageRequest extends Notification {

    public MarriageRequest(String message, Player sender) {
        super(message , sender);
    }

    @Override
    public String toString() {
        return "sender : " + sender.getUsername() + "  message: " + getMessage() +"   (Marriage Request)";
    }

}
