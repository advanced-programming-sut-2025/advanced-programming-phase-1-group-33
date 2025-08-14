package com.yourgame.view.GameViews;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.Graphics.GameAssets.InventorySlotUI;
import com.yourgame.Graphics.MenuAssetManager;
import com.yourgame.model.Item.Inventory.Inventory;
import com.yourgame.model.UserInfo.Player;
import com.yourgame.view.AppViews.GameScreen;

public class InventoryMenuView extends Window {
    private final Skin skin;
    private final InventorySlotUI[] uiSlots;

    public InventoryMenuView(Player player, Stage stage, GameScreen gameScreen) {
        super("Inventory", MenuAssetManager.getInstance().getSkin(3));
        this.skin = MenuAssetManager.getInstance().getSkin(3);

        this.uiSlots = new InventorySlotUI[36];

        setSize(1200, 900);
        setModal(true);
        setMovable(false);
        pad(20f);

        Table playerTable = createInventoryGrid();
        playerTable.center();

        this.add(playerTable).pad(20);
        this.pack();
        this.setPosition(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f, Align.center);

        // Add a close button to the window's title bar
        TextButton closeButton = new TextButton("X", MenuAssetManager.getInstance().getSkin(1));
        this.getTitleTable().add(closeButton).size(30, 30).padRight(5);
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage.clear();
                stage.addActor(new MainMenuView(skin, stage, gameScreen));
            }
        });

        for (int i = 0; i < uiSlots.length; i++) {
            uiSlots[i].update(player.getBackpack().getInventory().getSlot(i), GameAssetManager.getInstance());
        }
    }

    private Table createInventoryGrid() {
        Table container = new Table();

        Stack inventoryStack = new Stack();
        inventoryStack.add(new Image(GameAssetManager.getInstance().getTexture("Game/Clock/Inventory/Inventory_Full.png")));

        Table table = new Table();
        table.pad(12, 20, 12, 20);
        for (int i = 0; i < uiSlots.length; i++) {
            InventorySlotUI slot = new InventorySlotUI(skin.getFont("Text"));
            uiSlots[i] = slot;
            table.add(slot).width(56).height(56).pad(8, 4, 8, 4);

            if ((i + 1) % 12 == 0) {
                table.row();
            }
        }
        inventoryStack.add(table);

        container.add(inventoryStack);
        return container;
    }

}

