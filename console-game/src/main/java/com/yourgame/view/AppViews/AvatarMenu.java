package com.yourgame.view.AppViews;

import java.util.Scanner;

import com.yourgame.controller.AppController.AvatarMenuController;
import com.yourgame.model.IO.Response;

public class AvatarMenu implements AppMenu {
    AvatarMenuController controller = new AvatarMenuController();

    @Override
    public Response handleMenu(String input, Scanner scanner) {
        return new Response(false, "");
    }
}
