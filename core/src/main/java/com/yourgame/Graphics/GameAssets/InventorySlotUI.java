package com.yourgame.Graphics.GameAssets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.model.Item.Inventory.InventorySlot;
import com.yourgame.model.Item.Item;

/**
 * Represents a single visual slot in the inventory UI bar.
 * It is a Stack that layers a selection highlight, an item image, and a quantity label.
 */
public class InventorySlotUI extends Stack {
    private Item item;
    private final Image selectionImage;
    private final Image itemImage;
    private final Label quantityLabel;

    public InventorySlotUI(Drawable selectionDrawable, BitmapFont font) {
        // The item's image
        this.itemImage = new Image();
        this.itemImage.setAlign(Align.center);

        // The highlight image shown when selected
        this.selectionImage = new Image(selectionDrawable);
        this.selectionImage.setVisible(false); // Hidden by default

        // The label for the item quantity
        Label.LabelStyle style = new Label.LabelStyle(font, Color.WHITE);
        this.quantityLabel = new Label("", style);
        this.quantityLabel.setAlignment(Align.bottomRight);
        this.quantityLabel.setVisible(false); // Hidden by default

        // Add actors to the stack
        this.add(itemImage);      // Bottom layer
        this.add(selectionImage); // Middle layer
        this.add(quantityLabel);  // Top layer, aligned to bottom right
    }

    /**
     * Updates the visual state of this UI slot based on the data from the game's inventory.
     * @param dataSlot The data slot from the player's Inventory, or null if empty.
     */
    public void update(InventorySlot dataSlot, GameAssetManager assetManager) {
        if (dataSlot == null || dataSlot.item() == null) {
            // The slot is empty
            item = null;
            itemImage.setDrawable(null);
            quantityLabel.setVisible(false);
            return;
        }

        item = dataSlot.item();

        // Update the item texture
        itemImage.setDrawable(new TextureRegionDrawable(dataSlot.item().getTextureRegion(assetManager)));

        // Update the quantity label
        if (dataSlot.item().isStackable() && dataSlot.quantity() > 1) {
            quantityLabel.setText(String.valueOf(dataSlot.quantity()));
            quantityLabel.setVisible(true);
        } else {
            quantityLabel.setVisible(false);
        }
    }

    /**
     * Shows or hides the yellow selection highlight.
     * @param isSelected True to show the highlight, false to hide it.
     */
    public void setSelected(boolean isSelected) {
        selectionImage.setVisible(isSelected);
    }

    public Item getItem() {
        return item;
    }
}
