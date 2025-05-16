package com.yourgame.model.Map;

import com.yourgame.model.Stores.*;

import java.awt.*;
import java.util.ArrayList;

public class NpcVillage {
    private final Rectangle rectangle;
    private final Blacksmith blacksmith;
    private final CarpenterShop carpenterShop;
    private final FishShop fishShop;
    private final JojaMart jojaMart;
    private final MarnieRanch marnieRanch;
    private final PierreGeneralStore pierreGeneralStore;
    private final StardropSaloon saloon;
    private final ArrayList<Placeable> placeables = new ArrayList<>();
    private final ArrayList<Door> doors = new ArrayList<>();

    public NpcVillage(Rectangle rectangle, Blacksmith blacksmith, CarpenterShop carpenterShop, FishShop fishShop,
            JojaMart jojaMart, MarnieRanch marnieRanch, PierreGeneralStore pierreGeneralStore, StardropSaloon saloon) {
        this.rectangle = rectangle;

        this.blacksmith = blacksmith;
        placeables.add((Placeable) this.blacksmith);
        this.carpenterShop = carpenterShop;
        placeables.add((Placeable) this.carpenterShop);
        this.fishShop = fishShop;
        placeables.add((Placeable) this.fishShop);
        this.jojaMart = jojaMart;
        placeables.add((Placeable) this.jojaMart);
        this.marnieRanch = marnieRanch;
        placeables.add((Placeable) this.marnieRanch);
        this.pierreGeneralStore = pierreGeneralStore;
        placeables.add((Placeable) this.pierreGeneralStore);
        this.saloon = saloon;
        placeables.add((Placeable) this.saloon);

    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public StardropSaloon getSaloon() {
        return saloon;
    }

    public PierreGeneralStore getPierreGeneralStore() {
        return pierreGeneralStore;
    }

    public MarnieRanch getMarnieRanch() {
        return marnieRanch;
    }

    public JojaMart getJojaMart() {
        return jojaMart;
    }

    public FishShop getFishShop() {
        return fishShop;
    }

    public StardropSaloon getStardopSaloon() {
        return saloon;
    }

    public CarpenterShop getCarpenterShop() {
        return carpenterShop;
    }

    public Blacksmith getBlacksmith() {
        return blacksmith;
    }

    public ArrayList<Placeable> getPlaceables() {
        return placeables;
    }

    public void addDoors(Door door) {
        doors.add(door);
        placeables.add(door);
    }

    public ArrayList<Door> getDoors() {
        return doors;
    }
}
