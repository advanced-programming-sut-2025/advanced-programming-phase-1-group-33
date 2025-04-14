package com.yourgame.model.Building;

import java.awt.Dimension;
import java.util.Map;

import com.yourgame.model.Item.Item;
import com.yourgame.model.Map.Coordinate;

public class Coop extends Building{

    public Coop(String name, Map<Item, Integer> cost, Coordinate coordinate, Dimension size, int capacity,
            int upgradeLevel) {
        super(name, cost, coordinate, size, capacity, upgradeLevel);
        //TODO Auto-generated constructor stub
    }
    
}
