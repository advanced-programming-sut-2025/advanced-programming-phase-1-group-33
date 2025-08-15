package com.yourgame.view.GameViews.ArtisanMenuView;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.model.Food.Cooking.CookingRecipe;
import com.yourgame.model.Food.Cooking.CookingRecipeSource;
import com.yourgame.model.Food.Cooking.Ingredient;
import com.yourgame.model.Item.Inventory.InventorySlot;
import com.yourgame.model.UserInfo.Player;
import com.yourgame.view.AppViews.GameScreen;
import com.yourgame.view.GameViews.MainMenuView;

import java.util.ArrayList;

public class BeeHouseMenuView extends Window {
    private final Skin skin;
    private final GameScreen gameScreen;
    private final Stage stage;

    public BeeHouseMenuView(Skin skin, Stage stage, GameScreen gameScreen) {
        super("BeeHouse Menu", skin);
        this.stage = stage;
        this.gameScreen = gameScreen;
        this.skin = skin;

        setSize(1400, 900);
        setModal(true);
        setMovable(false);
        pad(20f);

        Table table = new Table();
        table.defaults().pad(20f);

        TextButton backButton = GameAssetManager.getInstance().getButton("Close");

        // Create a Table for the scroll pane content
        Table recipeTable = new Table();
        recipeTable.top().center();
        initTable(recipeTable);


        // Scroll pane
        ScrollPane scrollPane = new ScrollPane(recipeTable, skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);

        table.add(scrollPane).expandX().fillX().row();
        table.add(backButton).colspan(5).center();

        add(table);

        setPosition((stage.getWidth() - getWidth()) / 2f, (stage.getHeight() - getHeight()) / 2f);

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage.clear();
                gameScreen.closeMenu();
                remove();
            }
        });
    }

    private void initTable(Table recipeTable) {
        // Add headers
        recipeTable.add(new Label("Icon", skin, "Bold")).pad(10);
        recipeTable.add(new Label("Product", skin, "Bold")).pad(10);
        recipeTable.add(new Label("Ingredients", skin, "Bold")).pad(10);
        recipeTable.add(new Label("Processing Time", skin, "Bold")).pad(10);
        recipeTable.add(new Label("", skin,"Bold")).pad(10);
        recipeTable.row();

        //recipeTable.add()
    }

    private boolean isAvailable(CookingRecipeSource source, Player player){
        if(source.isStarter())
            return true;
        if(source.isBought())
            return true;
        if((source.getFarmingSkillLevelNeeded() > 0) || (source.getFishingSkillLevelNeeded() > 0)
            || (source.getMiningSkillLevelNeeded() > 0) || (source.getForagingSkillLevelNeeded() > 0)) {
            if ((player.getFarmingLevel() >= source.getFarmingSkillLevelNeeded())
                && (player.getFishingLevel() >= source.getFishingSkillLevelNeeded())
                && (player.getMiningLevel() >= source.getMiningSkillLevelNeeded())
                && (player.getForagingLevel() >= source.getForagingSkillLevelNeeded()))
                return true;
        }
        return false;
    }

    private boolean haveEnoughIngredients(CookingRecipe recipe, Player player){
        for(Ingredient ingredient : recipe.getIngredients()){
            boolean found = false;
            for(InventorySlot slot : player.getBackpack().getInventory().getSlots()){
                if(ingredient.getItem().getName().equals(slot.item().getName())){
                    found = true;
                    if(ingredient.getQuantity() > slot.quantity()){
                        return false;
                    }
                }
            }
            if(!found){
                return false;
            }
        }
        return true;
    }

    private void updateInventory(CookingRecipe recipe,Player player){
        for(Ingredient ingredient : recipe.getIngredients()){
            for(InventorySlot slot : player.getBackpack().getInventory().getSlots()){
                if(ingredient.getItem().getName().equals(slot.item().getName())){
                    slot.reduceQuantity(ingredient.getQuantity());
                    if(slot.quantity() == 0){
                        player.getBackpack().getInventory().getSlots().remove(slot);
                    }
                }
            }
        }
    }
}

