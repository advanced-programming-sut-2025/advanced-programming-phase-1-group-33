package com.yourgame.model.Stores;

import java.awt.*;

import com.yourgame.model.enums.SymbolType;

public class JojaMart extends Store {


    public JojaMart(int x, int y, int width, int height) {
        super(new Rectangle(x,y,width,height),"Morris",9,23);
    }

    @Override
    public SymbolType getSymbol() {
        return SymbolType.JojaMart;
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
