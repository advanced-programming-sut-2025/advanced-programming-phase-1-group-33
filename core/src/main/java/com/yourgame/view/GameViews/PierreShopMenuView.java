package com.yourgame.view.GameViews;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.yourgame.Graphics.MenuAssetManager;
import com.yourgame.controller.GameController.PierreShopMenuController;
import com.yourgame.view.AppViews.GameScreen;

public class PierreShopMenuView extends Window {
    private final PierreShopMenuController controller;

    public PierreShopMenuView(Skin skin, Stage stage, GameScreen gameScreen) {
        super("Pierre General Store Menu", skin);
        controller = new PierreShopMenuController();
        controller.setView(this);

        setSize(800, 600);
        setModal(true);
        setMovable(false);
        center();

        Table contentTable = new Table();
        contentTable.defaults().pad(10f);

        TextButton buySeedsButton = new TextButton("Buy Seeds", MenuAssetManager.getInstance().getSkin(3));
        TextButton sellGoodsButton = new TextButton("Sell Goods", MenuAssetManager.getInstance().getSkin(3));
        TextButton closeButton = new TextButton("Close", MenuAssetManager.getInstance().getSkin(3));

        contentTable.add(buySeedsButton).row();
        contentTable.add(sellGoodsButton).row();
        contentTable.add(closeButton).row();

        add(contentTable).expand().center();

        setPosition((stage.getWidth() - getWidth()) / 2f,
                    (stage.getHeight() - getHeight()) / 2f);

        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameScreen.closeMenu();
                remove();
            }
        });
    }
}
