package com.yourgame.model.Weather;

public enum DaysOfTheWeek {
    Saturday("Saturday"),
    Sunday("Sunday"),
    Monday("Monday"),
    Tuesday("Tuesday"),
    Wednesday("Wednesday"),
    Thursday("Thursday"),
    Friday("Friday");

    private final String dayOfWeek;
    DaysOfTheWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }
}
