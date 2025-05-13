package com.yourgame.model.Weather;

public enum Season {
    Spring("Spring"),
    Summer("Summer"),
    Fall("Fall"),
    Winter("Winter"),
    Special("[Spring, Summer, Fall, Winter]");

    private final String nameOfSeason;

    private String season;

    Season(String nameOfSeason) {
        this.nameOfSeason = nameOfSeason;
    }

    @Override
    public String toString() {
        return nameOfSeason;
    }
}
