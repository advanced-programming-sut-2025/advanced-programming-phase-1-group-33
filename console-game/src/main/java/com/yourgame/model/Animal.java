package com.yourgame.model;

import com.yourgame.model.enums.AnimalType;

public class Animal {
    private String name;
    private AnimalType type;  // Type of animal (Enum: COW, CHICKEN, etc.)
    private int friendship;   // Friendship level (e.g., 0 to 100)
    private int mood;         // Mood level (e.g., 0 to 100)
    private int daysSinceFed; // Days since the animal was fed
    private int daysSincePetted; // Days since the animal was petted
    private Item productReady;  // The product that the animal is ready to produce (e.g., milk, eggs)
    private Building locationBuilding; // Building where the animal resides (e.g., barn, coop)

    // Constructor
    public Animal(String name, AnimalType type, Building locationBuilding) {
        this.name = name;
        this.type = type;
        this.friendship = 50;   // Default friendship level
        this.mood = 50;         // Default mood
        this.daysSinceFed = 0;  // Default days since fed
        this.daysSincePetted = 0;  // Default days since petted
        this.productReady = null;  // No product ready initially
        this.locationBuilding = locationBuilding;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AnimalType getType() {
        return type;
    }

    public void setType(AnimalType type) {
        this.type = type;
    }

    public int getFriendship() {
        return friendship;
    }

    public void setFriendship(int friendship) {
        this.friendship = friendship;
    }

    public int getMood() {
        return mood;
    }

    public void setMood(int mood) {
        this.mood = mood;
    }

    public int getDaysSinceFed() {
        return daysSinceFed;
    }

    public void setDaysSinceFed(int daysSinceFed) {
        this.daysSinceFed = daysSinceFed;
    }

    public int getDaysSincePetted() {
        return daysSincePetted;
    }

    public void setDaysSincePetted(int daysSincePetted) {
        this.daysSincePetted = daysSincePetted;
    }

    public Item getProductReady() {
        return productReady;
    }

    public void setProductReady(Item productReady) {
        this.productReady = productReady;
    }

    public Building getLocationBuilding() {
        return locationBuilding;
    }

    public void setLocationBuilding(Building locationBuilding) {
        this.locationBuilding = locationBuilding;
    }

    // Method to feed the animal (resets daysSinceFed, increases mood and friendship)
    public void feed() {
        this.daysSinceFed = 0;  // Reset days since fed
        this.mood = Math.min(this.mood + 10, 100);  // Increase mood (max 100)
        this.friendship = Math.min(this.friendship + 5, 100);  // Increase friendship (max 100)
        System.out.println(this.name + " has been fed.");
    }

    // Method to pet the animal (resets daysSincePetted, increases mood)
    public void pet() {
        this.daysSincePetted = 0;  // Reset days since petted
        this.mood = Math.min(this.mood + 5, 100);  // Increase mood (max 100)
        this.friendship = Math.min(this.friendship + 3, 100);  // Increase friendship (max 100)
        System.out.println(this.name + " has been petted.");
    }

    // Method to check if the animal is ready to produce (i.e., ready to give a product)
    public void produce() {
        if (productReady != null) {
            System.out.println(this.name + " produced: " + productReady.getName());
            productReady = null;  // The product is now taken and no longer ready
        } else {
            System.out.println(this.name + " is not ready to produce anything.");
        }
    }

    // Method to simulate a day passing (increases daysSinceFed and daysSincePetted)
    public void endDay() {
        this.daysSinceFed++;
        this.daysSincePetted++;

        if (this.daysSinceFed >= 3) {
            this.mood -= 10;  // Decrease mood if the animal hasn't been fed in 3 days
        }


        if (this.daysSincePetted >= 3) {
            this.mood -= 5;  // Decrease mood if the animal hasn't been petted in 3 days
        }

        if (this.mood <= 0) {
            this.mood = 0;  // Minimum mood level
        }
    }
}
