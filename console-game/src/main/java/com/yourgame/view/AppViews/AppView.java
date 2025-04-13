package com.yourgame.view.AppViews;


import com.yourgame.model.AppModel.App;
import com.yourgame.model.AppModel.enums.Menu;

import java.util.Scanner;
public class AppView {
    public  void run(){
        Scanner scanner = new Scanner(System.in);
        while (App.getCurrentMenu() != Menu.ExitMenu) {
            App.getCurrentMenu().checkCommand(scanner);
        }
        scanner.close();
    }


}
