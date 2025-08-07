package com.yourgame.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.yourgame.model.Animals.Animal;
import com.yourgame.model.Item.Crop;
import com.yourgame.model.Item.Growable;
import com.yourgame.model.Item.Tree;
import com.yourgame.model.Map.Farm;
import com.yourgame.model.Map.GreenHouse;
import com.yourgame.model.Map.Map;
import com.yourgame.model.Map.ShippingBin;
import com.yourgame.model.Map.Tile;
import com.yourgame.model.UserInfo.GiftBetweenPlayers;
import com.yourgame.model.UserInfo.Player;
import com.yourgame.model.UserInfo.RelationNetwork;
import com.yourgame.model.UserInfo.PlayersRelation;
import com.yourgame.model.UserInfo.User;
import com.yourgame.model.WeatherAndTime.TimeSystem;
import com.yourgame.model.enums.SymbolType;

public class GameState {
    private final ArrayList<Player> players = new ArrayList<>();
    private final ArrayList<Farm> farms = new ArrayList<>();
    private TimeSystem timeSystem;
    
    private Map map;

    private Player currentPlayer;
    private RelationNetwork relationsBetweenPlayers;

    private final ArrayList<GiftBetweenPlayers> gifts = new ArrayList<>();
    private int giftIndex = 0;

    private int tradeIndex = 0;
    private final ArrayList<Trade> trades = new ArrayList<>();

    public GameState(ArrayList<Player> players) {
        this.players.addAll(players);
        this.timeSystem = new TimeSystem();

    }


    public GameState(ArrayList<Player> players, ArrayList<Farm> farms, User u, Map x) {
        this.farms.addAll(farms);
        this.players.addAll(players);
        this.timeSystem = new TimeSystem();
        this.map = x;
        relationInitializer(players);
    }


    public void nextPlayerTurn() {

    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public TimeSystem getGameTime() {
        return timeSystem;
    }

    private void relationInitializer(ArrayList<Player> players) {
        relationsBetweenPlayers = new RelationNetwork();

        for (int i = 0; i < players.size(); i++) {
            for (int j = i + 1; j < players.size(); j++) {
                Set<Player> key = new HashSet<>(Arrays.asList(players.get(i), players.get(j)));
                relationsBetweenPlayers.relationNetwork.put(key, new PlayersRelation());
            }
        }
    }

    public void addGiftsIndex() {
        giftIndex++;
    }

    public ArrayList<GiftBetweenPlayers> getGifts() {
        return gifts;
    }

    public void addToGifts(GiftBetweenPlayers gift) {
        gifts.add(gift);
    }

    public int getGiftIndex() {
        return giftIndex;
    }

    public void addTradesIndex() {
        tradeIndex++;
    }

    public int getTradeIndex() {
        return tradeIndex;
    }

    public void addToTrades(Trade trade) {
        trades.add(trade);
    }

    public ArrayList<Trade> getTrades() {
        return trades;
    }

    public void startGame() {
    }

    public void MakePlayersReadyForNextDay() {
        for (Player player : players) {

            player.setEnergy(200);
            player.setConsumedEnergyInThisTurn(0);
            player.setFaintedToday(false);
        }
    }

    public void MakeGameReadyForNextDay() {
        // TODO: need to Clear this one 
    }

    public RelationNetwork getRelationsBetweenPlayers() {
        return relationsBetweenPlayers;
    }

    public void setRelationsBetweenPlayers(RelationNetwork relationsBetweenPlayers) {
        this.relationsBetweenPlayers = relationsBetweenPlayers;
    }

}
