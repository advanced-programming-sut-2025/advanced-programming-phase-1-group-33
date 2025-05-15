package com.yourgame.model.Stores;

import java.awt.*;

import com.yourgame.model.enums.SymbolType;

public class PierreGeneralStore extends Store {


    public PierreGeneralStore(int x, int y, int width, int height) {
        super(new Rectangle(x,y,width,height),"Pierre",9,23);
    }

    @Override
    public SymbolType getSymbol() {
        return SymbolType.PierreGeneralStore;
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
