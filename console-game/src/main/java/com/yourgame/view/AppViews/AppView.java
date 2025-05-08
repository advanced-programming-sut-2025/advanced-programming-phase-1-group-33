package com.yourgame.view.AppViews;


import com.yourgame.model.App;
import com.yourgame.model.enums.Commands.MenuTypes;

import java.util.Scanner;

public class AppView {
    public final static Scanner scanner = new Scanner(System.in);

    public void run() {

        System.out.println("Trying to run DAO ");
        App.getUserDAO();
        System.out.println("Current menu: " + App.getCurrentMenu());

        while (App.getCurrentMenu() != MenuTypes.ExitMenu) {
            if(App.getCurrentMenu().getMenu().getPreview() != null){
                System.out.println(App.getCurrentMenu().getMenu().getPreview());
            }
            String input = scanner.nextLine().trim();
            System.out.println(App.getCurrentMenu().getMenu().handleMenu(input, scanner));
        }
    }


}
