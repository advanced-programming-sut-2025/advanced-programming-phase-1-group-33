package com.yourgame.model.Weather;

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
        // Assuming Season enum values are in order. This will wrap around
        // automatically.
        Season[] seasons = Season.values();
        int nextOrdinal = (season.ordinal() + 1) % seasons.length;
        season = seasons[nextOrdinal];
    }

    private int getDaysInMonth() {
        // Logic to return the number of days in the current month
        // This is a placeholder for the actual implementation
        return 28; // Example value
    }

    public String formatTime() {
        return String.format("%02d:00", hour);
    }

    public String formatDate() {
        return String.format("%d/%d/%d", dayOfMonth, season.ordinal() + 1, year);
    }

    // Returns the current time (hour)
    public String getTime() {
        return formatTime();
    }

    // Returns the current date
    public String getDate() {
        return formatDate();
    }

    // Returns the date and time together
    public String getDateTime() {
        return String.format("%s %s", formatDate(), formatTime());
    }

    // Returns the season as a String
    public String getSeason() {
        return season.toString();
    }

    // Returns the day of the week computed from dayOfMonth.
    public String getDayOfWeek() {
        String[] days = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };
        // This calculation is a placeholder. Adjust the offset as needed.
        int index = (dayOfMonth - 1) % 7;
        return days[index];
    }

    public boolean isNight() {
        return hour < 6 || hour >= 18; // Example logic for night time
    }

    // Getters and setters for hour, dayOfMonth, season, and year can be added here
}