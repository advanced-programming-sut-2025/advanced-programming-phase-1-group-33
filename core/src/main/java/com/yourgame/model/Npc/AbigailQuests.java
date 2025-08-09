package com.yourgame.model.Npc;


import com.yourgame.model.Item.Crop;
import com.yourgame.model.Item.CropType;
import com.yourgame.model.ManuFactor.ArtisanGood;
import com.yourgame.model.ManuFactor.ArtisanGoodType;
import com.yourgame.model.ManuFactor.Ingredient;
import com.yourgame.model.Map.NpcHome;
import com.yourgame.model.App;
import com.yourgame.model.UserInfo.Coin;

import java.util.ArrayList;
import java.util.Arrays;

public class AbigailQuests {
    private static final ArrayList<String> questsNames = new ArrayList<>(Arrays.asList("Delivery of a gold bar", "Delivery " +
            "of a pumpkin", "Delivery of 50 pieces of wheat"));

    public static ArrayList<String> getQuestsNames() {
        return questsNames;
    }


    public static boolean doFirstQuest(boolean isRewardTwice) {

        boolean isGoldBarAvailable = false;

        for (Ingredient ingredient : App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().keySet()) {
            if (ingredient instanceof ArtisanGood) {
                if (((ArtisanGood) ingredient).getType().equals(ArtisanGoodType.GoldBar)) {
                    int value = App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().getOrDefault(ingredient, 0);
                    if ( value > 0) {
                        App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().put(ingredient, value-1);
                        isGoldBarAvailable = true;
                        break;
                    }
                }
            }
        }

        if (!isGoldBarAvailable) {
            return false;
        }

        App.getGameState().getCurrentPlayer().getRelationWithAbigail().increaseFriendshipLevel();
        if (isRewardTwice) {
            App.getGameState().getCurrentPlayer().getRelationWithAbigail().increaseFriendshipLevel();
        }

//        for (NpcHome home : App.getGameState().getMap().getNpcHomes()) {
//            if (home.getNpc().getType().equals(NPCType.Abigail)) {
//                home.getNpc().setFirstQuestDone(true);
//                break;
//            }
//        }

        return true;
    }

    public static boolean doSecondQuest(boolean isRewardTwice) {

        boolean isPumpkinAvailable = false;

        for (Ingredient ingredient : App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().keySet()) {
            if (ingredient instanceof Crop) {
                if (((Crop) ingredient).getType().equals(CropType.Pumpkin)) {
                    int value = App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().getOrDefault(ingredient, 0);
                    if ( value > 0) {
                        App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().put(ingredient, value-1);
                        isPumpkinAvailable= true;
                        break;
                    }
                }
            }
        }

        if (!isPumpkinAvailable) {
            return false;
        }

        for (Ingredient ingredient : App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().keySet()) {
            if (ingredient instanceof Coin) {
                int value = App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().getOrDefault(ingredient, 0);
                if (isRewardTwice) {
                    App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().put(ingredient, value + 1000);
                } else {
                    App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().put(ingredient, value + 500);
                }
            }
        }

//        for (NpcHome home : App.getGameState().getMap().getNpcHomes()) {
//            if (home.getNpc().getType().equals(NPCType.Abigail)) {
//                home.getNpc().setSecondQuestDone(true);
//                break;
//            }
//        }

        return true;
    }

    public static boolean doThirdQuest(boolean isRewardTwice) {

        boolean are50WheatAvailable = false;

        for (Ingredient ingredient : App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().keySet()) {
            if (ingredient instanceof Crop) {
                if (((Crop) ingredient).getType().equals(CropType.Wheat)) {
                    int value = App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().getOrDefault(ingredient, 0);
                    if ( value >= 50) {
                        App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().put(ingredient, value-50);
                        are50WheatAvailable = true;
                        break;
                    }
                }
            }
        }

        if (!are50WheatAvailable) {
            return false;
        }

        //TODO : Add reward to player


//        for (NpcHome home : App.getGameState().getMap().getNpcHomes()) {
//            if (home.getNpc().getType().equals(NPCType.Abigail)) {
//                home.getNpc().setThirdQuestDone(true);
//                break;
//            }
//        }

        return true;


    }

}
