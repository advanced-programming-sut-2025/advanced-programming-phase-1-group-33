package com.yourgame.view.GameViews;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.model.Food.Cooking.CookingRecipe;
import com.yourgame.model.Food.Cooking.CookingRecipeSource;
import com.yourgame.model.Food.FoodType;
import com.yourgame.model.UserInfo.Player;
import com.yourgame.view.AppViews.GameScreen;

import java.util.ArrayList;

public class CookingMenuView extends Window {
    private final ArrayList<CookingRecipe> cookingRecipes;
    private final Skin skin;
    private final GameScreen gameScreen;
    private final Stage stage;

    public CookingMenuView(Skin skin, Stage stage, GameScreen gameScreen) {
        super("Cooking", skin);
        cookingRecipes = gameScreen.getPlayer().getCookingRecipeManager().getCookingRecipes();
        this.stage = stage;
        this.gameScreen = gameScreen;
        this.skin = skin;

        setSize(1400, 900);
        setModal(true);
        setMovable(false);
        pad(20f);

        Table table = new Table();
        table.defaults().pad(20f);

        TextButton backButton = GameAssetManager.getInstance().getButton("Back");

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
                stage.addActor(new MainMenuView(skin, stage, gameScreen));
            }
        });
    }

    private Table initTable(Table recipeTable) {
        // Add headers
        recipeTable.add(new Label("Icon", skin, "Bold")).pad(10);
        recipeTable.add(new Label("Food", skin, "Bold")).pad(10);
        recipeTable.add(new Label("Ingredients", skin, "Bold")).pad(10);
        recipeTable.add(new Label("Available", skin, "Bold")).pad(10);
        recipeTable.add(new Label("Action", skin,"Bold")).pad(10);
        recipeTable.row();

        int counter = 0;
        for (CookingRecipe recipe : cookingRecipes) {
            Image foodIcon = new Image(recipe.getResult().getTextureRegion(GameAssetManager.getInstance()));

            Label foodName = new Label(recipe.getResult().getFoodType().name(), skin);

            StringBuilder ingredientText = new StringBuilder();
            recipe.getIngredients().forEach(ingredient ->
                    ingredientText.append(ingredient.getItem().getName())
                            .append(" x")
                            .append(ingredient.getQuantity())
                            .append("\n")
            );
            Label ingredientsLabel = new Label(ingredientText.toString(), skin);

            boolean available = isAvailable(recipe.getSource(),gameScreen.getPlayer());
            Label availabilityLabel = new Label(available ? "Available" : "Locked", skin);

            Label cookLabel = new Label("Cook", skin, "BoldImpact");
            cookLabel.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if(!available) {
                        gameScreen.showMessage("error","Recipe is locked!",skin,0,200,stage);
                    }
                    if (available) {
                        //recipe.cook(gameScreen.getPlayer());
                        // refresh UI or update inventory
                    }
                }
            });

            // Add row to table
            recipeTable.add(foodIcon).size(64, 64).padLeft(20);
            recipeTable.add(foodName).pad(5);
            recipeTable.add(ingredientsLabel).pad(5);
            recipeTable.add(availabilityLabel).pad(5);
            recipeTable.add(cookLabel).padRight(20);
            recipeTable.row();
        }

        return recipeTable;
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
}
