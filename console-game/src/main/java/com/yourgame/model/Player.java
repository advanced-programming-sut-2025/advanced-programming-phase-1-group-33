package com.yourgame.model;

import javax.tools.Tool;
import java.util.*;
import java.util.Map;

public class Player {
    private String username;
    private String hashedPassword;
    private String nickname;
    private String email;
    private Gender gender;
    private int energy;
    private int maxEnergy;
    private int money;
    private Coordinate currentLocation;
    private Inventory inventory;
    private Tool equippedTool;
    private Map<SkillType, Skill> skills;
    private Map<String, Relationship> relationships; // NPC name or Player username -> Relationship
    private FarmMap farmMapReference;
    private Set<Recipe> knownCraftingRecipes;
    private Set<Recipe> knownCookingRecipes;
    private List<QuestStatus> activeQuests;

    public Player(String username, String hashedPassword, String nickname, String email, Gender gender, int maxEnergy) {
        this.username = username;
        this.hashedPassword = hashedPassword;
        this.nickname = nickname;
        this.email = email;
        this.gender = gender;
        this.maxEnergy = maxEnergy;
        this.energy = maxEnergy; // Start with full energy
        this.money = 0; // Start with no money
        this.inventory = new Inventory();
        this.skills = new HashMap<>();
        this.relationships = new HashMap<>();
        this.knownCraftingRecipes = new HashSet<>();
        this.knownCookingRecipes = new HashSet<>();
        this.activeQuests = new ArrayList<>();
    }

    // Getters and Setters for attributes

    public void changeEnergy(int amount) {
        this.energy = Math.max(0, Math.min(maxEnergy, this.energy + amount));
    }

    public void addXp(SkillType skillType, int xp) {
        Skill skill = skills.get(skillType);
        if (skill != null) {
            skill.addXp(xp);
        }
    }

    public boolean canAfford(int cost) {
        return this.money >= cost;
    }

    public void addMoney(int amount) {
        this.money += amount;
    }

    public void deductMoney(int amount) {
        if (canAfford(amount)) {
            this.money -= amount;
        }
    }

    public void addItem(Item item) {
        inventory.addItem(item);
    }

    public void removeItem(Item item) {
        inventory.removeItem(item);
    }

    public boolean hasItem(Item item) {
        return inventory.hasItem(item);
    }

    public void learnRecipe(Recipe recipe) {
        if (recipe.isCookingRecipe()) {
            knownCookingRecipes.add(recipe);
        } else {
            knownCraftingRecipes.add(recipe);
        }
    }

    public void updateQuest(QuestStatus questStatus) {
        // Update quest logic here
    }

    public int getCurrentSkillLevel(SkillType skillType) {
        Skill skill = skills.get(skillType);
        return skill != null ? skill.getLevel() : 0;
    }
}