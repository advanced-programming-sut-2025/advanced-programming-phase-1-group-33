package com.yourgame.controller.AppController;

import com.yourgame.Main;
import com.yourgame.model.App;
import com.yourgame.model.enums.Avatar;
import com.yourgame.model.enums.Commands.MenuTypes;
import com.yourgame.view.AppViews.ProfileMenuView;

public class AvatarMenuController {
    public void handleAvatarChoose(String name){
        Avatar avatar = Avatar.valueOf(name);
        App.getCurrentUser().setAvatar(avatar);
    }

    public void handleBackButton(){
        App.setCurrentMenu(MenuTypes.ProfileMenu);
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new ProfileMenuView());
    }
}
