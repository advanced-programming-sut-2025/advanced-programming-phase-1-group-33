package com.yourgame.view.GameViews;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.Graphics.MenuAssetManager;
import com.yourgame.model.NPC.NPCType;
import com.yourgame.model.UserInfo.Player;
import com.yourgame.model.enums.Avatar;
import com.yourgame.view.AppViews.GameScreen;

public class SocialMenuView extends Window {
    private final Skin skin;
    private final Player player;
    private final GameAssetManager assetManager = GameAssetManager.getInstance();

    public SocialMenuView(Skin skin, Stage stage, GameScreen gameScreen) {
        super("Social", skin);
        this.skin = skin;
        this.player = gameScreen.getPlayer();

        setSize(1200, 900);
        setModal(true);
        setMovable(false);
        pad(20f);

        Table contentTable = new Table();
        initTable(contentTable);

        TextButton backButton = GameAssetManager.getInstance().getButton("Back");

        add(contentTable).expandX().fillX().row();
        add(backButton).row();

        setPosition((stage.getWidth() - getWidth()) / 2f, (stage.getHeight() - getHeight()) / 2f);

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage.clear();
                stage.addActor(new MainMenuView(skin, stage, gameScreen));
            }
        });
    }

    public void initTable(Table table) {
        table.top().pad(10);
        table.defaults().pad(8);

        // Headers
        table.add(new Label("NPC", skin, "Bold"));
        table.add(new Label("Name", skin, "Bold"));
        table.add(new Label("Relation Level", skin, "Bold"));
        table.row().padTop(50);

        // Harvey
        table.add(MenuAssetManager.getInstance().getAvatarCharacter(Avatar.Harvey)).padRight(35);
        table.add(new Label("Harvey", skin, "Bold")).padRight(15);
        table.add(new Image(assetManager.getHeartImage(player.getRelationWithHarvey())));
        table.row().padTop(20);

        // Robin
        table.add(MenuAssetManager.getInstance().getAvatarCharacter(Avatar.Robin)).padRight(35);
        table.add(new Label("Robin", skin, "Bold")).padRight(15);
        table.add(new Image(assetManager.getHeartImage(player.getRelationWithRobin())));
        table.row().padTop(20);

        // Pierre
        table.add(MenuAssetManager.getInstance().getAvatarCharacter(Avatar.Pierre)).padRight(35);
        table.add(new Label("Pierre", skin, "Bold")).padRight(15);
        table.add(new Image(assetManager.getHeartImage(player.getRelationWithPierre())));
        table.row().padTop(20);

        // Sebastian
        table.add(MenuAssetManager.getInstance().getSebastianCharacter()).padRight(35);
        table.add(new Label("Sebastian", skin, "Bold")).padRight(15);
        table.add(new Image(assetManager.getHeartImage(player.getRelationWithSebastian())));
        table.row().padTop(20);

        // Leah
        table.add(MenuAssetManager.getInstance().getLeahCharacter()).padRight(35);
        table.add(new Label("Leah", skin, "Bold")).padRight(15);
        table.add(new Image(assetManager.getHeartImage(player.getRelationWithLeah())));
        table.row().padTop(20);
    }


}
