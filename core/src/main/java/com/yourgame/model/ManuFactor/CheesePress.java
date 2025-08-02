package com.yourgame.model.ManuFactor;


import com.yourgame.model.Animals.AnimalGood;
import com.yourgame.model.Animals.AnimalGoodType;
import com.yourgame.model.IO.Response;
import com.yourgame.model.UserInfo.Player;
import com.yourgame.model.WeatherAndTime.TimeFrame;

public class CheesePress extends ArtisanMachine {

    public CheesePress() {
        super();
        processingTimes.put(new ArtisanGood(ArtisanGoodType.CheeseByMilk), new TimeFrame(0, 3));
        processingTimes.put(new ArtisanGood(ArtisanGoodType.CheeseByLargeMilk), new TimeFrame(0, 3));
        processingTimes.put(new ArtisanGood(ArtisanGoodType.GoatCheeseByMilk), new TimeFrame(0, 3));
        processingTimes.put(new ArtisanGood(ArtisanGoodType.GoatCheeseByLargeMilk), new TimeFrame(0, 3));

    }

    @Override
    public Response canUse(Player player, String product) {
        if (product.equals("Cheese") || product.equals("cheese")) {
            for (Ingredient ingredient : player.getBackpack().getIngredientQuantity().keySet()) {

                if (ingredient instanceof AnimalGood animalGood &&
                        (animalGood.getType().equals(AnimalGoodType.Milk) ||
                                animalGood.getType().equals(AnimalGoodType.LargeMilk))) {

                    if (player.getBackpack().getIngredientQuantity().getOrDefault(ingredient, 0 ) >= 1) {

                        player.getBackpack().removeIngredients(ingredient, 1);

                        producingGood = animalGood.getType().equals(AnimalGoodType.Milk) ?
                                new ArtisanGood(ArtisanGoodType.CheeseByMilk) : new ArtisanGood(ArtisanGoodType.CheeseByLargeMilk);
                        return new Response(true, "Your product is being made.Please wait.");
                    }
                    return new Response(false, "You don't have enough Ingredients!");
                }
            }
            return new Response(false, "You don't have enough Ingredients!");
        }
        else if (product.equals("Goat_Cheese") || product.equals("goat_cheese")) {
            for (Ingredient ingredient : player.getBackpack().getIngredientQuantity().keySet()) {

                if (ingredient instanceof AnimalGood animalGood &&
                        (animalGood.getType().equals(AnimalGoodType.GoatMilk) ||
                                animalGood.getType().equals(AnimalGoodType.LargeGoatMilk))) {

                    if (player.getBackpack().getIngredientQuantity().getOrDefault(ingredient, 0) >= 1) {

                        player.getBackpack().removeIngredients(ingredient, 1);

                        producingGood = animalGood.getType().equals(AnimalGoodType.GoatMilk) ?
                                new ArtisanGood(ArtisanGoodType.GoatCheeseByMilk) :
                                new ArtisanGood(ArtisanGoodType.GoatCheeseByLargeMilk);
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
