package com.yourgame.model;

import javax.tools.Tool;
import java.util.*;

import com.yourgame.model.Building.FarmMap;
import com.yourgame.model.Inventory.Inventory;
import com.yourgame.model.Item.Item;
import com.yourgame.model.Map.Coordinate;
import com.yourgame.model.Skill.Skill;
import com.yourgame.model.Inventory.BackPack;
import com.yourgame.model.enums.BackPackType;
import com.yourgame.model.enums.AnimalType;
// import com.yourgame.model.Skill.SkillType;
import com.yourgame.model.enums.Gender;
public class Player {
    private String username;
    private String hashedPassword;
    private String nickname;
    private String email;
    private Gender gender;
    private int energy;
    private int maxEnergy= 1000; // made this uppp :))) :))) 
    private boolean unlimitedEnergy= false; 
    private int money;
    private final BackPack backPack = new BackPack(BackPackType.DEFAULT);
    private ArrayList<AnimalType> animals= new ArrayList<>(); 
    private Coordinate currentLocation;
    private Inventory inventory;
    private Tool equippedTool;
    private ArrayList<Skill> skills ; 
    // private Map<SkillType, Skill> skills;
    private Map<String, Relationship> relationships; // NPC name or Player username -> Relationship
    private FarmMap farmMapReference;
    private Set<Recipe> knownCraftingRecipes;
    private Set<Recipe> knownCookingRecipes;
    private List<QuestStatus> activeQuests;

    public Player(String username, String hashedPassword, String nickname, String email, Gender gender, int maxEnergy) {
        this.username = username;
        this.nickname = nickname;
        this.email = email;
        this.gender = gender;
        this.maxEnergy = maxEnergy;
        this.energy = maxEnergy; // Start with full energy
        this.money = 0; // Start with no money
        this.inventory = new Inventory();
        this.skills = new ArrayList<>();
        this.relationships = new HashMap<>();
        this.knownCraftingRecipes = new HashSet<>();
        this.knownCookingRecipes = new HashSet<>();
        this.activeQuests = new ArrayList<>();
    }

    // Business logic methods

    public Player(User currentUser) {
        this.username = currentUser.getUsername(); 
        this.nickname = currentUser.getNickname(); 
        this.energy = maxEnergy; // Start with full energy
        this.money = 0; // Start with no money
        this.inventory = new Inventory();
        this.skills = new ArrayList<>();
        this.relationships = new HashMap<>();
        this.knownCraftingRecipes = new HashSet<>();
        this.knownCookingRecipes = new HashSet<>();
        this.activeQuests = new ArrayList<>();
    }

    public void changeEnergy(int amount) {
        this.energy = Math.max(0, Math.min(maxEnergy, this.energy + amount));
    }

    public void addXp(Skill skill, int xp) {
        skill.addExperience(xp);
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
    public boolean isUnlimitedEnergy() {
        return unlimitedEnergy;
    }

    public void setUnlimitedEnergy(boolean unlimitedEnergy) {
        this.unlimitedEnergy = unlimitedEnergy;
    }

    public void updateQuest(QuestStatus questStatus) {
        // Update quest logic here
    }

    // public int getCurrentSkillLevel(SkillType skillType) {
    //     Skill skill = skills.get(skillType);
    //     return skill != null ? skill.getLevel() : 0;
    // }

    // Getters and setters for all attributes

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public int getMaxEnergy() {
        return maxEnergy;
    }

    public void setMaxEnergy(int maxEnergy) {
        this.maxEnergy = maxEnergy;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public Coordinate getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Coordinate currentLocation) {
        this.currentLocation = currentLocation;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public Tool getEquippedTool() {
        return equippedTool;
    }

    public void setEquippedTool(Tool equippedTool) {
        this.equippedTool = equippedTool;
    }

    public ArrayList<Skill> getSkills() {
        return skills;
    }

    public void setSkills(ArrayList<Skill> skills) {
        this.skills = skills;
    }

    public Map<String, Relationship> getRelationships() {
        return relationships;
    }

    public void setRelationships(Map<String, Relationship> relationships) {
        this.relationships = relationships;
    }

    public FarmMap getFarmMapReference() {
        return farmMapReference;
    }

    public void setFarmMapReference(FarmMap farmMapReference) {
        this.farmMapReference = farmMapReference;
    }

    public Set<Recipe> getKnownCraftingRecipes() {
        return knownCraftingRecipes;
    }

    public void setKnownCraftingRecipes(Set<Recipe> knownCraftingRecipes) {
        this.knownCraftingRecipes = knownCraftingRecipes;
    }

    public Set<Recipe> getKnownCookingRecipes() {
        return knownCookingRecipes;
    }

    public void setKnownCookingRecipes(Set<Recipe> knownCookingRecipes) {
        this.knownCookingRecipes = knownCookingRecipes;
    }

    public List<QuestStatus> getActiveQuests() {
        return activeQuests;
    }

    public void setActiveQuests(List<QuestStatus> activeQuests) {
        this.activeQuests = activeQuests;
    }

    public double getSpeed() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSpeed'");
    }

    public void setSpeed(double d) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setSpeed'");
    }

    public double getHealth() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getHealth'");
    }

    public void setHealth(double d) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setHealth'");
    }
}
