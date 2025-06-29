package com.yourgame.model.Stores;


import com.yourgame.model.Item.Seeds;
import com.yourgame.model.WeatherAndTime.Season;

public class PierreGeneralStoreSeedsItem extends ShopItem {

    private final Seeds seeds;
    private final Season season;


    public PierreGeneralStoreSeedsItem(String name, Seeds seed , Season season,int price, int dailyLimit) {
        super(name, price, dailyLimit);
        this.seeds = seed;
        this.season = season;
    }

    public Seeds getSeeds() {
        return seeds;
    }

    public Season getSeason() {
        return season;
    }
}
