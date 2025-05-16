package com.yourgame.model.Map;

import com.yourgame.model.Animals.Habitat;
import com.yourgame.model.Item.Crop;
import com.yourgame.model.Item.Tree;
import com.yourgame.model.Map.Water.Lake;
import com.yourgame.model.enums.SymbolType;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Farm {

    private final int type;
    private final Cottage cottage;
    private final GreenHouse greenHouse;
    private final ArrayList<Lake> lakes;
    private final ArrayList<Quarry> quarries;
    private ArrayList<Tree> trees;
    private final Rectangle rectangle;
    private final ArrayList<Stone> stones;
    private ArrayList<Placeable> placeables = new ArrayList<>();
    private ArrayList<Crop> crops = new ArrayList<>();
    private final ArrayList<Habitat> habitats = new ArrayList<>();
    private Door door;

    public Farm(Cottage cottage, GreenHouse greenHouse, ArrayList<Lake> lakes, ArrayList<Quarry> quarries,
            ArrayList<Tree> trees, ArrayList<Stone> stones, ArrayList<Crop> crops, Rectangle rectangle, int type) {

        this.cottage = cottage;
        placeables.add(cottage);
        this.greenHouse = greenHouse;
        placeables.add(greenHouse);
        this.lakes = lakes;
        placeables.addAll(lakes);
        this.quarries = quarries;
        placeables.addAll(quarries);
        this.trees = trees;
        placeables.addAll(trees);
        this.stones = stones;
        placeables.addAll(stones);
        this.type = type;
        this.rectangle = rectangle;
        this.crops = crops;

        placeables.addAll(crops);

    }

    public Cottage getCottage() {
        return cottage;
    }

    public ArrayList<Stone> getStones() {
        return stones;
    }

    public ArrayList<Tree> getTrees() {
        return trees;
    }

    public ArrayList<Quarry> getQuarries() {
        return quarries;
    }

    public ArrayList<Lake> getLakes() {
        return lakes;
    }

    public ArrayList<Crop> getCrops() {
        return crops;
    }

    public GreenHouse getGreenHouse() {
        return greenHouse;
    }

    public void setTilesSymbol(Tile[][] tiles) {
        for (int i = this.rectangle.x; i < this.rectangle.x + this.rectangle.width; i++) {
            for (int j = this.rectangle.y; j < this.rectangle.y + this.rectangle.height; j++) {
                tiles[i][j].setSymbol(SymbolType.DefaultFloor);
            }
        }
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public ArrayList<Placeable> getPlaceables() {
        return placeables;
    }

    public void addRandomTree() {
        Random rand = new Random();

    }

    public void setTrees(ArrayList<Tree> trees) {
        this.trees = trees;
    }

    public int getType() {
        return type;
    }

    public void setDoor(Door door) {
        this.door = door;
        placeables.add(door);
    }

    public Door getDoor() {
        return door;
    }

    public void addHabitat(Habitat habitat) {
        habitats.add(habitat);
    }

    public ArrayList<Habitat> getHabitats() {
        return habitats;
    }

    public Position getPlayerDefaultPosition() {
        int defaultX = rectangle.x + rectangle.width / 2;
        int defaultY = rectangle.y + rectangle.height / 2;
        return new Position(defaultX, defaultY);
    }
}
