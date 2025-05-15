package com.yourgame.model.Npc;

import com.yourgame.model.Animals.AnimalGood;
import com.yourgame.model.Animals.AnimalGoodType;
import com.yourgame.model.App;
import com.yourgame.model.Item.ForagingCrop;
import com.yourgame.model.Item.ForagingMineral;
import com.yourgame.model.Item.Fruit;
import com.yourgame.model.ManuFactor.ArtisanGood;
import com.yourgame.model.ManuFactor.ArtisanGoodType;
import com.yourgame.model.ManuFactor.Ingredient;
import com.yourgame.model.Map.Stone;
import com.yourgame.model.Map.Wood;
import com.yourgame.model.Recipes.CookingRecipe;
import com.yourgame.model.UserInfo.Coin;
import com.yourgame.model.UserInfo.Player;
import com.yourgame.model.WeatherAndTime.Season;
import com.yourgame.model.WeatherAndTime.Weather;
import com.yourgame.model.enums.SymbolType;

import java.util.ArrayList;
import java.util.Random;

public class NPC {

    private final NPCType type;
    private boolean isFirstQuestDone = false;
    private boolean isSecondQuestDone = false;
    private boolean isThirdQuestDone = false;

    public NPC(NPCType type) {
        this.type = type;
    }

    public NPCType getType() {
        return type;
    }

    public boolean isFirstQuestDone() {
        return isFirstQuestDone;
    }

    public void setFirstQuestDone(boolean firstQuestDone) {
        isFirstQuestDone = firstQuestDone;
    }

    public boolean isSecondQuestDone() {
        return isSecondQuestDone;
    }

    public void setSecondQuestDone(boolean secondQuestDone) {
        isSecondQuestDone = secondQuestDone;
    }

    public boolean isThirdQuestDone() {
        return isThirdQuestDone;
    }

    public void setThirdQuestDone(boolean thirdQuestDone) {
        isThirdQuestDone = thirdQuestDone;
    }

    public boolean doFirstQuest(boolean isRewardTwice) {

        if (this.type.equals(NPCType.Abigail)) {

            return AbigailQuests.doFirstQuest(isRewardTwice);

        } else if (this.type.equals(NPCType.Sebastian)) {

            return SebastianQuests.doFirstQuest(isRewardTwice);

        } else if (this.type.equals(NPCType.Harvey)) {

            return HarveyQuests.doFirstQuest(isRewardTwice);

        } else if (this.type.equals(NPCType.Leah)) {

            return LeahQuests.doFirstQuest(isRewardTwice);

        } else if (this.type.equals(NPCType.Robin)) {

            return RobinQuests.doFirstQuest(isRewardTwice);

        }

        return false;
    }

    public boolean doSecondQuest(boolean isRewardTwice) {

        if (this.type.equals(NPCType.Abigail)) {

            return AbigailQuests.doSecondQuest(isRewardTwice);

        } else if (this.type.equals(NPCType.Sebastian)) {

            return SebastianQuests.doSecondQuest(isRewardTwice);

        } else if (this.type.equals(NPCType.Harvey)) {

            return HarveyQuests.doSecondQuest(isRewardTwice);

        } else if (this.type.equals(NPCType.Leah)) {

            return LeahQuests.doSecondQuest(isRewardTwice);

        } else if (this.type.equals(NPCType.Robin)) {

            return RobinQuests.doSecondQuest(isRewardTwice);

        }

        return false;

    }

    public boolean doThirdQuest(boolean isRewardTwice) {

        if (this.type.equals(NPCType.Abigail)) {

            return AbigailQuests.doThirdQuest(isRewardTwice);

        } else if (this.type.equals(NPCType.Sebastian)) {

            return SebastianQuests.doThirdQuest(isRewardTwice);

        } else if (this.type.equals(NPCType.Harvey)) {

            return HarveyQuests.doThirdQuest(isRewardTwice);

        } else if (this.type.equals(NPCType.Leah)) {

            return LeahQuests.doThirdQuest(isRewardTwice);

        } else if (this.type.equals(NPCType.Robin)) {

            return RobinQuests.doThirdQuest(isRewardTwice);

        }
        return false;

    }

