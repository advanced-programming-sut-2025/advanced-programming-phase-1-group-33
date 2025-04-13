package com.yourgame.model.AppModel.enums;

import com.yourgame.view.AppViews.RegistrationView;
import com.yourgame.view.AppViews.AppMenu;
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
