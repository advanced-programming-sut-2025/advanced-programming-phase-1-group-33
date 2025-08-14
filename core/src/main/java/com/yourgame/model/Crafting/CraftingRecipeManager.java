package com.yourgame.model.Crafting;

import com.yourgame.Mining.MiningItem;
import com.yourgame.Mining.MiningItemType;
import com.yourgame.model.Farming.CropItem;
import com.yourgame.model.Farming.CropType;
import com.yourgame.model.Farming.ForagingCrop;
import com.yourgame.model.Food.Cooking.Ingredient;
import com.yourgame.model.Item.Resource;
import com.yourgame.model.ManuFactor.Artisan.ArtisanProduct;
import com.yourgame.model.ManuFactor.Artisan.ArtisanProductType;
import com.yourgame.model.Resource.ResourceType;

import java.util.ArrayList;

public class CraftingRecipeManager {
    private static CraftingRecipeManager instance;
    public static CraftingRecipeManager getInstance() {
        if (instance == null) {
            instance = new CraftingRecipeManager();
            instance.loadRecipes();
        }
        return instance;
    }

    private final ArrayList<CraftingRecipe> craftingRecipes = new ArrayList<>();

    public void loadRecipes() {
        // Cherry Bomb
        craftingRecipes.add(new CraftingRecipe(
            new ArrayList<Ingredient>() {{
                add(new Ingredient(new MiningItem(MiningItemType.CopperOre), 4));
                add(new Ingredient(new ArtisanProduct(ArtisanProductType.Coal), 1));
            }},
            new CraftedItem(CraftedItemType.CherryBomb),
            new CraftingRecipeSource(1, 0, 0, false, false)
        ));

        // Bomb
        craftingRecipes.add(new CraftingRecipe(
            new ArrayList<Ingredient>() {{
                add(new Ingredient(new MiningItem(MiningItemType.IronOre), 4));
                add(new Ingredient(new ArtisanProduct(ArtisanProductType.Coal), 1));
            }},
            new CraftedItem(CraftedItemType.Bomb),
            new CraftingRecipeSource(2, 0, 0, false, false)
        ));

        // Mega Bomb
        craftingRecipes.add(new CraftingRecipe(
            new ArrayList<Ingredient>() {{
                add(new Ingredient(new MiningItem(MiningItemType.GoldOre), 4));
                add(new Ingredient(new ArtisanProduct(ArtisanProductType.Coal), 1));
            }},
            new CraftedItem(CraftedItemType.MegaBomb),
            new CraftingRecipeSource(3, 0, 0, false, false)
        ));

        // Sprinkler
        craftingRecipes.add(new CraftingRecipe(
            new ArrayList<Ingredient>() {{
                add(new Ingredient(new MiningItem(MiningItemType.CopperBar), 1));
                add(new Ingredient(new MiningItem(MiningItemType.IronBar), 1));
            }},
            new CraftedItem(CraftedItemType.Sprinkler),
            new CraftingRecipeSource(0, 0, 1, false, false)
        ));

        // Quality Sprinkler
        craftingRecipes.add(new CraftingRecipe(
            new ArrayList<Ingredient>() {{
                add(new Ingredient(new MiningItem(MiningItemType.IronBar), 1));
                add(new Ingredient(new MiningItem(MiningItemType.GoldBar), 1));
            }},
            new CraftedItem(CraftedItemType.QualitySprinkler),
            new CraftingRecipeSource(0, 0, 2, false, false)
        ));

        // Iridium Sprinkler
        craftingRecipes.add(new CraftingRecipe(
            new ArrayList<Ingredient>() {{
                add(new Ingredient(new MiningItem(MiningItemType.GoldBar), 1));
                add(new Ingredient(new MiningItem(MiningItemType.IridiumBar), 1));
            }},
            new CraftedItem(CraftedItemType.IridiumSprinkler),
            new CraftingRecipeSource(0, 0, 3, false, false)
        ));

//        // Charcoal Kiln
//        craftingRecipes.add(new CraftingRecipe(
//            new ArrayList<Ingredient>() {{
//                add(new Ingredient(new Resource(ResourceType.Wood), 20));
//                add(new Ingredient(new MetalBar(MetalBarType.CopperBar), 2));
//            }},
//            new CraftedItem(CraftedItemType.CharcoalKiln),
//            new CraftingRecipeSource(0, 0, 1, false, false) // Foraging Level 1
//        ));
//
//        // Furnace
//        craftingRecipes.add(new CraftingRecipe(
//            new ArrayList<Ingredient>() {{
//                add(new Ingredient(new MiningItem(MiningItemType.CopperOre), 20));
//                add(new Ingredient(new Resource(ResourceType.Stone), 25));
//            }},
//            new CraftedItem(CraftedItemType.Furnace),
//            new CraftingRecipeSource(0, 0, 0, false, false) // No specific level
//        ));
//
//        // Scarecrow
//        craftingRecipes.add(new CraftingRecipe(
//            new ArrayList<Ingredient>() {{
//                add(new Ingredient(new Resource(ResourceType.Wood), 50));
//                add(new Ingredient(new ArtisanProduct(ArtisanProductType.Coal), 1));
//                add(new Ingredient(new Resource(ResourceType.Fiber), 20));
//            }},
//            new CraftedItem(CraftedItemType.Scarecrow),
//            new CraftingRecipeSource(0, 0, 0, false, false)
//        ));
//
//        // Deluxe Scarecrow
//        craftingRecipes.add(new CraftingRecipe(
//            new ArrayList<Ingredient>() {{
//                add(new Ingredient(new Resource(ResourceType.Wood), 50));
//                add(new Ingredient(new ArtisanProduct(ArtisanProductType.Coal), 1));
//                add(new Ingredient(new Resource(ResourceType.Fiber), 20));
//                add(new Ingredient(new MiningItem(MiningItemType.IridiumOre), 1));
//            }},
//            new CraftedItem(CraftedItemType.DeluxeScarecrow),
//            new CraftingRecipeSource(0, 2, 0, false, false) // Farming Level 2
//        ));
//
//        // Bee House
//        craftingRecipes.add(new CraftingRecipe(
//            new ArrayList<Ingredient>() {{
//                add(new Ingredient(new Resource(ResourceType.Wood), 40));
//                add(new Ingredient(new ArtisanProduct(ArtisanProductType.Coal), 8));
//                add(new Ingredient(new MetalBar(MetalBarType.IronBar), 1));
//            }},
//            new CraftedItem(CraftedItemType.BeeHouse),
//            new CraftingRecipeSource(0, 1, 0, false, false) // Farming Level 1
//        ));
//
//        // Cheese Press
//        craftingRecipes.add(new CraftingRecipe(
//            new ArrayList<Ingredient>() {{
//                add(new Ingredient(new Resource(ResourceType.Wood), 45));
//                add(new Ingredient(new Resource(ResourceType.Stone), 45));
//                add(new Ingredient(new MetalBar(MetalBarType.CopperBar), 1));
//            }},
//            new CraftedItem(CraftedItemType.CheesePress),
//            new CraftingRecipeSource(0, 2, 0, false, false) // Farming Level 2
//        ));
//
//        // Keg
//        craftingRecipes.add(new CraftingRecipe(
//            new ArrayList<Ingredient>() {{
//                add(new Ingredient(new Resource(ResourceType.Wood), 30));
//                add(new Ingredient(new MetalBar(MetalBarType.CopperBar), 1));
//                add(new Ingredient(new MetalBar(MetalBarType.IronBar), 1));
//            }},
//            new CraftedItem(CraftedItemType.Keg),
//            new CraftingRecipeSource(0, 3, 0, false, false) // Farming Level 3
//        ));
//
//        // Loom
//        craftingRecipes.add(new CraftingRecipe(
//            new ArrayList<Ingredient>() {{
//                add(new Ingredient(new Resource(ResourceType.Wood), 60));
//                add(new Ingredient(new Resource(ResourceType.Fiber), 30));
//            }},
//            new CraftedItem(CraftedItemType.Loom),
//            new CraftingRecipeSource(0, 3, 0, false, false) // Farming Level 3
//        ));
//
//        // Mayonnaise Machine
//        craftingRecipes.add(new CraftingRecipe(
//            new ArrayList<Ingredient>() {{
//                add(new Ingredient(new Resource(ResourceType.Wood), 15));
//                add(new Ingredient(new Resource(ResourceType.Stone), 15));
//                add(new Ingredient(new MetalBar(MetalBarType.CopperBar), 1));
//            }},
//            new CraftedItem(CraftedItemType.MayonnaiseMachine),
//            new CraftingRecipeSource(0, 0, 0, false, false)
//        ));
//
//        // Oil Maker
//        craftingRecipes.add(new CraftingRecipe(
//            new ArrayList<Ingredient>() {{
//                add(new Ingredient(new Resource(ResourceType.Wood), 100));
//                add(new Ingredient(new MetalBar(MetalBarType.GoldBar), 1));
//                add(new Ingredient(new MetalBar(MetalBarType.IronBar), 1));
//            }},
//            new CraftedItem(CraftedItemType.OilMaker),
//            new CraftingRecipeSource(0, 3, 0, false, false) // Farming Level 3
//        ));
//
//        // Preserves Jar
//        craftingRecipes.add(new CraftingRecipe(
//            new ArrayList<Ingredient>() {{
//                add(new Ingredient(new Resource(ResourceType.Wood), 50));
//                add(new Ingredient(new Resource(ResourceType.Stone), 40));
//                add(new Ingredient(new ArtisanProduct(ArtisanProductType.Coal), 8));
//            }},
//            new CraftedItem(CraftedItemType.PreservesJar),
//            new CraftingRecipeSource(0, 2, 0, false, false) // Farming Level 2
//        ));
//
//        // Dehydrator
//        craftingRecipes.add(new CraftingRecipe(
//            new ArrayList<Ingredient>() {{
//                add(new Ingredient(new Resource(ResourceType.Wood), 30));
//                add(new Ingredient(new Resource(ResourceType.Stone), 20));
//                add(new Ingredient(new Resource(ResourceType.Fiber), 30));
//            }},
//            new CraftedItem(CraftedItemType.Dehydrator),
//            new CraftingRecipeSource(0, 0, 0, true, false) // Sold at Pierre's
//        ));
//
//        // Grass Starter
//        craftingRecipes.add(new CraftingRecipe(
//            new ArrayList<Ingredient>() {{
//                add(new Ingredient(new Resource(ResourceType.Wood), 1));
//                add(new Ingredient(new Resource(ResourceType.Fiber), 1));
//            }},
//            new CraftedItem(CraftedItemType.GrassStarter),
//            new CraftingRecipeSource(0,
    }
}
