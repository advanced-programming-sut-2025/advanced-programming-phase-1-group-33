package com.yourgame.model.enums.Commands;

import com.yourgame.view.AppViews.*;
import com.yourgame.view.Store.BlackSmithMenu;
import com.yourgame.view.Store.CarpenterShopMenu;
import com.yourgame.view.Store.FishShopMenu;
import com.yourgame.view.Store.JojaMartMenu;
import com.yourgame.view.Store.MarnieRanchMenu;
import com.yourgame.view.Store.PierreGeneralStoreMenu;
import com.yourgame.view.Store.StardopSaloonMenu;
import com.yourgame.view.GameMenu;
import com.yourgame.view.TradeMenu;

import java.util.Scanner;

public enum MenuTypes {
    MainMenu(new MainMenu()),
    ProfileMenu(new ProfileMenu()),
    AvatarMenu(new AvatarMenu()),
    PreGameMenu(new PreGameMenu()),
    LoginMenu(new LoginMenu()),
    GameMenu(new GameMenu()), 
    ExitMenu(new ExitMenu()),
    BlackSmithMenu(new BlackSmithMenu()),
    CarpenterShopMenu(new CarpenterShopMenu()),
    FishShopMenu(new FishShopMenu()),
    JojaMartMenu(new JojaMartMenu()),
    MarnieRanchMenu(new MarnieRanchMenu()),
    PierreGeneralStoreMenu(new PierreGeneralStoreMenu()),
    TradeMenu(new TradeMenu()),
    StardopSaloonMenu(new StardopSaloonMenu());

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
