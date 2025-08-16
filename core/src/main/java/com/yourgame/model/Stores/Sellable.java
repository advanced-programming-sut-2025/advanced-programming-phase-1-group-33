package com.yourgame.model.Stores;


import com.yourgame.model.App;
import com.yourgame.model.Farming.Crop;
import com.yourgame.model.Farming.CropType;
import com.yourgame.model.Farming.Fruit;
import com.yourgame.model.Food.FoodType;
import com.yourgame.model.Item.*;
import com.yourgame.model.ManuFactor.ArtisanGood;
import com.yourgame.model.ManuFactor.ArtisanGoodType;
import com.yourgame.model.ManuFactor.Ingredient;
import com.yourgame.model.UserInfo.Player;

public interface Sellable {

    int getSellPrice();

    static Sellable getSellableByName(String name) {
        if (!isSellable(name))
            return null;

        Player player = App.getGameState().getCurrentPlayer();

        FoodType foodType = FoodType.getFoodByName(name);
        if (foodType != null) {
            return foodType;
        }

        Fruit fruit = Fruit.getByName(name);
        if (fruit != null) {
            return fruit;
        }

        ForagingMineral foragingMineral = ForagingMineral.getByName(name);
        if (foragingMineral != null) {
            return foragingMineral;
        }

        ArtisanGoodType artisanGoodType = ArtisanGoodType.getByName(name);
        if (artisanGoodType != null) {
            for (Ingredient ingredient : player.getBackpack().getIngredientQuantity().keySet()) {
                if (ingredient instanceof ArtisanGood artisanGood && artisanGood.getType().equals(artisanGoodType)) {
                    return artisanGood;
                }
            }
            return null;
        }

        return null;
    }

    static boolean isSellable(String name) {
        return  CropType.getCropTypeByName(name) != null ||
                FoodType.getFoodByName(name) != null ||
                Fruit.getByName(name) != null ||
                ForagingMineral.getByName(name) != null ||
                ArtisanGoodType.getByName(name) != null;

    }

    static String getNameInString(Sellable sellable) {
        if (sellable instanceof Crop){
            return ((Crop) sellable).getType().toString();
        }
        if (sellable instanceof FoodType){
            return sellable.toString();
        }
        if (sellable instanceof Fruit){
            return sellable.toString();
        }
        if (sellable instanceof ForagingMineral){
            return sellable.toString();
        }
        if (sellable instanceof ArtisanGood){
            return ((ArtisanGood) sellable).getType().toString();
        }
        return sellable.toString();
    }
}
