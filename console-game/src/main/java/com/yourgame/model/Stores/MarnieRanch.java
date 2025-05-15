package com.yourgame.model.Stores;

import java.awt.*;
import java.util.ArrayList;

import com.yourgame.model.enums.SymbolType;

public class MarnieRanch extends Store {

    private ArrayList<ShopItem> inventory;

    public MarnieRanch(int x, int y, int width, int height) {
        super(new Rectangle(x, y, width, height), "Marnie", 9, 16);
    }


    @Override
    public SymbolType getSymbol() {
        return SymbolType.MarnieRanch;
    }

    @Override
    public void loadInventory() {
        
    }

//    @Override
//    public void removeGood() {
//
//    }
//
//    @Override
//    public void addGood() {
//
//    }
//
//    @Override
//    public void sellProduct() {
//
//    }
//
//    @Override
//    public String showAllProducts() {
//        return "";
//    }
//
//    @Override
//    public String showAllAvailableProducts() {
//        return "";
//    }
//
//    @Override
//    public void purchase() {
//
//    }
}
