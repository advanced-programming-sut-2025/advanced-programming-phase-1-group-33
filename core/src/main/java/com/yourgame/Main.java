package com.yourgame;

import com.badlogic.gdx.Game;
import com.yourgame.view.AppViews.AppView;

import java.sql.SQLException;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    @Override
    public void create() {
        setScreen(new FirstScreen());
        /* This is from the project.
        try {
            (new AppView()).run();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        */
    }
}
