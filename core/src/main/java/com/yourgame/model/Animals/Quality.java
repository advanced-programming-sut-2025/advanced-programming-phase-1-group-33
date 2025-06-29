package com.yourgame.model.Animals;

public enum Quality {
    Regular(1),
    Silver(1.25),
    Gold(1.5),
    Iridium(2);

    private final double ratio;

    Quality(double ratio) {
        this.ratio = ratio;
    }

    public double getRatio() {
        return ratio;
    }

    public static Quality getQualityByValue(double qualityValue) {
        if (qualityValue >= 0 && qualityValue <= 0.5)
            return Quality.Regular;
        else if (qualityValue > 0.5 && qualityValue <= 0.7)
            return Quality.Silver;
        else if (qualityValue > 0.7 && qualityValue <= 0.9)
            return Quality.Gold;
        else
            return Quality.Iridium;
    }
}
