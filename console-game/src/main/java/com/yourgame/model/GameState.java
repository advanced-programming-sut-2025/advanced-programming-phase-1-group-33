package com.yourgame.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    /**
     * Advances the game turn:
     * <ul>
     *   <li>If all players are fainted, it skips the day and resets players for the new day.</li>
     *   <li>Otherwise, it selects the next active (non-fainted) player.</li>
     *   <li>If a full round is completed (wraps around), it advances the game clock by one hour.</li>
     * </ul>
     */
    public void nextPlayerTurn() {
        // Filter to get the active players (i.e., not fainted)
        List<Player> activePlayers = players.stream()
            .filter(p -> !p.isFaintedToday())
            .collect(Collectors.toList());

        // If all players are fainted, skip the day
        if (activePlayers.isEmpty()) {
            time.advancedDay(1);
            MakePlayersReadyForNextDay();
            // Reset currentPlayer (assuming players list is not empty)
            currentPlayer = players.get(0);
            return;
        }

        // Find the index of the current player in the active players list
        int size = activePlayers.size();
        int currentIndex = activePlayers.indexOf(currentPlayer);
        
        // If the current player is not in the active list, start with the first one
        if (currentIndex == -1) {
            currentPlayer = activePlayers.get(0);
            return;
        }
        
        // Determine the next player's index
        int nextIndex = (currentIndex + 1) % size;
        // If we've wrapped around to the start, advance one hour on the clock.
        if (nextIndex == 0) {
            time.advancedHour(1);
        }
        
        // Set the next active player and reset their consumed energy.
        currentPlayer = activePlayers.get(nextIndex);
        currentPlayer.setConsumedEnergyInThisTurn(0);
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

	public void MakePlayersReadyForNextDay() {
        for(Player player: players){
            player.setEnergy(200);
            player.setConsumedEnergyInThisTurn(0);
            player.setFaintedToday(false); 
        }
    }
}
