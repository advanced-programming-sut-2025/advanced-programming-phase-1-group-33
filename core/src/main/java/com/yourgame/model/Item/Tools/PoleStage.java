package com.yourgame.model.Item.Tools;

public enum PoleStage {
    Training(0.1 , 2000),
    Bamboo(0.5 , 5000),
    Fiberglass(0.9, 10000),
    Iridium(1.2 ,12500 );

    private final double effectiveness;
    private final int priceForUpgrade;

    PoleStage(double effectiveness , int priceForUpgrade) {
        this.effectiveness = effectiveness;
        this.priceForUpgrade = priceForUpgrade;
    }

    public double getEffectiveness() {
        return effectiveness;
    }
    public int getPriceForUpgrade() {
        return priceForUpgrade;
    }
}
