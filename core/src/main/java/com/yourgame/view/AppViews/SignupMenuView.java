package com.yourgame.view.AppViews;

import com.badlogic.gdx.Gdx;

public class SignupMenuView extends MenuBaseScreen {
    public SignupMenuView() {
    }

    @Override
    public void show() {
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
