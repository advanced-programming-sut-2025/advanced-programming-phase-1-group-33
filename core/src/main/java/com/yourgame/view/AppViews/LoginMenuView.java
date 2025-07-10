package com.yourgame.view.AppViews;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class LoginMenuView extends MenuBaseScreen{
    public LoginMenuView() {}

    @Override
    public void show() {
        Table table = new Table();

        table.setFillParent(true);
        table.left();
        table.setPosition(table.getX() - 50, table.getY());
        table.pad(30);

        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
