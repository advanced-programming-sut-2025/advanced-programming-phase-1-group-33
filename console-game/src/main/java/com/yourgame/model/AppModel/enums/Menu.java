package com.yourgame.model.AppModel.enums;

import com.yourgame.view.AppViews.*;

import java.util.Scanner;

public enum Menu {
    MainMenuView(new MainMenuView()),
    ProfileMenuView(new ProfileMenuView()), 
    AvatarMenuView(new AvatarMenuView()), 
    PreGameMenu(new PreGameMenuView()),     
    RegisterationView(new RegistrationView()),
    LoginMenuView(new LoginMenuView()), 
    ExitMenu(new ExitMenu());

    private final AppMenu menu;

    Menu(AppMenu menu){
        this.menu = menu;
    }

    public void checkCommand(Scanner scanner){
        this.menu.check(scanner);
    }
}