    public boolean isFavoriteGift(Ingredient gift) {

        if (this.type.equals(NPCType.Abigail)) {

            if (gift instanceof Stone) {
                return true;
            }
            if (gift.equals(ForagingMineral.Iron)) {
                return true;
            }
            if (gift instanceof ArtisanGood) {
                return ((ArtisanGood) gift).getType().equals(ArtisanGoodType.Coffee);
            }

        } else if (this.type.equals(NPCType.Sebastian)) {
            if (gift instanceof AnimalGood) {
                if (((AnimalGood) gift).getType().equals(AnimalGoodType.Wool)) {
                    return true;
                }
            }
            if (gift.equals(CookingRecipe.PumpkinPie)) {
                return true;
            }
            return gift.equals(CookingRecipe.Pizza);

        } else if (this.type.equals(NPCType.Harvey)) {

            if (gift instanceof ArtisanGood) {
                if (((ArtisanGood) gift).getType().equals(ArtisanGoodType.Coffee)) {
                    return true;
                }
            }
            if (gift instanceof ArtisanGood) {
                if (((ArtisanGood) gift).getType().equals(ArtisanGoodType.Pickles)) {
                    return true;
                }
            }
            if (gift instanceof ArtisanGood) {
                return ((ArtisanGood) gift).getType().equals(ArtisanGoodType.Wine);
            }

        } else if (this.type.equals(NPCType.Leah)) {

            if (gift.equals(CookingRecipe.Salad)) {
                return true;
            }
            if (gift.equals(ForagingCrop.Grape)) {
                return true;
            }
            if (gift instanceof ArtisanGood) {
                return ((ArtisanGood) gift).getType().equals(ArtisanGoodType.Wine);
            }

        } else if (this.type.equals(NPCType.Robin)) {

            if (gift.equals(CookingRecipe.Spaghetti)) {
                return true;
            }
            if (gift instanceof Wood) {
                return true;
            }
            if (gift instanceof ArtisanGood) {
                return ((ArtisanGood) gift).getType().equals(ArtisanGoodType.IronBar);
            }

        }

        return false;
    }

    public String getDialogue(NPCFriendshipLevel level) {

        int index = 6;

        if (App.getGameState().getGameTime().getSeason().equals(Season.Summer) || App.getGameState().getGameTime().getWeather().equals(Weather.Sunny)) {
            index = 0;
        } else if (App.getGameState().getGameTime().getWeather().equals(Weather.Rainy)) {
            index = 1;
        } else if (App.getGameState().getGameTime().getWeather().equals(Weather.Sunny) && App.getGameState().getGameTime().getSeason().equals(Season.Spring)) {
            index = 2;
        } else if (App.getGameState().getGameTime().getSeason().equals(Season.Fall) && App.getGameState().getGameTime().getHour() > 18) {
            index = 3;
        } else if (App.getGameState().getGameTime().getWeather().equals(Weather.Snowy)) {
            index = 4;
        } else if (App.getGameState().getGameTime().getHour() > 18 && level.equals(NPCFriendshipLevel.LevelThree)) {
            index = 5;
        }

        return this.type.getDialogues().get(index);
    }

    public SymbolType getSymbol() {
        return this.type.getSymbol();
    }

