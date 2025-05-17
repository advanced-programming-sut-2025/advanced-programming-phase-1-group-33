package com.yourgame.controller.GameController.StoreController;
import com.yourgame.model.App;
import com.yourgame.model.IO.Request;
import com.yourgame.model.IO.Response;


public class BlackSmithController extends StoreController{

    @Override
    public Response showAllProducts() {
        String message = App.getGameState().getMap().getNpcVillage().getBlacksmith().showAllProducts();
        return new Response(true, message);
    }

    @Override
    public Response showAvailableProducts() {
        String message = App.getGameState().getMap().getNpcVillage().getBlacksmith().showAvailableProducts();
        return new Response(true, message);
    }

    @Override
    public Response PurchaseProduct(int value, String productName) {
        return App.getGameState().getMap().getNpcVillage().getBlacksmith().purchaseProduct(value, productName);
    }

}
