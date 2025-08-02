package com.yourgame.model.ManuFactor;


import com.yourgame.model.IO.Response;
import com.yourgame.model.Item.Crop;
import com.yourgame.model.Item.Fruit;
import com.yourgame.model.UserInfo.Player;
import com.yourgame.model.WeatherAndTime.TimeFrame;

public class PreservesJar extends ArtisanMachine {

    public PreservesJar() {
        super();
        processingTimes.put(new ArtisanGood(ArtisanGoodType.Pickles), new TimeFrame(0, 6));
        processingTimes.put(new ArtisanGood(ArtisanGoodType.Jelly), new TimeFrame(3, 0));
    }

    @Override
    public Response canUse(Player player, String product) {
        if (product.equals("Pickles") || product.equals("pickles")) {
            for (Ingredient ingredient : player.getBackpack().getIngredientQuantity().keySet()) {
                if (ingredient instanceof Crop crop) {

                    if (player.getBackpack().getIngredientQuantity().getOrDefault(ingredient, 0) >= 1) {

                        player.getBackpack().removeIngredients(ingredient, 1);

                        producingGood = new ArtisanGood(ArtisanGoodType.Pickles,
                                (int) (1.75 * crop.getType().getEnergy()),
                                2 * crop.getType().getBaseSellPrice() + 50);
                        return new Response(true, "Your product is being made.Please wait.");
                    }
                    return new Response(false, "You don't have enough Ingredients!");
                }
            }
            return new Response(false, "You don't have enough Ingredients!");
        }
        else if (product.equals("Jelly") || product.equals("jelly")) {
            for (Ingredient ingredient : player.getBackpack().getIngredientQuantity().keySet()) {
                if (ingredient instanceof Crop crop) {
                    player.getBackpack().removeIngredients(ingredient, 1);
                    producingGood = new ArtisanGood(ArtisanGoodType.Jelly,
                            2 * crop.getType().getEnergy(),
                            2 * crop.getType().getBaseSellPrice() + 50);
                    return new Response(true, "Your product is being made.Please wait.");
                }
                if (ingredient instanceof Fruit fruit) {
                    player.getBackpack().removeIngredients(ingredient, 1);
                    producingGood = new ArtisanGood(ArtisanGoodType.Jelly,
                            2 * fruit.getEnergy(),
                            2 * fruit.getSellPrice());
                    return new Response(true, "Your product is being made.Please wait.");
                }
            }
            return new Response(false, "You don't have enough Ingredients!");
        }
        return new Response(false, "This Machine can't make this Item!!");
    }
}
