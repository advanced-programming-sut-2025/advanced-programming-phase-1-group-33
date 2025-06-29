package com.yourgame.model.Animals;

public class Hay {
    private int number;

    public Hay() {
        this.number = 0;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void increaseNumber(int number) {
        this.number += number;
    }

    public void decreaseNumber(int number) {
        this.number -= number;
    }
}
