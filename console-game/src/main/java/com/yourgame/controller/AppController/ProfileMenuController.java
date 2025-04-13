package com.yourgame.controller.AppController;

import java.util.Scanner;

import com.yourgame.model.AppModel.App;
import com.yourgame.model.AppModel.Result;
import com.yourgame.model.AppModel.enums.Menu;
import com.yourgame.model.AppModel.enums.ProfileMenuCommands;

public class ProfileMenuController {
    public Result handleCommand(String command, Scanner scanner) {
        ProfileMenuCommands mainCommand = ProfileMenuCommands.parse(command);
        if (mainCommand == null) {
            return Result.failure("Invalid command!");
        }

        switch (mainCommand) {
            case CHANGE_EMAIL:
                return handleChangeEmail(command);
            case CHANGE_PASSWORD:
                return handleChangePassword(command);
            case CHANGE_NICKNAME:
                return handleChangeNickname(command);
            case CHANGE_USERNAME:
                return handleChangeUsername(command);
            case GO_BACK:
                return handleGoBack();
            case USER_INFO:
                return showUserInfo();
            default:
                return Result.failure("Command not implemented yet.");
        }
    }

    private Result showUserInfo() {
        return Result.success("user info");
    }

    private Result handleChangeUsername(String command) {
        return Result.success("change Username");
    }

    private Result handleChangePassword(String command) {
        return Result.success("change Username");
    }


    private Result handleChangeNickname(String command) {
        return Result.success("change Username");
    }

    private Result handleChangeEmail(String command) {
        return Result.success("change Username");
    }


    private Result handleGoBack() {
        App.setCurrentMenu(Menu.MainMenuView);
        return Result.success("You are now in main menu");
    }

}
