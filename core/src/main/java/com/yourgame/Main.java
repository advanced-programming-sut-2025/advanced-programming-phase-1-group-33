package com.yourgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.yourgame.view.AppViews.AppView;
import com.yourgame.view.AppViews.MainMenuView;
import com.yourgame.view.AppViews.SignupMenuView;

import java.sql.SQLException;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    private static Main main;
    private static SpriteBatch batch;

    @Override
    public void create() {
        /* This is from the project.
        try {
            (new AppView()).run();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        */
        System.out.println("hi");
        main = this;
        batch = new SpriteBatch();
        main.setScreen(new MainMenuView());
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    public static Main getMain() {
        return main;
    }

    public static SpriteBatch getBatch() {
        return batch;
    }
}
