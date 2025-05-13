package com.yourgame.view.AppViews;

import com.yourgame.model.IO.Response;

import java.sql.SQLException;
import java.util.Scanner;

public interface AppMenu {
    Response handleMenu(String command, Scanner scanner) throws SQLException;

    // Add this method
    default String getPreview() {
        return null; // Default: no preview
    }
    
    default void printResponse(Response response) {
        System.out.println(response.getMessage());
    }
    default Response getInvalidCommand() {
        return new Response(false, "Invalid command");
    }
}