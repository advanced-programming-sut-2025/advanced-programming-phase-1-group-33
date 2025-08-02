package com.yourgame.controller.GameController.StoreController;
import com.yourgame.model.App;
import com.yourgame.model.IO.Response;


public class JojaMartController extends StoreController {
    @Override
    public Response showAllProducts() {
        String message = App.getGameState().getMap().getNpcVillage().getJojaMart().showAllProducts();
        return new Response(true, message);
    }

    @Override
    public Response showAvailableProducts() {
        String message = App.getGameState().getMap().getNpcVillage().getJojaMart().showAvailableProducts();
        return new Response(true, message);
    }

    @Override
    public Response PurchaseProduct(int value, String productName) {
        return App.getGameState().getMap().getNpcVillage().getJojaMart().purchaseProduct(value, productName);
    }


}
