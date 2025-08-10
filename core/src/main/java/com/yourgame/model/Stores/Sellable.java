package com.yourgame.model.Stores;


import com.yourgame.model.Animals.AnimalGood;
import com.yourgame.model.Animals.AnimalGoodType;
import com.yourgame.model.Animals.Fish;
import com.yourgame.model.Animals.FishType;
import com.yourgame.model.App;
import com.yourgame.model.Farming.Crop;
import com.yourgame.model.Farming.CropType;
import com.yourgame.model.Farming.Fruit;
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

        AnimalGoodType animalGoodType = AnimalGoodType.getAnimalGoodTypeByName(name);
        if (animalGoodType != null) {
            for (Ingredient ingredient : player.getBackpack().getIngredientQuantity().keySet()) {
                if (ingredient instanceof AnimalGood animalGood && animalGood.getType().equals(animalGoodType)) {
                    return animalGood;
                }
            }
            return null;
        }

        CropType cropType = CropType.getCropTypeByName(name);
        if (cropType != null) {
            for (Ingredient ingredient : player.getBackpack().getIngredientQuantity().keySet()) {
                if (ingredient instanceof Crop crop && crop.getType().equals(cropType)) {
                    return crop;
                }
            }
            return null;
        }

        FishType fishType = FishType.getFishTypeByName(name);
        if (fishType != null) {
            for (Ingredient ingredient : player.getBackpack().getIngredientQuantity().keySet()) {
                if (ingredient instanceof Fish fish && fish.getType().equals(fishType)) {
                    return fish;
                }
            }
            return null;
        }

        Food food = Food.getFoodByName(name);
        if (food != null) {
            return food;
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
        return  AnimalGoodType.getAnimalGoodTypeByName(name) != null ||
                CropType.getCropTypeByName(name) != null ||
                FishType.getFishTypeByName(name) != null ||
                Food.getFoodByName(name) != null ||
                Fruit.getByName(name) != null ||
                ForagingMineral.getByName(name) != null ||
                ArtisanGoodType.getByName(name) != null;

    }

    static String getNameInString(Sellable sellable) {
        if (sellable instanceof AnimalGood){
            return ((AnimalGood) sellable).getType().toString();
        }
        if (sellable instanceof Crop){
            return ((Crop) sellable).getType().toString();
        }
        if (sellable instanceof Fish){
            return ((Fish) sellable).getType().toString();
        }
        if (sellable instanceof Food){
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
