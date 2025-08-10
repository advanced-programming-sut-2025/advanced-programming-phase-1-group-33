package com.yourgame.model.Farming;

import com.yourgame.model.ManuFactor.Ingredient;
import com.yourgame.model.WeatherAndTime.Season;
import java.util.HashMap;

public enum Seeds implements Ingredient {
    // Remove the CropType argument from here
    JazzSeeds(Season.Spring),
    CarrotSeeds(Season.Spring),
    CauliflowerSeeds(Season.Spring),
    CoffeeBean(Season.Spring),
    GarlicSeeds(Season.Spring),
    BeanStarter(Season.Spring),
    KaleSeeds(Season.Spring),
    ParsnipSeeds(Season.Spring),
    PotatoSeeds(Season.Spring),
    RhubarbSeeds(Season.Spring),
    StrawberrySeeds(Season.Spring),
    TulipBulb(Season.Spring),
    RiceShoot(Season.Spring),
    BlueberrySeeds(Season.Summer),
    CornSeeds(Season.Summer),
    HopsStarter(Season.Summer),
    PepperSeeds(Season.Summer),
    MelonSeeds(Season.Summer),
    PoppySeeds(Season.Summer),
    RadishSeeds(Season.Summer),
    RedCabbageSeeds(Season.Summer),
    StarfruitSeeds(Season.Summer),
    SpangleSeeds(Season.Summer),
    SummerSquashSeeds(Season.Summer),
    SunflowerSeeds(Season.Summer),
    TomatoSeeds(Season.Summer),
    WheatSeeds(Season.Summer),
    AmaranthSeeds(Season.Fall),
    ArtichokeSeeds(Season.Fall),
    BeetSeeds(Season.Fall),
    BokChoySeeds(Season.Fall),
    BroccoliSeeds(Season.Fall),
    CranberrySeeds(Season.Fall),
    EggplantSeeds(Season.Fall),
    FairySeeds(Season.Fall),
    GrapeStarter(Season.Fall),
    PumpkinSeeds(Season.Fall),
    YamSeeds(Season.Fall),
    RareSeed(Season.Fall),
    PowdermelonSeeds(Season.Winter),
    // AncientSeeds(Season.Special),
    // MixedSeeds(Season.Special);
    AncientSeeds(Season.Winter),
    MixedSeeds(Season.Winter);

    private final Season season;
    // Remove the CropType field
    // private CropType crop;

    private final static HashMap<String, Seeds> stringToSeeds = new HashMap<>();

    // Remove CropType cropType from the constructor
    Seeds(Season season) {
        // Remove setting the crop field
        // this.crop = cropType;
        this.season = season;
    }

    static {
        for (Seeds value : Seeds.values()) {
            stringToSeeds.put(value.name().toLowerCase(), value);
        }
    }

    public String getName() {
        return name();
    }

    public Season getSeason() {
        return season;
    }

    // Remove the getCrop() method
    /*
    public CropType getCrop() {
        return crop;
    }
    */

    public static Seeds getSeedByName(String name) {
        if (name == null || name.isEmpty())
            return null;
        return stringToSeeds.getOrDefault(name.trim().toLowerCase(), null);
    }
}
