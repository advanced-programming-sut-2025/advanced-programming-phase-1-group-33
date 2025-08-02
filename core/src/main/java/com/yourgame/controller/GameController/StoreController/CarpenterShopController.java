package com.yourgame.controller.GameController.StoreController;

import com.yourgame.model.App;
import com.yourgame.model.IO.Response;


public class CarpenterShopController extends StoreController {
    @Override
    public Response showAllProducts() {
        String message = App.getGameState().getMap().getNpcVillage().getCarpenterShop().showAllProducts();
        return new Response(true, message);
    }

    @Override
    public Response showAvailableProducts() {
        String message = App.getGameState().getMap().getNpcVillage().getCarpenterShop().showAvailableProducts();
        return new Response(true, message);
    }

    @Override
    public Response PurchaseProduct(int value, String productName) {
        return App.getGameState().getMap().getNpcVillage().getCarpenterShop().purchaseProduct(value, productName);
    }


}
