package com.yourgame.view.AppViews;

import java.util.Scanner;

public class ExitMenu implements AppMenu{
    
    @Override
    public void check(Scanner scanner) {
        scanner.close();
        System.exit(0);
    }
}
