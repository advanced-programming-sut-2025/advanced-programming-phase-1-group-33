package com.yourgame.model.UserInfo;

import java.util.*;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.yourgame.model.Animals.AnimalType;
import com.yourgame.model.App;
import com.yourgame.model.Farming.Fertilizer;
import com.yourgame.model.Farming.Seeds;
import com.yourgame.model.Item.Inventory.BackpackType;
import com.yourgame.model.Item.Inventory.TrashCan;
import com.yourgame.model.Item.Tools.Axe;
import com.yourgame.model.Item.Tools.Hoe;
import com.yourgame.model.Item.Tools.Pickaxe;
import com.yourgame.model.Item.Tools.Scythe;
import com.yourgame.model.Item.Tools.Tool;
import com.yourgame.model.Item.Tools.WateringCan;
import com.yourgame.model.Farming.Wood;
import com.yourgame.model.Item.Inventory.Backpack;
import com.yourgame.model.Map.Position;
import com.yourgame.model.Npc.NPCType;
import com.yourgame.model.Npc.RelationWithNPC;
import com.yourgame.model.Skill.Ability;
import com.yourgame.model.enums.Avatar;
import com.yourgame.model.enums.Gender;
import com.yourgame.model.enums.SecurityQuestion;
import com.yourgame.model.notification.Notification;

public class Player {
    public static final int PLAYER_WIDTH = 16;
    public static final int PLAYER_HEIGHT = 32;
    public static final float SPEED = 150f;
    public static final int MAX_ENERGY = 300;

    private final User user;
    private int energy;
    private boolean isFaintedToday = false;
    private boolean isInfinite = false;
    private int consumedEnergyInThisTurn = 0;
    private boolean unlimitedEnergy = false;

    private final Backpack backpack = new Backpack(BackpackType.Primary);
    private final ArrayList<Notification> notifications = new ArrayList<>();
    private final Ability ability = new Ability(this);

    private int remainingDaysAfterMarriageDenied = 0;
    private boolean isMarried = false;
    private RelationWithNPC relationWithAbigail;
    private RelationWithNPC relationWithSebastian;
    private RelationWithNPC relationWithHarvey;
    private RelationWithNPC relationWithLeah;
    private RelationWithNPC relationWithRobin;

    // Graphic fields
    private final Texture playerSheet;
    public final Animation<TextureRegion>[] walkAnimations;
    public Vector2 playerPosition;
    public Vector2 playerVelocity;
    public int direction; // 0=Down, 1=Right, 2=Up, 3=Left

    public static Player guest() {
        return new Player(
                new User("Aeen", "Aeen", "Aeen", "aeen", Gender.Male, SecurityQuestion.BirthDate, "Iran", Avatar.Sam));
    }

    public Player(User currentUser) {
        this.user = currentUser;
        this.energy = MAX_ENERGY;
        this.backpack.addTool(new Hoe());
        this.backpack.addTool(new Pickaxe());
        this.backpack.addTool(new Axe());
        this.backpack.addTool(new WateringCan());
        this.backpack.addTool(new Scythe());
        this.backpack.addItem(new Seeds.SeedItem(Seeds.Grape_Starter), 5);
        this.backpack.addItem(new Fertilizer.FertilizerItem(Fertilizer.Growth_Fertilizer), 2);
        this.backpack.addItem(new Fertilizer.FertilizerItem(Fertilizer.Water_Fertilizer), 2);
        this.backpack.getIngredientQuantity().put(new Coin(), 20);
        this.backpack.getIngredientQuantity().put(new Wood(), 100);
        this.relationWithAbigail = new RelationWithNPC(NPCType.Abigail);
        this.relationWithSebastian = new RelationWithNPC(NPCType.Sebastian);
        this.relationWithHarvey = new RelationWithNPC(NPCType.Harvey);
        this.relationWithLeah = new RelationWithNPC(NPCType.Leah);
        this.relationWithRobin = new RelationWithNPC(NPCType.Robin);

        // Load player sprite sheet
        playerSheet = new Texture("Game/Player/player.png");
        TextureRegion[][] frames = TextureRegion.split(playerSheet, PLAYER_WIDTH, PLAYER_HEIGHT);

        walkAnimations = new Animation[4]; // down, left, right, up

        walkAnimations[0] = new Animation<>(0.2f, frames[0]); // Down
        walkAnimations[1] = new Animation<>(0.2f, frames[1]); // Right
        walkAnimations[2] = new Animation<>(0.2f, frames[2]); // Up
        walkAnimations[3] = new Animation<>(0.2f, frames[3]); // Left

        playerPosition = new Vector2();
        playerVelocity = new Vector2();
        direction = 0;
    }

    public User getUser() {
        return user;
    }

    public void addEnergy(int energy) {
        if (!isInfinite) {
            this.energy += energy;
            this.energy = Math.min(this.energy, MAX_ENERGY);
        }
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

    public int getEnergyPhase() {
        if (energy <= 0)
            return 0;
        float energyPercentage = (float) energy / MAX_ENERGY;
        if (energyPercentage > 0.75f)
            return 4;
        if (energyPercentage > 0.50f)
            return 3;
        if (energyPercentage > 0.25f)
            return 2;
        return 1;
    }

    public boolean isFaintedToday() {
        return isFaintedToday;
    }

    public void consumeEnergy(int energy) {
        if (isInfinite) {
            return ;
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

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
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

    public int getRemainingDaysAfterMarriageDenied() {
        return remainingDaysAfterMarriageDenied;
    }

    public void setRemainingDaysAfterMarriageDenied(int remainingDaysAfterMarriageDenied) {
        this.remainingDaysAfterMarriageDenied = remainingDaysAfterMarriageDenied;
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
}
