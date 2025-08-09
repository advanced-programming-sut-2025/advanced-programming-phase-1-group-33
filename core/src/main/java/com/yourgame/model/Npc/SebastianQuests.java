package com.yourgame.model.Npc;


import com.yourgame.model.App;
import com.yourgame.model.Item.Food;
import com.yourgame.model.Item.ForagingMineral;
import com.yourgame.model.Item.Stone;
import com.yourgame.model.ManuFactor.Ingredient;
import com.yourgame.model.Map.NpcHome;
import com.yourgame.model.UserInfo.Coin;

import java.util.ArrayList;
import java.util.Arrays;

public class SebastianQuests {
    private static final ArrayList<String> questsNames = new ArrayList<>(Arrays.asList("Delivery of 50 irons", "Delivery of " +
            "a pumpkin", "Delivery of 150 stones"));

    public static ArrayList<String> getQuestsNames() {
        return questsNames;
    }

    public static boolean doFirstQuest(boolean isRewardTwice) {

        boolean are50IronsAvailable = false;

        for (Ingredient ingredient :
                App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().keySet()) {
            if (ingredient.equals(ForagingMineral.Iron)) {
                int value =
                        App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().getOrDefault(ingredient, 0 );
                if (value >= 50) {
                    App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().put(ingredient,
                            value - 50);
                    are50IronsAvailable = true;
                    break;
                }
            }
        }

        if (!are50IronsAvailable) {
            return false;
        }

        App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().put(ForagingMineral.Diamond,
                App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().getOrDefault(ForagingMineral.Diamond, 0) + 2);

        if (isRewardTwice) {
            App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().put(ForagingMineral.Diamond,
                    App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().getOrDefault(ForagingMineral.Diamond, 0) + 2);
        }

//        for (NpcHome home : App.getGameState().getMap().getNpcHomes()) {
//            if (home.getNpc().getType().equals(NPCType.Sebastian)) {
//                home.getNpc().setFirstQuestDone(true);
//                break;
//            }
//        }

        return true;
    }

    public static boolean doSecondQuest(boolean isRewardTwice) {

        boolean isPumpkinPieAvailable = false;

        for (Ingredient ingredient : App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().keySet()) {
            if (ingredient.equals(Food.PumpkinPie)) {
                int value = App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().getOrDefault(ingredient, 0 );
                if (value > 0) {
                    App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().put(ingredient,value-1);
                    isPumpkinPieAvailable = true;
                    break;
                }
            }
        }

        if (!isPumpkinPieAvailable) {
            return false;
        }

        for (Ingredient ingredient :
                App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().keySet()) {
            if (ingredient instanceof Coin) {
                int value =
                        App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().getOrDefault(ingredient, 0 );
                if (isRewardTwice) {
                    App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().put(ingredient,
                            value + 10000);
                } else {
                    App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().put(ingredient,
                            value + 5000);
                }
            }
        }

//        for (NpcHome home : App.getGameState().getMap().getNpcHomes()) {
//            if (home.getNpc().getType().equals(NPCType.Sebastian)) {
//                home.getNpc().setSecondQuestDone(true);
//                break;
//            }
//        }

        return true;
    }

    public static boolean doThirdQuest(boolean isRewardTwice) {

        boolean are150StonesAvailable = false;

        for (Ingredient ingredient : App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().keySet()) {
            if (ingredient instanceof Stone) {
                int value = App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().getOrDefault(ingredient, 0);
                if (value >= 150) {
                    App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().put(ingredient,
                            value - 150);
                    are150StonesAvailable = true;
                    break;
                }
            }
        }

        if (!are150StonesAvailable) {
            return false;
        }

        App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().put(ForagingMineral.Quartz,
                App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().getOrDefault(ForagingMineral.Quartz, 0) + 50);

        if (isRewardTwice) {
            App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().put(ForagingMineral.Quartz,
                    App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().getOrDefault(ForagingMineral.Quartz, 0) + 50);
        }

//        for (NpcHome home : App.getGameState().getMap().getNpcHomes()) {
//            if (home.getNpc().getType().equals(NPCType.Sebastian)) {
//                home.getNpc().setThirdQuestDone(true);
//                break;
//            }
//        }

        return true;
    }

}
