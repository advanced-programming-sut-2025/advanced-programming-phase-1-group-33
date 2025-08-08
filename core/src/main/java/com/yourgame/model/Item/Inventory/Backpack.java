package com.yourgame.model.Item.Inventory;

import com.yourgame.model.Animals.Animal;
import com.yourgame.model.Animals.AnimalGood;
import com.yourgame.model.Animals.Fish;
import com.yourgame.model.Animals.Hay;
import com.yourgame.model.Item.Inventory.Tools.Tool;
import com.yourgame.model.Item.*;
import com.yourgame.model.ManuFactor.ArtisanMachine;
import com.yourgame.model.ManuFactor.Ingredient;
import com.yourgame.model.Recipes.CookingRecipe;
import com.yourgame.model.Recipes.CraftingRecipes;
import com.yourgame.model.UserInfo.Coin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Backpack {
    private BackpackType type;
    private int capacity;
    private final ArrayList<Tool> tools = new ArrayList<>();
    private final Hay hay = new Hay();
    private final HashSet<CookingRecipe> cookingRecipes = new HashSet<>();
    private final HashSet<CraftingRecipes> craftingRecipes = new HashSet<>();
    private final ArrayList<ArtisanMachine> artisanMachines = new ArrayList<>();
    private final Refrigerator refrigerator = new Refrigerator();
    private final TrashCan trashCan = new TrashCan();
    private final ArrayList<Animal> animals = new ArrayList<>();

    private final HashMap<Ingredient, Integer> ingredientQuantity = new HashMap<>();

    public Backpack(BackpackType type) {
        this.type = type;
        switch (type) {
            case Primary:
                this.capacity = 12;
                break;
            case Big:
                this.capacity = 24;
                break;
            case Deluxe:
                this.capacity = Integer.MAX_VALUE;
        }
    }

    public void changeType(BackpackType type) {
        switch (type) {
            case Big:
                this.type = BackpackType.Big;
                this.capacity = 24;
                break;
            case Primary:
                this.type = BackpackType.Primary;
                this.capacity = 12;
                break;
            case Deluxe:
                this.capacity = Integer.MAX_VALUE;
                this.type = BackpackType.Deluxe;
        }

    }

    public int getCapacity() {
        return capacity;
    }

    public boolean hasCapacity() {
        return capacity > ingredientQuantity.size();
    }

    public boolean hasCapacity(int quantity) {
        return capacity > ingredientQuantity.size() + quantity;
    }

    public void addTool(Tool tool) {
        tools.add(tool);

    }

    public ArrayList<Tool> getTools() {
        return tools;
    }

    public void removeTool(Tool tool) {
        tools.remove(tool);

    }

    public void addIngredients(Ingredient ingredient, int quantity) {
        if (capacity > ingredientQuantity.size()) {
            int value = ingredientQuantity.getOrDefault(ingredient, 0);
            ingredientQuantity.put(ingredient, value + quantity);
        }
    }

    public void removeIngredients(Ingredient ingredient, int quantity) {
        int value = ingredientQuantity.getOrDefault(ingredient, 0);
        if (value == quantity) {
            ingredientQuantity.remove(ingredient);

            int returnPercentage = trashCan.getReturnValuePercentage();

            int refund = 0;
            if (ingredient instanceof AnimalGood ag)
                refund = ag.getSellPrice() * returnPercentage / 100;
            else if (ingredient instanceof Fish f)
                refund = f.getSellPrice() * returnPercentage / 100;
            else if (ingredient instanceof Crop c)
                refund = c.getSellPrice() * returnPercentage / 100;
            else if (ingredient instanceof Fruit fr)
                refund = fr.getSellPrice() * returnPercentage / 100;
            else if (ingredient instanceof Food fd)
                refund = fd.getSellPrice() * returnPercentage / 100;
            else if (ingredient instanceof ForagingMineral fm)
                refund = fm.getSellPrice() * returnPercentage / 100;

            if (refund > 0) {
                Coin coin = new Coin();
                ingredientQuantity.put(coin, ingredientQuantity.getOrDefault(coin, 0) + refund);
            }
        } else {
            ingredientQuantity.put(ingredient, value - quantity);
        }
    }

    public HashMap<Ingredient, Integer> getIngredientQuantity() {
        return ingredientQuantity;
    }

    public BackpackType getType() {
        return type;
    }

    public void addArtisanMachine(ArtisanMachine artisanMachine) {
        artisanMachines.add(artisanMachine);
    }

    public ArrayList<ArtisanMachine> getArtisanMachines() {
        return artisanMachines;
    }

    public ArtisanMachine getArtisanMachineByName(String name) {
        ArtisanMachine machineIns = ArtisanMachine.getArtisanMachineByRecipe(CraftingRecipes.getRecipeByName(name));
        if (machineIns == null) {
            return null;
        }
        for (ArtisanMachine machine : artisanMachines) {
            if (machine.getClass() == machineIns.getClass())
                return machine;
        }
        return null;
    }

    public void addRecipe(CraftingRecipes craftingRecipe) {
        craftingRecipes.add(craftingRecipe);
    }

    public HashSet<CraftingRecipes> getCraftingRecipes() {
        return craftingRecipes;
    }

    public void addRecipe(CookingRecipe cookingRecipe) {
        cookingRecipes.add(cookingRecipe);
    }

    public HashSet<CookingRecipe> getCookingRecipes() {
        return cookingRecipes;
    }

    public boolean containRecipe(CookingRecipe recipe) {
        return cookingRecipes.contains(recipe);
    }

    public boolean containRecipe(CraftingRecipes recipe) {
        return craftingRecipes.contains(recipe);
    }

    public void addAnimal(Animal animal) {
        animals.add(animal);
    }

    public ArrayList<Animal> getAllAnimals() {
        return animals;
    }

    public Animal getAnimalByName(String name) {
        for (Animal animal : animals) {
            if (animal.getName().equals(name)) {
                return animal;
            }
        }
        return null;
    }

    public Refrigerator getRefrigerator() {
        return refrigerator;
    }

    public void increaseHay(int number) {
        hay.increaseNumber(number);
    }

    public void decreaseHay(int number) {
        hay.decreaseNumber(number);
        hay.setNumber(Math.max(hay.getNumber(), 0));
    }

    public boolean hasEnoughHay(int number) {
        return hay.getNumber() >= number;
    }

    public int getNumberOfHay() {
        return hay.getNumber();
    }

    public TrashCan getTrashCan() {
        return trashCan;
    }

}
