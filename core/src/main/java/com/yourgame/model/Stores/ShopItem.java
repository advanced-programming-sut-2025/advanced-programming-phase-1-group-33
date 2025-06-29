package com.yourgame.model.Stores;

public class ShopItem {

    protected final String name;
    protected final int price;
    protected final Integer dailyLimit;
    protected Integer remainingQuantity;

    public ShopItem(String name, int price, int dailyLimit) {
        this.name = name;
        this.price = price;
        this.dailyLimit = dailyLimit;
        this.remainingQuantity = dailyLimit;
    }

    public void resetQuantityEveryNight() {
        this.remainingQuantity = dailyLimit;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public void decreaseRemainingQuantity(int quantity) {
        remainingQuantity -= quantity;
    }

    public Integer getRemainingQuantity() {
        return remainingQuantity;
    }

}
