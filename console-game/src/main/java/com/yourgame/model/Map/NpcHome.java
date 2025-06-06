package com.yourgame.model.Map;


import com.yourgame.model.Npc.NPC;
import com.yourgame.model.enums.SymbolType;

import java.awt.*;

public class NpcHome implements Placeable {
    private final Rectangle rectangle;
    private final NPC npc;
    public NpcHome(int x, int y, int width, int height , NPC npc) {
        rectangle = new Rectangle(x, y, width, height);
        this.npc = npc;
    }

    public Rectangle getBounds() {
        return rectangle;
    }

    public NPC getNpc() {
        return npc;
    }

    public SymbolType getSymbol() {
        return npc.getSymbol();
    }
}
