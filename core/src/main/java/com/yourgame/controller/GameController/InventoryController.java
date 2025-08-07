package com.yourgame.controller.GameController;
import com.badlogic.gdx.graphics.Texture;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.model.Inventory.Tools.*;
import com.yourgame.model.UserInfo.Player;
import com.yourgame.view.GameViews.InventoryView;

import java.util.ArrayList;
import java.util.concurrent.TransferQueue;

public class InventoryController {
    private InventoryView view;
    private Player player;

    public void setView(InventoryView view) {
        this.view = view;
        player = view.getPlayer();
    }

    public Texture[] handleMiniBarItems() {
        ArrayList<Tool> tools = player.getInventory().getTools();
        ArrayList<Texture> toolTextures = new ArrayList<>();
        for(Tool tool : tools) {
            toolTextures.add(GameAssetManager.getInstance().getTexture(findPath(tool)));
        }
    }

    private String findPath(Tool tool) {
        StringBuilder path = new StringBuilder();
        switch (tool.getToolType()){
            case Metal -> path.append("Steel_");
            case Iridium -> path.append("Iridium_");
            case Coppery -> path.append("Copper_");
            case Golden -> path.append("Golden_");
        }

        switch (tool) { //TrashCan :))))
            case Hoe -> {path.append("Hoe");}
            case Axe -> {path.append("Axe");}
            case Pickaxe -> {path.append("Pickaxe");}
            case Scythe -> path.append("Scythe");
            case WateringCan -> {path.append("WateringCan");}
            case MilkPail -> path.append("MilkPail");
            case Shear -> path.append("Shears");
        }
    }
}
