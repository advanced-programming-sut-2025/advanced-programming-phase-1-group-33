package com.yourgame.model;

import java.util.ArrayList;
import java.util.List;

import com.yourgame.model.Map.Farm;
import com.yourgame.model.Map.Map;
import com.yourgame.model.UserInfo.Player;
import com.yourgame.model.UserInfo.User;
import com.yourgame.model.WeatherAndTime.TimeSystem;

public class GameState {
    private final ArrayList<Player> players = new ArrayList<>();
    private final ArrayList<Farm> farms = new ArrayList<>();
    private TimeSystem time;
    private Map map;
    private final User gameCreator;
    private Player currentPlayer;
//    private RelationNetwork relationsBetweenPlayers;
//    private final ArrayList<BetweenPlayersGift> gifts = new ArrayList<>();
    private int giftIndex = 0;

    public GameState(ArrayList<Player> players, ArrayList<Farm> farms, User u, Map x) {
        this.farms.addAll(farms);
        this.players.addAll(players);
        this.gameCreator = u;
        this.time = new TimeSystem();
        this.map = x;
//        relationInitializer(players);
    }

    public void nextPlayerTurn() {
        int size = players.size();
        int currentIndex = players.indexOf(currentPlayer);
        int checkedPlayers = 0;

        while (checkedPlayers < size) {
            currentIndex = (currentIndex + 1) % size;
            Player nextPlayer = players.get(currentIndex);

            if (!nextPlayer.isFaintedToday()) {
                currentPlayer = nextPlayer;
                break;
            }
            checkedPlayers++;
        }

        if (checkedPlayers == size) {
            time.advancedDay(1);;
        }

        if (currentPlayer == players.get(0)) {
            App.getGameState().getGameTime().advancedHour(1);
        }
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
        return time;
    }


    public void startGame() {
    }
}
