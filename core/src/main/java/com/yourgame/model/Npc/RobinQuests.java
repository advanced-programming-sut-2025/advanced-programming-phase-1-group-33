package com.yourgame.model.Npc;


import com.yourgame.model.App;
import com.yourgame.model.Item.Wood;
import com.yourgame.model.ManuFactor.ArtisanGood;
import com.yourgame.model.ManuFactor.ArtisanGoodType;
import com.yourgame.model.ManuFactor.BeeHouse;
import com.yourgame.model.ManuFactor.Ingredient;
import com.yourgame.model.Map.NpcHome;
import com.yourgame.model.UserInfo.Coin;

import java.util.ArrayList;
import java.util.Arrays;

public class RobinQuests{
    private static final ArrayList<String> questsNames = new ArrayList<>(Arrays.asList("Delivery of 80 pieces of wood",
            "Delivery of 10 iron bar", "Delivery of 1000 pieces of wood"));

    public static ArrayList<String> getQuestsNames() {
        return questsNames;
    }

    public static boolean doFirstQuest(boolean isRewardTwice) {

        boolean are80WoodAvailable = false;

        for (Ingredient ingredient :
                App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().keySet()) {
            if (ingredient instanceof Wood) {
                int value =
                        App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().getOrDefault(ingredient, 0);
                if (value >= 80) {
                    App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().put(ingredient,
                            value - 80);
                    are80WoodAvailable = true;
                    break;
                }
            }
        }

        if (!are80WoodAvailable) {
            return false;
        }

        for (Ingredient ingredient : App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().keySet()) {
            if (ingredient instanceof Coin) {
                int value = App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().getOrDefault(ingredient, 0);
                if (isRewardTwice) {
                    App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().put(ingredient, value + 2000);
                } else {
                    App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().put(ingredient, value + 1000);
                }
            }
        }

//        for (NpcHome home : App.getGameState().getMap().getNpcHomes()) {
//            if (home.getNpc().getType().equals(NPCType.Robin)) {
//                home.getNpc().setFirstQuestDone(true);
//                break;
//            }
//        }

        return true;
    }

    public static boolean doSecondQuest(boolean isRewardTwice) {

        boolean are10IronBarAvailable = false;

        for (Ingredient ingredient : App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().keySet()) {
            if (ingredient instanceof ArtisanGood) {
                if (((ArtisanGood) ingredient).getType().equals(ArtisanGoodType.IronBar)) {
                    int value = App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().getOrDefault(ingredient, 0);
                    if ( value >= 10) {
                        App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().put(ingredient, value-10);
                        are10IronBarAvailable= true;
                        break;
                    }
                }
            }
        }

        if (!are10IronBarAvailable) {
            return false;
        }

        int numberOfRepetitions = (isRewardTwice ? 3 : 6);

        for (int i=0; i<numberOfRepetitions; i++) {
            App.getGameState().getCurrentPlayer().getBackpack().getArtisanMachines().add(new BeeHouse());
        }


//        for (NpcHome home : App.getGameState().getMap().getNpcHomes()) {
//            if (home.getNpc().getType().equals(NPCType.Robin)) {
//                home.getNpc().setSecondQuestDone(true);
//                break;
//            }
//        }

        return true;
    }

    public static boolean doThirdQuest(boolean isRewardTwice) {

        boolean are1000WoodAvailable = false;

        for (Ingredient ingredient :
                App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().keySet()) {
            if (ingredient instanceof Wood) {
                int value =
                        App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().getOrDefault(ingredient, 0);
                if (value >= 1000) {
                    App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().put(ingredient,
                            value - 1000);
                    are1000WoodAvailable = true;
                    break;
                }
            }
        }

        if (!are1000WoodAvailable) {
            return false;
        }

        for (Ingredient ingredient : App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().keySet()) {
            if (ingredient instanceof Coin) {
                int value = App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().getOrDefault(ingredient, 0);
                if (isRewardTwice) {
                    App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().put(ingredient, value + 50000);
                } else {
                    App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().put(ingredient, value + 25000);
                }
            }
        }

//        for (NpcHome home : App.getGameState().getMap().getNpcHomes()) {
//            if (home.getNpc().getType().equals(NPCType.Robin)) {
//                home.getNpc().setThirdQuestDone(true);
//                break;
//            }
//        }

        return true;
    }

}
