package com.yourgame.model.Npc;


import com.yourgame.model.Animals.Fish;
import com.yourgame.model.Animals.FishType;
import com.yourgame.model.App;
import com.yourgame.model.ManuFactor.ArtisanGood;
import com.yourgame.model.ManuFactor.ArtisanGoodType;
import com.yourgame.model.ManuFactor.Ingredient;
import com.yourgame.model.Map.NpcHome;
import com.yourgame.model.Map.Wood;
import com.yourgame.model.Recipes.CookingRecipe;
import com.yourgame.model.UserInfo.Coin;

import java.util.ArrayList;
import java.util.Arrays;

public class LeahQuests {
    private static final ArrayList<String> questsNames = new ArrayList<>(Arrays.asList("Delivery of a gold bar", "Delivery " +
            "of a salmon", "Delivery of 200 pieces of wood"));

    public static ArrayList<String> getQuestsNames() {
        return questsNames;
    }

    public static boolean doFirstQuest(boolean isRewardTwice) {

        boolean isGoldBarAvailable = false;

        for (Ingredient ingredient :
                App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().keySet()) {
            if (ingredient instanceof ArtisanGood) {
                if (((ArtisanGood) ingredient).getType().equals(ArtisanGoodType.GoldBar)) {
                    int value =
                            App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().getOrDefault(ingredient, 0);
                    if (value > 0) {
                        App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().put(ingredient,
                                value - 1);
                        isGoldBarAvailable = true;
                        break;
                    }
                }
            }
        }

        if (!isGoldBarAvailable) {
            return false;
        }

        for (Ingredient ingredient :
                App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().keySet()) {
            if (ingredient instanceof Coin) {
                int value =
                        App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().getOrDefault(ingredient, 0);
                if (isRewardTwice) {
                    App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().put(ingredient,
                            value + 1000);
                } else {
                    App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().put(ingredient,
                            value + 500);
                }
            }
        }

        for (NpcHome home : App.getGameState().getMap().getNpcHomes()) {
            if (home.getNpc().getType().equals(NPCType.Leah)) {
                home.getNpc().setFirstQuestDone(true);
                break;
            }
        }

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

        App.getGameState().getCurrentPlayer().getBackpack().getCookingRecipes().add(CookingRecipe.SalmonDinner);

        for (NpcHome home : App.getGameState().getMap().getNpcHomes()) {
            if (home.getNpc().getType().equals(NPCType.Leah)) {
                home.getNpc().setSecondQuestDone(true);
                break;
            }
        }

        return true;
    }


    public static boolean doThirdQuest(boolean isRewardTwice) {

        boolean are200WoodAvailable = false;

        for (Ingredient ingredient :
                App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().keySet()) {
            if (ingredient instanceof Wood) {
                int value =
                        App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().getOrDefault(ingredient, 0);
                if (value >= 200) {
                    App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().put(ingredient,
                            value - 200);
                    are200WoodAvailable = true;
                    break;
                }
            }
        }

        if (!are200WoodAvailable) {
            return false;
        }

        // we don't need the reward in our app, so we don't add it to player inventory

        for (NpcHome home : App.getGameState().getMap().getNpcHomes()) {
            if (home.getNpc().getType().equals(NPCType.Leah)) {
                home.getNpc().setThirdQuestDone(true);
                break;
            }
        }

        return true;
    }

}
