package com.yourgame.controller.GameController;

import com.yourgame.model.GameState;
import com.yourgame.model.IO.Request;
import com.yourgame.model.IO.Response;
import com.yourgame.model.WeatherAndTime.Weather;

public class WeatherAndTimeController {
    private GameState gameState;

    public Response getTime() {
        return new Response(true, String.format("current time is: %d", gameState.getGameTime().getHour()));
    }

    public Response getDate() {
        return new Response(true, String.format("current season is %s, in %d ", gameState.getGameTime().getSeason().name(), gameState.getGameTime().getDate()));
    }

    public Response getDateTime() {
        return new Response(true, String.format("Season : %s , Day : %d , Hour : %d", gameState.getGameTime()
                .getSeason().name(), gameState.getGameTime().getDate(), gameState.getGameTime().getHour()));
    }

    public Response getDayOfWeek() {
        // TODO Auto-generated method stub
        return new Response(true, String.format("Day : %s ", gameState.getGameTime().getDayOfWeek().name()));

    }

    public Response getSeason() {
        // TODO Auto-generated method stub
        return new Response(true, "Current Season is " + gameState.getGameTime().getSeason());
    }

    public Response getAdvancedDate(Request request) {
        // TODO Auto-generated method stub

        int amountOfDays = Integer.parseInt(request.body.get("amount"));

        gameState.getGameTime().advancedDay(amountOfDays);

        return new Response(true, "Time Traveling... (" + amountOfDays + "Days)");
    }

    public Response getAdvancedTime(Request request) {
        int amountOfHours = Integer.parseInt(request.body.get("amount"));

        gameState.getGameTime().advancedHour(amountOfHours);

        return new Response(true, "Time Traveling... (" + amountOfHours + " hours)");
    }

    public Response getWeather() {
        return new Response(true, "Current Weather is " + gameState.getGameTime().getWeather().getName());
    }

    public Response cheatWeather(Request request) {
        String weather = request.body.get("Type");
        Weather w;
        try {
            w = Weather.valueOf(weather.trim());

        } catch (Exception exception) {
            return new Response(false, "Invalid weather");
        }
        gameState.getGameTime().setNextDayWeather(w);
        return new Response(true, "Tomorrow weather : " + w.getName());
    }

    public Response cheatThor(Request request) {
        // TODO Auto-generated method stub
        return new Response(false, "To Do We need to create the Thor After Map ");
    }

    public Response getWeatherForecast() {
        return new Response(true, "Tomorrow Weather is " + gameState.getGameTime().getWeather().getName());
    }
}
