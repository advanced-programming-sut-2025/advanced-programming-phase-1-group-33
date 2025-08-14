package com.yourgame.view.GameViews;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.view.AppViews.GameScreen;

public class InfoMenuView extends Window {
    public InfoMenuView(Skin skin, Stage stage, GameScreen gameScreen) {
        super("Plants & Trees", skin);

        setSize(1200, 900);
        setModal(true);
        setMovable(false);
        pad(20f);
        setPosition((stage.getWidth() - getWidth()) / 2f, (stage.getHeight() - getHeight()) / 2f);

        Table infoTable = new Table(skin);
        infoTable.center().pad(50f);

        // Header row
        infoTable.add(new Label("Icon", skin, "Bold"));
        infoTable.add(new Label("Name", skin, "Bold"));
        infoTable.add(new Label("Type", skin, "Bold"));
        infoTable.add(new Label("Season", skin, "Bold"));
        infoTable.add(new Label("Product", skin, "Bold"));
        infoTable.row();

        // Load icons from your atlas or asset manager

        GameAssetManager assetManager = GameAssetManager.getInstance();
        addPlantRow(infoTable, assetManager.getTexture("Game/Crop/Cauliflower/Cauliflower.png"), "Cauliflower", "Crop", "Spring", "Cauliflower", skin);
        addPlantRow(infoTable, assetManager.getTexture("Game/Crop/Melon/Melon.png"), "Melon", "Crop", "Summer", "Melon", skin);
        addPlantRow(infoTable, assetManager.getTexture("Game/Crop/Corn/Corn.png"), "Corn", "Crop", "Summer&Fall", "Corn", skin);
        addPlantRow(infoTable, assetManager.getTexture("Game/Crop/Cranberry/Cranberries.png"), "Cranberries", "Crop", "Fall", "Cranberries", skin);
        addPlantRow(infoTable, assetManager.getTexture("Game/Crop/Garlic/Garlic.png"), "Garlic", "Crop", "Spring", "Garlic", skin);
        addPlantRow(infoTable, assetManager.getTexture("Game/Crop/Tomato/Tomato.png"), "Tomato", "Crop", "Summer", "Tomato", skin);
        addPlantRow(infoTable, assetManager.getTexture("Game/Crop/Yam/Yam.png"), "Yam", "Crop", "Fall", "Yam", skin);
        addPlantRow(infoTable, assetManager.getTexture("Game/Crop/Wheat/Wheat.png"), "Wheat", "Crop", "Summer&Fall", "Wheat", skin);
        addPlantRow(infoTable, assetManager.getTexture("Game/Crop/Sunflower/Sunflower.png"), "Sunflower", "Crop", "Summer&Fall", "Sunflower", skin);
        addPlantRow(infoTable, assetManager.getTexture("Game/Crop/Coffee/Coffee_Bean.png"), "CoffeeBean", "Crop", "Spring&Summer", "Coffee", skin);
        addPlantRow(infoTable, assetManager.getTexture("Game/Tree/Banana/Banana_Stage_5_Fruit.png"), "BananaTree", " FruitTree ", "Summer", "Bananas", skin);
        addPlantRow(infoTable, assetManager.getTexture("Game/Tree/Mango/Mango_Stage_5_Fruit.png"), "MangoTree", " FruitTree ", "Summer", "Mangoes", skin);
        addPlantRow(infoTable, assetManager.getTexture("Game/Tree/Orange/Orange_Stage_5_Fruit.png"), "OrangeTree", " FruitTree ", "Summer", "Oranges", skin);
        addPlantRow(infoTable, assetManager.getTexture("Game/Tree/Cherry/Cherry_Stage_5_Fruit.png"), "CherryTree", " FruitTree ", "Spring", "Cherries", skin);
        addPlantRow(infoTable, assetManager.getTexture("Game/Tree/Pomegranate/Pomegranate_Stage_5_Fruit.png"), "PomeTree", " FruitTree ", "Fall", "Pomegranates", skin);

        ScrollPane scrollPane = new ScrollPane(infoTable, skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);
        add(scrollPane).expand().fill().row();

        TextButton backButton = GameAssetManager.getInstance().getButton("Back");
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage.clear();
                stage.addActor(new MainMenuView(skin, stage, gameScreen));
            }
        });

        add(backButton).padTop(20f);
    }

    private void addPlantRow(Table table, Texture texture, String name, String type, String season, String product, Skin skin) {
        Image icon = new Image(texture);
        table.add(icon).size(64);
        table.add(new Label(name, skin)).padRight(20);
        table.add(new Label(type, skin)).padRight(20);
        table.add(new Label(season, skin));
        table.add(new Label(product, skin));
        table.row().padTop(10);
    }
}
