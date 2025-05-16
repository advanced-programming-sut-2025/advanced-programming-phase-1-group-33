package com.yourgame.model.Map;
import com.yourgame.model.App;
import com.yourgame.model.Item.Fertilizer;
import com.yourgame.model.enums.SymbolType;
import com.yourgame.model.enums.TileType;

public class Tile {
    private Position position;
    private boolean gotThor;
    private SymbolType symbol = SymbolType.WALL;
    private boolean walkable ;
    private Placeable placeable;
    private boolean isPlowed = false;
    private Fertilizer fertilizer = null;
    public Tile(Position position) {
        this.position = position; 
        this.gotThor = false;
        this.walkable = true;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
    
    
    public Position getPosition() {
        return position;
    }
    public SymbolType getSymbol() {
        return symbol;
    }
    public SymbolType getSymbolToPrint() {
        if(gotThor){
            return SymbolType.Thor; 
        }
        if(getPlaceable()!= null){
            return getPlaceable().getSymbol(); 
        }
        return symbol;
    }
    public void setSymbol(SymbolType symbol) {
        this.symbol = symbol;
    }
    public boolean isGotThor() {
        return gotThor;
    }
    public void setGotThor(boolean gotThunder) {
        this.gotThor = gotThunder;
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
