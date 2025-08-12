package com.yourgame.model.Map.Store.PierreGeneralStore;

import com.yourgame.model.Farming.Seeds;
import com.yourgame.model.Map.Store.ShopItem;
import com.yourgame.model.WeatherAndTime.Season;

public class PierreGeneralStoreSeedsItem extends ShopItem {

    private final int priceOutOfSeason;
    private final Seeds seeds;
    private final Season season;


    public PierreGeneralStoreSeedsItem(String name, ItemType type, int priceOutOfSeason, int price,boolean isStackable, int dailyLimit, String filePath, Seeds seed , Season season) {
        super(name, type, price, isStackable, dailyLimit, filePath);
        this.seeds = seed;
        this.season = season;
        this.priceOutOfSeason = priceOutOfSeason;
    }

    public Seeds getSeeds() {
        return seeds;
    }

    public Season getSeason() {
        return season;
    }

    public int getPriceOutOfSeason() {
        return priceOutOfSeason;
    }
}
