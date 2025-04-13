package com.yourgame.view.AppViews;


import com.yourgame.model.AppModel.App;

import java.util.Scanner;
public class AppView {
    private static App app;
    public static void run(){
        Scanner scanner = new Scanner(System.in);
        app.getCurrentMenu().checkCommand(scanner);
    }

}
