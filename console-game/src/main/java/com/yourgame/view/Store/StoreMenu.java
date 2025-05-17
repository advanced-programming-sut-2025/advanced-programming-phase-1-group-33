package com.yourgame.view.Store;

import java.sql.SQLException;
import java.util.Scanner;

import com.yourgame.controller.GameController.StoreController.StoreController;
import com.yourgame.model.IO.Response;
import com.yourgame.model.enums.Commands.GameViewCommands;
import com.yourgame.model.enums.Commands.StoreMenuCommands;
import com.yourgame.view.AppViews.AppMenu;

public class StoreMenu implements AppMenu {
    protected StoreController controller;

    
    @Override
    public Response handleMenu(String input, Scanner scanner) throws SQLException {
        StoreMenuCommands command = StoreMenuCommands.parse(input);
        if(command == null){
            return getInvalidCommand(); 
        }
        switch (command) {
            case ShowAllProducts:
                return controller.showAllProducts();
            case Exit:
                controller.exit();
                return new Response(true, "Going Back to the GameMenu");
            case ShowAvailableProducts:
                return controller.showAllProducts(); 
            case PurchaseProduct:
                return getPurchaseProduct(input);
            default:
                return getInvalidCommand(); 
            }
    }

    private Response getPurchaseProduct(String input) {
        String productName = StoreMenuCommands.PurchaseProduct.getGroup(input, "productName");
        int count = Integer.parseInt(StoreMenuCommands.PurchaseProduct.getGroup(input, "count")) ; 
        return controller.PurchaseProduct(count,productName); 
    }

}
