package com.yourgame.model;

import java.awt.Dimension;
import java.util.Map;

public class Building {
    private String name;
    private Map<Item, Integer> cost;
    private Coordinate coordinate;
    private Dimension size;
    private int capacity;
    private int upgradeLevel;

    public Building(String name, Map<Item, Integer> cost, Coordinate coordinate, Dimension size, int capacity, int upgradeLevel) {
        this.name = name;
        this.cost = cost;
        this.coordinate = coordinate;
        this.size = size;
        this.capacity = capacity;
        this.upgradeLevel = upgradeLevel;
    }

    // Getters and Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<Item, Integer> getCost() {
        return cost;
    }

    public void setCost(Map<Item, Integer> cost) {
        this.cost = cost;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate location) {
        this.coordinate = location;
    }

    public Dimension getSize() {
        return size;
    }

    public void setSize(Dimension size) {
        this.size = size;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getUpgradeLevel() {
        return upgradeLevel;
    }

    public void setUpgradeLevel(int upgradeLevel) {
        this.upgradeLevel = upgradeLevel;
    }
}
