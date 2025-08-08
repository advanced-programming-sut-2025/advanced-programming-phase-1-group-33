package com.yourgame.model.Item.Inventory.Tools;

public enum ToolStage {
    Primary(2000),
    Coppery(5000),
    Metal(10000),
    Golden(25000),
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
