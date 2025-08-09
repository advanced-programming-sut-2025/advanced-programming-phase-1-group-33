package com.yourgame.model.Npc;


import com.yourgame.model.Animals.Fish;
import com.yourgame.model.Animals.FishType;
import com.yourgame.model.Item.Crop;
import com.yourgame.model.Item.CropType;
import com.yourgame.model.Item.Food;
import com.yourgame.model.Item.Fruit;
import com.yourgame.model.ManuFactor.ArtisanGood;
import com.yourgame.model.ManuFactor.ArtisanGoodType;
import com.yourgame.model.ManuFactor.Ingredient;

import java.util.ArrayList;
import java.util.Arrays;

import com.yourgame.model.App;
import com.yourgame.model.Map.NpcHome;
import com.yourgame.model.UserInfo.Coin;

public class HarveyQuests {

    private static final ArrayList<String> questsNames = new ArrayList<>(Arrays.asList("Delivery of 12 desired plants",
            "Delivery of a salmon", "Delivery of a bottle of wine"));

    public static ArrayList<String> getQuestsNames() {
        return questsNames;
    }

    public static boolean doFirstQuest(boolean isRewardTwice) {

        boolean are12PlantAvailable = false;
        for (Ingredient ingredient : App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().keySet()) {
            if (ingredient instanceof Crop || ingredient instanceof CropType || ingredient instanceof Fruit) {
                int value = App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().getOrDefault(ingredient, 0);
                if (value >= 12) {
                    App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().put(ingredient, value-12);
                    are12PlantAvailable = true;
                    break;
                }
            }
        }

        if (!are12PlantAvailable) {
            return false;
        }

        for (Ingredient ingredient :
                App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().keySet()) {
            if (ingredient instanceof Coin) {
                int value =
                        App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().getOrDefault(ingredient, 0);
                if (isRewardTwice) {
                    App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().put(ingredient,
                            value + 1500);
                } else {
                    App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().put(ingredient,
                            value + 750);
                }
            }
        }

//        for (NpcHome home : App.getGameState().getMap().getNpcHomes()) {
//            if (home.getNpc().getType().equals(NPCType.Harvey)) {
//                home.getNpc().setFirstQuestDone(true);
//                break;
//            }
//        }

        return true;
    }

    public static boolean doSecondQuest(boolean isRewardTwice) {

        boolean isSalmonAvailable = false;

        for (Ingredient ingredient : App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().keySet()) {
            if (ingredient instanceof Fish) {
                if (((Fish) ingredient).getType().equals(FishType.Salmon)) {
                    int value = App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().getOrDefault(ingredient, 0);
                    if (value > 0) {
                        App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().put(ingredient, value-1);
                        isSalmonAvailable = true;
                        break;
                    }
                }
            }
        }

        if (!isSalmonAvailable) {
            return false;
        }

        App.getGameState().getCurrentPlayer().getRelationWithHarvey().increaseFriendshipLevel();
        if (isRewardTwice) {
            App.getGameState().getCurrentPlayer().getRelationWithHarvey().increaseFriendshipLevel();
        }

//        for (NpcHome home : App.getGameState().getMap().getNpcHomes()) {
//            if (home.getNpc().getType().equals(NPCType.Harvey)) {
//                home.getNpc().setSecondQuestDone(true);
//                break;
//            }
//        }
        return true;
    }

    public static boolean doThirdQuest(boolean isRewardTwice) {

        boolean isWineAvailable = false;

        for (Ingredient ingredient :
                App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().keySet()) {
            if (ingredient instanceof ArtisanGood) {
                if (((ArtisanGood) ingredient).getType().equals(ArtisanGoodType.Wine)) {
                    int value =
                            App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().getOrDefault(ingredient, 0);
                    if (value > 0) {
                        App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().put(ingredient,
                                value - 1);
                        isWineAvailable = true;
                        break;
                    }
                }
            }
        }

        if (!isWineAvailable) {
            return false;
        }

        App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().put(Food.Salad,
                App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().getOrDefault(Food.Salad, 0) + 5);
        if (isRewardTwice) {
            App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().put(Food.Salad,
                    App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().getOrDefault(Food.Salad, 0) + 5);
        }

//        for (NpcHome home : App.getGameState().getMap().getNpcHomes()) {
//            if (home.getNpc().getType().equals(NPCType.Harvey)) {
//                home.getNpc().setThirdQuestDone(true);
//                break;
//            }
//        }

        return true;
    }

}
