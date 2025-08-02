package com.yourgame.model.WeatherAndTime;

import java.util.ArrayList;
import java.util.Random;

/**
 * Manages the in-game time, date, season, and weather with a real-time update cycle.
 * The timing is based on the Stardew Valley day cycle, where time advances based on real seconds.
 */
public class TimeSystem {

    // Stardew Valley timing: 10 in-game minutes pass every 7 real-world seconds.
    private static final float REAL_SECONDS_PER_10_IN_GAME_MINUTES = 7.0f;
    private static final int GAME_DAY_START_MINUTE = 360; // 6:00 AM
    private static final int GAME_DAY_END_MINUTE = 1560; // 2:00 AM (next day)

    private Season season;
    private DaysOfTheWeek dayOfWeek;
    private int dayOfMonth;
    private float timeOfDayInMinutes; // Total minutes from midnight

    private float timeAccumulator = 0f; // Accumulates real-time seconds

    private Weather weather;
    private Weather nextDayWeather;
    private final Random random = new Random();

    public TimeSystem() {
        this.dayOfMonth = 1;
        this.dayOfWeek = DaysOfTheWeek.Saturday;
        this.season = Season.Spring;
        this.timeOfDayInMinutes = GAME_DAY_START_MINUTE;
        this.weather = Weather.Sunny;
        this.nextDayWeather = createNextDayWeather();
    }

    /**
     * Updates the in-game time based on the real-time delta.
     * @param delta The time in seconds since the last frame.
     * @return True if a new day has started, otherwise false.
     */
    public boolean update(float delta) {
        timeAccumulator += delta;

        // Check if enough real time has passed to advance in-game time
        if (timeAccumulator >= REAL_SECONDS_PER_10_IN_GAME_MINUTES) {
            timeAccumulator -= REAL_SECONDS_PER_10_IN_GAME_MINUTES;
            timeOfDayInMinutes += 10;
        }

        // Check if the day has ended
        if (timeOfDayInMinutes >= GAME_DAY_END_MINUTE) {
            advanceToNextDay();
            return true; // Signal that a new day has begun
        }
        return false;
    }

    /**
     * Advances the game state to the next day.
     */
    private void advanceToNextDay() {
        this.dayOfMonth++;
        if (this.dayOfMonth > 28) {
            this.dayOfMonth = 1;
            advanceToNextSeason();
        }

        // Advance day of the week
        DaysOfTheWeek[] days = DaysOfTheWeek.values();
        int currentIndex = this.dayOfWeek.ordinal();
        this.dayOfWeek = days[(currentIndex + 1) % days.length];

        // Update weather
        this.weather = this.nextDayWeather;
        this.nextDayWeather = createNextDayWeather();
        
        // Reset time to the start of the new day
        this.timeOfDayInMinutes = GAME_DAY_START_MINUTE;
    }

    private void advanceToNextSeason() {
        Season[] seasons = Season.values();
        int currentIndex = this.season.ordinal();
        this.season = seasons[(currentIndex + 1) % seasons.length];
    }

    private Weather createNextDayWeather() {
        ArrayList<Weather> possibleWeathers = new ArrayList<>();
        for (Weather w : Weather.values()) {
            if (w.getSeasons().contains(this.season)) {
                possibleWeathers.add(w);
            }
        }
        if (possibleWeathers.isEmpty()) return Weather.Sunny; // Fallback
        return possibleWeathers.get(random.nextInt(possibleWeathers.size()));
    }

    // --- GETTERS for displaying info on the HUD ---

    public int getHour() {
        return (int) (timeOfDayInMinutes / 60);
    }

    public int getMinute() {
        return (int) (timeOfDayInMinutes % 60);
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public DaysOfTheWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public Season getSeason() {
        return season;
    }

    public Weather getWeather() {
        return weather;
    }
}
