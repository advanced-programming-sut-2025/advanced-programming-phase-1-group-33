package com.yourgame.model.Animals;

import com.yourgame.model.Placeable;

import java.awt.*;
import java.util.ArrayList;

public class Habitat implements Placeable {
    private final HabitatType type;
    private final HabitatSize size;
    private final ArrayList<Animal> animals;
    private final Rectangle bounds;


    public Habitat(HabitatType type, HabitatSize size , int x , int y , int width, int height) {
        this.type = type;
        this.size = size;
        animals = new ArrayList<>();
        this.bounds = new Rectangle(x, y, width, height);
    }

    public HabitatType getType() {
        return type;
    }

    public HabitatSize getSize() {
        return size;
    }

    public int getTotalCapacity() {
        return size.getCapacity();
    }

    public int getEmptyCapacity() {
        return size.getCapacity() - animals.size();
    }

    public boolean hasEmptyCapacity() {
        return getEmptyCapacity() > 0;
    }

    public ArrayList<Animal> getAnimals() {
        return animals;
    }

    public boolean addAnimal(Animal animal) {
        if (hasEmptyCapacity()) {
            animals.add(animal);
            return true;
        }
        return false;
    }

    public void removeAnimal(Animal animal) {
        animals.remove(animal);
    }

    @Override
    public Rectangle getBounds() {
        return bounds;
    }
    public char getSymbol() {
        if(type == HabitatType.Barn) {
            return 'b';
        }
        return 'c';
    }
}
