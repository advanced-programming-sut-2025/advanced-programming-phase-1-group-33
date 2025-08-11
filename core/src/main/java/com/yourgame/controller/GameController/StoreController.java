package com.yourgame.controller.GameController;

import com.yourgame.model.App;
import com.yourgame.model.IO.Request;
import com.yourgame.model.IO.Response;
import com.yourgame.model.enums.Commands.MenuTypes;

import java.util.regex.Matcher;

public abstract class StoreController {
    public abstract Response showAllProducts();


    public abstract Response showAvailableProducts();
    public Response ProcessPurchaseCommand(Request request) {
        String count = request.body.get("count");
        String productName = request.body.get("productName");
        int value = 1;

        if (count != null) {
            value = Integer.parseInt(count);
        }
        return PurchaseProduct(value, productName);

    }
    public abstract Response PurchaseProduct(int value, String productName);

//    public void exit() {
//        App.setCurrentMenu(MenuTypes.GameMenu);
//    }

}
