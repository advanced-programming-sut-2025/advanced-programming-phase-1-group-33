package com.yourgame.view.AppViews;


import com.yourgame.model.App;
import com.yourgame.model.enums.Commands.MenuTypes;

import java.util.Scanner;

public class AppView {
    public final static Scanner scanner = new Scanner(System.in);

    public void run() {
        while (App.getCurrentMenu() != MenuTypes.ExitMenu) {
            String input = scanner.nextLine().trim();
            App.getCurrentMenu().getMenu().handleMenu(input);
        }
    }


}
