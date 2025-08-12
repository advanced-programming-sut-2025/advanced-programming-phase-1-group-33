package com.yourgame.view.GameViews;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.model.Skill.Ability;
import com.yourgame.view.AppViews.GameScreen;

public class SkillMenuView extends Window {
    public SkillMenuView(Skin skin, Stage stage, GameScreen gameScreen) {
        super("Skills", skin);

        setSize(1200, 900);
        setModal(true);
        setMovable(false);
        pad(20f);

        Label farmingSkill = new Label("Farming Skill  :    ", skin , "Bold");
        Label miningSkill = new Label("Mining Skill  :    ", skin , "Bold");
        Label foragingSkill = new Label("Foraging Skill  :    ", skin , "Bold");
        Label fishingSkill = new Label("Fishing Skill  :    ", skin , "Bold");

        Ability ability = gameScreen.getPlayer().getAbility();
        Label farmingSkillLevel = new Label("Level " + ability.getFarmingLevel(), skin);
        Label miningSkillLevel = new Label("Level " + ability.getMiningLevel(), skin);
        Label foragingSkillLevel = new Label("Level " + ability.getForagingLevel(), skin);
        Label fishingSkillLevel = new Label("Level " + ability.getFishingLevel(), skin);

        Image farmingIcon = new Image(GameAssetManager.getInstance().getSkillMenuIcons("farming"));
        Image miningIcon = new Image(GameAssetManager.getInstance().getSkillMenuIcons("mining"));
        Image foragingIcon = new Image(GameAssetManager.getInstance().getSkillMenuIcons("foraging"));
        Image fishingIcon = new Image(GameAssetManager.getInstance().getSkillMenuIcons("fishing"));

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
