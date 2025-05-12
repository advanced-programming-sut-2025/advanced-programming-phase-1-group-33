package com.yourgame.model.Map;

public class Portal {
    private String destinationMapId;
    private int destRow;
    private int destCol;
    
    public Portal(String destinationMapId, int destRow, int destCol) {
        this.destinationMapId = destinationMapId;
        this.destRow = destRow;
        this.destCol = destCol;
    }
    
    public String getDestinationMapId() {
        return destinationMapId;
    }
    
    public int getDestRow() {
        return destRow;
    }
    
    public int getDestCol() {
        return destCol;
    }
}