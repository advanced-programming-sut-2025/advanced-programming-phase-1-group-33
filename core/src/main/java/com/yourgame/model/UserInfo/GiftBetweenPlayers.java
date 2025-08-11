package com.yourgame.model.UserInfo;

import com.yourgame.model.Stores.Sellable;

public class GiftBetweenPlayers {
    private int rate = 3;
    private final Sellable product;
    private final Player receiver;
    private final Player sender;
    private final int id;
    private boolean isRated;

    public GiftBetweenPlayers(Sellable sellable, Player sender, Player receiver, int id) {
        this.receiver = receiver;
        this.product = sellable;
        this.sender = sender;
        this.isRated = false;
        this.id = id;
    }

    public Sellable getProduct() {
        return product;
    }

    public int getId() {
        return id;
    }

    public Player getReceiver() {
        return receiver;
    }

    public Player getSender() {
        return sender;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public boolean isRated() {
        return isRated;
    }

    public void setRated() {
        isRated = true;
    }
}
