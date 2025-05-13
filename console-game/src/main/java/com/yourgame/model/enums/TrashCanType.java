package com.yourgame.model.enums;

public enum TrashCanType {
    DEFAULT(0),
    COPPER(15),
    IRON(30),
    GOLD(45),
    IRIDIUM(60),
    ;
    final public int refundPercentage;

    TrashCanType(int refundPercentage) {
        this.refundPercentage = refundPercentage;
    }
}
