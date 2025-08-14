package com.yourgame.model.Food.Cooking;

import com.yourgame.model.Animals.AnimalPackage.Animal;
import com.yourgame.model.Animals.AnimalPackage.AnimalProduct;
import com.yourgame.model.Animals.AnimalPackage.AnimalProductType;
import com.yourgame.model.Animals.FishPackage.Fish;
import com.yourgame.model.Animals.FishPackage.FishItem;
import com.yourgame.model.Farming.CropItem;
import com.yourgame.model.Farming.CropType;
import com.yourgame.model.Food.Food;
import com.yourgame.model.Food.FoodType;
import com.yourgame.model.ManuFactor.Artisan.ArtisanProduct;
import com.yourgame.model.ManuFactor.Artisan.ArtisanProductType;
import com.yourgame.model.ManuFactor.Mill.MillProduct;
import com.yourgame.model.ManuFactor.Mill.MillProductType;

import java.util.ArrayList;

public class CookingRecipeManager {
    private static CookingRecipeManager instance;
    public static CookingRecipeManager getInstance() {
        if (instance == null) {
            instance = new CookingRecipeManager();
            instance.loadRecipes();
        }
        return instance;
    }

    ArrayList<CookingRecipe> cookingRecipes = new ArrayList<>();

    public void loadRecipes() {
        cookingRecipes.clear();

        // Fried Egg: 1 Egg
        cookingRecipes.add(new CookingRecipe(
            new ArrayList<>() {{
                add(new Ingredient(new AnimalProduct(AnimalProductType.Egg,Animal.Chicken), 1));
            }},
            new Food(FoodType.FriedEgg),
            new CookingRecipeSource(0,0,0,0,true,false, false)
        ));

        // Baked Fish: 1 Sardine + 1 Salmon + 1 Wheat Flour
        cookingRecipes.add(new CookingRecipe(
            new ArrayList<>() {{
                add(new Ingredient(new FishItem(Fish.Sardine), 1));
                add(new Ingredient(new FishItem(Fish.Salmon), 1));
                add(new Ingredient(new MillProduct(MillProductType.WheatFlour), 1));
            }},
            new Food(FoodType.BakedFish),
            new CookingRecipeSource(0,0,0,0,true,false, false)
        ));

        // Omelet: 1 Egg + 1 Milk
        cookingRecipes.add(new CookingRecipe(
            new ArrayList<>() {{
                add(new Ingredient(new AnimalProduct(AnimalProductType.Egg,Animal.Chicken), 1));
                add(new Ingredient(new AnimalProduct(AnimalProductType.Milk,Animal.Cow), 1));
            }},
            new Food(FoodType.Omelet),
            new CookingRecipeSource(0,0,0,0,false,false, false)
        ));


        // Pumpkin Pie: 1 Pumpkin + 1 Wheat Flour + 1 Milk + 1 Sugar
        cookingRecipes.add(new CookingRecipe(
            new ArrayList<>() {{
                add(new Ingredient(new AnimalProduct(AnimalProductType.Milk,Animal.Cow), 1));
                add(new Ingredient(new CropItem(CropType.Pumpkin),1));
                add(new Ingredient(new MillProduct(MillProductType.WheatFlour),1));
                add(new Ingredient(new MillProduct(MillProductType.Sugar),1));
            }},
            new Food(FoodType.PumpkinPie),
            new CookingRecipeSource(0,0,0,0,false,false, false)
        ));

        // Spaghetti: 1 Wheat Flour + 1 Tomato
        cookingRecipes.add(new CookingRecipe(
            new ArrayList<>() {{
                add(new Ingredient(new CropItem(CropType.Tomato),1));
                add(new Ingredient(new MillProduct(MillProductType.WheatFlour),1));
            }},
            new Food(FoodType.Spaghetti),
            new CookingRecipeSource(0,0,0,0,false,false, false)
        ));

        // Pizza: 1 Wheat Flour + 1 Tomato + 1 Cheese
        cookingRecipes.add(new CookingRecipe(
            new ArrayList<>() {{
                //add(new Ingredient(new ArtisanProduct(ArtisanProductType.CheeseByMilk), 1));
                add(new Ingredient(new CropItem(CropType.Tomato),1));
                add(new Ingredient(new MillProduct(MillProductType.WheatFlour),1));
            }},
            new Food(FoodType.Pizza),
            new CookingRecipeSource(0,0,0,0,false,false, false)
        ));

        // Tortilla: 1 Corn
        cookingRecipes.add(new CookingRecipe(
            new ArrayList<>() {{
                add(new Ingredient(new CropItem(CropType.Corn),1));
            }},
            new Food(FoodType.Tortilla),
            new CookingRecipeSource(0,0,0,0,false,false, false)
        ));

        // Triple Shot Espresso: 3 Coffee
        cookingRecipes.add(new CookingRecipe(
            new ArrayList<>() {{
                add(new Ingredient(new ArtisanProduct(ArtisanProductType.Coffee),3));
            }},
            new Food(FoodType.TripleShotEspresso),
            new CookingRecipeSource(0,0,0,0,false,false, false)
        ));

        // Cookie: 1 Wheat Flour + 1 Sugar + 1 Egg
        cookingRecipes.add(new CookingRecipe(
            new ArrayList<>() {{
                add(new Ingredient(new AnimalProduct(AnimalProductType.Egg,Animal.Chicken), 1));
                add(new Ingredient(new MillProduct(MillProductType.WheatFlour),1));
                add(new Ingredient(new MillProduct(MillProductType.Sugar),1));
            }},
            new Food(FoodType.Cookie),
            new CookingRecipeSource(0,0,0,0,false,false, false)
        ));

        // Hash Browns: 1 Potato + 1 Oil
        cookingRecipes.add(new CookingRecipe(
            new ArrayList<>() {{
                //add(new Ingredient(new ArtisanProduct(ArtisanProductType.Oil), 1));
                add(new Ingredient(new CropItem(CropType.Potato),1));
            }},
            new Food(FoodType.HashBrowns),
            new CookingRecipeSource(0,0,0,0,false,false, false)
        ));

        // Pancakes: 1 Wheat Flour + 1 Egg
        cookingRecipes.add(new CookingRecipe(
            new ArrayList<>() {{
                add(new Ingredient(new AnimalProduct(AnimalProductType.Egg,Animal.Chicken), 1));
                add(new Ingredient(new MillProduct(MillProductType.WheatFlour),1));
            }},
            new Food(FoodType.Pancakes),
            new CookingRecipeSource(0,0,0,0,false,false, false)
        ));

        // Red Plate: 1 Red Cabbage + 1 Radish
        cookingRecipes.add(new CookingRecipe(
            new ArrayList<>() {{
                add(new Ingredient(new CropItem(CropType.Red_Cabbage),1));
                add(new Ingredient(new CropItem(CropType.Radish),1));
            }},
            new Food(FoodType.RedPlate),
            new CookingRecipeSource(0,0,0,0,false,false, false)
        ));

        // Bread: 1 Wheat Flour
        cookingRecipes.add(new CookingRecipe(
            new ArrayList<>() {{
                add(new Ingredient(new MillProduct(MillProductType.WheatFlour),1));
            }},
            new Food(FoodType.Bread),
            new CookingRecipeSource(0,0,0,0,false,false, false)
        ));

        // Salmon Dinner: 1 Salmon + 1 Amaranth + 1 Kale
        cookingRecipes.add(new CookingRecipe(
            new ArrayList<>() {{
                add(new Ingredient(new FishItem(Fish.Salmon), 1));
                add(new Ingredient(new CropItem(CropType.Amaranth),1));
                add(new Ingredient(new CropItem(CropType.Kale),1));
            }},
            new Food(FoodType.SalmonDinner),
            new CookingRecipeSource(0,0,0,0,false,true, false)
        ));

        // Vegetable Medley: 1 Tomato + 1 Beet
        cookingRecipes.add(new CookingRecipe(
            new ArrayList<>() {{
                add(new Ingredient(new CropItem(CropType.Tomato),1));
                add(new Ingredient(new CropItem(CropType.Beet),1));
            }},
            new Food(FoodType.VegetableMedley),
            new CookingRecipeSource(0,2,0,0,false,false, false)
        ));

        // Farmer's Lunch: 1 Omelet + 1 Parsnip
        cookingRecipes.add(new CookingRecipe(
            new ArrayList<>() {{
                add(new Ingredient(new Food(FoodType.Omelet), 1));
                add(new Ingredient(new CropItem(CropType.Parsnip),1));
            }},
            new Food(FoodType.FarmersLunch),
            new CookingRecipeSource(0,0,0,1,false,false, false)
        ));

        // Survival Burger: 1 Bread + 1 Carrot + 1 Eggplant
        cookingRecipes.add(new CookingRecipe(
            new ArrayList<>() {{
                add(new Ingredient(new Food(FoodType.Bread), 1));
                add(new Ingredient(new CropItem(CropType.Carrot), 1));
                add(new Ingredient(new CropItem(CropType.Eggplant), 1));
            }},
            new Food(FoodType.SurvivalBurger),
            new CookingRecipeSource(0,3,0,0,false,false, false)
        ));

        // Dish O' The Sea: 2 Sardines + 1 Hash Browns
        cookingRecipes.add(new CookingRecipe(
            new ArrayList<>() {{
                add(new Ingredient(new FishItem(Fish.Sardine), 2));
                add(new Ingredient(new Food(FoodType.HashBrowns), 1));
            }},
            new Food(FoodType.DishOTheSea),
            new CookingRecipeSource(0,0,2,0,false,false, false)
        ));

        // Seafoam Pudding: 1 Flounder + 1 Midnight Carp
        cookingRecipes.add(new CookingRecipe(
            new ArrayList<>() {{
                add(new Ingredient(new FishItem(Fish.Flounder), 1));
                add(new Ingredient(new FishItem(Fish.MidnightCarp), 1));
            }},
            new Food(FoodType.SeafoamPudding),
            new CookingRecipeSource(0,0,3,0,false,false, false)
        ));

        // Miner's Treat: 2 Carrot + 1 Sugar + 1 Milk
        cookingRecipes.add(new CookingRecipe(
            new ArrayList<>() {{
                add(new Ingredient(new CropItem(CropType.Carrot), 2));
                add(new Ingredient(new MillProduct(MillProductType.Sugar), 1));
                add(new Ingredient(new AnimalProduct(AnimalProductType.Milk,Animal.Cow), 1));
            }},
            new Food(FoodType.MinersTreat),
            new CookingRecipeSource(1,0,0,0,false,false,false)
        ));
    }

    public ArrayList<CookingRecipe> getCookingRecipes() {
        return cookingRecipes;
    }
}
