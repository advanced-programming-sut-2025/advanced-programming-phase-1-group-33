package com.yourgame.model.Map;

import com.yourgame.model.App;
import com.yourgame.model.ManuFactor.Ingredient;
import com.yourgame.model.UserInfo.Coin;
import com.yourgame.model.UserInfo.Player;
import com.yourgame.model.enums.SymbolType;

import java.awt.*;
import java.util.HashMap;

public class ShippingBin implements Placeable{
    private HashMap<Player,Integer> dailyRevenue;
    private final Rectangle bounds;

    public ShippingBin(int x, int y) {

        this.bounds = new Rectangle(x,y,1,1);
        for (Player player : App.getGameState().getPlayers()) {
            dailyRevenue.put(player,0);
        }

    }

    @Override
    public Rectangle getBounds() {
        return bounds;
    }

    @Override
    public SymbolType getSymbol() {
        return SymbolType.SIPPINGBIN;
    }

    public void checkEveryNight() {

        for (Player player : this.dailyRevenue.keySet()) {
            for (Ingredient ingredient : player.getBackpack().getIngredientQuantity().keySet()) {
                if (ingredient instanceof Coin coin) {
                    player.getBackpack().addIngredients(coin, dailyRevenue.get(player));
                }
            }
            dailyRevenue.put(player,0);
        }

    }

    public void increaseRevenue(Player player,int revenue) {
        dailyRevenue.put(player,dailyRevenue.get(player) + revenue);
    }
}
