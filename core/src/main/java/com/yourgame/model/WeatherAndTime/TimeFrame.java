package com.yourgame.model.WeatherAndTime;

public class TimeFrame {
    private final int days;
    private final int hours;

    public TimeFrame(int days, int hours) {
        this.days = days;
        this.hours = hours;
    }

    public int getDays() {
        return days;
    }

    public int getHours() {
        return hours;
    }
}
