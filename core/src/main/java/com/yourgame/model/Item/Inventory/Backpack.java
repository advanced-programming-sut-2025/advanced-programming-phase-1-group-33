package com.yourgame.model.Item.Inventory;

import com.yourgame.model.Animals.Animal;
import com.yourgame.model.Animals.AnimalGood;
import com.yourgame.model.Animals.Hay;
import com.yourgame.model.Farming.Crop;
import com.yourgame.model.Farming.Fruit;
import com.yourgame.model.Food.FoodType;
import com.yourgame.model.Item.Tools.Tool;
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
    private final Inventory inventory;
    private final Inventory refrigerator;
    private final Hay hay = new Hay();
    private final HashSet<CookingRecipe> cookingRecipes = new HashSet<>();
    private final HashSet<CraftingRecipes> craftingRecipes = new HashSet<>();
    private final ArrayList<ArtisanMachine> artisanMachines = new ArrayList<>();
    private final TrashCan trashCan = new TrashCan();
    private final ArrayList<Animal> animals = new ArrayList<>();

    private final HashMap<Ingredient, Integer> ingredientQuantity = new HashMap<>();

    public Backpack(BackpackType type) {
        this.type = type;
        this.inventory = new Inventory(type.capacity);
        this.refrigerator = new Inventory(36);
    }

    public boolean changeType(BackpackType type) {
        if (inventory.setCapacity(type.capacity)) {
            this.type = type;
            return true;
        }
        return false;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Inventory getRefrigeratorInventory() {
        return refrigerator;
    }

    public boolean isInventoryFull() {
        return inventory.isInventoryFull();
    }

    public void addTool(Tool tool) {
        inventory.addItem(tool, 1);
    }

    public void addItem(Item item, int quantity) {
        inventory.addItem(item, quantity);
    }

    public ArrayList<Tool> getTools() {
        ArrayList<Tool> tools = new ArrayList<>();
        for (InventorySlot slot : inventory.getSlots()) {
            if (slot.item().getItemType() == Item.ItemType.TOOL) tools.add((Tool) slot.item());
        }
        return tools;
    }

    public void addIngredients(Ingredient ingredient, int quantity) {
        if (inventory.getCapacity() > ingredientQuantity.size()) {
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
            else if (ingredient instanceof Crop c)
                refund = c.getSellPrice() * returnPercentage / 100;
            else if (ingredient instanceof Fruit fr)
                refund = fr.getSellPrice() * returnPercentage / 100;
            else if (ingredient instanceof FoodType fd)
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
