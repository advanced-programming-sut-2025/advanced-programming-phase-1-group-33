package com.yourgame.view.GameViews;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.Graphics.MenuAssetManager;
import com.yourgame.model.Animals.AnimalPackage.Animal;
import com.yourgame.model.UserInfo.Player;
import com.yourgame.view.AppViews.GameScreen;

public class AnimalMenuView extends Window {
    private final Skin skin;
    private final Player player;
    private final GameAssetManager assetManager;

    public AnimalMenuView(Stage stage, GameScreen gameScreen, Player player) {
        super("Animals", MenuAssetManager.getInstance().getSkin(3));
        this.skin = MenuAssetManager.getInstance().getSkin(3);
        this.player = player;
        this.assetManager = GameAssetManager.getInstance();

        // --- Window Setup ---
        //setSize(1200, 900);
        setModal(true);
        padTop(40f);

        // --- Main Layout Table ---
        Table mainTable = new Table();
        mainTable.pad(10f);

        // --- Scrollable List for Animals ---
        Table animalListTable = new Table();
        populateAnimalList(animalListTable);

        ScrollPane scrollPane = new ScrollPane(animalListTable, skin);
        scrollPane.setFadeScrollBars(false);

        mainTable.add(scrollPane).expand().fill();
        this.add(mainTable).expand().fill();
        this.pack();
        this.setPosition((stage.getWidth() - getWidth()) / 2f, (stage.getHeight() - getHeight()) / 2f);

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
    }

    private void populateAnimalList(Table table) {
        for (Animal animal : player.getAllAnimals()) {
            Table animalRow = new Table();
            animalRow.setBackground(new Image(assetManager.getTexture("Backgrounds/Background7.png")).getDrawable());

            String portraitPath = "Game/Animal/" + animal.getType().getName() + ".png";
            Image portrait = new Image(assetManager.getTexture(portraitPath));
            animalRow.add(portrait).size(64, 64).pad(10);

            // 2. Name and Friendship Bar
            Table infoTable = new Table();
            infoTable.add(new Label(animal.getName(), skin)).align(Align.left).row();

            // Friendship Bar (max value is 1000)
            ProgressBar friendshipBar = new ProgressBar(0, 500, 1, false, MenuAssetManager.getInstance().getSkin(1));
            friendshipBar.setValue(animal.getFriendship());
            infoTable.add(friendshipBar).width(200).height(20).align(Align.left);

            animalRow.add(infoTable).expandX().fillX().pad(10);

            // 3. Product Ready Indicator
            if (animal.hasProductReady()) {
                Label label = new Label("Product is ready.", skin);
                animalRow.add(label).pad(10);
            } else {
                Label label = new Label("No Product's ready yet.", skin);
                animalRow.add(label).pad(10);
            }

            // Add the completed row to the main list table
            table.add(animalRow).growX().pad(5).row();
        }
    }
}
