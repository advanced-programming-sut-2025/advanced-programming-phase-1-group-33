package com.yourgame.model.Inventory;


public class TrashCan {
    enum Type {
        Primary(0 , 1000),
        Copper(15 , 2500),
        Iron(30 , 5000),
        Gold(45 , 12500),
        Iridium(60 , 0);

        private final int returnValuePercentage;
        private final int priceForUpgrade;
        Type(int returnValuePercentage , int priceForUpgrade) {
            this.returnValuePercentage = returnValuePercentage;
            this.priceForUpgrade = priceForUpgrade;
        }
    }

    private Type type = Type.Primary;

    public int getReturnValuePercentage() {
        return this.type.returnValuePercentage;
    }

    public void upgradeTool() {
        if(type == Type.Primary) {
            type = Type.Copper;
        }
        else if(type == Type.Copper) {
            type = Type.Iron;
        }
        else if(type == Type.Iron) {
            type = Type.Gold;
        }
        else if(type == Type.Gold) {
            type = Type.Iridium;
        }
    }
    public void useTool() {

    }
    public int getPriceForUpgrade() {
        return this.type.priceForUpgrade;
    }

}
