package com.yourgame.model.ManuFactor;

import com.yourgame.model.Animals.Fish;
import com.yourgame.model.Animals.FishType;
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
        FishType fishType = FishType.getFishTypeByName(product);
        if (fishType != null) {
            for (Ingredient ingredient : player.getBackpack().getIngredientQuantity().keySet()) {

                if (ingredient instanceof Fish fish && fish.getType().equals(fishType)) {
                    for (Ingredient ingredient1 : player.getBackpack().getIngredientQuantity().keySet()) {
                        if (ingredient1.equals(new ArtisanGood(ArtisanGoodType.Coal))) {

                            player.getBackpack().removeIngredients(ingredient, 1);
                            player.getBackpack().removeIngredients(ingredient1, 1);

                            producingGood = new ArtisanGood(ArtisanGoodType.SmokedFish,
                                    (int) (1.5 * fish.getType().getPrice()),
                                    2 * fish.getType().getPrice());
                            return new Response(true, "Your product is being made.Please wait.");
                        }
                    }
                }
            }
            return new Response(false, "You don't have enough Ingredients!");
        }
        return new Response(false, "This Machine can't make this Item!!");
    }
}
