package com.yourgame.model;

import com.yourgame.model.enums.Weather;
import java.util.Random;

public class WeatherSystem {
    private Weather currentWeather;
    private Weather tomorrowWeather;
    private Random random;

    public WeatherSystem() {
        this.random = new Random();
        // Initialize the weather for today and tomorrow
        this.currentWeather = generateRandomWeather();
        this.tomorrowWeather = generateRandomWeather();
    }

    // Getter for currentWeather
    public Weather getCurrentWeather() {
        return currentWeather;
    }

    // Getter for tomorrowWeather
    public Weather getTomorrowWeather() {
        return tomorrowWeather;
    }

    // Predict tomorrow's weather (could be extended with more logic)
    public Weather predictTomorrowWeather() {
        return tomorrowWeather;
    }

    // Advance the weather system to the next day
    public void advanceToNextDay() {
        // Tomorrow's weather becomes today's weather, and generate new weather for tomorrow
        this.currentWeather = this.tomorrowWeather;
        this.tomorrowWeather = generateRandomWeather();
    }

    // Utility method to randomly choose a weather type
    private Weather generateRandomWeather() {
        Weather[] weathers = Weather.values();
        return weathers[random.nextInt(weathers.length)];
    }
}
