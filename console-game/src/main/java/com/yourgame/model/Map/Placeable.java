package com.yourgame.model.Map;

import java.awt.*;

import com.yourgame.model.enums.SymbolType;

public interface Placeable {
    Rectangle getBounds();
    SymbolType getSymbol();
}
