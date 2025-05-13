package com.yourgame;

import com.yourgame.view.AppViews.AppView;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {
        (new AppView()).run();
    }
}
