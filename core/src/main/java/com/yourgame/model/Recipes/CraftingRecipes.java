package com.yourgame.model.Recipes;

import com.yourgame.model.Item.ForagingMineral;
import com.yourgame.model.Item.Stone;
import com.yourgame.model.Item.TreeSource;
import com.yourgame.model.Item.Wood;
import com.yourgame.model.ManuFactor.ArtisanGood;
import com.yourgame.model.ManuFactor.ArtisanGoodType;
import com.yourgame.model.ManuFactor.Ingredient;

import java.util.HashMap;
import java.util.Map;
public enum CraftingRecipes {
    CherryBomb(new HashMap<>(Map.of(
            ForagingMineral.Copper, 4,
            ForagingMineral.Coal, 1))),
    Bomb(new HashMap<>(Map.of(
            ForagingMineral.Iron, 4,
            ForagingMineral.Coal, 1))),
    MegaBomb(new HashMap<>(Map.of(
            ForagingMineral.Gold, 4,
            ForagingMineral.Coal, 1))),
    Sprinkler(new HashMap<>(Map.of(
            new ArtisanGood(ArtisanGoodType.CopperBar), 1,
            new ArtisanGood(ArtisanGoodType.IronBar), 1))),
    QualitySprinkler(new HashMap<>(Map.of(
            new ArtisanGood(ArtisanGoodType.IronBar), 1,
            new ArtisanGood(ArtisanGoodType.GoldBar), 1))),
    IridiumSprinkler(new HashMap<>(Map.of(
            new ArtisanGood(ArtisanGoodType.GoldBar), 1,
            new ArtisanGood(ArtisanGoodType.IridiumBar), 1))),
    CharcoalKiln(new HashMap<>(Map.of(
            new Wood(), 20,
            new ArtisanGood(ArtisanGoodType.CopperBar), 2))),
    Furnace(new HashMap<>(Map.of(
            ForagingMineral.Copper, 20,
            new Stone(), 25))),
    Scarecrow(new HashMap<>(Map.of(
            new Wood(), 50,
            ForagingMineral.Coal, 1))),
    DeluxeScarecrow(new HashMap<>(Map.of(
            new Wood(), 50,
            ForagingMineral.Coal, 1,
            ForagingMineral.Iridium, 1))),
    BeeHouse(new HashMap<>(Map.of(
            new Wood(), 40,
            ForagingMineral.Coal, 8,
            new ArtisanGood(ArtisanGoodType.IronBar), 1))),
    CheesePress(new HashMap<>(Map.of(
            new Wood(), 45,
            new Stone(), 45,
            new ArtisanGood(ArtisanGoodType.CopperBar), 1))),
    Keg(new HashMap<>(Map.of(
            new Wood(), 30,
            new ArtisanGood(ArtisanGoodType.CopperBar), 1,
            new ArtisanGood(ArtisanGoodType.IronBar), 1))),
    Loom(new HashMap<>(Map.of(
            new Wood(), 60))),
    MayonnaiseMachine(new HashMap<>(Map.of(
            new Wood(), 15,
            new Stone(), 15,
            new ArtisanGood(ArtisanGoodType.CopperBar), 1))),
    OilMaker(new HashMap<>(Map.of(
            new Wood(), 100,
            new ArtisanGood(ArtisanGoodType.GoldBar), 1,
            new ArtisanGood(ArtisanGoodType.IronBar), 1))),
    PreservesJar(new HashMap<>(Map.of(
            new Wood(), 50,
            new Stone(), 40,
            ForagingMineral.Coal, 8))),
    Dehydrator(new HashMap<>(Map.of(
            new Wood(), 30,
            new Stone(), 20))),
    FishSmoker(new HashMap<>(Map.of(
            new Wood(), 50,
            new ArtisanGood(ArtisanGoodType.IronBar), 3,
            ForagingMineral.Coal, 10))),
    MysticTreeSeed(new HashMap<>(Map.of(
            TreeSource.Acorns, 5,
            TreeSource.MapleSeeds, 5,
            TreeSource.PineCones, 5,
            TreeSource.MahoganySeeds, 5)));

    private final HashMap<Ingredient, Integer> ingredients;
    private final static HashMap<String, CraftingRecipes> stringToRecipes = new HashMap<>();

    static {
        for (CraftingRecipes value : CraftingRecipes.values()) {
            stringToRecipes.put(value.name().toLowerCase(), value);
        }
    }

    CraftingRecipes(HashMap<Ingredient, Integer> ingredients) {
        this.ingredients = ingredients;
    }

    public HashMap<Ingredient, Integer> getIngredients() {
        return ingredients;
    }

    public static CraftingRecipes getRecipeByName(String name) {
        if (name == null || name.isEmpty())
            return null;
        return stringToRecipes.getOrDefault(name.toLowerCase(), null);
    }
}
