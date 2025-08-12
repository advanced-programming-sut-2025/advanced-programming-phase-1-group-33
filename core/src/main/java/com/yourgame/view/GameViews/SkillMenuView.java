package com.yourgame.view.GameViews;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.model.UserInfo.Player;
import com.yourgame.view.AppViews.GameScreen;

public class SkillMenuView extends Window {
    public SkillMenuView(Skin skin, Stage stage, GameScreen gameScreen) {
        super("Skills", skin);

        setSize(1200, 900);
        setModal(true);
        setMovable(false);
        pad(20f);

        TooltipManager tooltipManager = TooltipManager.getInstance();
        tooltipManager.initialTime = 0.2f;
        tooltipManager.subsequentTime = 0.1f;

        Label farmingSkill = new Label("Farming Skill  :    ", skin , "Bold");
        Label miningSkill = new Label("Mining Skill  :    ", skin , "Bold");
        Label foragingSkill = new Label("Foraging Skill  :    ", skin , "Bold");
        Label fishingSkill = new Label("Fishing Skill  :    ", skin , "Bold");

        Player player = gameScreen.getPlayer();
        Label farmingSkillLevel = new Label("Level " + player.getFarmingSkill().level(), skin);
        Label miningSkillLevel = new Label("Level " + player.getMiningSkill().level(), skin);
        Label foragingSkillLevel = new Label("Level " + player.getForagingSkill().level(), skin);
        Label fishingSkillLevel = new Label("Level " + player.getFishingSkill().level(), skin);

        Image farmingIcon = new Image(GameAssetManager.getInstance().getSkillMenuIcons("farming"));
        Image miningIcon = new Image(GameAssetManager.getInstance().getSkillMenuIcons("mining"));
        Image foragingIcon = new Image(GameAssetManager.getInstance().getSkillMenuIcons("foraging"));
        Image fishingIcon = new Image(GameAssetManager.getInstance().getSkillMenuIcons("fishing"));

        farmingIcon.addListener(new TextTooltip(
                "Levels are gained by harvesting crops and caring for animals.\nEach level grants +1 hoe and watering can proficiency.",
                skin));

        miningIcon.addListener(new TextTooltip(
                "Mining skill is increased by breaking rocks with a Pickaxe.\nEach level grants +1 pickaxe proficiency.",
                skin));

        foragingIcon.addListener(new TextTooltip(
                "Foraging skill includes gathered goods and wood from chopped trees.\nEach level grants +1 axe proficiency.",
                skin));

        fishingIcon.addListener(new TextTooltip(
                "Fishing skill increases by catching fish or completing the fishing mini-game.\nEach level grants +1 fishing rod proficiency.",
                skin));

        TextButton backButton = GameAssetManager.getInstance().getButton("Back");

        add(farmingIcon).padRight(10);
        add(farmingSkill);
        add(farmingSkillLevel);
        row().padTop(20);
        add(miningIcon).padRight(10);
        add(miningSkill);
        add(miningSkillLevel);
        row().padTop(20);
        add(foragingIcon).padRight(10);
        add(foragingSkill);
        add(foragingSkillLevel);
        row().padTop(20);
        add(fishingIcon).padRight(10);
        add(fishingSkill);
        add(fishingSkillLevel);
        row().padTop(20);
        add(backButton).colspan(3).center().padTop(50).row();

        setPosition((stage.getWidth() - getWidth())/2f, (stage.getHeight() - getHeight())/2f);

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage.clear();
                stage.addActor(new MainMenuView(skin, stage, gameScreen));
            }
        });
    }
}
