package com.yourgame.model.ManuFactor;

import com.yourgame.model.IO.Response;
import com.yourgame.model.Recipes.CraftingRecipes;
import com.yourgame.model.UserInfo.Player;
import com.yourgame.model.WeatherAndTime.TimeFrame;
import com.yourgame.model.WeatherAndTime.TimeSystem;
import com.yourgame.model.*;

import java.util.HashMap;

public abstract class ArtisanMachine {
    protected TimeSystem timeOfRequest;
    protected HashMap<ArtisanGood, TimeFrame> processingTimes;
    protected ArtisanGood producingGood;

    public ArtisanMachine() {
        processingTimes = new HashMap<>();
        timeOfRequest = null;
        producingGood = null;
    }

    public void use() {
        timeOfRequest = App.getGameState().getGameTime().clone();
    }

    public ArtisanGood get() {
        if (isReady().getSuccessful())
            return producingGood;
        return null;
    }

    public abstract Response canUse(Player player, String product);

    public void reset() {
        timeOfRequest = null;
        producingGood = null;
    }

    public Response isReady() {
        if (timeOfRequest == null)
            return new Response(false, "You don't have any artisan goods in machine yet!!");
        int todayDate = App.getGameState().getGameTime().getDate();
        int todayHour = App.getGameState().getGameTime().getHour();
        if (App.getGameState().getGameTime().getSeason() != timeOfRequest.getSeason()) {
            todayDate += 28;
            if(timeOfRequest.getDate() + processingTimes.get(producingGood).getDays() < todayDate ||
                    timeOfRequest.getDate() + processingTimes.get(producingGood).getDays() == todayDate &&
                            timeOfRequest.getHour() + processingTimes.get(producingGood).getHours() <= todayHour)
                return new Response(true, "Your product is Ready.");
        }
        return new Response(false, "Your product is Not Ready.");
    }

    public static ArtisanMachine getArtisanMachineByRecipe(CraftingRecipes recipe) {
        if (recipe == null)
            return null;
        return switch (recipe) {
            case CharcoalKiln -> new CharcoalKiln();
            case Furnace -> new Furnace();
            case BeeHouse -> new BeeHouse();
            case CheesePress -> new CheesePress();
            case Keg -> new Keg();
            case Loom -> new Loom();
            case MayonnaiseMachine -> new MayonnaiseMachine();
            case OilMaker -> new OilMaker();
            case PreservesJar -> new PreservesJar();
            case Dehydrator -> new Dehydrator();
            case FishSmoker -> new FishSmoker();
            default -> null;
        };
    }
}
