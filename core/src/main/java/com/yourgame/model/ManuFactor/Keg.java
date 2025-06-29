package com.yourgame.model.ManuFactor;


import com.yourgame.model.IO.Response;
import com.yourgame.model.Item.Crop;
import com.yourgame.model.Item.CropType;
import com.yourgame.model.Item.Fruit;
import com.yourgame.model.UserInfo.Player;
import com.yourgame.model.WeatherAndTime.TimeFrame;

public class Keg extends ArtisanMachine {

    public Keg() {
        super();
        processingTimes.put(new ArtisanGood(ArtisanGoodType.Beer), new TimeFrame(1, 0));
        processingTimes.put(new ArtisanGood(ArtisanGoodType.Vinegar), new TimeFrame(0, 10));
        processingTimes.put(new ArtisanGood(ArtisanGoodType.Coffee), new TimeFrame(0, 2));
        processingTimes.put(new ArtisanGood(ArtisanGoodType.Juice), new TimeFrame(4, 0));
        processingTimes.put(new ArtisanGood(ArtisanGoodType.Mead), new TimeFrame(0, 10));
        processingTimes.put(new ArtisanGood(ArtisanGoodType.PaleAle), new TimeFrame(3, 0));
        processingTimes.put(new ArtisanGood(ArtisanGoodType.Wine) , new TimeFrame(3, 0));
    }

    @Override
    public Response canUse(Player player, String product) {
        switch (product) {
            case "Beer", "beer" -> {
                for (Ingredient ingredient : player.getBackpack().getIngredientQuantity().keySet()) {
                    if (ingredient instanceof Crop crop && crop.getType().equals(CropType.Wheat)) {

                        if (player.getBackpack().getIngredientQuantity().getOrDefault(ingredient, 0) >= 1) {

                            player.getBackpack().removeIngredients(ingredient, 1);

                            producingGood = new ArtisanGood(ArtisanGoodType.Beer);
                            return new Response(true, "Your product is being made.Please wait.");
                        }
                        return new Response(false, "You don't have enough Ingredients!");
                    }
                }
                return new Response(false, "You don't have enough Ingredients!");
            }
            case "Vinegar", "vinegar" -> {
                for (Ingredient ingredient : player.getBackpack().getIngredientQuantity().keySet()) {
                    if (ingredient instanceof Crop crop && crop.getType().equals(CropType.UnMilledRice)) {

                        if (player.getBackpack().getIngredientQuantity().getOrDefault(ingredient, 0) >= 1) {

                            player.getBackpack().removeIngredients(ingredient, 1);

                            producingGood = new ArtisanGood(ArtisanGoodType.Vinegar);
                            return new Response(true, "Your product is being made.Please wait.");
                        }
                        return new Response(false, "You don't have enough Ingredients!");
                    }
                }
                return new Response(false, "You don't have enough Ingredients!");
            }
            case "Coffee", "coffee" -> {
                for (Ingredient ingredient : player.getBackpack().getIngredientQuantity().keySet()) {
                    if (ingredient instanceof Crop crop && crop.getType().equals(CropType.CoffeeBean)) {

                        if (player.getBackpack().getIngredientQuantity().getOrDefault(ingredient, 0) >= 5) {

                            player.getBackpack().removeIngredients(ingredient, 5);

                            producingGood = new ArtisanGood(ArtisanGoodType.Coffee);
                            return new Response(true, "Your product is being made.Please wait.");
                        }
                        return new Response(false, "You don't have enough Ingredients!");
                    }
                }
                return new Response(false, "You don't have enough Ingredients!");
            }
            case "Juice", "juice" -> {
                for (Ingredient ingredient : player.getBackpack().getIngredientQuantity().keySet()) {
                    if (ingredient instanceof Crop crop) {

                        if (player.getBackpack().getIngredientQuantity().getOrDefault(ingredient, 0) >= 1) {

                            player.getBackpack().removeIngredients(ingredient, 1);

                            producingGood = new ArtisanGood(ArtisanGoodType.Juice,
                                    2 * crop.getType().getEnergy(),
                                    (int) (2.25 * crop.getType().getBaseSellPrice()));
                            return new Response(true, "Your product is being made.Please wait.");
                        }
                        return new Response(false, "You don't have enough Ingredients!");
                    }
                }
                return new Response(false, "You don't have enough Ingredients!");
            }
            case "Mead", "mead" -> {
                for (Ingredient ingredient : player.getBackpack().getIngredientQuantity().keySet()) {
                    if (ingredient instanceof ArtisanGood artisanGood && artisanGood.getType().equals(ArtisanGoodType.Honey)) {

                        if (player.getBackpack().getIngredientQuantity().getOrDefault(ingredient, 0) >= 1) {

                            player.getBackpack().removeIngredients(ingredient, 1);

                            producingGood = new ArtisanGood(ArtisanGoodType.Mead);
                            return new Response(true, "Your product is being made.Please wait.");
                        }
                        return new Response(false, "You don't have enough Ingredients!");
                    }
                }
                return new Response(false, "You don't have enough Ingredients!");
            }
            case "Pale_Ale", "pale_ale" -> {
                for (Ingredient ingredient : player.getBackpack().getIngredientQuantity().keySet()) {
                    if (ingredient instanceof Crop crop && crop.getType().equals(CropType.Hops)) {

                        if (player.getBackpack().getIngredientQuantity().getOrDefault(ingredient, 0) >= 1) {

                            player.getBackpack().removeIngredients(ingredient, 1);

                            producingGood = new ArtisanGood(ArtisanGoodType.PaleAle);
                            return new Response(true, "Your product is being made.Please wait.");
                        }
                        return new Response(false, "You don't have enough Ingredients!");
                    }
                }
                return new Response(false, "You don't have enough Ingredients!");
            }
            case "Wine", "wine" -> {
                for (Ingredient ingredient : player.getBackpack().getIngredientQuantity().keySet()) {
                    if (ingredient instanceof Fruit) {

                        if (player.getBackpack().getIngredientQuantity().getOrDefault(ingredient, 0) >= 1) {

                            player.getBackpack().removeIngredients(ingredient, 1);

                            producingGood = new ArtisanGood(ArtisanGoodType.Wine,
                                    (int) (1.75 * ((Fruit) ingredient).getEnergy()),
                                    3 * ((Fruit) ingredient).getSellPrice());
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
