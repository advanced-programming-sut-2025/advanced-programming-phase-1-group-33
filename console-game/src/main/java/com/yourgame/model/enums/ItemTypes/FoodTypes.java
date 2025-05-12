package com.yourgame.model.enums.ItemTypes;

import com.yourgame.model.Inventory.Slot;
import com.yourgame.model.Item.FoodBuff;
import com.yourgame.model.*;
import com.yourgame.model.enums.Quality;

public enum FoodTypes implements Item {
    APRICOT("Apricot", 38, new FoodBuff("", 0, 0), 59),
    CHERRY("Cherry", 38, new FoodBuff("", 0, 0), 80),
    BANANA("Banana", 75, new FoodBuff("", 0, 0), 150),
    MANGO("Mango", 100, new FoodBuff("", 0, 0), 130),
    ORANGE("Orange", 38, new FoodBuff("", 0, 0), 100),
    PEACH("Peach", 38, new FoodBuff("", 0, 0), 140),
    APPLE("Apple", 38, new FoodBuff("", 0, 0), 100),
    POMEGRANATE("Pomegranate", 38, new FoodBuff("", 0, 0), 140),
    OAK_RESIN("Oak Resin", 0, new FoodBuff("", 0, 0), 150),
    MAPLE_SYRUP("Maple Syrup", 0, new FoodBuff("", 0, 0), 200),
    PINE_TAR("Pine Tar", 0, new FoodBuff("", 0, 0), 100),
    SAP("Sap", -2, new FoodBuff("", 0, 0), 2),
    COMMON_MUSHROOM("Common Mushroom", 38, new FoodBuff("", 0, 0), 40),
    MYSTIC_SYRUP("Mystic Syrup", 500, new FoodBuff("", 0, 0), 1000),
    FRIED_EGG("Fried Egg", 50, new FoodBuff("", 0, 0), 35),
    BAKED_FISH("Baked Fish", 75, new FoodBuff("", 0, 0), 100),
    SALAD("Salad", 113, new FoodBuff("", 0, 0), 110),
    OMELETTE("Omelette", 100, new FoodBuff("", 0, 0), 125),
    PUMPKIN_PIE("Pumpkin Pie", 225, new FoodBuff("", 0, 0), 385),
    SPAGHETTI("Spaghetti", 75, new FoodBuff("", 0, 0), 120),
    PIZZA("Pizza", 150, new FoodBuff("", 0, 0), 300),
    TORTILLA("Tortilla", 50, new FoodBuff("", 0, 0), 50),
    MAKI_ROLL("Maki Roll", 100, new FoodBuff("", 0, 0), 100),
    TRIPLE_SHOT_ESPRESSO("Triple Shot Espresso", 200, new FoodBuff("maxEnergy", 100, 5), 450),
    COOKIE("Cookie", 90, new FoodBuff("", 0, 0), 140),
    HASH_BROWNS("Hash Browns", 90, new FoodBuff("farming", 1, 5), 120),
    PANCAKES("Pancakes", 90, new FoodBuff("foraging", 1, 11), 80),
    FRUIT_SALAD("Fruit Salad", 263, new FoodBuff("", 0, 0), 450),
    RED_PLATE("Red Plate", 240, new FoodBuff("maxEnergy", 50, 3), 400),
    BREAD("Bread", 50, new FoodBuff("", 0, 0), 60),
    SALMON_DINNER("Salmon Dinner", 125, new FoodBuff("", 0, 0), 300),
    VEGETABLE_MEDLEY("Vegetable Medley", 165, new FoodBuff("", 0, 0), 120),
    FARMERS_LUNCH("Farmer's Lunch", 200, new FoodBuff("farming", 1, 5), 150),
    SURVIVAL_BURGER("Survival Burger", 125, new FoodBuff("foraging", 1, 5), 180),
    DISH_OF_THE_SEA("Dish O' The Sea", 150, new FoodBuff("fishing", 1, 5), 220),
    SEAFORM_PUDDING("Seaform Pudding", 175, new FoodBuff("fishing", 1, 10), 300),
    MINERS_TREAT("Miner's Treat", 125, new FoodBuff("mining", 1, 5), 200),
    BLUE_JAZZ("Blue Jazz", 45, new FoodBuff("", 0, 0), 50),
    CARROT("Carrot", 75, new FoodBuff("", 0, 0), 35),
    CAULIFLOWER("Cauliflower", 75, new FoodBuff("", 0, 0), 175),
    COFFEE_BEAN("Coffee Bean", 0, new FoodBuff("", 0, 0), 15),
    GARLIC("Garlic", 20, new FoodBuff("", 0, 0), 60),
    GREEN_BEAN("Green Bean", 25, new FoodBuff("", 0, 0), 40),
    KALE("Kale", 50, new FoodBuff("", 0, 0), 110),
    PARSNIP("Parsnip", 25, new FoodBuff("", 0, 0), 35),
    POTATO("Potato", 25, new FoodBuff("", 0, 0), 80),
    RHUBARB("Rhubarb", 0, new FoodBuff("", 0, 0), 220),
    STRAWBERRY("Strawberry", 50, new FoodBuff("", 0, 0), 120),
    TULIP("Tulip", 45, new FoodBuff("", 0, 0), 30),
    UNMILLED_RICE("Unmilled Rice", 3, new FoodBuff("", 0, 0), 30),
    BLUEBERRY("Blueberry", 25, new FoodBuff("", 0, 0), 50),
    CORN("Corn", 25, new FoodBuff("", 0, 0), 50),
    HOPS("Hops", 45, new FoodBuff("", 0, 0), 25),
    HOT_PEPPER("Hot Pepper", 13, new FoodBuff("", 0, 0), 40),
    MELON("Melon", 113, new FoodBuff("", 0, 0), 250),
    POPPY("Poppy", 45, new FoodBuff("", 0, 0), 140),
    RADISH("Radish", 45, new FoodBuff("", 0, 0), 90),
    RED_CABBAGE("Red Cabbage", 75, new FoodBuff("", 0, 0), 260),
    STARFRUIT("Starfruit", 125, new FoodBuff("", 0, 0), 750),
    SUMMER_SPANGLE("Summer Spangle", 45, new FoodBuff("", 0, 0), 90),
    SUMMER_SQUASH("Summer Squash", 63, new FoodBuff("", 0, 0), 45),
    SUNFLOWER("Sunflower", 45, new FoodBuff("", 0, 0), 80),
    TOMATO("Tomato", 20, new FoodBuff("", 0, 0), 60),
    WHEAT("Wheat", 0, new FoodBuff("", 0, 0), 25),
    AMARANTH("Amaranth", 50, new FoodBuff("", 0, 0), 150),
    ARTICHOKE("Artichoke", 30, new FoodBuff("", 0, 0), 160),
    BEET("Beet", 30, new FoodBuff("", 0, 0), 100),
    BOK_CHOY("Bok Choy", 25, new FoodBuff("", 0, 0), 80),
    BROCCOLI("Broccoli", 63, new FoodBuff("", 0, 0), 70),
    CRANBERRIES("Cranberries", 38, new FoodBuff("", 0, 0), 75),
    EGGPLANT("Eggplant", 20, new FoodBuff("", 0, 0), 60),
    FAIRY_ROSE("Fairy Rose", 45, new FoodBuff("", 0, 0), 290),
    GRAPE("Grape", 38, new FoodBuff("", 0, 0), 80),
    PUMPKIN("Pumpkin", 0, new FoodBuff("", 0, 0), 320),
    YAM("Yam", 45, new FoodBuff("", 0, 0), 160),
    SWEET_GEM_BERRY("Sweet Gem Berry", 0, new FoodBuff("", 0, 0), 3000),
    POWDER_MELON("Powder Melon", 63, new FoodBuff("", 0, 0), 60),
    ANCIENT_FRUIT("Ancient Fruit", 0, new FoodBuff("", 0, 0), 550),
    DAFFODIL("Daffodil", 0, new FoodBuff("", 0, 0), 30),
    DANDELION("Dandelion", 25, new FoodBuff("", 0, 0), 40),
    LEEK("Leek", 40, new FoodBuff("", 0, 0), 60),
    MOREL("Morel", 20, new FoodBuff("", 0, 0), 150),
    SALMON_BERRY("Salmon Berry", 13, new FoodBuff("", 0, 0), 8),
    SPRING_ONION("Spring Onion", 13, new FoodBuff("", 0, 0), 8),
    WILD_HORSERADISH("Wild Horseradish", 13, new FoodBuff("", 0, 0), 50),
    FIDDLE_HEAD_FERN("Fiddle Head Fern", 25, new FoodBuff("", 0, 0), 90),
    RED_MUSHROOM("Red Mushroom", -50, new FoodBuff("", 0, 0), 75),
    SPICE_BERRY("Spice Berry", 25, new FoodBuff("", 0, 0), 80),
    SWEET_PEA("Sweet Pea", 0, new FoodBuff("", 0, 0), 50),
    BLACKBERRY("Black Berry", 25, new FoodBuff("", 0, 0), 25),
    CHANTERELLE("Chanterelle", 75, new FoodBuff("", 0, 0), 160),
    HAZELNUT("Hazelnut", 38, new FoodBuff("", 0, 0), 40),
    PURPLE_MUSHROOM("Purple Mushroom", 30, new FoodBuff("", 0, 0), 90),
    WILD_PLUM("Wild Plum", 25, new FoodBuff("", 0, 0), 80),
    CROCUS("Crocus", 0, new FoodBuff("", 0, 0), 60),
    CRYSTAL_FRUIT("Crystal Fruit", 63, new FoodBuff("", 0, 0), 150),
    HOLLY("Holly", -37, new FoodBuff("", 0, 0), 80),
    SNOW_YAM("Snow Yam", 30, new FoodBuff("", 0, 0), 100),
    WINTER_ROOT("Winter Root", 25, new FoodBuff("", 0, 0), 70),
    BEER("Beer", 50, new FoodBuff("", 0, 0), 200),
    COFFEE("Coffee", 3, new FoodBuff("", 0, 0), 150),
    JOJA_COLA("Joja Cola", 13, new FoodBuff("", 0, 0), 25),
    SUGAR("Sugar", 25, new FoodBuff("", 0, 0), 50),
    WHEAT_FLOUR("Wheat Flour", 13, new FoodBuff("", 0, 0), 50),
    RICE("Rice", 13, new FoodBuff("", 0, 0), 100),
    OIL("Oil", 13, new FoodBuff("", 0, 0), 100),
    VINEGAR("Vinegar", 13, new FoodBuff("", 0, 0), 100),
    TROUT_SOUP("Trout Soup", 100, new FoodBuff("", 0, 0), 100),
    HONEY("Honey", 0, new FoodBuff("", 0, 0), 100),
    MAYONNAISE("Mayonnaise", 50, new FoodBuff("", 0, 0), 190),
    DUCK_MAYONNAISE("Duck Mayonnaise", 75, new FoodBuff("", 0, 0), 375),
    DINOSAUR_MAYONNAISE("Dinosaur Mayonnaise", 125, new FoodBuff("", 0, 0), 800),
    TRUFFLE_OIL("Truffle Oil", 38, new FoodBuff("", 0, 0), 1065),
    CHEESE("Cheese", 125, new FoodBuff("", 0, 0), 230),
    GOAT_CHEESE("Goat Cheese", 125, new FoodBuff("", 0, 0), 400),
    MEAD("Mead", 75, new FoodBuff("", 0, 0), 300),
    PALE_ALE("Pale Ale", 50, new FoodBuff("", 0, 0), 300),
    RAISINS("Raisins", 125, new FoodBuff("", 0, 0), 600),
    //Determined based on base ingredient.
    WINE("Wine", null, new FoodBuff("", 0, 0), null),
    JUICE("Juice", null, new FoodBuff("", 0, 0), null),
    DRIED_MUSHROOMS("Dried Mushrooms", 50, new FoodBuff("", 0, 0), null),
    DRIED_FRUIT("Dried Fruit", 75, new FoodBuff("", 0, 0), null),
    PICKLES("Pickles", null, new FoodBuff("", 0, 0), null),
    JELLY("Jelly", null, new FoodBuff("", 0, 0), null),
    SMOKED_FISH("Smoked Fish", null, new FoodBuff("", 0, 0), null)
    //END OF LIST
    ;

    final public String name;
    final public Integer energy;
    final public FoodBuff foodBuff;
    final public Integer value;

    FoodTypes(String name, Integer energy, FoodBuff foodBuff, Integer value) {
        this.name = name;
        this.energy = energy;
        this.foodBuff = foodBuff;
        this.value = value;
    }

    public static int getEnergy(String itemName) {
        for (FoodTypes type : FoodTypes.values()) {
            if (type.name.equals(itemName))
                return type.energy;
        }
        return 0;
    }

    public static int getPrice(String itemName) {
        for (FoodTypes t : FoodTypes.values()) {
            if (t.name.equals(itemName))
                return t.value;
        }
        return 0;
    }

    public static FoodTypes getFoodTypeByName(String name) {
        for (FoodTypes t : FoodTypes.values()) {
            if (t.name.compareToIgnoreCase(name) == 0) {
                return t;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }
//
//    public Slot createAmountOfItem(int amount) {
//        return new Slot(new FoodTypes(Quality.DEFAULT, this), amount);
//    }
}