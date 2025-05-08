package com.yourgame.view.AppViews;

import com.yourgame.model.IO.Response;

import java.util.Scanner;

public class ExitMenu implements AppMenu {

    public Response handleMenu(String input, Scanner scanner) {
        return new Response(true, "");
    }
}
