package com.yourgame.model.Weather;

import com.yourgame.model.enums.Weather;
import com.yourgame.model.enums.Season;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WeatherSystem {
    private Weather currentWeather;
    private Weather tomorrowWeather;

    private Random random;
    // private Season currentSeason;

    // Pass initial season (from your TimeSystem, for example) to the constructor.
    public WeatherSystem(Season currentSeason) {
        this.random = new Random();
        this.currentWeather = generateRandomWeather(currentSeason);
        this.tomorrowWeather = generateRandomWeather(currentSeason);
    }

    // Utility method to generate weather valid for a specific season.
    private Weather generateRandomWeather(Season season) {
        Weather[] weathers = Weather.values();
        List<Weather> validWeathers = new ArrayList<>();
        for (Weather weather : weathers) {
            if (Weather.isValidForSeason(weather, season)) {
                validWeathers.add(weather);
            }
        }
        // Safety check if no weather is valid.
        if (validWeathers.isEmpty()) {
            return Weather.SUNNY; // fallback option
        }
        return validWeathers.get(random.nextInt(validWeathers.size()));
    }

    // Getter for currentWeather.
    public Weather getCurrentWeather() {
        return currentWeather;
    }

    // Getter for tomorrowWeather.
    public Weather getTomorrowWeather() {
        return tomorrowWeather;
    }

    // Predict tomorrow's weather.
    public Weather predictTomorrowWeather() {
        return tomorrowWeather;
    }

    // Advance the weather system to the next day.
    public void advanceToNextDay(Season currentSeason) {
        this.currentWeather = this.tomorrowWeather;
        // In a real game you might update currentSeason as well if needed.
        this.tomorrowWeather = generateRandomWeather(currentSeason);
    }

    public void setCurrentWeather(Weather currentWeather) {
        this.currentWeather = currentWeather;
    }

    public void setTomorrowWeather(Weather tomorrowWeather) {
        this.tomorrowWeather = tomorrowWeather;
    }

}