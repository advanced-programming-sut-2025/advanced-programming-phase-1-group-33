package com.yourgame.model.Map;
import com.yourgame.model.Item.Fertilizer;
import com.yourgame.model.Placeable;
import com.yourgame.model.enums.TileType;

public class Tile {
    private Position position;
    private boolean gotThunder;
    private char symbol = '#';
    private boolean walkable ;
    private Placeable placeable;
    private boolean isPlowed = false;
    private Fertilizer fertilizer = null;
    public Tile(Position position) {
        this.position = position;
        this.gotThunder = false;
        this.walkable = true;

    }
    public void setPosition(Position position) {
        this.position = position;
    }


    public Position getPosition() {
        return position;

    }
    public char getSymbol() {
        return symbol;
    }
    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }
    public boolean isGotThunder() {
        return gotThunder;
    }
    public void setGotThunder(boolean gotThunder) {
        this.gotThunder = gotThunder;
    }

    public boolean isWalkable() {
        return walkable;
    }

    public void setWalkable(boolean walkable) {
        this.walkable = walkable;
    }
    public Placeable getPlaceable() {
        return placeable;
    }
    public void setPlaceable(Placeable placeable) {
        this.placeable = placeable;
    }

    public boolean isPlowed() {
        return isPlowed;
    }

    public void setPlowed(boolean plowed) {
        isPlowed = plowed;
    }

    public Fertilizer getFertilizer() {
        return fertilizer;
    }

    public void setFertilizer(Fertilizer fertilizer) {
        this.fertilizer = fertilizer;
    }
}
