package com.yourgame.model.Stores;

import java.awt.*;

import com.yourgame.model.enums.SymbolType;

public class CarpenterShop extends Store {

    public CarpenterShop(int x , int y, int width, int height) {
        super(new Rectangle(x,y,width,height),"Robin",9,20);
    }

    @Override
    public SymbolType getSymbol() {
        return SymbolType.CarpenterShop;
    }


}
