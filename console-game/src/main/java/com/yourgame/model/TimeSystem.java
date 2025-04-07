package com.yourgame.model;
import com.yourgame.model.enums.Season;
public class TimeSystem {
    private int hour;
    private int dayOfMonth;
    private Season season;
    private int year;

    public TimeSystem(int hour, int dayOfMonth, Season season, int year) {
        this.hour = hour;
        this.dayOfMonth = dayOfMonth;
        this.season = season;
        this.year = year;
    }

    public void advanceHour() {
        hour++;
        if (hour >= 24) {
            hour = 0;
            advanceDay();
        }
    }

    public void advanceDay() {
        dayOfMonth++;
        if (dayOfMonth > getDaysInMonth()) {
            dayOfMonth = 1;
            advanceSeason();
        }
    }

    private void advanceSeason() {
        // Logic to advance the season based on the current month
        // This is a placeholder for the actual implementation
    }

    private int getDaysInMonth() {
        // Logic to return the number of days in the current month
        // This is a placeholder for the actual implementation
        return 30; // Example value
    }

    public String formatTime() {
        return String.format("%02d:00", hour);
    }

    public String formatDate() {
        return String.format("%d/%d/%d", dayOfMonth, season.ordinal() + 1, year);
    }

    public boolean isNight() {
        return hour < 6 || hour >= 18; // Example logic for night time
    }

    // Getters and setters for hour, dayOfMonth, season, and year can be added here
}