package com.yourgame.view.AppViews;

import java.util.Scanner;

import com.yourgame.controller.AppController.LoginMenuController;
import com.yourgame.controller.AppController.ProfileMenuController;
import com.yourgame.model.App;
import com.yourgame.model.IO.Request;
import com.yourgame.model.IO.Response;
import com.yourgame.model.enums.Commands.LoginMenuCommands;
import com.yourgame.model.enums.Commands.MenuTypes;
import com.yourgame.model.enums.Commands.ProfileMenuCommands;

public class ProfileMenu implements AppMenu {
    ProfileMenuController controller = new ProfileMenuController();

    @Override
    public Response handleMenu(String input, Scanner scanner) {
        if (App.getCurrentUser() == null) {
            App.setCurrentMenu(MenuTypes.MainMenu);
            return new Response(false, "Access denied. Please log in to access the profile menu.");
        }

        ProfileMenuCommands command = ProfileMenuCommands.parse(input);

        if (command == null) {
            return getInvalidCommand();
        }

        switch (command) {
            case UserInfo:
                return getUserInfo();
            case CHANGE_USERNAME:
                String newUsername = command.getGroup(input, "username");
                return ProfileMenuController.changeUsername(newUsername);
                case CHANGE_NICKNAME:
                String newNickname = command.getGroup(input, "nickname");
                return ProfileMenuController.changeNickname(newNickname);
            case CHANGE_EMAIL:
                String newEmail = command.getGroup(input, "email");
                return ProfileMenuController.changeEmail(newEmail);
            case CHANGE_PASSWORD:
                String newPassword = command.getGroup(input, "newPassword");
                String oldPassword = command.getGroup(input, "oldPassword");
                return ProfileMenuController.changePassword(newPassword, oldPassword);
            case GO_Back:
                App.setCurrentMenu(MenuTypes.MainMenu);
                return new Response(true, "Going Back to MainMenu");
            default:
                break;
        }

        return new Response(false, "");
    }

    private Response getUserInfo() {
        return ProfileMenuController.showUserInfo();

    }
}
