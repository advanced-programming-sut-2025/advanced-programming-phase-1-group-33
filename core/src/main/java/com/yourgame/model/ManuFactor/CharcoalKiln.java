package com.yourgame.model.ManuFactor;


import com.yourgame.model.IO.Response;
import com.yourgame.model.Resource.Wood;
import com.yourgame.model.UserInfo.Player;
import com.yourgame.model.WeatherAndTime.TimeFrame;

public class CharcoalKiln extends ArtisanMachine {

    public CharcoalKiln() {
        super();
        processingTimes.put(new ArtisanGood(ArtisanGoodType.Coal), new TimeFrame(0, 1));
    }

    @Override
    public Response canUse(Player player, String product) {
        if (product.equals("Coal") || product.equals("coal")) {
            for (Ingredient ingredient : player.getBackpack().getIngredientQuantity().keySet()) {

                if (ingredient instanceof Wood) {
                    if (player.getBackpack().getIngredientQuantity().getOrDefault(ingredient, 0 ) >= 10) {

                        player.getBackpack().removeIngredients(ingredient, 10);

                        producingGood = new ArtisanGood(ArtisanGoodType.Coal);
                        return new Response(true, "Your product is being made.Please wait.");
                    }
                    return new Response(false, "You don't have enough Ingredients!");
                }
            }
            return new Response(false, "You don't have enough Ingredients!");
        }
        return new Response(false, "This Machine can't make this Item!!");
    }
}
