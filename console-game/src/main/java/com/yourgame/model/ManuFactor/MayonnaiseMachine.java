package com.yourgame.model.ManuFactor;


import com.yourgame.model.Animals.AnimalGood;
import com.yourgame.model.Animals.AnimalGoodType;
import com.yourgame.model.IO.Response;
import com.yourgame.model.UserInfo.Player;
import com.yourgame.model.WeatherAndTime.TimeFrame;

public class MayonnaiseMachine extends ArtisanMachine {

    public MayonnaiseMachine() {
        super();
        processingTimes.put(new ArtisanGood(ArtisanGoodType.Mayonnaise), new TimeFrame(0, 3));
        processingTimes.put(new ArtisanGood(ArtisanGoodType.DuckMayonnaise), new TimeFrame(0, 3));
        processingTimes.put(new ArtisanGood(ArtisanGoodType.DinosaurMayonnaise), new TimeFrame(0, 3));
    }

    @Override
    public Response canUse(Player player, String product) {
        switch (product) {
            case "Mayonnaise", "mayonnaise" -> {
                for (Ingredient ingredient : player.getBackpack().getIngredientQuantity().keySet()) {
                    if (ingredient instanceof AnimalGood animalGood &&
                            (animalGood.getType().equals(AnimalGoodType.Egg) ||
                                    animalGood.getType().equals(AnimalGoodType.LargeEgg))) {

                        if (player.getBackpack().getIngredientQuantity().getOrDefault(ingredient, 0) >= 1) {

                            player.getBackpack().removeIngredients(ingredient, 1);

                            producingGood = new ArtisanGood(ArtisanGoodType.Mayonnaise);
                            return new Response(true, "Your product is being made.Please wait.");
                        }
                        return new Response(false, "You don't have enough Ingredients!");
                    }
                }
                return new Response(false, "You don't have enough Ingredients!");
            }
            case "Duck_Mayonnaise", "duck_mayonnaise" -> {
                for (Ingredient ingredient : player.getBackpack().getIngredientQuantity().keySet()) {
                    if (ingredient instanceof AnimalGood animalGood && animalGood.getType().equals(AnimalGoodType.DuckEgg)) {

                        if (player.getBackpack().getIngredientQuantity().getOrDefault(ingredient, 0) >= 1) {

                            player.getBackpack().removeIngredients(ingredient, 1);

                            producingGood = new ArtisanGood(ArtisanGoodType.DuckMayonnaise);
                            return new Response(true, "Your product is being made.Please wait.");
                        }
                        return new Response(false, "You don't have enough Ingredients!");
                    }
                }
                return new Response(false, "You don't have enough Ingredients!");
            }
            case "Dinosaur_Mayonnaise", "dinosaur_mayonnaise" -> {
                for (Ingredient ingredient : player.getBackpack().getIngredientQuantity().keySet()) {
                    if (ingredient instanceof AnimalGood animalGood && animalGood.getType().equals(AnimalGoodType.DinosaurEgg)) {

                        if (player.getBackpack().getIngredientQuantity().getOrDefault(ingredient, 0) >= 1) {

                            player.getBackpack().removeIngredients(ingredient, 1);

                            producingGood = new ArtisanGood(ArtisanGoodType.DinosaurMayonnaise);
                            return new Response(true, "Your product is being made.Please wait.");
                        }
                        return new Response(false, "You don't have enough Ingredients!");
                    }
                }
                return new Response(false, "You don't have enough Ingredients!");
            }
        }
        return new Response(false, "This Machine can't make this Item!!");
    }
}
