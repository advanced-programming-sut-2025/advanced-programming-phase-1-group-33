package com.yourgame.view.AppViews;

import java.util.Scanner;

import com.yourgame.controller.AppController.ProfileMenuController;
import com.yourgame.model.IO.Response;

public class ProfileMenu implements AppMenu {
    ProfileMenuController controller = new ProfileMenuController();

    @Override
    public Response handleMenu(String input, Scanner scanner) {

        return new Response(false, "");
    }
}
