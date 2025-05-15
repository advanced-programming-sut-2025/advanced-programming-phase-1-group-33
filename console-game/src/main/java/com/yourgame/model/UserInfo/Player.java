package com.yourgame.model.UserInfo;

import java.util.*;

import com.yourgame.model.Animals.AnimalType;
import com.yourgame.model.App;
import com.yourgame.model.Inventory.BackpackType;
import com.yourgame.model.Inventory.Tools.Axe;
import com.yourgame.model.Inventory.Tools.Hoe;
import com.yourgame.model.Inventory.Tools.Pickaxe;
import com.yourgame.model.Inventory.Tools.Tool;
import com.yourgame.model.Item.Wood;
import com.yourgame.model.Inventory.Backpack;
import com.yourgame.model.Map.Farm;
import com.yourgame.model.Map.Position;
import com.yourgame.model.Npc.NPCType;
import com.yourgame.model.Npc.RelationWithNPC;
import com.yourgame.model.Skill.Ability;
import com.yourgame.model.enums.Gender;

public class Player {
    private String username;
    private String hashedPassword;
    private String nickname;
    private String email;
    private Gender gender;
    private int energy;
    private Tool currentTool;
    private boolean isFaintedToday = false;
    private boolean isInfinite = false;
    private int maxEnergy = 1000;
    private boolean unlimitedEnergy = false;
    private final Backpack backpack = new Backpack(BackpackType.Primary);
    private ArrayList<AnimalType> animals = new ArrayList<>();
    private Backpack inventory;
    private final Ability ability = new Ability(this);


    private Farm farm;
    private Position currentPosition;
    private RelationWithNPC relationWithAbigail;
    private RelationWithNPC relationWithSebastian;
    private RelationWithNPC relationWithHarvey;
    private RelationWithNPC relationWithLeah;
    private RelationWithNPC relationWithRobin;

    public Player(User currentUser) {
        this.username = currentUser.getUsername();
        this.nickname = currentUser.getNickname();
        this.energy = maxEnergy;
        this.currentPosition = new Position(0 , 0);
        this.backpack.getTools().add(new Hoe());
        this.backpack.getTools().add(new Pickaxe());
        this.backpack.getTools().add(new Axe());
        this.backpack.getIngredientQuantity().put(new Coin(), 20);
        this.backpack.getIngredientQuantity().put(new Wood(), 100);
        this.relationWithAbigail = new RelationWithNPC(NPCType.Abigail);
        this.relationWithSebastian = new RelationWithNPC(NPCType.Sebastian);
        this.relationWithHarvey = new RelationWithNPC(NPCType.Harvey);
        this.relationWithLeah = new RelationWithNPC(NPCType.Leah);
        this.relationWithRobin = new RelationWithNPC(NPCType.Robin);

    }

    public void addEnergy(int energy) {
        if (!isInfinite) {
            this.energy += energy;
            this.energy = Math.min(this.energy, maxEnergy);
        }
    }

    public void setCurrentTool(Tool currentTool) {
        this.currentTool = currentTool;
    }

    public Tool getCurrentTool() {
        return currentTool;
    }

    public Backpack getBackpack() {
        return backpack;
    }


    public Ability getAbility() {
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
        if (isInfinite) {
            return;
        }
        this.energy -= energy;
        if (this.energy < 0) {
            this.energy = 0;
            faint();
        }
    }

    public boolean isUnlimitedEnergy() {
        return unlimitedEnergy;
    }

    public void setUnlimitedEnergy(boolean unlimitedEnergy) {
        this.unlimitedEnergy = unlimitedEnergy;
    }

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


    public Backpack getInventory() {
        return inventory;
    }

    public void setInventory(Backpack inventory) {
        this.inventory = inventory;

    }

    public Farm getFarm(){
        return farm;
    }

    public void setFarm(Farm map){
        this.farm = map;
    }
    public Position getPosition(){
        return currentPosition;
    }


    public RelationWithNPC getRelationWithAbigail() {
        return relationWithAbigail;
    }

    public void setRelationWithAbigail(RelationWithNPC relationWithAbigail) {
        this.relationWithAbigail = relationWithAbigail;
    }

    public RelationWithNPC getRelationWithSebastian() {
        return relationWithSebastian;
    }

    public void setRelationWithSebastian(RelationWithNPC relationWithSebastian) {
        this.relationWithSebastian = relationWithSebastian;
    }

    public RelationWithNPC getRelationWithHarvey() {
        return relationWithHarvey;
    }

    public void setRelationWithHarvey(RelationWithNPC relationWithHarvey) {
        this.relationWithHarvey = relationWithHarvey;
    }

    public RelationWithNPC getRelationWithLeah() {
        return relationWithLeah;
    }

    public void setRelationWithLeah(RelationWithNPC relationWithLeah) {
        this.relationWithLeah = relationWithLeah;
    }

    public RelationWithNPC getRelationWithRobin() {
        return relationWithRobin;
    }

    public void setRelationWithRobin(RelationWithNPC relationWithRobin) {
        this.relationWithRobin = relationWithRobin;
    }
}
