package com.yourgame.model.UserInfo;

import com.yourgame.model.Stores.Sellable;

public class BetweenPlayersGift {
    private final Sellable product;
    private final Player sender;
    private final Player receiver;
    private final int id;
    private int rate = 3;
    private boolean isRated;

    public BetweenPlayersGift(Sellable sellable, Player sender, Player receiver, int id) {
        this.product = sellable;
        this.sender = sender;
        this.receiver = receiver;
        this.id = id;
        this.isRated = false;
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

    @Override
    public String toString() {
        return "id:" + id + "   sender:" +  sender.getUsername() + "    rate:" + rate + "   " + Sellable.getNameInString(product);
    }

    public String toStringWithReceiver() {
        return "id:" + id + "   sender:" +  sender.getUsername() + "    receiver:" + receiver.getUsername() + "    rate:" + rate + "   " + Sellable.getNameInString(product);
    }
}
