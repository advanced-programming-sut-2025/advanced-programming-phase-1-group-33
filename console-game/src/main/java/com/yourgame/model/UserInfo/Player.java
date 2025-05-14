package com.yourgame.model.UserInfo;

import java.util.*;

import com.yourgame.model.Animals.AnimalType;
import com.yourgame.model.App;
import com.yourgame.model.Building.FarmMap;
import com.yourgame.model.Inventory.BackpackType;
import com.yourgame.model.Inventory.Tools.Axe;
import com.yourgame.model.Inventory.Tools.Hoe;
import com.yourgame.model.Inventory.Tools.Pickaxe;
import com.yourgame.model.Inventory.Tools.Tool;
import com.yourgame.model.Item.Wood;
import com.yourgame.model.Map.Coordinate;
import com.yourgame.model.Inventory.Backpack;
import com.yourgame.model.Skill.Ability;
import com.yourgame.model.enums.Gender;

public class Player {
    private String username;
    private String hashedPassword;
    private String nickname;
    private String email;
    private Gender gender;
    private int energy;
    private boolean isFaintedToday = false;
    private boolean isInfinite = false;
    private int maxEnergy= 1000; // made this uppp :))) :))) 
    private boolean unlimitedEnergy= false; 
    private int money;
    private  final Backpack backpack = new Backpack(BackpackType.Primary);
    private ArrayList<AnimalType> animals= new ArrayList<>();
    private Coordinate currentLocation;
    private Backpack inventory;
    private Tool equippedTool;
    private final Ability ability = new Ability(this);
    // private Map<SkillType, Skill> skills;
    private Map<String, Relationship> relationships; // NPC name or Player username -> Relationship
    private FarmMap farmMapReference;
    private String currentMapId;
    private Coordinate currentCoordinate;

    public Player(String username, String hashedPassword, String nickname, String email, Gender gender, int maxEnergy) {
        this.username = username;
        this.nickname = nickname;
        this.email = email;
        this.gender = gender;
        this.maxEnergy = maxEnergy;
        this.energy = maxEnergy; // Start with full energy
        this.money = 0; // Start with no money
        this.backpack.getTools().add(new Hoe());
        this.backpack.getTools().add(new Pickaxe());
        this.backpack.getTools().add(new Axe());
        this.backpack.getIngredientQuantity().put(new Coin() , 20);
        this.backpack.getIngredientQuantity().put(new Wood() , 100);
        this.relationships = new HashMap<>();
    }

    // Business logic methods

    public Player(User currentUser) {
        this.username = currentUser.getUsername(); 
        this.nickname = currentUser.getNickname(); 
        this.energy = maxEnergy; // Start with full energy
        this.money = 0; // Start with no money
        this.backpack.getTools().add(new Hoe());
        this.backpack.getTools().add(new Pickaxe());
        this.backpack.getTools().add(new Axe());
        this.backpack.getIngredientQuantity().put(new Coin() , 20);
        this.backpack.getIngredientQuantity().put(new Wood() , 100);

        this.relationships = new HashMap<>();
    }
    public Backpack getBackpack() {
        return backpack;
    }

    public void changeEnergy(int amount) {
        this.energy = Math.max(0, Math.min(maxEnergy, this.energy + amount));
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

    public Ability getAbility(){
        return ability;
    }
    public void faint() {
        isFaintedToday = true;
        App.getGameState().nextPlayerTurn();

    }

    public boolean isFaintedToday() {
        return isFaintedToday;
    }

    public void consumeEnergy(int energy) {
        if(isInfinite){
            return;
        }
        this.energy -= energy;
        if(this.energy < 0){
            this.energy = 0;
            faint();
        }
    }

//    public void addItem(Item item) {
//        inventory.addItem(item);
//    }
//
//    public void removeItem(Item item) {
//        inventory.removeItem(item);
//    }
//
//    public boolean hasItem(Item item) {
//        return inventory.hasItem(item);
//    }

    public boolean isUnlimitedEnergy() {
        return unlimitedEnergy;
    }

    public void setUnlimitedEnergy(boolean unlimitedEnergy) {
        this.unlimitedEnergy = unlimitedEnergy;
    }

//    public void updateQuest(QuestStatus questStatus) {
//        // Update quest logic here
//    }

    // public int getCurrentSkillLevel(SkillType skillType) {
    //     Skill skill = skills.get(skillType);
    //     return skill != null ? skill.getLevel() : 0;
    // }

    // Getters and setters for all attributes

    public String getUsername() {
        return username;
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


    public Coordinate getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Coordinate currentLocation) {
        this.currentLocation = currentLocation;
    }

    public Backpack getInventory() {
        return inventory;
    }

    public void setInventory(Backpack inventory) {
        this.inventory = inventory;
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


    public String getCurrentMapId() {
        return currentMapId;
    }

    public void setCurrentMapId(String currentMapId) {
        this.currentMapId = currentMapId;
    }

    public Coordinate getCurrentCoordinate() {
        return currentCoordinate;
    }

    public void setCurrentCoordinate(Coordinate currentCoordinate) {
        this.currentCoordinate = currentCoordinate;
    }

    public void initializeFarm(FarmMap farmMap) {
        setFarmMapReference(farmMap);
        setCurrentMapId(farmMap.getMapId());
        setCurrentCoordinate(new Coordinate(0, 0)); 
    }
}
