package com.yourgame.model.WeatherAndTime;

public enum Season {
    Spring("Spring", "SpringButton"),
    Summer("Summer", "SummerButton"),
    Fall("Fall", "FallButton"),
    Winter("Winter", "WinterButton");

    private final String nameOfSeason;

    private String season;
    private final String pathToButton;

    Season(String nameOfSeason, String pathToButton) {
        this.nameOfSeason = nameOfSeason;
        this.pathToButton = pathToButton;
    }

    @Override
    public String toString() {
        return nameOfSeason;
    }

    public String getButtonPath() {
        return pathToButton;
    }

}
