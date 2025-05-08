package com.yourgame.view.AppViews;

import java.util.Scanner;

import com.yourgame.controller.AppController.PreGameController;
import com.yourgame.model.IO.Response;

public class PreGameMenu implements AppMenu {
    PreGameController controller = new PreGameController();

    @Override
    public Response handleMenu(String input, Scanner scanner) {

        return new Response(false, "");
    }
}
