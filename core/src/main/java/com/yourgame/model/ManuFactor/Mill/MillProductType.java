package com.yourgame.model.ManuFactor.Mill;

public enum MillProductType {
    WheatFlour("WheatFlour",50),
    Sugar("Sugar", 50),
    Rice("Rice", 100);

    private final String name;
    private final int sellPrice;

    MillProductType(String name, int sellPrice) {
        this.name = name;
        this.sellPrice = sellPrice;
    }

    public String getName() {
        return name;
    }

    public int getSellPrice() {
        return sellPrice;
    }
}
