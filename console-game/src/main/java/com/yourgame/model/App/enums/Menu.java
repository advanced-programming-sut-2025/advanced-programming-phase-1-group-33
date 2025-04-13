package com.yourgame.model.App.enums;

import com.yourgame.view.RegistrationView;
import com.yourgame.view.AppMenu;
import java.util.Scanner;

public enum Menu {
    RegisterationView(new RegistrationView());

    private final AppMenu menu;

    Menu(AppMenu menu){
        this.menu = menu;
    }

    public void checkCommand(Scanner scanner){
        this.menu.check(scanner);
    }
}
