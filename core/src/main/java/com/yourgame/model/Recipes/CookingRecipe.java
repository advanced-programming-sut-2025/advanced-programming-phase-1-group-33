package com.yourgame.model.Recipes;

import com.yourgame.model.Animals.AnimalGoodType;
import com.yourgame.model.Animals.FishPackage.Fish;
import com.yourgame.model.Farming.CropType;
import com.yourgame.model.Food.FoodType;
import com.yourgame.model.Farming.ForagingCrop;
import com.yourgame.model.Farming.Fruit;
import com.yourgame.model.ManuFactor.ArtisanGood;
import com.yourgame.model.ManuFactor.ArtisanGoodType;
import com.yourgame.model.ManuFactor.Ingredient;

import java.util.HashMap;
import java.util.Map;

public enum CookingRecipe implements Ingredient {
    FriedEgg(new HashMap<>(Map.of(AnimalGoodType.Egg, 1))),
    BakedFish(new HashMap<>(Map.of(Fish.Sardine, 1, Fish.Salmon, 1, CropType.Wheat, 1))),
    Salad(new HashMap<>(Map.of(ForagingCrop.Leek, 1, ForagingCrop.Dandelion, 1))),
    Omelet(new HashMap<>(Map.of(AnimalGoodType.Egg, 1, AnimalGoodType.Milk, 1))),
    PumpkinPie(new HashMap<>(Map.of(CropType.Pumpkin, 1, CropType.Wheat, 1, AnimalGoodType.Milk, 1))),
    Spaghetti(new HashMap<>(Map.of(CropType.Wheat, 1, CropType.Tomato, 1))),
    Pizza(new HashMap<>(Map.of(CropType.Wheat, 1, CropType.Tomato, 1, new ArtisanGood(ArtisanGoodType.CheeseByMilk), 1))),
    Tortilla(new HashMap<>(Map.of(CropType.Corn, 1))),
    MakiRoll(new HashMap<>(Map.of(Fish.Salmon, 1, CropType.Unmilled_Rice, 1))),
    TripleShotEspresso(new HashMap<>(Map.of(new ArtisanGood(ArtisanGoodType.Coffee), 3))),
    Cookie(new HashMap<>(Map.of(CropType.Wheat, 1, AnimalGoodType.Egg, 1))),
    HashBrowns(new HashMap<>(Map.of(CropType.Potato, 1, new ArtisanGood(ArtisanGoodType.Oil), 1))),
    Pancakes(new HashMap<>(Map.of(CropType.Wheat, 1, AnimalGoodType.Egg, 1))),
    FruitSalad(new HashMap<>(Map.of(CropType.Blueberry, 1, CropType.Melon, 1, Fruit.Apricot, 1))),
    RedPlate(new HashMap<>(Map.of(CropType.Red_Cabbage, 1, CropType.Radish, 1))),
    Bread(new HashMap<>(Map.of(CropType.Wheat, 1))),
    SalmonDinner(new HashMap<>(Map.of(Fish.Salmon, 1, CropType.Amaranth, 1, CropType.Kale, 1))),
    VegetableMedley(new HashMap<>(Map.of(CropType.Tomato, 1, CropType.Beet, 1))),
    FarmersLunch(new HashMap<>(Map.of(FoodType.Omelet, 1, CropType.Parsnip, 1))),
    SurvivalBurger(new HashMap<>(Map.of(FoodType.Bread, 1, CropType.Carrot, 1, CropType.Eggplant, 1))),
    DishOTheSea(new HashMap<>(Map.of(Fish.Sardine, 2, FoodType.HashBrowns, 1))),
    SeaFormPudding(new HashMap<>(Map.of(Fish.Flounder, 1, Fish.MidnightCarp, 1))),
    MinersTreat(new HashMap<>(Map.of(CropType.Carrot, 1, AnimalGoodType.Milk, 1)));

    private final HashMap<Ingredient, Integer> ingredients;
    private final static HashMap<String, CookingRecipe> stringToRecipes = new HashMap<>();

    static {
        for (CookingRecipe value : CookingRecipe.values()) {
            stringToRecipes.put(value.name().toLowerCase(), value);
        }
    }

    CookingRecipe(HashMap<Ingredient, Integer> ingredients) {
        this.ingredients = ingredients;
    }

    public HashMap<Ingredient, Integer> getIngredients() {
        return ingredients;
    }

    public static CookingRecipe getRecipeByName(String name) {
        if (name == null || name.isEmpty())
            return null;
        return stringToRecipes.getOrDefault(name.toLowerCase(), null);
    }
}
