package com.yourgame.model.Stores;

import java.awt.*;

public class CarpenterShop extends Store {

    public CarpenterShop(int x , int y, int width, int height) {
        super(new Rectangle(x,y,width,height),"Robin",9,20);
    }

    @Override
    public char getSymbol() {
        return 'w';
    }


}
