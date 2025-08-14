package com.yourgame.view.GameViews;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.*;
import com.badlogic.gdx.utils.Align;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.Graphics.GameAssets.InventorySlotUI;
import com.yourgame.Graphics.MenuAssetManager;
import com.yourgame.model.Item.Inventory.InventorySlot;
import com.yourgame.model.UserInfo.Player;
import com.yourgame.view.AppViews.GameScreen;

public class InventoryMenuView extends Window {
    private final Skin skin;
    private final Player player;
    private final InventorySlotUI[] uiSlots;
    private final DragAndDrop dragAndDrop;

    public InventoryMenuView(Player player, Stage stage, GameScreen gameScreen) {
        super("Inventory", MenuAssetManager.getInstance().getSkin(3));
        this.skin = MenuAssetManager.getInstance().getSkin(3);
        this.player = player;
        this.uiSlots = new InventorySlotUI[36];
        this.dragAndDrop = new DragAndDrop();

        // --- Window Setup ---
        setModal(true);
        setMovable(false);
        pad(20f);

        // --- Main Layout: Inventory on the left, Trash Can on the right ---
        Table mainLayout = new Table();
        Table inventoryTable = createInventoryGrid();
        Image trashCan = createTrashCan();

        mainLayout.add(inventoryTable);
        mainLayout.add(trashCan).width(64).height(80).padLeft(20);

        this.add(mainLayout).pad(20);
        this.pack();
        this.setPosition(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f, Align.center);

        // --- Close Button ---
        TextButton closeButton = new TextButton("X", MenuAssetManager.getInstance().getSkin(1));
        this.getTitleTable().add(closeButton).size(30, 30).padRight(5);
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage.clear();
                stage.addActor(new MainMenuView(skin, stage, gameScreen));
            }
        });

        updateAllSlots();
    }

    private Table createInventoryGrid() {
        Table container = new Table();
        Stack inventoryStack = new Stack();
        inventoryStack.add(new Image(GameAssetManager.getInstance().getTexture("Game/Clock/Inventory/Inventory_Full.png")));

        Table slotsTable = new Table();
        slotsTable.pad(12, 20, 12, 20);
        for (int i = 0; i < uiSlots.length; i++) {
            final int index = i;
            InventorySlotUI slotUI = new InventorySlotUI(skin.getFont("Text"));
            uiSlots[i] = slotUI;

            setupDragAndDrop(slotUI, index);

            slotsTable.add(slotUI).width(56).height(56).pad(8, 4, 8, 4);
            if ((i + 1) % 12 == 0) {
                slotsTable.row();
            }
        }
        inventoryStack.add(slotsTable);
        container.add(inventoryStack);
        return container;
    }

    private Image createTrashCan() {
        Image trashCanImage = new Image(GameAssetManager.getInstance().getTexture("Game/Tool/TrashCan.png"));

        dragAndDrop.addTarget(new Target(trashCanImage) {
            @Override
            public boolean drag(Source source, Payload payload, float x, float y, int pointer) {
                // The trash can turns red when an item is dragged over it
                setColor(224f / 256f, 164f / 256f, 111f / 256f, 1);
                return true;
            }

            @Override
            public void reset(Source source, Payload payload) {
                setColor(1, 1, 1, 1); // Reset to normal color
            }

            @Override
            public void drop(Source source, Payload payload, float x, float y, int pointer) {
                InventorySlot sourceSlot = (InventorySlot) payload.getObject();
                player.getBackpack().getInventory().removeItem(sourceSlot.item());
                updateAllSlots();
            }
        });

        return trashCanImage;
    }

    private void setupDragAndDrop(final InventorySlotUI slotUI, final int index) {
        dragAndDrop.addSource(new Source(slotUI) {
            @Override
            public Payload dragStart(InputEvent event, float x, float y, int pointer) {
                InventorySlot sourceSlot = player.getBackpack().getInventory().getSlot(index);
                if (sourceSlot == null || sourceSlot.item() == null) {
                    return null; // Don't drag an empty slot
                }

                Payload payload = new Payload();
                payload.setObject(sourceSlot); // The data we are dragging

                // Create a "ghost" image of the item that follows the mouse
                Image dragActor = new Image(sourceSlot.item().getTextureRegion(GameAssetManager.getInstance()));
                dragActor.setSize(56, 56);
                payload.setDragActor(dragActor);

                // Temporarily hide the item in the source slot
                slotUI.setVisible(false);
                return payload;
            }

            @Override
            public void dragStop(InputEvent event, float x, float y, int pointer, Payload payload, Target target) {
                // This is called when the item is dropped. Make the original slot visible again.
                slotUI.setVisible(true);
            }
        });

        // --- TARGET: Where the item can be dropped ---
        dragAndDrop.addTarget(new Target(slotUI) {
            @Override
            public boolean drag(Source source, Payload payload, float x, float y, int pointer) {
                return true; // Any slot is a valid target
            }

            @Override
            public void drop(Source source, Payload payload, float x, float y, int pointer) {
                InventorySlot sourceSlotData = (InventorySlot) payload.getObject();
                int sourceIndex = player.getBackpack().getInventory().getSlots().indexOf(sourceSlotData);

                // Swap the items in the data model
                player.getBackpack().getInventory().swapSlots(sourceIndex, index);

                // Refresh the UI for both affected slots
                updateAllSlots();
            }
        });
    }

    public void updateAllSlots() {
        for (int i = 0; i < uiSlots.length; i++) {
            uiSlots[i].update(player.getBackpack().getInventory().getSlot(i), GameAssetManager.getInstance());
        }
    }
}

