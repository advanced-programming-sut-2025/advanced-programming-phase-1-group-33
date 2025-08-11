package com.yourgame.model.Farming;


import com.yourgame.model.WeatherAndTime.Season;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MixedSeeds {
    private static final HashMap<Season, ArrayList<CropType>> seasonSeeds = new HashMap<>();

    static {
        seasonSeeds.put(Season.Spring, new ArrayList<>(Arrays.asList(CropType.Cauliflower, CropType.Parsnip,
                CropType.Potato, CropType.Blue_Jazz, CropType.Tulip)));
        seasonSeeds.put(Season.Summer, new ArrayList<>(Arrays.asList(CropType.Corn, CropType.Hot_Pepper,
                CropType.Radish, CropType.Wheat, CropType.Poppy, CropType.Sunflower, CropType.Summer_Spangle)));
        seasonSeeds.put(Season.Fall, new ArrayList<>(Arrays.asList(CropType.Artichoke, CropType.Corn,
                CropType.Eggplant, CropType.Pumpkin, CropType.Sunflower, CropType.Fairy_Rose)));
        seasonSeeds.put(Season.Winter, new ArrayList<>(List.of(CropType.PowderMelon)));
    }


    public static ArrayList<CropType> getSeasonCrops(Season season) {
        return seasonSeeds.get(season);
    }

}
