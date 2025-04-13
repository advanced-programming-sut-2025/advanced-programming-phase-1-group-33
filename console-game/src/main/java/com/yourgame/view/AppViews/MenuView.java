package com.yourgame.view.AppViews;

import java.util.Scanner;

public class MenuView {
    private Scanner scanner;

    public MenuView() {
        this.scanner = new Scanner(System.in);
    }

    public void displayLoginRegisterOptions() {
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.print("Choose an option: ");
    }

    public void displayMainMenu() {
        System.out.println("Main Menu:");
        System.out.println("1. Start Game");
        System.out.println("2. Load Game");
        System.out.println("3. Exit");
        System.out.print("Choose an option: ");
    }

    public void displayProfileMenu() {
        System.out.println("Profile Menu:");
        System.out.println("1. Change Username");
        System.out.println("2. Change Nickname");
        System.out.println("3. Change Email");
        System.out.println("4. Change Password");
        System.out.println("5. Back to Main Menu");
        System.out.print("Choose an option: ");
    }

    public void displayGameMenu() {
        System.out.println("Game Menu:");
        System.out.println("1. Inventory");
        System.out.println("2. Quests");
        System.out.println("3. Map");
        System.out.println("4. Settings");
        System.out.println("5. Exit Game");
        System.out.print("Choose an option: ");
    }

    public void displayShopMenu() {
        System.out.println("Shop Menu:");
        System.out.println("1. Buy Items");
        System.out.println("2. Sell Items");
        System.out.println("3. Exit Shop");
        System.out.print("Choose an option: ");
    }

    public void displayCraftingMenu() {
        System.out.println("Crafting Menu:");
        System.out.println("1. Show Recipes");
        System.out.println("2. Craft Item");
        System.out.println("3. Back to Game Menu");
        System.out.print("Choose an option: ");
    }

    public void displayCookingMenu() {
        System.out.println("Cooking Menu:");
        System.out.println("1. Show Cooking Recipes");
        System.out.println("2. Cook Item");
        System.out.println("3. Back to Game Menu");
        System.out.print("Choose an option: ");
    }

    public void displayAnimalMenu() {
        System.out.println("Animal Menu:");
        System.out.println("1. Feed Animals");
        System.out.println("2. Pet Animals");
        System.out.println("3. Back to Game Menu");
        System.out.print("Choose an option: ");
    }

    public void displayBuildMenu() {
        System.out.println("Build Menu:");
        System.out.println("1. Build New Structure");
        System.out.println("2. Upgrade Structure");
        System.out.println("3. Back to Game Menu");
        System.out.print("Choose an option: ");
    }

    public void displayQuestLog() {
        System.out.println("Quest Log:");
        System.out.println("1. Show Active Quests");
        System.out.println("2. Show Completed Quests");
        System.out.println("3. Back to Game Menu");
        System.out.print("Choose an option: ");
    }

    public void displayTradeMenu() {
        System.out.println("Trade Menu:");
        System.out.println("1. Show Trade Offers");
        System.out.println("2. Make a Trade Offer");
        System.out.println("3. Back to Game Menu");
        System.out.print("Choose an option: ");
    }

    public void displayError(String s) {
        System.out.println("Error: " + s);
    }

    public void displayMessage(String s) {
        System.out.println("Message: " + s);
    }
}