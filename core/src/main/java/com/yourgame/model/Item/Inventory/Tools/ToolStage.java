package com.yourgame.model.Item.Inventory.Tools;

public enum ToolStage {
    Primary(2000),
    Copper(5000),
    Steel(10000),
    Gold(25000),
    Iridium(0);

    private final int priceForUpgrade;

    ToolStage(int priceForUpgrade) {
        this.priceForUpgrade = priceForUpgrade;
    }

    public int getPriceForUpgrade() {
        return priceForUpgrade;
    }

    @Override
    public String toString() {
        return String.format("%s (Upgrade Cost: %d)", this.name(), this.priceForUpgrade);
    }
}
