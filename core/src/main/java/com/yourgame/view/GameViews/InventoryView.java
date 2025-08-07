package com.yourgame.view.GameViews;

import com.yourgame.controller.GameController.InventoryController;
import com.yourgame.model.UserInfo.Player;
import com.yourgame.view.AppViews.GameBaseScreen;

public class InventoryView extends GameBaseScreen {
    private final InventoryController inventoryController;
    private final Player player;

    public InventoryView(Player player) {
        this.inventoryController = new InventoryController();
        inventoryController.setView(InventoryView.this);

        this.player = player;
    }

    @Override
    public void show(){

    }

    @Override
    public void render(float delta) {
        super.render(delta);

    }

    public Player getPlayer() {
        return player;
    }
}
