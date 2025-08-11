package com.yourgame.model.ManuFactor;


import com.yourgame.model.Animals.AnimalGood;
import com.yourgame.model.Animals.AnimalGoodType;
import com.yourgame.model.IO.Response;
import com.yourgame.model.Farming.Crop;
import com.yourgame.model.Farming.CropType;
import com.yourgame.model.Farming.Seeds;
import com.yourgame.model.UserInfo.Player;
import com.yourgame.model.WeatherAndTime.TimeFrame;

public class OilMaker extends ArtisanMachine {

    public OilMaker() {
        super();
        processingTimes.put(new ArtisanGood(ArtisanGoodType.TruffleOil), new TimeFrame(0, 6));
        //for oil, it will calculate dynamically
    }

    @Override
    public Response canUse(Player player, String product) {
        if (product.equals("Truffle_Oil") || product.equals("truffle_oil")) {
            for (Ingredient ingredient : player.getBackpack().getIngredientQuantity().keySet()) {
                if (ingredient instanceof AnimalGood animalGood && animalGood.getType().equals(AnimalGoodType.Truffle)) {

                    if (player.getBackpack().getIngredientQuantity().getOrDefault(ingredient, 0) >= 1) {

                        player.getBackpack().removeIngredients(ingredient, 1);

                        producingGood = new ArtisanGood(ArtisanGoodType.TruffleOil);
                        return new Response(true, "Your product is being made.Please wait.");
                    }
                    return new Response(false, "You don't have enough Ingredients!");
                }
            }
            return new Response(false, "You don't have enough Ingredients!");
        }
        else if (product.equals("Oil") || product.equals("oil")) {
            for (Ingredient ingredient : player.getBackpack().getIngredientQuantity().keySet()) {
                if (ingredient instanceof Crop crop && crop.getType().equals(CropType.Corn)) {
                    player.getBackpack().removeIngredients(ingredient, 1);
                    producingGood = new ArtisanGood(ArtisanGoodType.Oil);
                    processingTimes.put(new ArtisanGood(ArtisanGoodType.Oil), new TimeFrame(0, 6));
                    return new Response(true, "Your product is being made.Please wait.");
                }
                if (ingredient.equals(Seeds.Sunflower_Seeds)) {
                    player.getBackpack().removeIngredients(ingredient, 1);
                    producingGood = new ArtisanGood(ArtisanGoodType.Oil);
                    processingTimes.put(new ArtisanGood(ArtisanGoodType.Oil), new TimeFrame(2, 0));
                    return new Response(true, "Your product is being made.Please wait.");
                }
                if (ingredient instanceof Crop crop && crop.getType().equals(CropType.Sunflower)) {
                    player.getBackpack().removeIngredients(ingredient, 1);
                    producingGood = new ArtisanGood(ArtisanGoodType.Oil);
                    processingTimes.put(new ArtisanGood(ArtisanGoodType.Oil), new TimeFrame(0, 1));
                    return new Response(true, "Your product is being made.Please wait.");
                }
            }
            return new Response(false, "You don't have enough Ingredients!");
        }
        return new Response(false, "This Machine can't make this Item!!");
    }
}
