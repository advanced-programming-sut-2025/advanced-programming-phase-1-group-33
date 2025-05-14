package com.yourgame.model.ManuFactor;


import com.yourgame.model.Animals.AnimalGood;
import com.yourgame.model.Animals.AnimalGoodType;
import com.yourgame.model.IO.Response;
import com.yourgame.model.UserInfo.Player;
import com.yourgame.model.WeatherAndTime.TimeFrame;

public class Loom extends ArtisanMachine {

    public Loom() {
        super();
        processingTimes.put(new ArtisanGood(ArtisanGoodType.Cloth), new TimeFrame(0, 4));
    }

    @Override
    public Response canUse(Player player, String product) {
        if (product.equals("Cloth") || product.equals("cloth")) {
            for (Ingredient ingredient : player.getBackpack().getIngredientQuantity().keySet()) {
                if (ingredient instanceof AnimalGood animalGood && animalGood.getType().equals(AnimalGoodType.Wool)) {

                    if (player.getBackpack().getIngredientQuantity().get(ingredient) >= 1) {

                        player.getBackpack().removeIngredients(ingredient, 1);

                        producingGood = new ArtisanGood(ArtisanGoodType.Cloth);
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
