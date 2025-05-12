package com.yourgame.model.enums;

public enum Weather {
    SUNNY,
    RAINY,
    STORMY,
    SNOWY; 

    /**
     * Returns true if the weather is valid for the given season.
     */
    public static boolean isValidForSeason(Weather weather, Season season) {
        switch (weather) {
            case SUNNY:
                // Sunny weather is valid in every season.
                return true;
            case RAINY:
            case STORMY:
                // Rain and Storm are only possible in Spring, Summer, and Fall.
                return season == Season.SPRING ||
                       season == Season.SUMMER ||
                       season == Season.FALL;
            case SNOWY:
                // Snow is only possible in Winter.
                return season == Season.WINTER;
            default:
                return false;
        }
    }
    @Override
    public String toString() {
        // Capitalize the first letter, lowercase the rest
        String lower = name().toLowerCase();
        return Character.toUpperCase(lower.charAt(0)) + lower.substring(1);
    }

    public static Weather fromString(String input) {
        switch (input.toLowerCase()) {
            case "sunny": return SUNNY;
            case "rain": return RAINY;
            case "storm": return STORMY;
            case "snow": return SNOWY;
            default: throw new IllegalArgumentException("Unknown weather type: " + input);
        }
    }    
}
