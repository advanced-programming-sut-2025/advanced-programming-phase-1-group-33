package com.yourgame.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.controller.GameController.GameController;
import com.yourgame.model.Map.Farm;
import com.yourgame.model.Map.Map;
import com.yourgame.model.UserInfo.GiftBetweenPlayers;
import com.yourgame.model.UserInfo.Player;
import com.yourgame.model.UserInfo.RelationNetwork;
import com.yourgame.model.UserInfo.PlayersRelation;
import com.yourgame.model.UserInfo.User;
import com.yourgame.model.WeatherAndTime.ThunderManager;
import com.yourgame.model.WeatherAndTime.TimeSystem;

public class GameState {
    private final ArrayList<Player> players = new ArrayList<>();
    private final ArrayList<Farm> farms = new ArrayList<>();
    private TimeSystem timeSystem;
    private ThunderManager thunderManager;

    private Map map;

    private Player currentPlayer;
    private RelationNetwork relationsBetweenPlayers;

    private final ArrayList<GiftBetweenPlayers> gifts = new ArrayList<>();
    private int giftIndex = 0;

    private int tradeIndex = 0;
    private final ArrayList<Trade> trades = new ArrayList<>();

    public GameState(List<Player> players) {
        this.players.addAll(players);
        this.timeSystem = new TimeSystem();
        this.thunderManager = new ThunderManager();
    }

    public GameState(ArrayList<Player> players, ArrayList<Farm> farms, User u, Map x) {
        this.farms.addAll(farms);
        this.players.addAll(players);
        this.timeSystem = new TimeSystem();
        this.map = x;
        relationInitializer(players);
    }

    public ThunderManager getThunderManager() {
        return thunderManager;
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
        // To do
        // if (App.getCurrentMenu() != null && App.getCurrentMenu().getMenu() instanceof
        // GameMenu) {
        // ((GameMenu) App.getCurrentMenu().getMenu()).doNights();
        // }

//        for (Player player : players) {
//            player.setConsumedEnergyInThisTurn(0);
//            int ratio = 1;
//            if (player.getRemainingDaysAfterMarriageDenied() > 0) {
//                ratio = 2;
//                player.setRemainingDaysAfterMarriageDenied(
//                        player.getRemainingDaysAfterMarriageDenied() - 1);
//            }
//            if (player.isFaintedToday()) {
//                player.setEnergy(150 / ratio);
//            } else {
//                player.setEnergy(200 / ratio);
//            }
//            player.setFaintedToday(false);
//            Iterator<Tree> treeIterator = player.getFarm().getTrees().iterator();
//            Iterator<Crop> cropIterator = player.getFarm().getCrops().iterator();
//            GreenHouse gh = player.getFarm().getGreenHouse();
//            Iterator<Growable> greenHouseCrops = gh.getGrowables().iterator();
//
//
//            while (cropIterator.hasNext()) {
//                Crop crop = cropIterator.next();
//                crop.grow(time);
//                if (!crop.canBeAlive(time)) {
//                    cropIterator.remove();
//
//                    player.getFarm().getPlaceables().removeIf(p -> p == crop);
//                    Tile tile = map.findTile(crop.getBounds().x, crop.getBounds().y);
//                    tile.setWalkable(true);
//                    tile.setPlaceable(null);
//                    tile.setSymbol(SymbolType.DefaultFloor);
//                    tile.setFertilizer(null);
//                    tile.setPlowed(false);
//                }
//            }
//            while (treeIterator.hasNext()) {
//                Tree tree = treeIterator.next();
//                tree.grow(time);
//                if (!tree.canBeAlive(time)) {
//                    treeIterator.remove();
//                    player.getFarm().getPlaceables().remove(tree);
//                    Tile tile = map.findTile(tree.getPixelBounds().x, tree.getPixelBounds().y);
//                    tile.setWalkable(true);
//                    tile.setPlaceable(null);
//                    tile.setSymbol(SymbolType.DefaultFloor);
//                    tile.setFertilizer(null);
//                    tile.setPlowed(false);
//                }
//            }
//
//            if (!gh.isBroken()) {
//                while (greenHouseCrops.hasNext()) {
//                    Growable growable = greenHouseCrops.next();
//                    growable.grow(time);
//                    if (!growable.canBeAlive(time)) {
//                        greenHouseCrops.remove();
//                        if (growable instanceof Crop) {
//                            player.getFarm().getPlaceables().removeIf(p -> p == growable);
//
//                        } else if (growable instanceof Tree tree) {
//                            player.getFarm().getPlaceables().remove(tree);
//                        }
//                    }
//                }
//            }
//            map.generateRandomForagingCrop(player.getFarm());
//            map.generateRandomStoneFarm(player.getFarm());
//
//            for (Animal animal : player.getBackpack().getAllAnimals()) {
//                if (animal.isOutOfHabitat()) {
//                    animal.decrementFriendShip(20);
//                }
//                if (!animal.hasFedYesterday()) {
//                    animal.decrementFriendShip(20);
//                }
//                if (!animal.hasPettedYesterday()) {
//                    animal.decrementFriendShip((animal.getFriendShip() / 200) + 10);
//                }
//            }
//        }
//        map.GotThunderByStormyWeather();
//        map.randomForagingMineralGenerator();
//        for (ShippingBin bin : this.map.getShippingBins()) {
//            bin.checkEveryNight();
//        }
//
//        for (PlayersRelation relation : App.getGameState().relationsBetweenPlayers.relationNetwork.values()) {
//            relation.checkEveryNight();
//        }
//
//        for (Player player : players) {
//            player.getRelationWithAbigail().checkEveryNight(player);
//            player.getRelationWithHarvey().checkEveryNight(player);
//            player.getRelationWithLeah().checkEveryNight(player);
//            player.getRelationWithRobin().checkEveryNight(player);
//            player.getRelationWithSebastian().checkEveryNight(player);
//        }
//
//        this.getMap().getNpcVillage().getBlacksmith().ResetQuantityEveryNight();
//        this.getMap().getNpcVillage().getPierreGeneralStore().ResetQuantityEveryNight();
//        this.getMap().getNpcVillage().getMarnieRanch().ResetQuantityEveryNight();
//        this.getMap().getNpcVillage().getFishShop().ResetQuantityEveryNight();
//        this.getMap().getNpcVillage().getJojaMart().ResetQuantityEveryNight();
//        this.getMap().getNpcVillage().getStardopSaloon().ResetQuantityEveryNight();
//        this.getMap().getNpcVillage().getCarpenterShop().ResetQuantityEveryNight();

    }

    public RelationNetwork getRelationsBetweenPlayers() {
        return relationsBetweenPlayers;
    }

    public void setRelationsBetweenPlayers(RelationNetwork relationsBetweenPlayers) {
        this.relationsBetweenPlayers = relationsBetweenPlayers;
    }

}