    public boolean giveRandomGiftToPlayer(Player player) {

        Random rand = new Random();
        int randomNumber = rand.nextInt(2);

        if (randomNumber == 0) {
            return false;
        }

        int playerIndex = App.getGameState().getPlayers().indexOf(player);
        int secondRandomNumber = rand.nextInt(2);

        if (this.type.equals(NPCType.Abigail)) {

            if (secondRandomNumber == 0) {

                App.getGameState().getPlayers().get(playerIndex).getBackpack().getIngredientQuantity().put(ForagingMineral.Diamond,
                        App.getGameState().getPlayers().get(playerIndex).getBackpack().getIngredientQuantity().getOrDefault(ForagingMineral.Diamond, 0) + 1);

            } else {

                App.getGameState().getPlayers().get(playerIndex).getBackpack().getIngredientQuantity().put(ForagingMineral.Quartz,
                        App.getGameState().getPlayers().get(playerIndex).getBackpack().getIngredientQuantity().getOrDefault(ForagingMineral.Quartz, 0) + 5);

            }

        } else if (this.type.equals(NPCType.Leah)) {

            if (secondRandomNumber == 0) {

                App.getGameState().getPlayers().get(playerIndex).getBackpack().getIngredientQuantity().put(ForagingMineral.Emerald,
                        App.getGameState().getPlayers().get(playerIndex).getBackpack().getIngredientQuantity().getOrDefault(ForagingMineral.Emerald, 0) + 2);

            } else {

                for (Ingredient ingredient :
                        App.getGameState().getPlayers().get(playerIndex).getBackpack().getIngredientQuantity().keySet()) {
                    if (ingredient instanceof Coin) {
                        App.getGameState().getPlayers().get(playerIndex).getBackpack().getIngredientQuantity().put(ingredient, App.getGameState().getPlayers().get(playerIndex).getBackpack().getIngredientQuantity().getOrDefault(ingredient, 0) + 200);
                    }
                }

            }

        } else if (this.type.equals(NPCType.Robin)) {

            if (secondRandomNumber == 0) {
                App.getGameState().getPlayers().get(playerIndex).getBackpack().getIngredientQuantity().put(ForagingMineral.Iron,
                        App.getGameState().getPlayers().get(playerIndex).getBackpack().getIngredientQuantity().getOrDefault(ForagingMineral.Iron, 0) + 50);
            } else {

                for (Ingredient ingredient :
                        App.getGameState().getPlayers().get(playerIndex).getBackpack().getIngredientQuantity().keySet()) {
                    if (ingredient instanceof Wood) {
                        App.getGameState().getPlayers().get(playerIndex).getBackpack().getIngredientQuantity().put(ingredient, App.getGameState().getPlayers().get(playerIndex).getBackpack().getIngredientQuantity().getOrDefault(ingredient, 0) + 100);
                    }
                }

            }

        } else if (this.type.equals(NPCType.Harvey)) {

            if (secondRandomNumber == 0) {

                App.getGameState().getPlayers().get(playerIndex).getBackpack().getIngredientQuantity().put(Fruit.Orange,
                        App.getGameState().getPlayers().get(playerIndex).getBackpack().getIngredientQuantity().getOrDefault(Fruit.Orange, 0) + 10);

            } else {
                App.getGameState().getPlayers().get(playerIndex).getBackpack().getIngredientQuantity().put(Fruit.Banana,
                        App.getGameState().getPlayers().get(playerIndex).getBackpack().getIngredientQuantity().getOrDefault(Fruit.Banana, 0) + 10);
            }

        } else if (this.type.equals(NPCType.Sebastian)) {

            if (secondRandomNumber == 0) {
                App.getGameState().getPlayers().get(playerIndex).getBackpack().getIngredientQuantity().put(ForagingMineral.Gold,
                        App.getGameState().getPlayers().get(playerIndex).getBackpack().getIngredientQuantity().getOrDefault(ForagingMineral.Gold, 0) + 10);
            } else {
                App.getGameState().getPlayers().get(playerIndex).getBackpack().getIngredientQuantity().put(ForagingMineral.Ruby,
                        App.getGameState().getPlayers().get(playerIndex).getBackpack().getIngredientQuantity().getOrDefault(ForagingMineral.Ruby, 0) + 2);
            }

        }


        return true;
    }

    public String showQuestLists () {

        String message = "Quests List:\n";
        RelationWithNPC relation = null;
        ArrayList<String> quests = new ArrayList<>();

        if (this.type.equals(NPCType.Abigail)) {

            relation = App.getGameState().getCurrentPlayer().getRelationWithAbigail();
            quests = AbigailQuests.getQuestsNames();

        } else if (this.type.equals(NPCType.Leah)) {

            relation = App.getGameState().getCurrentPlayer().getRelationWithLeah();
            quests = LeahQuests.getQuestsNames();

        } else if (this.type.equals(NPCType.Robin)) {

            relation = App.getGameState().getCurrentPlayer().getRelationWithRobin();
            quests = RobinQuests.getQuestsNames();

        } else if (this.type.equals(NPCType.Harvey)) {

            relation = App.getGameState().getCurrentPlayer().getRelationWithHarvey();
            quests = HarveyQuests.getQuestsNames();

        } else if (this.type.equals(NPCType.Sebastian)) {

            relation = App.getGameState().getCurrentPlayer().getRelationWithSebastian();
            quests = SebastianQuests.getQuestsNames();

        }

        if (relation == null) {
            return message;
        }

        message += quests.get(0);

        if (this.isFirstQuestDone) {
            message += "    (already completed)";
        }


        if (!relation.isSecondQuestLocked()) {
            message += "\n";
            message += quests.get(1);
        }

        if (this.isSecondQuestDone) {
            message += "    (already completed)";
        }

        if (!relation.isThirdQuestLocked()) {
            message += "\n";
            message += quests.get(2);
        }

        if (this.isThirdQuestDone) {
            message += "    (already completed)";
        }

        return message;
    }

}