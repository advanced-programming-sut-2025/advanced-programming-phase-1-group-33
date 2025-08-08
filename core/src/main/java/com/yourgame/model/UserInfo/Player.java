package com.yourgame.model.UserInfo;

import java.util.*;

import com.yourgame.model.Animals.AnimalType;
import com.yourgame.model.IO.Response;
import com.yourgame.model.App;
import com.yourgame.model.Item.Inventory.BackpackType;
import com.yourgame.model.Item.Inventory.TrashCan;
import com.yourgame.model.Item.Tools.Axe;
import com.yourgame.model.Item.Tools.Hoe;
import com.yourgame.model.Item.Tools.Pickaxe;
import com.yourgame.model.Item.Tools.Scythe;
import com.yourgame.model.Item.Tools.Tool;
import com.yourgame.model.Item.Tools.WateringCan;
import com.yourgame.model.Item.Wood;
import com.yourgame.model.Item.Inventory.Backpack;
import com.yourgame.model.Map.Farm;
import com.yourgame.model.Map.Position;
import com.yourgame.model.Npc.NPCType;
import com.yourgame.model.Npc.RelationWithNPC;
import com.yourgame.model.Skill.Ability;
import com.yourgame.model.enums.Avatar;
import com.yourgame.model.enums.Gender;
import com.yourgame.model.enums.SecurityQuestion;
import com.yourgame.model.notification.Notification;

public class Player {
    private String username;
    private String hashedPassword;
    private String nickname;

    private String email;
    private Gender gender;
    private int energy;
    private Tool currentTool;
    private boolean isFaintedToday = false;
    private boolean isMarried = false;

    private boolean isInfinite = false;
    private int consumedEnergyInThisTurn = 0;

    private int maxEnergy = 200;
    private boolean unlimitedEnergy = false;
    private final Backpack backpack = new Backpack(BackpackType.Primary);
    private final TrashCan trashCan = new TrashCan();

    private final ArrayList<Notification> notifications = new ArrayList<>();

    private ArrayList<AnimalType> animals = new ArrayList<>();
    private Backpack inventory;
    private final Ability ability = new Ability(this);

    private int remainingDaysAfterMarigDenied = 0;

    private Farm farm;
    private Position currentPosition;
    private RelationWithNPC relationWithAbigail;
    private RelationWithNPC relationWithSebastian;
    private RelationWithNPC relationWithHarvey;
    private RelationWithNPC relationWithLeah;
    private RelationWithNPC relationWithRobin;

    public static Player guest() {
        return new Player(
            new User("Aeen", "Aeen", "Aeen", "aeen", Gender.Male, SecurityQuestion.BirthDate, "Iran", Avatar.Sam)

        );
    }

    public Player(User currentUser) {
        this.username = currentUser.getUsername();
        this.nickname = currentUser.getNickname();
        this.energy = maxEnergy;
        this.currentPosition = new Position(0, 0);
        this.backpack.getTools().add(new Hoe());
        this.backpack.getTools().add(new Pickaxe());
        this.backpack.getTools().add(new Axe());
        this.backpack.getTools().add(new WateringCan());
        this.backpack.getTools().add(new Scythe());
        this.backpack.getIngredientQuantity().put(new Coin(), 20);
        this.backpack.getIngredientQuantity().put(new Wood(), 100);
        this.relationWithAbigail = new RelationWithNPC(NPCType.Abigail);
        this.relationWithSebastian = new RelationWithNPC(NPCType.Sebastian);
        this.relationWithHarvey = new RelationWithNPC(NPCType.Harvey);
        this.relationWithLeah = new RelationWithNPC(NPCType.Leah);
        this.relationWithRobin = new RelationWithNPC(NPCType.Robin);
        for (Tool tool : this.backpack.getTools()) {
            if (tool instanceof Axe) {
                setCurrentTool(tool);
                break;
            }
        }
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

    public Response consumeEnergy(int energy) {
        if (isInfinite) {
            return new Response(true, "infinite energy for this turn\n ");
        }
        this.energy -= energy;
        this.consumedEnergyInThisTurn += energy;

        if (consumedEnergyInThisTurn >= 50 && this.energy > 0) {
            App.getGameState().nextPlayerTurn();
            return new Response(false,
                    "You consumed " + this.consumedEnergyInThisTurn
                            + " energy in your turn! The turn will be changed!\n");
        }
        if (this.energy < 0) {
            this.energy = 0;
            faint();
            return new Response(false, "You Fainted... Dummy\n");
        }
        return new Response(true, "You consumed " + this.consumedEnergyInThisTurn + " in this turn until now! ");
    }

    public boolean isUnlimitedEnergy() {
        return unlimitedEnergy;
    }

    public void setFaintedToday(boolean isFaintedToday) {
        this.isFaintedToday = isFaintedToday;
    }

    public int getConsumedEnergyInThisTurn() {
        return consumedEnergyInThisTurn;
    }

    public void setConsumedEnergyInThisTurn(int consumedEnergyInThisTurn) {
        this.consumedEnergyInThisTurn = consumedEnergyInThisTurn;
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

    public Farm getFarm() {
        return farm;
    }

    public void setFarm(Farm map) {
        this.farm = map;
        this.currentPosition = farm.getPlayerDefaultPosition();
    }

    public Position getPosition() {
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

    public int getRemainingDaysAfterMarigDenied() {
        return remainingDaysAfterMarigDenied;
    }

    public void setRemainingDaysAfterMarigDenied(int remainingDaysAfterMarigDenied) {
        this.remainingDaysAfterMarigDenied = remainingDaysAfterMarigDenied;
    }

    public String getNickname() {
        return nickname;
    }

    public void addNotification(Notification notification) {
        this.notifications.add(notification);
    }

    public ArrayList<Notification> getNotifications() {
        return notifications;
    }

    public boolean isMarried() {
        return isMarried;
    }

    public void setMarried(boolean married) {
        isMarried = married;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

}
