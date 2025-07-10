package com.yourgame.controller.GameController;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;

import com.yourgame.model.App;
import com.yourgame.model.Trade;
import com.yourgame.model.IO.Response;
import com.yourgame.model.ManuFactor.Ingredient;
import com.yourgame.model.Stores.Sellable;
import com.yourgame.model.UserInfo.Coin;
import com.yourgame.model.UserInfo.Player;
import com.yourgame.model.UserInfo.RelationNetwork;
import com.yourgame.model.UserInfo.PlayersRelation;
import com.yourgame.model.enums.Commands.MenuTypes;

public class TradeController {

    public Response trade(Matcher matcher) {

        Player buyer = null;

        for (Player p : App.getGameState().getPlayers()) {
            if (p.getUsername().equals(matcher.group("username"))) {
                buyer = p;
            }
        }

        if (buyer == null) {
            return new Response(false, "player not found");
        }

        if (Sellable.isSellable(matcher.group("item"))) {
            return new Response(false, "Invalid item for trade");
        }

        if (Sellable.getSellableByName(matcher.group("item")) == null) {
            return new Response(false, "Not enough stock");
        }

        int amount = Integer.parseInt(matcher.group("amount"));

        if (App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().getOrDefault((Ingredient) Sellable.getSellableByName(matcher.group("item")), 0) < amount) {
            return new Response(false, "Not enough stock");
        }

        App.getGameState().addTradesIndex();
        App.getGameState().addToTrades(new Trade(App.getGameState().getCurrentPlayer(), buyer, amount,
                Sellable.getSellableByName(matcher.group("item")), Integer.parseInt(matcher.group("price")),App.getGameState().getTradeIndex()));

        return new Response(true, "your offer with id " + App.getGameState().getTradeIndex() +" successfully submitted");
    }

    public Response tradeList() {
        StringBuilder message = new StringBuilder("Requests(not responded): ");

        for (Trade trade : App.getGameState().getTrades()) {
            if (trade.getBuyer().equals(App.getGameState().getCurrentPlayer()) && !trade.isResponded()) {
                message.append("\n").append(trade);
            }
        }

        return new Response(true, message.toString());
    }

    public Response tradeResponse(Matcher matcher) {

        int id = Integer.parseInt(matcher.group("id"));
        Trade tempTrade = null;

        for (Trade trade : App.getGameState().getTrades()) {
            if (trade.getId() == id) {
                tempTrade = trade;
                break;
            }
        }

        if (tempTrade == null) {
            return new Response(false, "Trade not found");
        }

        if (!tempTrade.getBuyer().equals(App.getGameState().getCurrentPlayer())) {
            return new Response(false, "you can't respond this trade");
        }

        if (tempTrade.isResponded()) {
            return new Response(false, "Trade is already responded");
        }

        RelationNetwork tempNetwork = App.getGameState().getRelationsBetweenPlayers();
        Set<Player> lookUpKey = new HashSet<>();
        lookUpKey.add(App.getGameState().getCurrentPlayer());
        lookUpKey.add(tempTrade.getSeller());

        PlayersRelation tempRelation = tempNetwork.relationNetwork.get(lookUpKey);

        if (matcher.group("state").equals("reject")) {

            if (!tempRelation.HaveTradedToday()) {
                tempRelation.changeXp(-30);
            }

            tempRelation.setHaveTradedToday(true);
            tempTrade.setResponded(true);
            return new Response(true, "Trade rejected");
        }

        if (tempTrade.getSeller().getBackpack().getIngredientQuantity().getOrDefault((Ingredient) tempTrade.getSellable(),0) < tempTrade.getQuantity()) {

            if (!tempRelation.HaveTradedToday()) {
                tempRelation.changeXp(-30);
            }
            tempRelation.setHaveTradedToday(true);

            return new Response(false, "seller doesn't have enough stock");
        }

        if (tempTrade.getBuyer().getBackpack().getIngredientQuantity().getOrDefault(new Coin(),0) < tempTrade.getPrice()) {

            if (!tempRelation.HaveTradedToday()) {
                tempRelation.changeXp(-30);
            }

            tempRelation.setHaveTradedToday(true);

            return new Response(false, "you don't have enough money");
        }

        tempTrade.getBuyer().getBackpack().removeIngredients(new Coin(), tempTrade.getPrice());
        tempTrade.getSeller().getBackpack().addIngredients(new Coin(), tempTrade.getPrice());

        tempTrade.getSeller().getBackpack().removeIngredients((Ingredient) tempTrade.getSellable() , tempTrade.getQuantity());
        tempTrade.getBuyer().getBackpack().addIngredients((Ingredient) tempTrade.getSellable() , tempTrade.getQuantity());

        tempTrade.setResponded(true);
        tempTrade.setAccepted(true);

        if (!tempRelation.HaveTradedToday()) {
            tempRelation.changeXp(50);
        }

        if (tempRelation.isMarriage()) {
            App.getGameState().getCurrentPlayer().addEnergy(50);
            tempTrade.getSeller().addEnergy(50);
        }

        tempRelation.setHaveTradedToday(true);

        return new Response(true, "Trade accepted");
    }

    public Response tradeHistory() {
        StringBuilder message = new StringBuilder("Trade history: ");

        for (Trade trade : App.getGameState().getTrades()) {
            if (trade.getBuyer().equals(App.getGameState().getCurrentPlayer()) || trade.getSeller().equals(App.getGameState().getCurrentPlayer())) {
                message.append("\n").append(trade);
            }
        }

        return new Response(true, message.toString());
    }

//    public Response exit() {
//        App.setCurrentMenu(MenuTypes.GameMenu);
//        return new Response(true, "you are in game menu now");
//    }
}
