package com.yourgame.model.ManuFactor;

import com.yourgame.model.IO.Response;
import com.yourgame.model.UserInfo.Player;
import com.yourgame.model.App;

public class FishSmoker extends ArtisanMachine {

    @Override
    public Response isReady() {
        if (timeOfRequest == null)
            return new Response(false, "You don't have any artisan goods in machine yet!!");
        if (timeOfRequest.getDay() + processingTimes.get(producingGood).getDays() < App.getGameState().getGameTime().getDay() ||
                timeOfRequest.getHour() + 1 <= App.getGameState().getGameTime().getHour())
            return new Response(true, "Your product is Ready.");
        return new Response(false, "Your product is Not Ready.");
    }

    @Override
    public Response canUse(Player player, String product) {
        return new Response(false, "This Machine can't make this Item!!");
    }
}
