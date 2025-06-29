package com.yourgame.model.ManuFactor;

import com.yourgame.model.IO.Response;
import com.yourgame.model.Recipes.CraftingRecipes;
import com.yourgame.model.UserInfo.Player;
import com.yourgame.model.WeatherAndTime.TimeFrame;
import com.yourgame.model.WeatherAndTime.TimeSystem;
import com.yourgame.model.*;

import java.util.HashMap;

public abstract class ArtisanMachine {
    protected ArtisanGood producingGood;
    protected TimeSystem timeOfRequest;
    protected HashMap<ArtisanGood, TimeFrame> processingTimes;

    public ArtisanMachine() {
        timeOfRequest = null;
        producingGood = null;
        processingTimes = new HashMap<>();
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
        if (timeOfRequest == null) {
            return new Response(false, "You don't have any artisan goods in machine yet!!");
        }
        int todayHour = App.getGameState().getGameTime().getHour();
        int todayDate = App.getGameState().getGameTime().getDay();
        if (App.getGameState().getGameTime().getSeason() != timeOfRequest.getSeason()) {
            todayDate += 28;
            if (timeOfRequest.getDay() + processingTimes.get(producingGood).getDays() < todayDate ||
                    timeOfRequest.getDay() + processingTimes.get(producingGood).getDays() == todayDate &&
                            timeOfRequest.getHour() + processingTimes.get(producingGood).getHours() <= todayHour) {

                return new Response(true, "Your product is Ready.");
            }
        }
        return new Response(false, "Your product is Not Ready.");
    }

    public static ArtisanMachine getArtisanMachineByRecipe(CraftingRecipes recipe) {
        if (recipe == null){
            return null;
        }
        return switch (recipe) {
            case com.yourgame.model.ManuFactor.Furnace -> new Furnace();
            case com.yourgame.model.ManuFactor.CharcoalKiln -> new CharcoalKiln();
            case com.yourgame.model.ManuFactor.CheesePress -> new CheesePress();
            case com.yourgame.model.ManuFactor.BeeHouse -> new BeeHouse();
            case com.yourgame.model.ManuFactor.Loom -> new Loom();
            case com.yourgame.model.ManuFactor.Keg -> new Keg();
            case com.yourgame.model.ManuFactor.OilMaker -> new OilMaker();
            case com.yourgame.model.ManuFactor.MayonnaiseMachine -> new MayonnaiseMachine();
            case com.yourgame.model.ManuFactor.Dehydrator -> new Dehydrator();
            case com.yourgame.model.ManuFactor.FishSmoker -> new FishSmoker();
            case com.yourgame.model.ManuFactor.PreservesJar -> new PreservesJar();
            default -> null;
        };
    }
}
