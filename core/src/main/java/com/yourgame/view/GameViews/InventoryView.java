package com.yourgame.view.GameViews;

import com.yourgame.controller.GameController.InventoryController;
import com.yourgame.view.AppViews.GameBaseScreen;

public class InventoryView extends GameBaseScreen {
    private final InventoryController inventoryController;

    public InventoryView() {
        this.inventoryController = new InventoryController();
        inventoryController.setView(InventoryView.this);
    }

    @Override
    public void show(){

    }

    @Override
    public void render(float delta) {
        super.render(delta);

    }
}
