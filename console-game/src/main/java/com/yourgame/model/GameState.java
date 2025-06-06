package com.yourgame.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.yourgame.controller.GameController.GameController;
import com.yourgame.model.Animals.Animal;
import com.yourgame.model.IO.Response;
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
import com.yourgame.view.GameMenu;

public class GameState {
    private final ArrayList<Player> players = new ArrayList<>();
    private final ArrayList<Farm> farms = new ArrayList<>();
    private TimeSystem time;
    private Map map;

    private Player currentPlayer;
    private RelationNetwork relationsBetweenPlayers;

    private final ArrayList<GiftBetweenPlayers> gifts = new ArrayList<>();
    private int giftIndex = 0;

    private int tradeIndex = 0;
    private final ArrayList<Trade> trades = new ArrayList<>();

    public GameState(ArrayList<Player> players, ArrayList<Farm> farms, User u, Map x) {
        this.farms.addAll(farms);
        this.players.addAll(players);
        this.time = new TimeSystem();
        this.map = x;
        relationInitializer(players);
    }

    /**
     * Advances the game turn:
     * <ul>
     * <li>If all players are fainted, it skips the day and resets players for the
     * new day.</li>
     * <li>Otherwise, it selects the next active (non-fainted) player.</li>
     * <li>If a full round is completed (wraps around), it advances the game clock
     * by one hour.</li>
     * </ul>
     */
    public void nextPlayerTurn() {
        int size = players.size();
        int currentIndex = players.indexOf(currentPlayer);
        int checkedPlayers = 0;

        while (checkedPlayers < size) {
            currentIndex = (currentIndex + 1) % size;
            Player nextPlayer = players.get(currentIndex);

            if (!nextPlayer.isFaintedToday()) {
                setCurrentPlayer(nextPlayer);

                break;
            }
            checkedPlayers++;
        }

        if (checkedPlayers == size) {
            time.advancedDay(1);
            // return new Response(true, "All players have been fainted! Next day is
            // started!\n";

        }

        if (currentPlayer.equals(players.get(0))) {
            time.advancedHour(1);
            // return new Response(true, "An hour passed!\n");
            // "Current player: " + players.getFirst().getUsername() + "\n\n" +
            // players.getFirst().UncheckedNotifications());
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
        // To do
        // if (App.getCurrentMenu() != null && App.getCurrentMenu().getMenu() instanceof
        // GameMenu) {
        // ((GameMenu) App.getCurrentMenu().getMenu()).doNights();
        // }

        for (Player player : players) {
            player.setConsumedEnergyInThisTurn(0);
            int ratio = 1;
            if (player.getRemainingDaysAfterMarigDenied() > 0) {
                ratio = 2;
                player.setRemainingDaysAfterMarigDenied(
                        player.getRemainingDaysAfterMarigDenied() - 1);
            }
            if (player.isFaintedToday()) {
                player.setEnergy(150 / ratio);
            } else {
                player.setEnergy(200 / ratio);
            }
            player.setFaintedToday(false);
            Iterator<Tree> treeIterator = player.getFarm().getTrees().iterator();
            Iterator<Crop> cropIterator = player.getFarm().getCrops().iterator();
            GreenHouse gh = player.getFarm().getGreenHouse();
            Iterator<Growable> greenHouseCrops = gh.getGrowables().iterator();


            while (cropIterator.hasNext()) {
                Crop crop = cropIterator.next();
                crop.grow(time);
                if (!crop.canBeAlive(time)) {
                    cropIterator.remove();

                    player.getFarm().getPlaceables().removeIf(p -> p == crop);
                    Tile tile = map.findTile(crop.getBounds().x, crop.getBounds().y);
                    tile.setWalkable(true);
                    tile.setPlaceable(null);
                    tile.setSymbol(SymbolType.DefaultFloor);
                    tile.setFertilizer(null);
                    tile.setPlowed(false);
                }
            }
            while (treeIterator.hasNext()) {
                Tree tree = treeIterator.next();
                tree.grow(time);
                if (!tree.canBeAlive(time)) {
                    treeIterator.remove();
                    player.getFarm().getPlaceables().remove(tree);
                    Tile tile = map.findTile(tree.getBounds().x, tree.getBounds().y);
                    tile.setWalkable(true);
                    tile.setPlaceable(null);
                    tile.setSymbol(SymbolType.DefaultFloor);
                    tile.setFertilizer(null);
                    tile.setPlowed(false);
                }
            }

            if (!gh.isBroken()) {
                while (greenHouseCrops.hasNext()) {
                    Growable growable = greenHouseCrops.next();
                    growable.grow(time);
                    if (!growable.canBeAlive(time)) {
                        greenHouseCrops.remove();
                        if (growable instanceof Crop) {
                            player.getFarm().getPlaceables().removeIf(p -> p == growable);

                        } else if (growable instanceof Tree tree) {
                            player.getFarm().getPlaceables().remove(tree);
                        }
                    }
                }
            }
            map.generateRandomForagingCrop(player.getFarm());
            map.generateRandomStoneFarm(player.getFarm());

            for (Animal animal : player.getBackpack().getAllAnimals()) {
                if (animal.isOutOfHabitat()) {
                    animal.decrementFriendShip(20);
                }
                if (!animal.hasFedYesterday()) {
                    animal.decrementFriendShip(20);
                }
                if (!animal.hasPettedYesterday()) {
                    animal.decrementFriendShip((animal.getFriendShip() / 200) + 10);
                }
            }
        }
        map.GotThunderByStormyWeather();
        map.randomForagingMineralGenerator();
        for (ShippingBin bin : this.map.getShippingBins()) {
            bin.checkEveryNight();
        }

        for (PlayersRelation relation : App.getGameState().relationsBetweenPlayers.relationNetwork.values()) {
            relation.checkEveryNight();
        }

        for (Player player : players) {
            player.getRelationWithAbigail().checkEveryNight(player);
            player.getRelationWithHarvey().checkEveryNight(player);
            player.getRelationWithLeah().checkEveryNight(player);
            player.getRelationWithRobin().checkEveryNight(player);
            player.getRelationWithSebastian().checkEveryNight(player);
        }

        this.getMap().getNpcVillage().getBlacksmith().ResetQuantityEveryNight();
        this.getMap().getNpcVillage().getPierreGeneralStore().ResetQuantityEveryNight();
        this.getMap().getNpcVillage().getMarnieRanch().ResetQuantityEveryNight();
        this.getMap().getNpcVillage().getFishShop().ResetQuantityEveryNight();
        this.getMap().getNpcVillage().getJojaMart().ResetQuantityEveryNight();
        this.getMap().getNpcVillage().getStardopSaloon().ResetQuantityEveryNight();
        this.getMap().getNpcVillage().getCarpenterShop().ResetQuantityEveryNight();

    }

    public RelationNetwork getRelationsBetweenPlayers() {
        return relationsBetweenPlayers;
    }

    public void setRelationsBetweenPlayers(RelationNetwork relationsBetweenPlayers) {
        this.relationsBetweenPlayers = relationsBetweenPlayers;
    }

}
