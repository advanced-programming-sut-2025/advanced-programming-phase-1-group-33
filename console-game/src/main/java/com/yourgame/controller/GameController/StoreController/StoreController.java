package com.yourgame.controller.GameController.StoreController;

import com.yourgame.model.App;
import com.yourgame.model.IO.Request;
import com.yourgame.model.IO.Response;
import com.yourgame.model.enums.Commands.MenuTypes;

public abstract class StoreController {
    public abstract Response showAllProducts();


    public abstract Response showAvailableProducts();

    public abstract Response PurchaseProduct(int value, String productName);

    public void exit() {
        App.setCurrentMenu(MenuTypes.GameMenu);
    }

}
