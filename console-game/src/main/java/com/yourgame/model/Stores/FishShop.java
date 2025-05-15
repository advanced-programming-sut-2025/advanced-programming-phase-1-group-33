package com.yourgame.model.Stores;

import java.awt.*;

public class FishShop extends Store {

    public FishShop(int x, int y, int width, int height) {
        super(new Rectangle(x,y,width,height),"willy",9,17);
    }

    @Override
    public char getSymbol() {
        return '≈';
    }

}
