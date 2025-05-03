package com.yourgame.view.AppViews;

import com.yourgame.model.IO.Response;

import java.util.Scanner;

public interface AppMenu {
    void handleMenu(String input);

    default void printResponse(Response response) {
        System.out.println(response.getMessage());
    }

    default Response getInvalidCommand() {
        return new Response(false, "Invalid command");
    }

}