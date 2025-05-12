package com.yourgame.model.enums.Commands;

import com.yourgame.view.AppViews.*;
import com.yourgame.view.GameMenu;

import java.util.Scanner;

public enum MenuTypes {
    MainMenu(new MainMenu()),
    ProfileMenu(new ProfileMenu()),
    AvatarMenu(new AvatarMenu()),
    PreGameMenu(new PreGameMenu()),
    LoginMenu(new LoginMenu()),
    RegisterMenu(new RegisterMenu()),
    GameMenu(new GameMenu()), 
    ExitMenu(new ExitMenu());

    private final AppMenu menu;

    MenuTypes(AppMenu menu){
        this.menu = menu;
    }

    public AppMenu getMenu() {
        return menu;
    }

    @Override
    public String toString() {
        if (this == MenuTypes.ExitMenu) {
            return "Exit Menu";
        } else if (this == MenuTypes.AvatarMenu) {
            return "Avatar Menu";
        } else if (this == MenuTypes.MainMenu) {
            return "Main Menu";
        } else if (this == MenuTypes.ProfileMenu) {
            return "Profile Menu";
        } else if (this == MenuTypes.LoginMenu) {
            return "Login Menu";
        } else {
            return "";
        }
    }
}
