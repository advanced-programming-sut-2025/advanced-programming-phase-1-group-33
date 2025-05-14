package com.yourgame.model.Item;

import com.yourgame.model.ManuFactor.Ingredient;
import com.yourgame.model.WeatherAndTime.Season;

import java.util.HashMap;

public enum Seeds implements Ingredient {
    JazzSeeds(Season.Spring, CropType.BlueJazz),
    CarrotSeeds(Season.Spring, CropType.Carrot),
    CauliflowerSeeds(Season.Spring, CropType.Cauliflower),
    CoffeeBean(Season.Spring, CropType.CoffeeBean),
    GarlicSeeds(Season.Spring, CropType.Garlic),
    BeanStarter(Season.Spring, CropType.GreenBean),
    KaleSeeds(Season.Spring, CropType.Kale),
    ParsnipSeeds(Season.Spring, CropType.Parsnip),
    PotatoSeeds(Season.Spring, CropType.Potato),
    RhubarbSeeds(Season.Spring, CropType.Rhubarb),
    StrawberrySeeds(Season.Spring, CropType.Strawberry),
    TulipBulb(Season.Spring, CropType.Tulip),
    RiceShoot(Season.Spring, CropType.UnMilledRice),
    BlueberrySeeds(Season.Summer, CropType.Blueberry),
    CornSeeds(Season.Summer, CropType.Corn),
    HopsStarter(Season.Summer, CropType.Hops),
    PepperSeeds(Season.Summer, CropType.HotPepper),
    MelonSeeds(Season.Summer, CropType.Melon),
    PoppySeeds(Season.Summer, CropType.Poppy),
    RadishSeeds(Season.Summer, CropType.Radish),
    RedCabbageSeeds(Season.Summer, CropType.RedCabbage),
    StarfruitSeeds(Season.Summer, CropType.Starfruit),
    SpangleSeeds(Season.Summer, CropType.SummerSpangle),
    SummerSquashSeeds(Season.Summer, CropType.SummerSquash),
    SunflowerSeeds(Season.Summer, CropType.Sunflower),
    TomatoSeeds(Season.Summer, CropType.Tomato),
    WheatSeeds(Season.Summer, CropType.Wheat),
    AmaranthSeeds(Season.Fall,CropType.Amaranth ),
    ArtichokeSeeds(Season.Fall, CropType.Artichoke),
    BeetSeeds(Season.Fall, CropType.Beet),
    BokChoySeeds(Season.Fall, CropType.BokChoy),
    BroccoliSeeds(Season.Fall, CropType.Broccoli),
    CranberrySeeds(Season.Fall, CropType.Cranberries),
    EggplantSeeds(Season.Fall, CropType.Eggplant),
    FairySeeds(Season.Fall, CropType.FairyRose),
    GrapeStarter(Season.Fall, CropType.Grape),
    PumpkinSeeds(Season.Fall, CropType.Pumpkin),
    YamSeeds(Season.Fall, CropType.Yam),
    RareSeed(Season.Fall, CropType.SweetGemBerry),
    PowdermelonSeeds(Season.Winter, CropType.PowderMelon),
    AncientSeeds(Season.Special, CropType.AncientFruit),
    MixedSeeds(Season.Special, null);

    private final Season season;
    private final CropType crop;
    private final static HashMap<String, Seeds> stringToSeeds = new HashMap<>();

    static {
        for (Seeds value : Seeds.values()) {
            stringToSeeds.put(value.name().toLowerCase(), value);
        }
    }

    Seeds(Season season, CropType crop) {
        this.season = season;
        this.crop = crop;
    }

    public String getName(){
        return name();
    }

    public Season getSeason() {
        return season;
    }

    public CropType getCrop() {
        return crop;
    }

    public static Seeds getSeedByName(String name) {
        if (name == null || name.isEmpty())
            return null;
        return stringToSeeds.getOrDefault(name.toLowerCase(), null);
    }

}
