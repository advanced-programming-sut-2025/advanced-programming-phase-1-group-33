package com.yourgame.view;

import java.sql.SQLException;
import java.util.Scanner;

import com.yourgame.controller.GameController.TradeController;
import com.yourgame.model.IO.Response;
import com.yourgame.view.AppViews.AppMenu;

public class TradeMenu implements AppMenu {
    private final TradeController controller = new TradeController();

    @Override
    public Response handleMenu(String command, Scanner scanner) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleMenu'");
    }
    
}
