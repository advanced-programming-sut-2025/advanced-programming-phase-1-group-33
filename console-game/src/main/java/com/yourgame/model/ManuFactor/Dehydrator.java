package com.yourgame.model.ManuFactor;


import com.yourgame.model.IO.Response;
import com.yourgame.model.App;
import com.yourgame.model.Item.Crop;
import com.yourgame.model.Item.CropType;
import com.yourgame.model.Item.ForagingCrop;
import com.yourgame.model.Item.Fruit;
import com.yourgame.model.UserInfo.Player;

public class Dehydrator extends ArtisanMachine {

    public Dehydrator() {
        super();
    }

    @Override
    public Response isReady() {
        if (timeOfRequest == null)
            return new Response(false, "You don't have any artisan goods in machine yet!!");
        int todayDate = App.getGameState().getGameTime().getDay();
        if (App.getGameState().getGameTime().getSeason() != timeOfRequest.getSeason())
            todayDate += 28;

        //Next morning
        if (todayDate > timeOfRequest.getDay())
            return new Response(true, "Your product is Ready.");
        else
            return new Response(false, "Your product is Not Ready.");
    }

    @Override
    public Response canUse(Player player, String product) {
        switch (product) {
            case "Dried_Mushroom", "dried_mushroom" -> {
                for (Ingredient ingredient : player.getBackpack().getIngredientQuantity().keySet()) {

                    if (ingredient.equals(ForagingCrop.CommonMushroom) ||
                            ingredient.equals(ForagingCrop.RedMushroom) ||
                            ingredient.equals(ForagingCrop.PurpleMushroom)) {

                        if (player.getBackpack().getIngredientQuantity().get(ingredient) >= 5) {

                            player.getBackpack().removeIngredients(ingredient, 5);

                            producingGood = new ArtisanGood(ArtisanGoodType.DriedMushroom,
                                    50,
                                    (int) (7.5 * ((ForagingCrop) ingredient).getBaseSellPrice() + 25));
                            return new Response(true, "Your product is being made.Please wait.");
                        }
                        return new Response(false, "You don't have enough Ingredients!");
                    }
                }
                return new Response(false, "You don't have enough Ingredients!");
            }
            case "Dried_Fruit", "dried_fruit" -> {
                for (Ingredient ingredient : player.getBackpack().getIngredientQuantity().keySet()) {

                    if (ingredient instanceof Fruit ||
                            ingredient instanceof Crop crop && !(crop.getType().equals(CropType.Grape))) {

                        if (player.getBackpack().getIngredientQuantity().get(ingredient) >= 5) {

                            player.getBackpack().removeIngredients(ingredient, 5);

                            if (ingredient instanceof Fruit)
                                producingGood = new ArtisanGood(ArtisanGoodType.DriedFruit,
                                        75,
                                        (int) (7.5 * ((Fruit) ingredient).getBaseSellPrice() + 25));
                            else
                                producingGood = new ArtisanGood(ArtisanGoodType.DriedFruit,
                                        75,
                                        (int) (7.5 * ((Crop) ingredient).getType().getBaseSellPrice() + 25));
                            return new Response(true, "Your product is being made.Please wait.");
                        }
                        return new Response(false, "You don't have enough Ingredients!");
                    }
                }
                return new Response(false, "You don't have enough Ingredients!");
            }
            case "Raisins", "raisins" -> {
                for (Ingredient ingredient : player.getBackpack().getIngredientQuantity().keySet()) {

                    if (ingredient instanceof Crop crop && crop.getType().equals(CropType.Grape)) {

                        if (player.getBackpack().getIngredientQuantity().get(ingredient) >= 5) {

                            player.getBackpack().removeIngredients(ingredient, 5);

                            producingGood = new ArtisanGood(ArtisanGoodType.Raisins);
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
