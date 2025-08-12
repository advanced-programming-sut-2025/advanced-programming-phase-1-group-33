package com.yourgame.model.WeatherAndTime;

import com.yourgame.model.Farming.Plant;

import java.util.ArrayList;
import java.util.Random;
import java.util.List;

public class TimeSystem {
    private final List<TimeObserver> dayObservers = new ArrayList<>();
    private final List<TimeObserver> hourObservers = new ArrayList<>();
    private final List<Plant> plants = new ArrayList<>();

    private Season season;
    private DaysOfTheWeek dayOfWeek;
    private int day;
    private int minutes = 0;

    private int hour;
    private Weather weather;
    private Weather nextDayWeather;

    public TimeSystem() {
        this.minutes = 0;
        this.hour = 6; // Stardew Valley starts at 6:00 AM
        this.dayOfWeek = DaysOfTheWeek.Saturday;
        this.season = Season.Spring;
        this.day = 1;
        this.weather = Weather.Sunny;
        this.nextDayWeather = Weather.Sunny;
    }

    public void addPlant(Plant plant) {
        plants.add(plant);
    }

    public void removePlant(Plant plant) {
        plants.remove(plant);
    }

    public void addDayObserver(TimeObserver observer) {
        dayObservers.add(observer);
    }
    public void removeDayObserver(TimeObserver observer) {
        dayObservers.remove(observer);
    }
    private void notifyDayObservers() {
        for (TimeObserver observer : dayObservers) {
            observer.onTimeChanged(this);
        }
    }

    public void addHourObserver(TimeObserver observer) {
        hourObservers.add(observer);
    }
    public void removeHourObserver(TimeObserver observer) {
        hourObservers.remove(observer);
    }
    private void notifyHourObservers() {
        for (TimeObserver observer : hourObservers) {
            observer.onTimeChanged(this);
        }
    }

    private void notifyPlants() {
        for (Plant plant : plants) {
            plant.onTimeChanged(this);
        }
    }

    public void advanceMinutes(int gameMinutes) {
        this.minutes += gameMinutes;
        if (this.minutes >= 60) {
            int increment = this.minutes / 60;
            this.hour += increment;
            this.minutes %= 60;
            for (int i = 0; i < increment; i++) {
                notifyHourObservers();
            }
        }

        // If it's past midnight
        if (this.hour >= 24) {
            this.hour = this.hour % 24;
        }

        // Pass-out rule: if time >= 02:00 AM
        if (this.hour >= 2 && this.hour < 6) {
            this.hour = 6;
            this.minutes = 0;
            advanceDay(1);
        }
    }

    public void advanceDay(int d) {
        for (int i = 0; i < d; i++) {
            this.day++;
            if (this.day > 28) {
                this.day = 1;
                advanceSeason();
            }
            DaysOfTheWeek[] days = DaysOfTheWeek.values();
            int currentIndex = this.dayOfWeek.ordinal();
            this.dayOfWeek = days[(currentIndex + 1) % days.length];

            this.weather = this.nextDayWeather;
            this.nextDayWeather = createNextDayWeather();

            notifyPlants();
            notifyDayObservers();
        }
    }

    private void advanceSeason() {
        Season[] seasons = Season.values();
        int currentIndex = this.season.ordinal();
        this.season = (currentIndex < 3) ? seasons[currentIndex + 1] : Season.Spring;
    }

    public Weather createNextDayWeather() {
        Weather[] allWeathers = Weather.values();
        List<Weather> weatherList = new ArrayList<>();
        for (Weather w : allWeathers) {
            if (w.getSeasons().contains(this.season)) {
                weatherList.add(w);
            }
        }
        return weatherList.get(new Random().nextInt(weatherList.size()));
    }

    public Season getSeason() {
        return season;
    }

    public DaysOfTheWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public int getDay() {
        return day;
    }

    public int getHour() {
        return hour;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public Weather getNextDayWeather() {
        return nextDayWeather;
    }

    public void setNextDayWeather(Weather nextDayWeather) {
        this.nextDayWeather = nextDayWeather;
    }

    public String getTimeString() {
        return String.format("%02d:%02d", this.hour, this.minutes);
    }

    public String getDateToString() {
        String dayAbbreviation = this.dayOfWeek.getDayOfWeek().substring(0, 3);
        return String.format("%s %d", dayAbbreviation, this.day);
    }

    public TimeSystem clone() {
        TimeSystem cloned = new TimeSystem();
        cloned.season = season;
        cloned.dayOfWeek = dayOfWeek;
        cloned.day = day;
        cloned.hour = hour;
        cloned.weather = weather;
        cloned.nextDayWeather = nextDayWeather;
        return cloned;
    }
}
