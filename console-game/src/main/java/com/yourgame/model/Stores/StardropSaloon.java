package com.yourgame.model.Stores;

import java.awt.*;

public class StardropSaloon extends Store {


    public StardropSaloon(int x, int y, int width, int height) {
        super(new Rectangle(x,y,width,height),"Gus",12,24);
    }

    @Override
    public char getSymbol() {
        return '☕';
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
