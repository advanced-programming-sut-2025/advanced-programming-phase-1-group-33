package com.yourgame.model.NPC;

import com.badlogic.gdx.math.Vector2;

import java.util.TreeMap;

public class Schedule {
    // Key: Time of day (e.g., 900 for 9:00 AM). Value: Destination coordinates.
    private final TreeMap<Integer, Vector2> schedulePoints = new TreeMap<>();

    public Schedule add(int time, Vector2 point) {
        schedulePoints.put(time, point);
        return this;
    }

    /**
     * Gets the current destination based on the time of day.
     * @param time The current time (e.g., 1330 for 1:30 PM).
     * @return The target destination, or null if no destination is scheduled yet.
     */
    public Vector2 getDestination(int time) {
        // floorEntry gets the last entry whose key is less than or equal to the given time.
        var entry = schedulePoints.floorEntry(time);
        return (entry != null) ? entry.getValue() : null;
    }
}
