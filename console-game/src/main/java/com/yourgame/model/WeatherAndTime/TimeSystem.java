package com.yourgame.model.WeatherAndTime;

import java.util.ArrayList;
import java.util.Random;

import com.yourgame.model.App;
import com.yourgame.model.GameState;

public class TimeSystem {
    public TimeSystem clone;
    private Season season;
    private DaysOfTheWeek dayOfWeek;
    private int day;
    private int hour;
    private Weather weather;
    private Weather nextDayWeather;

    public TimeSystem() {
        this.hour = 9;
        this.dayOfWeek = DaysOfTheWeek.Saturday;
        this.season = Season.Spring;
        this.day = 1;
        this.weather = Weather.Sunny;
        this.nextDayWeather = Weather.Sunny;

    }

    public void advancedHour(int h){
        this.hour += h;
        while (this.hour >= 22){
            this.hour -= 22 ;
            this.hour += 9;
            advancedDay(1);

        }

    }

    public void advancedDay(int d) {
        for (int i = 0; i < d; i++) {
            this.day++;
            if (this.day > 28) {
                this.day = 1;
                advancedSeason();
            }
            DaysOfTheWeek[] days = DaysOfTheWeek.values();
            int currentIndex = this.dayOfWeek.ordinal();
            this.dayOfWeek = days[(currentIndex + 1) % days.length];
        }
        if (d == 1) {
            this.weather = this.nextDayWeather;
            this.nextDayWeather = createNextDayWeather();
        } else {
            this.weather = createNextDayWeather();
            this.nextDayWeather = createNextDayWeather();
        }
        App.getGameState().MakeGameReadyForNextDay(); 
    }

    public void advancedSeason() {
        Season[] seasons = Season.values();
        int currentIndex = this.season.ordinal();
        if (currentIndex < 3) {
            this.season = seasons[currentIndex + 1];
        } else {
            this.season = Season.Spring;
        }
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

    public Weather createNextDayWeather() {
        Weather[] weather = Weather.values();
        ArrayList<Weather> weatherList = new ArrayList<>();
        for (Weather w : weather) {
            if (w.getSeasons().contains(this.season)) {
                weatherList.add(w);
            }
        }
        Random random = new Random();
        int index = random.nextInt(weatherList.size());
        return weatherList.get(index);
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