package com.yourgame.model.ManuFactor;


import com.yourgame.model.IO.Response;
import com.yourgame.model.UserInfo.Player;
import com.yourgame.model.WeatherAndTime.TimeFrame;

public class BeeHouse extends ArtisanMachine {

    public BeeHouse() {
        super();
        processingTimes.put(new ArtisanGood(ArtisanGoodType.Honey), new TimeFrame(4, 0));
    }

    @Override
    public Response canUse(Player player, String product) {
        producingGood = new ArtisanGood(ArtisanGoodType.Honey);
        return new Response(true, "Your product is being made.Please wait.");
    }
}
