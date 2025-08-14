package com.yourgame.view.GameViews;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.Graphics.MenuAssetManager;
import com.yourgame.model.Item.Item;
import com.yourgame.model.NPC.Quest;
import com.yourgame.model.NPC.QuestManager;
import com.yourgame.model.UserInfo.Player;
import com.yourgame.view.AppViews.GameScreen;

import java.util.Map;

public class JournalMenuView extends Window {
    private final Skin skin;
    private final QuestManager questManager;
    private final Table questListTable;
    private final Table detailsTable;
    private boolean active = true;

    public JournalMenuView(Stage stage, GameScreen gameScreen, Player player) {
        super("Journal", MenuAssetManager.getInstance().getSkin(3));

        this.skin = MenuAssetManager.getInstance().getSkin(3);
        this.questManager = player.getQuestManager();

        // --- Window Setup ---
        //setSize(1200, 900);
        setModal(true);
        setMovable(true);
        padTop(40f);

        // --- Main Layout Table ---
        Table mainTable = new Table();
        mainTable.pad(10f);

        // --- Quest List Section ---
        this.questListTable = new Table();
        ScrollPane questListScrollPane = new ScrollPane(questListTable, skin);
        questListScrollPane.setFadeScrollBars(false);

        // --- Change Button ---
        CheckBox checkBox = new CheckBox("Active Quests", MenuAssetManager.getInstance().getSkin(1));
        checkBox.setChecked(true);
        checkBox.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                active = !active;
                populateQuestList(active);
            }
        });

        // --- Details Section ---
        this.detailsTable = new Table();
        detailsTable.pad(15);
        ScrollPane detailsScrollPane = new ScrollPane(detailsTable, skin);
        detailsScrollPane.setFadeScrollBars(false);
        detailsScrollPane.setStyle(new ScrollPane.ScrollPaneStyle(skin.get(ScrollPane.ScrollPaneStyle.class)));
        detailsScrollPane.getStyle().background = new TextureRegionDrawable(GameAssetManager.getInstance().getTexture("Backgrounds/Background7.png"));

        // --- Add sections to the main layout ---
        mainTable.add(checkBox).left().pad(5).row();
        mainTable.add(questListScrollPane).expandX().fillX().prefHeight(250).padBottom(10).row();
        mainTable.add(detailsScrollPane).expand().fill().prefHeight(250);

        this.add(mainTable).width(800).height(600); // Give the whole window a size
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

        // Initially populate the list with active quests
        populateQuestList(active);
    }

    /**
     * Clears and repopulates the quest list with either active or completed quests.
     * @param showActive True to show active quests, false to show completed quests.
     */
    private void populateQuestList(boolean showActive) {
        questListTable.clear();
        detailsTable.clear(); // Clear details when the list changes

        Iterable<Quest> quests = showActive ? questManager.getActiveQuests() : questManager.getCompletedQuests();

        if (!quests.iterator().hasNext()) {
            questListTable.add(new Label("No quests here.", skin));
            return;
        }

        for (Quest quest : quests) {
            // Create a button for each quest title
            TextButton questButton = new TextButton(quest.title(), skin, "default");
            questListTable.add(questButton).growX().pad(5).row();

            // Add a listener to show details when this quest is clicked
            questButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    displayQuestDetails(quest);
                }
            });
        }
    }

    /**
     * Populates the details panel with information about the selected quest.
     * @param quest The quest to display.
     */
    private void displayQuestDetails(Quest quest) {
        detailsTable.clear();
        detailsTable.align(Align.topLeft);

        // 1. Description
        Label descriptionLabel = new Label(quest.description(), skin);
        descriptionLabel.setWrap(true);
        detailsTable.add(descriptionLabel).growX().padBottom(15).row();

        // 2. Required Items (Objective)
        detailsTable.add(new Label("Objective:", skin)).align(Align.left).padBottom(5).row();
        for (Map.Entry<Item, Integer> entry : quest.requiredItems().entrySet()) {
            String objectiveText = String.format("- Bring %s (%d)", entry.getKey().getName(), entry.getValue());
            detailsTable.add(new Label(objectiveText, skin)).align(Align.left).row();
        }
        detailsTable.add().padBottom(15).row(); // Spacer

        // 3. Rewards
        detailsTable.add(new Label("Rewards:", skin)).align(Align.left).padBottom(5).row();
        String rewardText = String.format("- %d Gold\n- %d Friendship Points with %s",
            quest.rewardGold(), quest.friendshipPoints(), quest.questGiver().name());
        detailsTable.add(new Label(rewardText, skin)).align(Align.left).row();
    }
}
