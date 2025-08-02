package com.yourgame.controller.GameController;

import com.yourgame.model.Animals.*;
import com.yourgame.model.GameState;
import com.yourgame.model.Inventory.TrashCan;
import com.yourgame.model.Inventory.Tools.Axe;
import com.yourgame.model.Inventory.Tools.FishingPole;
import com.yourgame.model.Inventory.Tools.Hoe;
import com.yourgame.model.Inventory.Tools.MilkPail;
import com.yourgame.model.Inventory.Tools.Pickaxe;
import com.yourgame.model.Inventory.Tools.Scythe;
import com.yourgame.model.Inventory.Tools.Shear;
import com.yourgame.model.Inventory.Tools.Tool;
import com.yourgame.model.Inventory.Tools.WateringCan;
import com.yourgame.model.Item.*;
import com.yourgame.model.Item.Stone;
import com.yourgame.model.Item.Wood;
import com.yourgame.model.ManuFactor.ArtisanGood;
import com.yourgame.model.ManuFactor.ArtisanGoodType;
import com.yourgame.model.ManuFactor.ArtisanMachine;
import com.yourgame.model.ManuFactor.Bouquet;
import com.yourgame.model.ManuFactor.Ingredient;
import com.yourgame.model.Map.*;
import com.yourgame.model.Map.Map;
import com.yourgame.model.Recipes.CookingRecipe;
import com.yourgame.model.Recipes.CraftingRecipes;
import com.yourgame.model.Stores.Blacksmith;
import com.yourgame.model.Stores.CarpenterShop;
import com.yourgame.model.Stores.MarnieRanch;
import com.yourgame.model.Stores.Sellable;
import com.yourgame.model.UserInfo.GiftBetweenPlayers;
import com.yourgame.model.UserInfo.Coin;
import com.yourgame.model.UserInfo.PlayersDialogues;
import com.yourgame.model.UserInfo.PlayersFriendshipLevel;
import com.yourgame.model.UserInfo.Player;
import com.yourgame.model.UserInfo.RelationNetwork;
import com.yourgame.model.UserInfo.PlayersRelation;
import com.yourgame.model.UserInfo.User;
import com.yourgame.model.IO.Request;
import com.yourgame.model.IO.Response;
import com.yourgame.model.Map.Water.Lake;
import com.yourgame.model.WeatherAndTime.Season;
import com.yourgame.model.WeatherAndTime.Weather;
import com.yourgame.model.enums.Gender;
import com.yourgame.model.enums.SymbolType;
import com.yourgame.model.enums.Commands.MenuTypes;
import com.yourgame.model.notification.MarriageRequest;
import com.yourgame.model.notification.Notification;
import com.yourgame.view.ConsoleView;

import java.util.*;

import javax.naming.spi.DirStateFactory.Result;

import com.yourgame.controller.CommandParser;
import com.yourgame.model.App;
import com.yourgame.model.Command;

public class GameController {
    private GameState gameState;
    private ConsoleView consoleView;
    private boolean isRunning;

    public GameController() {
        User currentUser = App.getCurrentUser();
        if (currentUser == null || App.getGameState() == null) {
            throw new IllegalStateException(
                    "Cannot initialize GameController: No user logged in or game state available.");
        }
        this.gameState = App.getGameState();
        this.consoleView = new ConsoleView(System.out);
        this.isRunning = true;
    }

    public GameController(GameState gameState, ConsoleView consoleView) {
        this.gameState = gameState;
        this.consoleView = consoleView;
        this.isRunning = true;
    }

    public void startGame() {
        consoleView.displayWelcomeMessage();
        while (isRunning) {
            String userInput = getUserInput();
            processInput(userInput);
        }
    }

    private String getUserInput() {
        Scanner scanner = new Scanner(System.in);
        consoleView.promptForInput();
        return scanner.nextLine();
    }

    private void processInput(String input) {
        CommandParser commandParser = new CommandParser();
        Command command = commandParser.parse(input);

        if (command.isExitCommand()) {
            isRunning = false;
            consoleView.displayGoodbyeMessage();
        } else {
            // Delegate to the appropriate controller based on command
            // This part will be implemented as the game expands
        }
    }

    public Response getEnergy() {
        return new Response(true, "Your energy is " + gameState.getCurrentPlayer().getEnergy() + "/"
                + gameState.getCurrentPlayer().getMaxEnergy());
    }

    public Response setCurrentPlayerEnergy(Request request) {
        int energy = Integer.parseInt(request.body.get("value"));
        gameState.getCurrentPlayer().setEnergy(energy);
        return new Response(true, "Your energy is Setted" + gameState.getCurrentPlayer().getEnergy() + "/"
                + gameState.getCurrentPlayer().getMaxEnergy());
    }

    public Response setCurrentPlayerEnergyUnlimited() {
        gameState.getCurrentPlayer().setUnlimitedEnergy(true);
        return new Response(true, "Your energy is unlimited");
    }

    public Response NextTurn() {
        gameState.nextPlayerTurn();
        return new Response(true, "You are  playing as " + gameState.getCurrentPlayer().getUsername());

    }

    public Response getBuildGreenHouse() {
        HashMap<Ingredient, Integer> inventory = gameState.getCurrentPlayer().getBackpack().getIngredientQuantity();
        Ingredient coin = null, wood = null;

        for (Ingredient ing : inventory.keySet()) {
            if (ing instanceof Coin)
                coin = ing;
            if (ing instanceof Wood)
                wood = ing;
        }

        if (coin == null || inventory.get(coin) < 1000) {
            return new Response(false, "you don't have enough money");
        }
        if (wood == null || inventory.get(wood) < 500) {
            return new Response(false, "you don't have enough woods");
        }

        inventory.put(coin, inventory.get(coin) - 1000);
        inventory.put(wood, inventory.get(wood) - 500);
        gameState.getCurrentPlayer().getFarm().getGreenHouse().setBroken(false);
        return new Response(false, "greenhouse built successfully");
    }

    public Response getWalk(Request request) {
        // Obtain current player and destination.
        Player currentPlayer = gameState.getCurrentPlayer();
        int x = Integer.parseInt(request.body.get("x"));
        int y = Integer.parseInt(request.body.get("y"));

        List<Position> positions = new LinkedList<Position>();
        Response response = findPath(x, y, positions);
        if (response.getSuccessful()) {
            return walk(currentPlayer, x, y, positions);
        }
        return new Response(false, response.getMessage());
    }

    public Response walk(Player currentPlayer, int endX, int endY, List<Position> positions) {
        int energy = calculateEnergyBasedOnComplexFormula(positions);
        if (energy > currentPlayer.getEnergy()) {
            currentPlayer.faint();
            return new Response(false, "you are fainted");
        }
        Response response = currentPlayer.consumeEnergy(energy);
        if (response.getSuccessful()) {
            currentPlayer.getPosition().setX(endX);
            currentPlayer.getPosition().setY(endY);
            return new Response(true,
                    String.format(response.getMessage() + "you have been walked to " + endX + " " + endY));
        } else {
            return response;
        }
    }

    public int calculateEnergyBasedOnShortestDistance(List<Position> shortestPath) {
        int energy = 0;
        for (Position p : shortestPath) {
            energy++;
        }
        return (int) Math.ceil((double) energy / 20);
    }

    // Helper method: findPath using BFS with 8–directions.
    public Response findPath(int endX, int endY, List<Position> positions) {
        Tile destination = App.getGameState().getMap().findTile(endX, endY);

        if (App.getGameState().getMap().findTile(endX, endY).getPlaceable() != null) {
            return new Response(false, "Wrong place , Wrong Time");
        }
        for (Player p : App.getGameState().getPlayers()) {
            if (App.getGameState().getCurrentPlayer().equals(p)) {
                continue;
            }
            if (p.getFarm().getRectangle().contains(endX, endY)) {
                return new Response(false, "this position is not in your farm");
            }
        }

        List<Position> path = findShortestPath(App.getGameState().getMap(),
                App.getGameState().getCurrentPlayer().getPosition().getX(),
                App.getGameState().getCurrentPlayer().getPosition().getY(), endX, endY);

        if (path == null) {
            return new Response(false, "No path found");
        }

        positions.clear();
        positions.addAll(path);

        int energy = calculateEnergyBasedOnComplexFormula(positions);
        return new Response(true, String.format("energy needed: %d", energy));
    }

    public List<Position> findShortestPath(Map map, int startX, int startY, int endX, int endY) {
        int[] dx = { 1, -1, 0, 0 };
        int[] dy = { 0, 0, 1, -1 };
        boolean[][] visited = new boolean[250][200];
        Position[][] prev = new Position[250][200];
        Queue<Position> q = new LinkedList<>();
        q.add(new Position(startX, startY));
        visited[startX][startY] = true;
        while (!q.isEmpty()) {
            Position p = q.poll();
            if (p.getX() == endX && p.getY() == endY) {
                break;
            }
            for (int i = 0; i < 4; i++) {
                int nx = p.getX() + dx[i];
                int ny = p.getY() + dy[i];

                if (nx >= 0 && nx < 250 && ny >= 0 && ny < 200 && !visited[nx][ny]
                        && map.getTiles()[nx][ny].isWalkable()) {
                    visited[nx][ny] = true;
                    prev[nx][ny] = p;
                    q.add(new Position(nx, ny));
                }
            }

        }
        List<Position> shortestPath = new LinkedList<>();
        Position at = new Position(endX, endY);
        while (at != null && !(at.getX() == startX && at.getY() == startY)) {
            shortestPath.add(at);
            at = prev[at.getX()][at.getY()];
        }
        if (at == null)
            return null; // مسیر پیدا نشد
        shortestPath.add(new Position(startX, startY));
        Collections.reverse(shortestPath);
        return shortestPath;
    }

    // Check if a coordinate is within the bounds of the map.

    public Response handleShowInventory(Request request) {
        StringBuilder sb = new StringBuilder();
        for (java.util.Map.Entry<Ingredient, Integer> entry : gameState.getCurrentPlayer().getBackpack()
                .getIngredientQuantity().entrySet()) {
            sb.append(String.format("%s quantity : %d", entry.getKey().getClass().getSimpleName(), entry.getValue()));
            sb.append("\n");

        }
        return new Response(true, sb.toString());
    }

    public Response handleInventoryTrashing(Request request) {

        String name = request.body.get("itemName");
        String num = request.body.get("number");
        boolean hasNumber = false;

        if (num != null) {
            hasNumber = true;
        }
        int number = Integer.parseInt(num);

        for (java.util.Map.Entry<Ingredient, Integer> entry : App.getGameState().getCurrentPlayer().getBackpack()
                .getIngredientQuantity().entrySet()) {
            if (entry.getKey().getClass().getSimpleName().equals(name)) {
                if (hasNumber) {
                    App.getGameState().getCurrentPlayer().getBackpack().removeIngredients(entry.getKey(), number);
                    return new Response(true,
                            String.format("%s removed from backpack", entry.getKey().getClass().getSimpleName()));
                } else {
                    int quantity = entry.getValue();
                    App.getGameState().getCurrentPlayer().getBackpack().removeIngredients(entry.getKey(), quantity);
                    return new Response(true,
                            String.format("%s removed from backpack", entry.getKey().getClass().getSimpleName()));
                }

            }
        }
        return new Response(true, String.format("%s not found", name));
    }

    public Response getTime() {
        return new Response(true, String.format("current time is: %d", gameState.getGameTime().getHour()));
    }

    public Response getDate() {
        return new Response(true, String.format("current season is %s, in %d ",
                gameState.getGameTime().getSeason().name(), gameState.getGameTime().getDay()));
    }

    public Response getDateTime() {
        return new Response(true,
                String.format("Season : %s , Day : %d , Hour : %d", gameState.getGameTime().getSeason().name(),
                        gameState.getGameTime().getDay(), gameState.getGameTime().getHour()));
    }

    public Response getDayOfWeek() {
        return new Response(true, String.format("Day : %s ", gameState.getGameTime().getDayOfWeek().name()));

    }

    public Response getSeason() {
        return new Response(true, "Current Season is " + gameState.getGameTime().getSeason());
    }

    public Response getAdvancedDate(Request request) {
        int h = Integer.parseInt(request.body.get("amount"));
        if(h <= 0){
            return new Response(false , "day is less than 0");
        }
        App.getGameState().getGameTime().advancedDay(h);
        return new Response(true , "new advanced date is : " + App.getGameState().getGameTime().getDay());
    }

    public Response getAdvancedTime(Request request) {
        int h = Integer.parseInt(request.body.get("amount"));
        if(h <= 0){
            return new Response(false , "hour is less than 0");

        }
        App.getGameState().getGameTime().advancedHour(h);
        return new Response(true , String.format("new advanced hour is : %d", App.getGameState().getGameTime().getHour()));
    }

    public Response getWeather() {
        return new Response(true, "Current Weather is " + gameState.getGameTime().getWeather().getName());
    }

    public Response cheatWeather(Request request) {
        String weather = request.body.get("Type");
        Weather w;
        try {
            w = Weather.valueOf(weather.trim());

        } catch (Exception exception) {
            return new Response(false, "Invalid weather");
        }
        gameState.getGameTime().setNextDayWeather(w);
        return new Response(true, "Tomorrow weather : " + w.getName());
    }

    public Response cheatThor(Request request) {
        int X = Integer.parseInt(request.body.get("X"));
        int Y = Integer.parseInt(request.body.get("Y"));
        Tile tile = findTilePosition(X, Y);
        if (tile == null) {
            return new Response(true, "Tile not found");
        }
        if(tile.getPlaceable() instanceof Growable){
            tile.setPlaceable(null);
            tile.setSymbol(SymbolType.BurnedTree);
            App.getGameState().getCurrentPlayer().getBackpack().addIngredients(ForagingMineral.Coal, 1);
            tile.setFertilizer(null);
            tile.setPlowed(false);

        }
        tile.setGotThor(true);
        return new Response(false, "The tile with the postition : X:" + X + " Y: " + Y + " is under thor");
    }

    public Response getWeatherForecast() {
        return new Response(true, "Tomorrow Weather is " + gameState.getGameTime().getWeather().getName());
    }

    public Response handleToolsEquip(Request request) {
        String toolName = request.body.get("toolName");
        for (Tool t : App.getGameState().getCurrentPlayer().getBackpack().getTools()) {
            if (t.getClass().getSimpleName().toLowerCase().equals(toolName)) {
                App.getGameState().getCurrentPlayer().setCurrentTool(t);
                return new Response(true, t.getClass().getSimpleName() + " equipped");
            }
        }
        return new Response(false, toolName + " not found in Tools");
    }

    //
    public Response handleToolsShow(Request request) {
        return new Response(true,
                "Current Tool : " + App.getGameState().getCurrentPlayer().getCurrentTool().getClass().getSimpleName());
    }

    public Response handleToolsShowAvailable(Request request) {
        StringBuilder sb = new StringBuilder();
        for (Tool t : gameState.getCurrentPlayer().getBackpack().getTools()) {
            sb.append("Name: ").append(t.getClass().getSimpleName()).append("\n");
            sb.append(t.getClass().getSimpleName());
            sb.append("\n");
        }
        return new Response(true, "Available Tools : \n" + sb);
    }

    // public Response handleToolsUpgrade(Request request) {
    // //TODO
    // }

    // public Response handleToolsUseDirectionResponse(Request request) {
    // // TODO
    // return handleShowInventory(request);
    // }

    public Response craftInfo(Request request) {
        String craftName = request.body.get("craftName");
        CropType crop = CropType.getCropTypeByName(craftName);

        if (crop == null)
            return new Response(false, "Craft <" + craftName + "> not found");

        String output = String.format("Name: %s\n", crop.getName()) + String.format("Source: %s\n", crop.getSource())
                + String.format("Stages: %s\n", crop.getStages())
                + String.format("Total Harvest Time: %d\n", crop.getTotalHarvestTime())
                + String.format("One Time: %s\n", crop.isOneTime())
                + String.format("Regrowth Time: %d\n", crop.getRegrowthTime())
                + String.format("Base Sell Price: %d\n", crop.getBaseSellPrice())
                + String.format("Is Edible: %s\n", crop.isEdible())
                + String.format("Base Energy: %d\n", crop.getEnergy())
                + String.format("Season: %s\n", crop.getSeasons())
                + String.format("Can Become Giant: %s", crop.CanBecomeGiant());

        return new Response(true, output);
    }

    public void foragingRandom() {
        // this method should call everyday
        // TODO find an empty tile
        if (Math.random() <= 0.01) {
            ArrayList<ForagingCrop> foragingCrops = ForagingCrop
                    .getCropsBySeason(App.getGameState().getGameTime().getSeason());
            ForagingCrop foragingCrop = foragingCrops.get(new Random().nextInt(foragingCrops.size()));
            // TODO put it in backpack and in map
        }
    }

    public Response craftingShowRecipes(Request resquest) {
        Player player = App.getGameState().getCurrentPlayer();
        HashSet<CraftingRecipes> recipes = player.getBackpack().getCraftingRecipes();
        StringBuilder output = new StringBuilder();
        output.append("Crafting Recipes: \n");
        int counter = 1;
        for (CraftingRecipes recipe : recipes) {
            output.append(String.format("%-2d. %s\n", counter, recipe));
            counter++;
        }

        return new Response(true, output.toString());

    }

    public Response handleItemCrafting(Request request) {
        String itemName = request.body.get("itemName");
        CraftingRecipes recipe = CraftingRecipes.getRecipeByName(itemName);
        Player player = App.getGameState().getCurrentPlayer();

        if (recipe == null)
            return new Response(false, "Recipe <" + itemName + "> not found!");
        if (!player.getBackpack().containRecipe(recipe))
            return new Response(false, "You don't have <" + itemName + "> CraftingRecipe in your backpack!");
        if (!player.getBackpack().hasCapacity())
            return new Response(false, "You don't have enough space in backpack!");
        Response energyConsumptionResult = player.consumeEnergy(2);
        if (!energyConsumptionResult.getSuccessful())
            return energyConsumptionResult;

        HashMap<Ingredient, Integer> ingredients = recipe.getIngredients();
        for (Ingredient ingredient : ingredients.keySet()) {
            if (!(player.getBackpack().getIngredientQuantity().containsKey(ingredient) && player.getBackpack()
                    .getIngredientQuantity().getOrDefault(ingredient, 0) >= ingredients.get(ingredient))) {
                return new Response(false, "You don't have enough <" + ingredient + "> in your backpack!");
            }
            player.getBackpack().removeIngredients(ingredient, ingredients.get(ingredient));
        }

        ArtisanMachine artisanMachine = ArtisanMachine.getArtisanMachineByRecipe(recipe);
        if (artisanMachine != null)
            player.getBackpack().addArtisanMachine(artisanMachine);
        if (recipe.equals(CraftingRecipes.MysticTreeSeed))
            player.getBackpack().addIngredients(TreeSource.MysticTreeSeeds, 1);

        return new Response(true, "You craft <" + itemName + "> successfully!");
    }

    public Response handleAddItemCheat(Request request) {
        String count = request.body.get("count");
        int quantity = Integer.parseInt(count);
        String ItemName = request.body.get("itemName");
        Player player = App.getGameState().getCurrentPlayer();

        if (quantity <= 0)
            return new Response(false, "The quantity must be greater than zero!");
        if (!player.getBackpack().hasCapacity())
            return new Response(false,
                    "You don't have enough space in backpack!" + +player.getBackpack().getCapacity());
        CraftingRecipes craftingRecipe = CraftingRecipes.getRecipeByName(ItemName);
        ArtisanMachine machine;
        if (craftingRecipe != null) {
            if ((machine = ArtisanMachine.getArtisanMachineByRecipe(craftingRecipe)) != null) {
                player.getBackpack().addArtisanMachine(machine);
            } else if (craftingRecipe.equals(CraftingRecipes.MysticTreeSeed)) {
                player.getBackpack().addIngredients(TreeSource.MysticTreeSeeds, quantity);
            }
            player.getBackpack().addRecipe(craftingRecipe);
            return new Response(true, "You added<" + ItemName + "> successfully!");
        }

        CookingRecipe cookingRecipe = CookingRecipe.getRecipeByName(ItemName);
        if (cookingRecipe != null) {
            Food food = Food.getFoodByName(ItemName);
            player.getBackpack().addIngredients(food, quantity);
            player.getBackpack().addRecipe(cookingRecipe);
            return new Response(true, "You added<" + ItemName + "> successfully!");
        }

        CropType cropType = CropType.getCropTypeByName(ItemName);
        if (cropType != null) {
            player.getBackpack().addIngredients(new Crop(cropType, gameState.getGameTime(), null, 1, 1), quantity);
            return new Response(true, "You add <" + ItemName + "> successfully!");
        }

        FishType fishType = FishType.getFishTypeByName(ItemName);
        if (fishType != null) {
            player.getBackpack().addIngredients(new Fish(fishType, Quality.Regular), quantity);
            return new Response(true, "You add <" + ItemName + "> successfully!");
        }

        Seeds seeds = Seeds.getSeedByName(ItemName);
        if (seeds != null) {
            player.getBackpack().addIngredients(seeds, quantity);
            return new Response(true, "You added<" + ItemName + "> successfully!");
        }

        TreeSource treeSource = TreeSource.getTreeSourceByName(ItemName);
        if (treeSource != null) {
            player.getBackpack().addIngredients(treeSource, quantity);
            return new Response(true, "You added<" + ItemName + "> successfully!");
        }

        ArtisanGoodType artisanGoodType = ArtisanGoodType.getByName(ItemName);
        if (artisanGoodType != null) {
            player.getBackpack().addIngredients(new ArtisanGood(artisanGoodType), quantity);
            return new Response(true, "You added <" + ItemName + "> successfully!");
        }

        Tool tool = Tool.getToolByName(ItemName);
        if (tool != null) {
            for (int i = 0; i < quantity; i++)
                player.getBackpack().addTool(tool);
            return new Response(true, "You add <" + ItemName + "> successfully!");
        }
        Fruit fruit = Fruit.getByName(ItemName);
        if (fruit != null) {
            player.getBackpack().addIngredients(fruit, quantity);
            return new Response(true, "You added<" + ItemName + "> successfully!");
        }

        ForagingMineral foragingMineral = ForagingMineral.getByName(ItemName);
        if (foragingMineral != null) {
            player.getBackpack().addIngredients(foragingMineral, quantity);
            return new Response(true, "You added<" + ItemName + "> successfully!");
        }

        ForagingCrop foragingCrop = ForagingCrop.getByName(ItemName);
        if (foragingCrop != null) {
            player.getBackpack().addIngredients(foragingCrop, quantity);
            return new Response(true, "You added<" + ItemName + "> successfully!");
        }

        Fertilizer fertilizer = Fertilizer.getByName(ItemName);
        if (fertilizer != null) {
            player.getBackpack().addIngredients(fertilizer, quantity);
            return new Response(true, "You added<" + ItemName + "> successfully!");
        }

        AnimalGoodType animalGoodType = AnimalGoodType.getAnimalGoodTypeByName(ItemName);
        if (animalGoodType != null) {
            player.getBackpack().addIngredients(animalGoodType, quantity);
            return new Response(true, "You added<" + ItemName + "> successfully!");
        }
        if (ItemName.equalsIgnoreCase("hay")) {
            player.getBackpack().increaseHay(quantity);
            return new Response(true, "You add <" + ItemName + "> successfully!");
        }

        if (ItemName.equalsIgnoreCase("wood")) {
            player.getBackpack().addIngredients(new Wood(), quantity);
            return new Response(true, "You add <" + ItemName + "> successfully!");
        }

        if (ItemName.equalsIgnoreCase("stone")) {
            player.getBackpack().addIngredients(new Stone(), quantity);
            return new Response(true, "You add <" + ItemName + "> successfully!");
        }

        if (ItemName.equalsIgnoreCase("bouquet")) {
            player.getBackpack().addIngredients(new Bouquet(), quantity);
            return new Response(true, "You add <" + ItemName + "> successfully!");
        }

        return new Response(false, "There is no such Item!");
    }

    public Response cookingRefrigerator(Request request) {
        String action = request.body.get("action");
        String itemName = request.body.get("item");
        Food food = Food.getFoodByName(itemName);
        Player player = App.getGameState().getCurrentPlayer();
        Refrigerator refrigerator = player.getBackpack().getRefrigerator();

        if (food == null)
            return new Response(false, "Food <" + itemName + "> not found!");

        if (action.equals("put")) {
            if (!player.getBackpack().getIngredientQuantity().containsKey(food))
                return new Response(false, "You don't have this food in the backpack!");
            if (!player.getBackpack().getRefrigerator().hasCapacity())
                return new Response(false, "You don't have enough capacity in refrigerator!");
            player.getBackpack().removeIngredients(food, 1);
            refrigerator.addItem(food, 1);
            return new Response(true, "You put <" + itemName + "> successfully in refrigerator!");
        } else {
            if (!player.getBackpack().getRefrigerator().containFood(food))
                return new Response(false, "You don't have this food in the Refrigerator!");
            if (!player.getBackpack().hasCapacity())
                return new Response(false, "You don't have enough space in the Backpack!");
            player.getBackpack().getRefrigerator().removeItem(food, 1);
            player.getBackpack().addIngredients(food, 1);
            return new Response(true, "You pickUp <" + itemName + "> successfully!");
        }
    }

    public Response handleShowCookingRecipes(Request request) {
        Player player = App.getGameState().getCurrentPlayer();
        HashSet<CookingRecipe> recipes = player.getBackpack().getCookingRecipes();
        StringBuilder output = new StringBuilder();
        output.append("Cooking Recipes: \n");
        int counter = 1;
        for (CookingRecipe recipe : recipes) {
            output.append(String.format("%-2d. %s\n", counter, recipe));
            counter++;
        }
        return new Response(true, output.toString());

    }

    public Response handleCookingFood(Request request) {
        String ItemName = request.body.get("itemName");
        CookingRecipe recipe = CookingRecipe.getRecipeByName(ItemName);
        Player player = gameState.getCurrentPlayer();

        if (recipe == null) {
            return new Response(false, "Recipe <" + ItemName + "> not found!");
        }
        if (!player.getBackpack().containRecipe(recipe)) {
            return new Response(false, "You don't have <" + ItemName + "> CookingRecipe in your backpack!");
        }
        if (!player.getBackpack().hasCapacity()) {
            return new Response(false, "You don't have enough space in backpack!");
        }

        HashMap<Ingredient, Integer> requiredIngredients = recipe.getIngredients();

        for (Ingredient requiredIngredient : requiredIngredients.keySet()) {

            Ingredient ingredientInBackpack = getIngredient(requiredIngredient, player);

            if (ingredientInBackpack == null)
                return new Response(false, "You don't have any <" + requiredIngredient + "> in your backpack!");

            if (player.getBackpack().getIngredientQuantity().getOrDefault(ingredientInBackpack, 0) < requiredIngredients
                    .get(ingredientInBackpack)) {
                return new Response(false, "You don't have enough <" + requiredIngredient + "> in your backpack!");
            }

            player.getBackpack().removeIngredients(ingredientInBackpack, requiredIngredients.get(requiredIngredient));
        }

        Food food = Food.getFoodByName(recipe.name());
        player.getBackpack().addIngredients(food, 1);

        player.consumeEnergy(3);

        return new Response(true, "You cook <" + food + "> successfully!");
    }

    private Ingredient getIngredient(Ingredient requiredIngredient, Player player) {
        for (Ingredient myIngredient : player.getBackpack().getIngredientQuantity().keySet()) {
            if ((myIngredient instanceof Crop crop && crop.getType().equals(requiredIngredient))
                    || (myIngredient instanceof AnimalGood animalGood
                            && animalGood.getType().equals(requiredIngredient))
                    || (myIngredient instanceof Fish fish && fish.getType().equals(requiredIngredient))
                    || (myIngredient.equals(requiredIngredient))) {
                return myIngredient;
            }
        }
        return null;
    }

    public Response handleEating(Request request) {
        String foodName = request.body.get("foodName");
        Food food = Food.getFoodByName(foodName);
        Player player = App.getGameState().getCurrentPlayer();

        if (food == null)
            return new Response(false, "Food <" + foodName + "> not found!");
        if (player.getBackpack().getIngredientQuantity().containsKey(food)) {
            player.getBackpack().removeIngredients(food, 1);
            player.addEnergy(food.getEnergy());
            return new Response(true,
                    "You eat <" + food + "> successfully!And increased your energy " + food.getEnergy() + "!");
        } else
            return new Response(false, "You don't have Food <" + food + "> space in backpack!");
    }

    public Tile findTilePosition(int x, int y) {
        for (int i = 0; i < gameState.getMap().getTiles().length; i++) {
            for (int j = 0; j < gameState.getMap().getTiles().length; j++) {
                if (i == x && j == y) {
                    return gameState.getMap().getTiles()[i][j];
                }
            }
        }
        return null;
    }

    public Response printWholeMap() {
        String mapString = gameState.getMap().printTheWholeMap();
        return new Response(true, mapString);
    }

    public Response printMap(Request request) {

        int x = Integer.parseInt(request.body.get("x"));
        int y = Integer.parseInt(request.body.get("y"));
        int size = Integer.parseInt(request.body.get("size"));
        String mapString = gameState.getMap().printMap(x, y, size);
        return new Response(true, mapString);
    }

    public Response getHelpRedning() {
        String info = SymbolType.getSymbolsInfo();
        return new Response(true, info);

    }

    public Response showMyPostion() {
        Position pos = gameState.getCurrentPlayer().getPosition();
        return new Response(true, "You are in " + pos);

    }

    public Response getToolUpgrade(Request request) {
        Player player = gameState.getCurrentPlayer();
        ArrayList<Tool> tools = player.getBackpack().getTools();
        HashMap<Ingredient, Integer> ingredients = player.getBackpack().getIngredientQuantity();
        Map map = gameState.getMap();
        int price;
        String toolName = request.body.get("name").toLowerCase();
        if (!map.isAroundPlaceable(player, map.getNpcVillage().getBlacksmith())) {
            Blacksmith blacksmith = map.getNpcVillage().getBlacksmith();

            int bsX = blacksmith.getBounds().x - 1;
            int bsY = blacksmith.getBounds().y - 1;
            return new Response(false,
                    "you should be near blacksmith at  at (" + bsX + ", " + bsY + ") to upgrade your tool.");
        }

        if (!map.getNpcVillage().getBlacksmith().isOpen()) {
            return new Response(false, "this store is currently closed");
        }
        if (toolName.toLowerCase().equals("trashcan")) {

            TrashCan trashCan = player.getBackpack().getTrashCan();
            price = trashCan.getPriceForUpgrade();

            if (!haveEnoughCoins(price, player)) {
                return new Response(false, "you don't have enough coins,  You should at least have " + price);
            }

            switch (price) {
            case 0: {
                return new Response(false, "This tool is at the highest level");
            }
            case 1000: {
                if (ingredients.getOrDefault(new ArtisanGood(ArtisanGoodType.CopperBar), 0) < 5) {
                    return new Response(false, "you don't have enough Copper bar");
                }
                if (!map.getNpcVillage().getBlacksmith().canUpgradeTool("Copper Trash Can")) {
                    return new Response(false, "Insufficient remaining upgrades for today");
                }
                player.getBackpack().addIngredients(new ArtisanGood(ArtisanGoodType.CopperBar), -5);

                break;
            }
            case 2500: {
                if (ingredients.getOrDefault(new ArtisanGood(ArtisanGoodType.IronBar), 0) < 5) {
                    return new Response(false, "you don't have enough Iron bar");
                }
                if (!map.getNpcVillage().getBlacksmith().canUpgradeTool("Steel Trash Can")) {
                    return new Response(false, "Insufficient remaining upgrades for today");
                }
                player.getBackpack().addIngredients(new ArtisanGood(ArtisanGoodType.IronBar), -5);
                break;
            }
            case 5000: {
                if (ingredients.getOrDefault(new ArtisanGood(ArtisanGoodType.GoldBar), 0) < 5) {
                    return new Response(false, "you don't have enough Gold bar");
                }
                if (!map.getNpcVillage().getBlacksmith().canUpgradeTool("Gold Trash Can")) {
                    return new Response(false, "Insufficient remaining upgrades for today");
                }
                player.getBackpack().addIngredients(new ArtisanGood(ArtisanGoodType.GoldBar), -5);
                break;
            }
            case 12500: {
                if (ingredients.getOrDefault(new ArtisanGood(ArtisanGoodType.IridiumBar), 0) < 5) {
                    return new Response(false, "you don't have enough Iridium bar");
                }
                if (!map.getNpcVillage().getBlacksmith().canUpgradeTool("Iridium Trash Can")) {
                    return new Response(false, "Insufficient remaining upgrades for today");
                }
                player.getBackpack().addIngredients(new ArtisanGood(ArtisanGoodType.IridiumBar), -5);
                break;
            }

            }

            trashCan.upgradeTool();
            minusCoinForUpgradeTool(price, player);
            return new Response(true, toolName + " upgraded");

        }

        for (Tool t : tools) {

            if (t.getClass().getSimpleName().toLowerCase().equals(toolName)) {

                if (t instanceof FishingPole) {
                    return new Response(false, "you can't upgrade fishing pole");
                }

                if (t.getToolType() == null) {
                    return new Response(false, "you can't upgrade " + toolName);
                }

                price = t.getToolType().getPriceForUpgrade();
                if (!haveEnoughCoins(price, player)) {
                    return new Response(false, "you don't have enough coins you should have at least " + price);
                }

                switch (price) {
                case 0: {
                    return new Response(false, toolName + " is at the highest level");
                }
                case 2000: {
                    if (ingredients.getOrDefault(new ArtisanGood(ArtisanGoodType.CopperBar), 0) < 5) {
                        return new Response(false, "you don't have enough Copper bar");
                    }
                    if (!map.getNpcVillage().getBlacksmith().canUpgradeTool("Copper Tool")) {
                        return new Response(false, "Insufficient remaining upgrades for today");
                    }
                    player.getBackpack().addIngredients(new ArtisanGood(ArtisanGoodType.CopperBar), -5);

                    break;
                }
                case 5000: {
                    if (ingredients.getOrDefault(new ArtisanGood(ArtisanGoodType.IronBar), 0) < 5) {
                        return new Response(false, "you don't have enough Iron bar");
                    }
                    if (!map.getNpcVillage().getBlacksmith().canUpgradeTool("Steel Tool")) {
                        return new Response(false, "Insufficient remaining upgrades for today");
                    }
                    player.getBackpack().addIngredients(new ArtisanGood(ArtisanGoodType.IronBar), -5);
                    break;
                }
                case 10000: {
                    if (ingredients.getOrDefault(new ArtisanGood(ArtisanGoodType.GoldBar), 0) < 5) {
                        return new Response(false, "you don't have enough Gold bar");
                    }
                    if (!map.getNpcVillage().getBlacksmith().canUpgradeTool("Gold Tool")) {
                        return new Response(false, "Insufficient remaining upgrades for today");
                    }
                    player.getBackpack().addIngredients(new ArtisanGood(ArtisanGoodType.GoldBar), -5);
                    break;
                }
                case 25000: {
                    if (ingredients.getOrDefault(new ArtisanGood(ArtisanGoodType.IridiumBar), 0) < 5) {
                        return new Response(false, "you don't have enough Iridium bar");
                    }
                    if (!map.getNpcVillage().getBlacksmith().canUpgradeTool("Iridium Tool")) {
                        return new Response(false, "Insufficient remaining upgrades for today");
                    }
                    player.getBackpack().addIngredients(new ArtisanGood(ArtisanGoodType.IridiumBar), -5);
                    break;
                }

                }

                t.upgradeTool();
                minusCoinForUpgradeTool(price, player);
                return new Response(true, toolName + " upgraded");

            }

        }

        return new Response(false, toolName + " not found");

    }

    public void minusCoinForUpgradeTool(int price, Player player) {
        Coin coin = new Coin();
        int value = player.getBackpack().getIngredientQuantity().getOrDefault(coin, 0);
        player.getBackpack().getIngredientQuantity().put(coin, value - price);
    }

    private boolean haveEnoughCoins(int price, Player player) {
        int coinValue = player.getBackpack().getIngredientQuantity().getOrDefault(new Coin(), 0);
        return coinValue >= price;

    }

    public Response getAddDollars(Request request) {
        int amount = Integer.parseInt(request.body.get("amount"));
        gameState.getCurrentPlayer().getBackpack().addIngredients(new Coin(), amount);
        return new Response(true, amount + "g added");
    }

    public Response getUseTool(Request request) {
        String directionInput = request.body.get("direction");
        Direction direction = Direction.getDirectionByInput(directionInput);
        Player p = gameState.getCurrentPlayer();
        Tile tile = gameState.getMap().findTile(p.getPosition());
        Tool tool = p.getCurrentTool();

        if (direction == null) {
            return new Response(false, "Invalid direction");
        }
        Tile targetTile = gameState.getMap().getTileByDirection(tile, direction);

        if (tool instanceof Hoe hoe) {
            if (gameState.getMap().getTileByDirection(tile, direction) != null
                    && gameState.getMap().getTileByDirection(tile, direction).getPlaceable() == null) {
                Response energyConsumptionResult = hoe.useTool();
                if (!energyConsumptionResult.getSuccessful())
                    return energyConsumptionResult;
                gameState.getMap().getTileByDirection(tile, direction).setPlowed(true);
                return new Response(true, "tile plowed successfully!");
            } else
                return new Response(false, "You can't plow this tile with Hoe!");
        }
        if (tool instanceof Pickaxe pickaxe) {
            Response energyConsumptionResult = pickaxe.useTool();
            if (!energyConsumptionResult.getSuccessful())
                return energyConsumptionResult;

            if (targetTile.getPlaceable() != null) {
                if (targetTile.getPlaceable() instanceof Stone stone) {
                    targetTile.setPlaceable(null);
                    p.getFarm().getStones().remove(stone);
                    p.getFarm().getPlaceables().remove(stone);
                    targetTile.setSymbol(SymbolType.DefaultFloor);
                    p.getBackpack().addIngredients(stone, 1);
                    return new Response(true, "stone broken");

                } else if (targetTile.getPlaceable() instanceof Quarry quarry) {
                    Random rand = new Random();
                    if (!quarry.getForagingMinerals().isEmpty()) {

                        ForagingMineral fg = quarry.getForagingMinerals()
                                .get(rand.nextInt(quarry.getForagingMinerals().size()));
                        quarry.getForagingMinerals().remove(fg);
                        p.getBackpack().addIngredients(fg, 1);

                        return new Response(true, "you add a foraging mineral to the backpack");

                    }
                    return new Response(false, "this quarry is empty");
                }

            } else {
                targetTile.setPlowed(false);
                return new Response(true, "target tile unPlowed successfully!");
            }
        }
        if (tool instanceof Axe axe) {

            if (targetTile.getPlaceable() instanceof Tree tree) {
                Response energyConsumptionResponse = axe.useTool();
                if (!energyConsumptionResponse.getSuccessful())
                    return energyConsumptionResponse;
                p.getFarm().getTrees().remove(tree);
                p.getFarm().getPlaceables().remove(tree);
                targetTile.setPlaceable(null);
                targetTile.setSymbol(SymbolType.DefaultFloor);
                targetTile.setPlowed(false);
                targetTile.setFertilizer(null);
                targetTile.setWalkable(true);
                int numberOfWoods = tree.getCurrentStage();
                if (numberOfWoods > 0) {
                    Wood wood = new Wood();
                    p.getBackpack().addIngredients(wood, numberOfWoods);
                }
                p.getBackpack().addIngredients(tree.getType().getSource(), 2);
                return new Response(true, energyConsumptionResponse.getMessage() + "and Tree has been cutted :)");

            }

            return new Response(false, "there is no tree for cut!");
        }
        if (tool instanceof FishingPole fishingPole) {

            if (targetTile.getPlaceable() instanceof Lake lake) {
                Response energyConsumptionResponse = fishingPole.useTool();
                if (!energyConsumptionResponse.getSuccessful())
                    return energyConsumptionResponse;
                return fishing(fishingPole);
            }

            return new Response(false, "there is no lake for fishing!");
        }
        if (tool instanceof Scythe scythe) {

            if (targetTile.getPlaceable() instanceof Growable plant) {
                Response energyConsumptionResponse = scythe.useTool();
                if (!energyConsumptionResponse.getSuccessful())
                    return energyConsumptionResponse;
                return harvestWithScythe(plant, targetTile);
            }

            return new Response(false, "there is no tree or crop for harvest!");
        }
        if (tool instanceof WateringCan wateringCan) {

            if (targetTile.getPlaceable() instanceof Growable plant) {
                if (wateringCan.getWaterCapacity() <= 0) {
                    return new Response(false, "your wateringCan is empty");
                }
                Response energyConsumptionResponse = wateringCan.useTool();
                if (!energyConsumptionResponse.getSuccessful())
                    return energyConsumptionResponse;
                return waterPlantWithUseTool(plant);
            }
            if (targetTile.getPlaceable() instanceof Lake) {
                Response energyConsumptionResponse = wateringCan.useTool();
                if (!energyConsumptionResponse.getSuccessful())
                    return energyConsumptionResponse;
                wateringCan.makeFull();
            }

            return new Response(false, "there is no plant or lake!");

        }
        if (tool instanceof MilkPail || tool instanceof Shear) {
            return new Response(false, "please use command : collect produce -n <nameOfAnimal>");
        }

        return new Response(false, "you don't have a tool , please set your current tool, current player tool"
                + tool.getClass().getName());

    }

    private Response waterPlantWithUseTool(Growable plant) {
        plant.watering();
        return new Response(true, "You water this plant successfully!");

    }

    public Response treeInfo(Request request) {
        String treeName = request.body.get("treeName");
        TreeType tree = TreeType.getTreeTypeByName(treeName);

        if (tree == null)
            return new Response(false, "Tree <" + treeName + "> not found");
        String output =
                String.format("Name:               %s\n", tree.getName()) +
                        String.format("Source:             %s\n", tree.getSource()) +
                        String.format("Stages:             %s\n", tree.getStages()) +
                        String.format("Total Harvest Time: %d\n", tree.getTotalHarvestTime()) +
                        String.format("Fruit:              %s\n", tree.getFruit()) +
                        String.format("HarvestCycle:       %d\n", tree.getHarvestCycle()) +
                        String.format("FruitBaseSellPrice: %d\n", tree.getFruitBaseSellPrice()) +
                        String.format("IsFruitEdible:      %s\n", tree.isFruitEdible()) +
                        String.format("FruitEnergy:        %d\n", tree.getFruitEnergy()) +
                        String.format("Season:             %s\n", tree.getSeason());

        return new Response(true, output);
    }

    private Response harvestWithScythe(Growable plant, Tile targetTile) {
        Player player = gameState.getCurrentPlayer();
        if (!plant.isComplete())
            return new Response(false, "Plant hasn't grown completely!");

        if (plant.canGrowAgain()) {
            if (plant.harvest()) {
                if (plant instanceof Crop crop)
                    player.getBackpack().addIngredients(crop, 1);
                else if (plant instanceof Tree tree)
                    player.getBackpack().addIngredients(tree.getType().getFruit(), 1);

                player.getAbility().increaseFarmingRate(5);

                return new Response(true,
                        String.format("You picked up %s\nThis plant can grow again!", plant.getNameOfProduct()));
            } else {
                return new Response(false, "The plant hasn't grown again completely!");
            }
        } else {
            player.getBackpack().addIngredients(((Crop) plant), 1);
            player.getFarm().getCrops().removeIf(a -> a == plant);
            player.getFarm().getPlaceables().removeIf(a -> a == plant);
            targetTile.setPlaceable(null);
            targetTile.setWalkable(true);
            targetTile.setSymbol(SymbolType.DefaultFloor);
            targetTile.setPlowed(false);
            targetTile.setFertilizer(null);
            player.getAbility().increaseFarmingRate(5);
            return new Response(true,
                    String.format("You picked up %s\nThis plant cannot grow again!", plant.getNameOfProduct()));
        }
    }

    private Response fishing(FishingPole fishingPole) {
        Player player = gameState.getCurrentPlayer();
        int fishingLevel = player.getAbility().getFishingLevel();
        Weather weather = gameState.getGameTime().getWeather();
        Season season = gameState.getGameTime().getSeason();

        int numberOfFish = (int) Math.ceil(Math.random() * weather.getEffectivenessOnFishing() * (fishingLevel + 2));
        numberOfFish = Math.min(numberOfFish, 6);

        ArrayList<Fish> caughtFish = new ArrayList<>();
        ArrayList<FishType> availableFishType = FishType.getFishesBySeason(season, fishingLevel);

        for (int i = 0; i < numberOfFish; i++) {
            FishType fishType = availableFishType.get(new Random().nextInt(availableFishType.size()));
            double qualityValue = (Math.random() * (fishingLevel + 2) * fishingPole.getType().getEffectiveness())
                    / (7 - weather.getEffectivenessOnFishing());
            Quality quality = Quality.getQualityByValue(qualityValue);
            caughtFish.add(new Fish(fishType, quality));
        }

        StringBuilder output = new StringBuilder();
        output.append(String.format("Number of Fishes: %d\n", numberOfFish));
        for (Fish fish : caughtFish) {
            output.append("\t").append(fish.toString()).append("\n");
        }

        player.getAbility().increaseFishingRate(5);

        for (Fish fish : caughtFish) {
            player.getBackpack().addIngredients(fish, 1);
        }

        return new Response(true, output.toString());
    }

    public Response getWalk_FromHereAndShowMap(Request request) {
        // Obtain current player and destination.
        Response walkResponse;
        String gameMapString;
        Player currentPlayer = gameState.getCurrentPlayer();
        int x = Integer.parseInt(request.body.get("x"));
        int y = Integer.parseInt(request.body.get("y"));

        List<Position> positions = new LinkedList<Position>();
        Response response = findPath(currentPlayer.getPosition().getX() + x, currentPlayer.getPosition().getY() + y,
                positions);
        if (response.getSuccessful()) {
            walkResponse = walk(currentPlayer, currentPlayer.getPosition().getX() + x,
                    currentPlayer.getPosition().getY() + y, positions);
            return new Response(true, walkResponse.getMessage() + "\n" + printMapForCurrentPlayer());
        } else {
            return new Response(true, "Shirin cari is not possible");
        }

    }

    public String printMapForCurrentPlayer() {

        Player currentPlayer = gameState.getCurrentPlayer();
        int currX = currentPlayer.getPosition().getX();
        int currY = currentPlayer.getPosition().getY();
        if ((currX - 5 < 0) || (currY - 5 < 0)) {
            return gameState.getMap().printMap(currX, currY, 15);

        } else {
            return gameState.getMap().printMap(currX - 5, currY - 5, 15);
        }
    }

    public Response Plant(Request request) {
        String seedName = request.body.get("seed");
        String directionName = request.body.get("direction");
        Player player = gameState.getCurrentPlayer();
        Direction direction = Direction.getDirectionByInput(directionName);
        if (direction == null) {
            return new Response(false, "Direction " + directionName + " not found");
        }

        Tile myTile = gameState.getMap().findTile(player.getPosition());
        Tile targetTile = gameState.getMap().getTileByDirection(myTile, direction);

        if (targetTile == null) {
            return new Response(false, "Tile in direction <" + directionName + "> not found!");
        }
        if (targetTile.getPlaceable() != null) {
            return new Response(false, "You cannot plant in this tile!it isn't free.");
        }
        if (!targetTile.isPlowed()) {
            return new Response(false, "Tile is not plowed!");
        }

        Seeds seed = Seeds.getSeedByName(seedName);
        TreeSource treeSource = TreeSource.getTreeSourceByName(seedName);

        if (seed != null) {

            if (!player.getBackpack().getIngredientQuantity().containsKey(seed)) {
                return new Response(false, "You don't have this seed!");
            }

            if (!seed.getSeason().equals(Season.Special)
                    && !seed.getSeason().equals(gameState.getGameTime().getSeason())) {
                return new Response(false, "You can't plant this seed in this season!");
            }

            CropType cropType = plantCrop(seed);
            player.getBackpack().removeIngredients(seed, 1);
            Crop crop = new Crop(cropType, gameState.getGameTime(), targetTile.getFertilizer(),
                    targetTile.getPosition().getX(), targetTile.getPosition().getY());
            player.getFarm().getCrops().add(crop);
            player.getFarm().getPlaceables().add(crop);
            targetTile.setPlaceable(crop);
            targetTile.setWalkable(false);
            targetTile.setSymbol(crop.getSymbol());
            player.getAbility().increaseFarmingRate(5);
            return new Response(true, "You plant <" + crop.getType() + "> successfully!");

        } else if (treeSource != null) {

            if (!player.getBackpack().getIngredientQuantity().containsKey(treeSource))
                return new Response(false, "You don't have this seed!");

            if (!treeSource.getTreeType().getSeason().equals(gameState.getGameTime().getSeason()))
                return new Response(false, "You can't plant this tree in this season!");

            player.getBackpack().removeIngredients(treeSource, 1);
            Tree tree = new Tree(treeSource.getTreeType(), gameState.getGameTime(), targetTile.getFertilizer(),
                    targetTile.getPosition().getX(), targetTile.getPosition().getY(), 1, 1);
            player.getFarm().getTrees().add(tree);
            player.getFarm().getPlaceables().add(tree);
            targetTile.setPlaceable(tree);
            targetTile.setWalkable(false);
            targetTile.setSymbol(tree.getSymbol());
            player.getAbility().increaseFarmingRate(5);
            return new Response(true, "You successfully plant " + tree.getName());
        } else {
            return new Response(false, "Tree_source/seed not found");
        }

    }

    private CropType plantCrop(Seeds seed) {
        CropType cropType;

        if (seed.equals(Seeds.MixedSeeds)) {
            ArrayList<CropType> possibleCrops = MixedSeeds.getSeasonCrops(gameState.getGameTime().getSeason());
            cropType = possibleCrops.get(new Random().nextInt(possibleCrops.size()));
        } else {
            cropType = CropType.getCropForSeed(seed);
        }

        return cropType;
    }

    public Response handleFertilization(Request request) {
        String fertilizerName = request.body.get("fertilizer");
        String directionName = request.body.get("direction");
        Direction direction = Direction.getDirectionByInput(directionName);
        Fertilizer fertilizer = Fertilizer.getFertilizerByName(fertilizerName);
        Player player = gameState.getCurrentPlayer();
        Tile myTile = gameState.getMap().findTile(player.getPosition());
        Tile targetTile = gameState.getMap().getTileByDirection(myTile, direction);
        if (targetTile == null) {
            return new Response(false, "Tile in direction <" + directionName + "> not found!");
        }

        if (!targetTile.isPlowed())
            return new Response(false, "This tile hasn't plowed yet!");

        if (!player.getBackpack().getIngredientQuantity().containsKey(fertilizer)) {
            return new Response(false, "You don't have this fertilizer in the backpack!");
        }

        player.getBackpack().removeIngredients(fertilizer, 1);
        targetTile.setFertilizer(fertilizer);
        return new Response(true, "You fertilize this tile successfully!");
    }

    public Response handleShowPlant(Request request) {
        int x = Integer.parseInt(request.body.get("x"));
        int y = Integer.parseInt(request.body.get("y"));
        Tile tile = gameState.getMap().findTile(x, y);

        if (tile == null)
            return new Response(false, "Tile not found!");

        Placeable content = tile.getPlaceable();
        Growable plant;
        if (!(content instanceof Crop || content instanceof Tree)) {
            return new Response(false, "Here is no Plant!");
        } else {
            plant = ((Growable) content);
        }

        return new Response(true,
                String.format("Name:              %s\n", plant.getName())
                        + String.format("Days to complete:  %d\n", plant.getNumberOfDaysToComplete())
                        + String.format("Current stage:     %d\n", plant.getCurrentStage())
                        + String.format("Has Watered Today: %s\n", plant.hasWateredToday())
                        + String.format("Has Fertilized:    %s\n", plant.hasFertilized())
                        + String.format("Fertilizer:        %s", plant.getFertilizer()));
    }

    public Response build(Request request) {
        String buildingName = request.body.get("buildingName");
        int x = Integer.parseInt(request.body.get("X"));
        int y = Integer.parseInt(request.body.get("Y"));

        Player player = gameState.getCurrentPlayer();
        Map map = gameState.getMap();
        HabitatType habitatType = Habitat.getHabitatTypeByInput(buildingName);
        HabitatSize habitatSize = Habitat.getHabitatSizeByInput(buildingName);

        if (habitatType == null || habitatSize == null)
            return new Response(false, "Invalid building name or type");
        if (!map.isAroundPlaceable(player, map.getNpcVillage().getCarpenterShop())) {

            CarpenterShop carpenter = map.getNpcVillage().getCarpenterShop();

            int bsX = carpenter.getBounds().x - 1;
            int bsY = carpenter.getBounds().y - 1;
            return new Response(false,
                    "you should be near carpentershopt at  at (" + bsX + ", " + bsY + ") to upgrade your tool.");

        }

        for (int i = x; i < x + habitatType.getLengthX(); i++) {
            for (int j = y; j < y + habitatType.getLengthY(); j++) {
                Tile tile = map.findTile(i, j);
                if (tile == null)
                    return new Response(false, "Invalid tile");
                if (tile.getPlaceable() != null)
                    return new Response(false, "You cannot build in this area! The area is not empty!");
            }
        }

        Response storeResult = map.getNpcVillage().getCarpenterShop().purchaseBuilding(habitatType, habitatSize);
        if (!storeResult.getSuccessful())
            return storeResult;

        Habitat habitat = new Habitat(habitatType, habitatSize, x, y);

        for (int i = x; i < x + habitatType.getLengthX(); i++) {
            for (int j = y; j < y + habitatType.getLengthY(); j++) {
                Tile tile = map.findTile(i, j);
                tile.setPlaceable(habitat);
                tile.setWalkable(false);
                tile.setSymbol(habitat.getSymbol());
            }
        }

        player.getFarm().addHabitat(habitat);
        player.getFarm().getPlaceables().add(habitat);

        return new Response(true, "A <" + buildingName + "> was built successfully!");
    }

    public Response handleBuyAnimal(Request request) {
        String animalT = request.body.get("animal");
        String name = request.body.get("name");
        Player player = App.getGameState().getCurrentPlayer();
        Map map = App.getGameState().getMap();
        AnimalType animalType = AnimalType.getAnimalTypeByInput(animalT);

        if (player.getBackpack().getAnimalByName(name) != null)
            return new Response(false, "Animal with this name already exists! Please choose another name");
        if (animalType == null)
            return new Response(false, "Invalid animal type!");
        if (!map.isAroundPlaceable(player, map.getNpcVillage().getMarnieRanch())) {

            MarnieRanch marnieRanch = map.getNpcVillage().getMarnieRanch();

            int bsX = marnieRanch.getBounds().x - 1;
            int bsY = marnieRanch.getBounds().y - 1;
            return new Response(false,
                    "you should be near marineRench at  at (" + bsX + ", " + bsY + ") to upgrade your tool.");

        }

        Habitat habitat = null;
        for (Habitat habitat1 : player.getFarm().getHabitats()) {
            if (habitat1.getType().equals(animalType.getAnimalHabitat()) && habitat1.hasEmptyCapacity()) {
                habitat = habitat1;
                break;
            }
        }
        if (habitat == null)
            return new Response(false, "You don't have any enough capacity to buy this animal!");

        Response storeResult = map.getNpcVillage().getMarnieRanch().PurchaseAnimal(animalType);
        if (!storeResult.getSuccessful())
            return storeResult;

        Animal animal = new Animal(animalType, name, habitat);
        player.getBackpack().addAnimal(animal);
        habitat.addAnimal(animal);

        return new Response(true, "You buy a <" + animalType + "> with name <" + name + "> successfully!");
    }

    public Response getCurrentPlayerMapPosition() {
        String map = printMapForCurrentPlayer();
        return new Response(true, map);
    }

    public Response walkPlayersToHome() {
        StringBuilder output = new StringBuilder();

        for (Player p : gameState.getPlayers()) {
            int cottageX = p.getFarm().getCottage().getBounds().x;
            int cottageY = p.getFarm().getCottage().getBounds().y;
            System.out.println("their cottage is in x:" + cottageX + " and y:" + cottageY);
            List<Position> path = findShortestPath(gameState.getMap(), p.getPosition().getX(), p.getPosition().getY(),
                    cottageX, cottageY);

            System.out.println(path);
            if (path == null) {
                output.append(String.format("No path found for Player : %s\n", p.getUsername()));
                continue;
            }

            int energy = calculateEnergyBasedOnComplexFormula(path);
            if (p.getEnergy() >= energy) {
                p.getPosition().setX(cottageX);
                p.getPosition().setY(cottageY);
                p.consumeEnergy(energy);
                output.append(String.format("Player <%s> walked to its home.\n", p.getUsername()));
            } else {
                p.setFaintedToday(true);
                output.append(String.format("Player <%s> fainted while walking to home!\n", p.getUsername()));
            }
        }
        return new Response(true, output.toString());
    }

    public int calculateEnergyBasedOnComplexFormula(List<Position> positions) {
        if (positions.size() < 2)
            return 0;

        int numberOfTurns = 0;
        int prevDx = positions.get(1).getX() - positions.get(0).getX();
        int prevDy = positions.get(1).getY() - positions.get(0).getY();

        for (int i = 1; i < positions.size() - 1; i++) {
            int dx = positions.get(i + 1).getX() - positions.get(i).getX();
            int dy = positions.get(i + 1).getY() - positions.get(i).getY();

            if (dx != prevDx || dy != prevDy) {
                numberOfTurns++;
            }

            prevDx = dx;
            prevDy = dy;
        }

        int distance = positions.size();
        return (distance + 10 * numberOfTurns) / 20;
    }

    public Response getHowMuchWater() {
        for (Tool t : gameState.getCurrentPlayer().getBackpack().getTools()) {

            if (t.getClass().getSimpleName().toLowerCase().equals("wateringcan")) {
                int capacity = ((WateringCan) t).getWaterCapacity();
                return new Response(true, "Current WateringCan Capacity is " + capacity);
            }
        }

        return new Response(false, "Error happend While Searching for Capacity");

    }

    public Response getSellAnimal(Request request) {
        String animalName = request.body.get("name");
        Player player = gameState.getCurrentPlayer();
        Animal animal = player.getBackpack().getAnimalByName(animalName);

        if (animal == null) {
            return new Response(false, "Animal <" + animalName + "> not found!");
        }

        double price = animal.getType().getPrice() * (((double) (animal.getFriendShip()) / 1000) + 0.3);

        player.getBackpack().addIngredients(new Coin(), ((int) price));
        player.getBackpack().getAllAnimals().remove(animal);
        animal.getHabitat().getAnimals().remove(animal);

        return new Response(true, "You sell Animal <" + animalName + "> $" + price + "!");

    }

    public Response getPetAnimal(Request request) {
        String animalName = request.body.get("name");
        Player player = gameState.getCurrentPlayer();
        Animal animal = player.getBackpack().getAnimalByName(animalName);

        if (animal == null) {
            return new Response(false, "Animal <" + animalName + "> not found!");

        }

        animal.pet();

        return new Response(true, "You pet <" + animalName + "> successfully!");
    }

    public Response getCheatSetFriendShip(Request request) {
        String animalName = request.body.get("name");
        int amount = Integer.parseInt(request.body.get("amount"));
        Player player = gameState.getCurrentPlayer();
        Animal animal = player.getBackpack().getAnimalByName(animalName);

        if (animal == null) {
            return new Response(false, "Animal <" + animalName + "> not found!");
        }
        if (amount <= 0) {
            return new Response(false, "You can't set friendship to negative amount!");
        }

        animal.setFriendShip(amount);

        return new Response(true, "You set friendship with <" + animalName + "> to " + amount + "!");
    }

    public Response getAnimals() {
        Player player = gameState.getCurrentPlayer();
        ArrayList<Animal> animals = player.getBackpack().getAllAnimals();

        if (animals.isEmpty()) {
            return new Response(false, "No animals found!");
        }
        StringBuilder animalsInfo = new StringBuilder();
        animalsInfo.append("Animals:\n");
        for (int i = 0; i < animals.size(); i++) {
            animalsInfo.append(String.format("\t%-2d: \n", i + 1));
            animalsInfo.append(String.format("\t    Name: %s\n", animals.get(i).getName()));
            animalsInfo.append(String.format("\t    Type: %s\n", animals.get(i).getType()));
            animalsInfo.append(String.format("\t    LevelOfFriendship: %d\n", animals.get(i).getFriendShip()));
            animalsInfo.append(String.format("\t    hasPettedToday: %s\n", animals.get(i).hasPettedToday()));
            animalsInfo.append(String.format("\t    hasFedToday: %s\n", animals.get(i).hasFedToday()));
        }

        return new Response(true, animalsInfo.toString());

    }

    public Response getAnimalShepherd(Request request) {
        String animalName = request.body.get("name");
        int x = Integer.parseInt(request.body.get("x"));
        int y = Integer.parseInt(request.body.get("y"));
        Player player = gameState.getCurrentPlayer();
        Animal animal = player.getBackpack().getAnimalByName(animalName);
        Tile tile = gameState.getMap().findTile(x, y);

        if (animal == null) {
            return new Response(false, "Animal <" + animalName + "> not found!");
        }
        if (tile == null) {
            return new Response(false, "This position not found!");
        }
        if (tile.getPlaceable() != null) {
            return new Response(false, "This position is not free!");
        }

        if (animal.isOutOfHabitat()) {
            animal.goToHabitat();
            return new Response(true, "You put <" + animalName + "> in the habitat!");
        }

        if (!gameState.getGameTime().getWeather().equals(Weather.Sunny)) {
            return new Response(false, "Weather is not Sunny! you can't shepherd your animal!");
        }

        animal.shepherdAnimal();
        animal.feed();
        animal.incrementFriendShip(8);

        return new Response(true, "You shepherd your animal!");
    }

    public Response feedHey(Request request) {
        String animalName = request.body.get("name");
        Player player = gameState.getCurrentPlayer();
        Animal animal = player.getBackpack().getAnimalByName(animalName);

        if (animal == null) {
            return new Response(false, "Animal <" + animalName + "> not found!");
        }

        if (!player.getBackpack().hasEnoughHay(1)) {
            return new Response(false, "You don't have enough hay to feed animal!");
        }

        player.getBackpack().decreaseHay(1);
        animal.feed();
        animal.incrementFriendShip(4);

        return new Response(true, "You feed animal <" + animalName + "> successfully!");

    }

    public Response getProduces() {
        Player player = gameState.getCurrentPlayer();
        ArrayList<Animal> animals = player.getBackpack().getAllAnimals();
        if (animals.isEmpty())
            return new Response(false, "No animals found!");

        StringBuilder output = new StringBuilder();
        output.append("Animals that their products haven't been collected yet:\n");

        for (Animal animal : animals) {
            if (animal.isReadyProduct())
                output.append(String.format("%-20s (%-9s ->   %-25s\n", animal.getName(), animal.getType() + ")",
                        animal.getType().getAnimalGoods()));
        }

        return new Response(true, output.toString());

    }

    public Response getCollectProduces(Request request) {

        String animalName = request.body.get("name");
        Player player = gameState.getCurrentPlayer();
        Animal animal = player.getBackpack().getAnimalByName(animalName);

        if (animal == null) {
            return new Response(false, "Animal <" + animalName + "> not found!");
        }
        if (!animal.isReadyProduct()) {
            return new Response(false, "Product is not ready!");
        }

        Tool tool = player.getCurrentTool();
        if (tool == null) {
            return new Response(false, "you don't have a tool, please set your current tool!");
        }

        if (animal.getType().equals(AnimalType.Sheep)) {

            if (!(tool instanceof Shear shear))
                return new Response(false, "Your current tool is not Shear!");

            Response energyConsumptionResult = shear.useTool();
            if (!energyConsumptionResult.getSuccessful()) {
                return energyConsumptionResult;
            }
        } else if (animal.getType().equals(AnimalType.Cow) || animal.getType().equals(AnimalType.Goat)) {

            if (!(tool instanceof MilkPail milkPail)) {
                return new Response(false, "Your current tool is not MilkPail!");
            }

            Response energyConsumptionResult = milkPail.useTool();
            if (!energyConsumptionResult.getSuccessful()) {
                return energyConsumptionResult;
            }
        }

        AnimalGood animalGood = animal.getProduct();
        player.getBackpack().addIngredients(animalGood, 1);
        player.getAbility().increaseFarmingRate(5);
        animal.incrementFriendShip(5);

        return new Response(true,
                String.format("You collect %s with quality %s. Previous price: %s -> New Price: %s",
                        animalGood.getType(), animalGood.getQuality(), animalGood.getType().getPrice(),
                        animalGood.getSellPrice()));
    }

    public Response getArtistanGet(Request request) {
        String artisanName = request.body.get("name");
        Player player = gameState.getCurrentPlayer();
        ArtisanMachine artisanMachine = player.getBackpack().getArtisanMachineByName(artisanName);

        if (artisanMachine == null)
            return new Response(false, "Artisan Machine not found!");

        Response result = artisanMachine.isReady();
        if (result.getSuccessful()) {
            player.getBackpack().addIngredients(artisanMachine.get(), 1);
            artisanMachine.reset();
        }
        return result;
    }

    public Response getArtistanUse(Request request) {
        String artisanName = request.body.get("name");
        String itemName = request.body.get("itemName");
        Player player = gameState.getCurrentPlayer();
        ArtisanMachine artisanMachine = player.getBackpack().getArtisanMachineByName(artisanName);

        if (artisanMachine == null) {
            return new Response(false, "Artisan Machine not found!");
        }

        Response Response = artisanMachine.canUse(player, itemName);
        if (Response.getSuccessful()) {
            artisanMachine.use();
        }
        return Response;
    }

//    public Response goToStoreMenu() {
//        Map gameMap = App.getGameState().getMap();
//        if (gameMap.isAroundPlaceable(App.getGameState().getCurrentPlayer(), gameMap.getNpcVillage().getBlacksmith())) {
//
//            App.setCurrentMenu(MenuTypes.BlackSmithMenu);
//            return new Response(true, "Now you are in the blacksmith");
//
//        } else if (gameMap.isAroundPlaceable(App.getGameState().getCurrentPlayer(),
//                gameMap.getNpcVillage().getCarpenterShop())) {
//
//            App.setCurrentMenu(MenuTypes.CarpenterShopMenu);
//            return new Response(true, "Now you are in the carpenterShop");
//
//        } else if (gameMap.isAroundPlaceable(App.getGameState().getCurrentPlayer(),
//                gameMap.getNpcVillage().getMarnieRanch())) {
//
//            App.setCurrentMenu(MenuTypes.MarnieRanchMenu);
//            return new Response(true, "Now you are in the Marnie's Ranch");
//
//        } else if (gameMap.isAroundPlaceable(App.getGameState().getCurrentPlayer(),
//                gameMap.getNpcVillage().getJojaMart())) {
//
//            App.setCurrentMenu(MenuTypes.JojaMartMenu);
//            return new Response(true, "Now you are in the Joja Mart");
//
//        } else if (gameMap.isAroundPlaceable(App.getGameState().getCurrentPlayer(),
//                gameMap.getNpcVillage().getPierreGeneralStore())) {
//
//            App.setCurrentMenu(MenuTypes.PierreGeneralStoreMenu);
//            return new Response(true, "Now you are in the Pierre General Store");
//
//        } else if (gameMap.isAroundPlaceable(App.getGameState().getCurrentPlayer(),
//                gameMap.getNpcVillage().getFishShop())) {
//
//            App.setCurrentMenu(MenuTypes.FishShopMenu);
//            return new Response(true, "Now you are in the Fish Shop");
//
//        } else if (gameMap.isAroundPlaceable(App.getGameState().getCurrentPlayer(),
//                gameMap.getNpcVillage().getStardopSaloon())) {
//
//            App.setCurrentMenu(MenuTypes.StardopSaloonMenu);
//            return new Response(true, "Now you are in the Stardop Saloon");
//
//        } else {
//
//            return new Response(false, "you must be near a store");
//
//        }
//
//    }

    public Response getSellProduct(Request request) {
        int amount = Integer.parseInt(request.body.get("count"));
        String productName = request.body.get("name");

        ShippingBin temp = null;

        for (ShippingBin bin : gameState.getMap().getShippingBins()) {
            if (gameState.getMap().isAroundPlaceable(gameState.getCurrentPlayer(), bin)) {
                temp = bin;
                break;
            }
        }

        if (temp == null) {
            return new Response(false, "you must be near a shipping bin");
        }

        if (!Sellable.isSellable(productName)) {
            return new Response(false, "you can't sell this product");
        }

        if (gameState.getCurrentPlayer().getBackpack().getIngredientQuantity()
                .getOrDefault((Ingredient) Sellable.getSellableByName(productName), 0) < amount) {
            return new Response(false, "Not enough stock");
        }

        int price = amount * Sellable.getSellableByName(productName).getSellPrice();

        gameState.getCurrentPlayer().getBackpack()
                .removeIngredients((Ingredient) Sellable.getSellableByName(productName), amount);
        temp.increaseRevenue(gameState.getCurrentPlayer(), price);

        return new Response(true, "you have sold this product successfully");
    }

    public Response getFriendShips() {

        StringBuilder message = new StringBuilder("FriendShips:");

        for (Set<Player> key : gameState.getRelationsBetweenPlayers().relationNetwork.keySet()) {

            message.append("\n");

            Iterator<Player> iterator = key.iterator();
            Player p1 = iterator.next();
            Player p2 = iterator.next();

            message.append(p1.getUsername()).append(" and ").append(p2.getUsername()).append(":");
            message.append(gameState.getRelationsBetweenPlayers().relationNetwork.get(key).toString());

        }

        return new Response(true, message.toString());

    }

    public Response getTalk(Request request) {
        String username = request.body.get("name");
        String message = request.body.get("message");

        Player receiver = null;
        if (App.getGameState().getCurrentPlayer().getUsername().equals(username)) {
            return new Response(false, "Dude Ba Khodet Ke nemitoni HarfBezani");
        }

        for (Player p : App.getGameState().getPlayers()) {
            if (p.getUsername().equals(username)) {
                receiver = p;
                break;
            }
        }

        if (receiver == null) {
            return new Response(false, "Player not found");
        }

        int distanceSquare = (int) Math
                .sqrt(App.getGameState().getCurrentPlayer().getPosition().getX() - receiver.getPosition().getX());
        distanceSquare += (int) Math
                .sqrt(App.getGameState().getCurrentPlayer().getPosition().getY() - receiver.getPosition().getY());

        if (distanceSquare > 2) {
            return new Response(false, "You are too far away");
        }

        RelationNetwork tempNetwork = App.getGameState().getRelationsBetweenPlayers();
        Set<Player> lookUpKey = new HashSet<>();
        lookUpKey.add(receiver);
        lookUpKey.add(App.getGameState().getCurrentPlayer());

        PlayersRelation tempRelation = tempNetwork.relationNetwork.get(lookUpKey);
        if (!tempRelation.HaveTalkedToday()) {
            tempRelation.changeXp(20);
            tempRelation.setHaveTalkedToday(true);
        }

        if (tempRelation.isMarriage()) {
            App.getGameState().getCurrentPlayer().addEnergy(50);
            receiver.addEnergy(50);
        }

        tempRelation.addDialogue(new PlayersDialogues(App.getGameState().getCurrentPlayer(), receiver, message));
        tempNetwork.relationNetwork.put(lookUpKey, tempRelation);
        receiver.addNotification(new Notification(message, App.getGameState().getCurrentPlayer()));

        return new Response(true, "message Sent : )");
    }

    public Response talkHistory(Request request) {
        String username = request.body.get("name");
        if (App.getGameState().getCurrentPlayer().getUsername().equals(username)) {
            return new Response(false, "Dude Ba Khodet Ke nemitoni HarfBezani");
        }
        Player temp = null;

        for (Player p : App.getGameState().getPlayers()) {
            if (p.getUsername().equals(username)) {
                temp = p;
                break;
            }
        }

        if (temp == null) {
            return new Response(false, "Player not found");
        }

        RelationNetwork tempNetwork = App.getGameState().getRelationsBetweenPlayers();
        Set<Player> lookUpKey = new HashSet<>();
        lookUpKey.add(temp);
        lookUpKey.add(App.getGameState().getCurrentPlayer());
        PlayersRelation tempRelation = tempNetwork.relationNetwork.get(lookUpKey);

        String message = "Talking History:\n";
        message += tempRelation.getTalkHistory() + "\n";

        return new Response(true, message);

    }

    public Response GiftToUSer(Request request) {
        String username = request.body.get("name");
        String item = request.body.get("item");
        int amount = Integer.parseInt(request.body.get("amount"));

        if (App.getGameState().getCurrentPlayer().getUsername().equals(username)) {
            return new Response(false, "Dude Ba Khodet Ke nemitoni HarfBezani");
        }

        Player receiver = null;

        for (Player p : App.getGameState().getPlayers()) {
            if (p.getUsername().equals(username)) {
                receiver = p;
                break;
            }
        }

        if (receiver == null) {
            return new Response(false, "Player not found");
        }

        int distanceSquare = (int) Math
                .sqrt(App.getGameState().getCurrentPlayer().getPosition().getX() - receiver.getPosition().getX());
        distanceSquare += (int) Math
                .sqrt(App.getGameState().getCurrentPlayer().getPosition().getY() - receiver.getPosition().getY());

        if (distanceSquare > 2) {
            return new Response(false, "You are too far away");
        }

        RelationNetwork tempNetwork = App.getGameState().getRelationsBetweenPlayers();
        Set<Player> lookUpKey = new HashSet<>();
        lookUpKey.add(App.getGameState().getCurrentPlayer());
        lookUpKey.add(receiver);

        PlayersRelation tempRelation = tempNetwork.relationNetwork.get(lookUpKey);

        if (tempRelation.getFriendshipLevel().equals(PlayersFriendshipLevel.LevelZero)) {
            return new Response(false, "you can't gift this player at this friendship level");
        }

        if (!Sellable.isSellable(item)) {
            return new Response(false, "you can't gift this item");
        }

        if (Sellable.getSellableByName(item) == null) {
            return new Response(false, "Not enough stock");
        }

        if (App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity()
                .getOrDefault((Ingredient) Sellable.getSellableByName(item), 0) < amount) {
            return new Response(false, "Not enough stock");
        }

        App.getGameState().addGiftsIndex();
        GiftBetweenPlayers tempGift = new GiftBetweenPlayers(Sellable.getSellableByName(item),
                App.getGameState().getCurrentPlayer(), receiver, App.getGameState().getGiftIndex());
        App.getGameState().addToGifts(tempGift);

        App.getGameState().getCurrentPlayer().getBackpack()
                .removeIngredients((Ingredient) Sellable.getSellableByName(item), amount);
        receiver.getBackpack().addIngredients((Ingredient) Sellable.getSellableByName(item), amount);

        receiver.addNotification(new Notification("you have received a gift", App.getGameState().getCurrentPlayer()));

        if (tempRelation.isMarriage()) {
            App.getGameState().getCurrentPlayer().addEnergy(50);
            receiver.addEnergy(50);
        }

        return new Response(true, "He/She received your gift with id " + App.getGameState().getGiftIndex());

    }

    public Response getGiftList() {

        StringBuilder message = new StringBuilder("GiftList:");

        for (GiftBetweenPlayers gift : App.getGameState().getGifts()) {
            if (gift.getReceiver().equals(App.getGameState().getCurrentPlayer())) {
                message.append("\n");
                message.append(gift);
            }

        }
        return new Response(true, message.toString());
    }

    public Response getGiftRate(Request request) {
        int rate = Integer.parseInt(request.body.get("rate"));
        int id = Integer.parseInt(request.body.get("giftNumber"));
        GiftBetweenPlayers tempGift = null;

        if (rate <= 0 || id >= 6) {
            return new Response(false, "Invalid gift rate");
        }

        for (GiftBetweenPlayers gift : App.getGameState().getGifts()) {
            if (gift.getId() == id) {
                tempGift = gift;
                break;
            }
        }

        if (tempGift == null) {
            return new Response(false, "Gift not found");
        }

        if (!tempGift.getReceiver().equals(App.getGameState().getCurrentPlayer())) {
            return new Response(false, "you can't rate this gift");
        }

        if (tempGift.isRated()) {
            return new Response(false, "you have already rated this gift");
        }

        tempGift.setRate(rate);
        tempGift.setRated();

        RelationNetwork tempNetwork = App.getGameState().getRelationsBetweenPlayers();
        Set<Player> lookUpKey = new HashSet<>();
        lookUpKey.add(tempGift.getReceiver());
        lookUpKey.add(tempGift.getSender());

        PlayersRelation tempRelation = tempNetwork.relationNetwork.get(lookUpKey);
        if (!tempRelation.HaveGaveGiftToday()) {
            tempRelation.changeXp((rate - 3) * 30 + 15);
        }
        tempRelation.setHaveGaveGiftToday(true);
        tempNetwork.relationNetwork.put(lookUpKey, tempRelation);

        return new Response(true, "you rated this gift successfully");

    }

    public Response getGiftHistory(Request request) {

        String username = request.body.get("username");

        Player temp = null;

        for (Player p : App.getGameState().getPlayers()) {
            if (p.getUsername().equals(username)) {
                temp = p;
                break;
            }
        }

        if (temp == null) {
            return new Response(false, "Player not found");
        }

        if (temp.equals(App.getGameState().getCurrentPlayer())) {
            return new Response(false, "you can't choose yourself");
        }

        StringBuilder message = new StringBuilder("GiftHistory:");

        for (GiftBetweenPlayers gift : App.getGameState().getGifts()) {
            if (gift.getReceiver().equals(App.getGameState().getCurrentPlayer()) && gift.getSender().equals(temp)
                    || gift.getSender().equals(App.getGameState().getCurrentPlayer())
                            && gift.getReceiver().equals(temp)) {
                message.append("\n");
                message.append(gift.toStringWithReceiver());
            }
        }

        return new Response(true, message.toString());
    }

    public Response getHug(Request request) {
        String username = request.body.get("username");

        Player temp = null;

        if (App.getGameState().getCurrentPlayer().getUsername().equals(username)) {
            return new Response(false, "You can not Hug yourself.");
        }

        for (Player p : App.getGameState().getPlayers()) {
            if (p.getUsername().equals(username)) {
                temp = p;
                break;
            }
        }

        if (temp == null) {
            return new Response(false, "Player not found");
        }

        if (temp.equals(App.getGameState().getCurrentPlayer())) {
            return new Response(false, "you can't choose yourself");
        }

        int distanceSquare = (App.getGameState().getCurrentPlayer().getPosition().getX() - temp.getPosition().getX())
                * (App.getGameState().getCurrentPlayer().getPosition().getX() - temp.getPosition().getX())
                + (App.getGameState().getCurrentPlayer().getPosition().getY() - temp.getPosition().getY())
                        * (App.getGameState().getCurrentPlayer().getPosition().getY() - temp.getPosition().getY());

        if (distanceSquare > 2) {
            return new Response(false, "You are too far away");
        }

        RelationNetwork tempNetwork = App.getGameState().getRelationsBetweenPlayers();
        Set<Player> lookUpKey = new HashSet<>();
        lookUpKey.add(App.getGameState().getCurrentPlayer());
        lookUpKey.add(temp);

        PlayersRelation tempRelation = tempNetwork.relationNetwork.get(lookUpKey);

        if (tempRelation.canHug()) {
            if (!tempRelation.HaveHuggedToday()) {
                tempRelation.setHaveHuggedToday(true);
                tempRelation.changeXp(60);
            }

            if (tempRelation.isMarriage()) {
                App.getGameState().getCurrentPlayer().addEnergy(50);
                temp.addEnergy(50);
            }

            return new Response(true, "BAghaasllll ");
        }

        return new Response(false, "your friendship level must be at least two");

    }

    public Response getFlower(Request request) {
        String username = request.body.get("username");

        Player temp = null;

        if (App.getGameState().getCurrentPlayer().getUsername().equals(username)) {
            return new Response(false, "You can not give flower to yourself.");
        }

        for (Player p : App.getGameState().getPlayers()) {
            if (p.getUsername().equals(username)) {
                temp = p;
                break;
            }
        }

        if (temp == null) {
            return new Response(false, "Player not found");
        }

        if (temp.equals(App.getGameState().getCurrentPlayer())) {
            return new Response(false, "you can't choose yourself");
        }

        int distanceSquare = (App.getGameState().getCurrentPlayer().getPosition().getX() - temp.getPosition().getX())
                * (App.getGameState().getCurrentPlayer().getPosition().getX() - temp.getPosition().getX())
                + (App.getGameState().getCurrentPlayer().getPosition().getY() - temp.getPosition().getY())
                        * (App.getGameState().getCurrentPlayer().getPosition().getY() - temp.getPosition().getY());

        if (distanceSquare > 2) {
            return new Response(false, "You are too far away");
        }

        if (App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().getOrDefault(new Bouquet(),
                0) == 0) {
            return new Response(false, "You don't have Bouquet");
        }

        RelationNetwork tempNetwork = App.getGameState().getRelationsBetweenPlayers();
        Set<Player> lookUpKey = new HashSet<>();
        lookUpKey.add(App.getGameState().getCurrentPlayer());
        lookUpKey.add(temp);

        PlayersRelation tempRelation = tempNetwork.relationNetwork.get(lookUpKey);

        if (tempRelation.canGiveFlower()) {
            tempRelation.setGaveFlower();
            tempRelation.setHaveGaveFlowerToday(true);
            App.getGameState().getCurrentPlayer().getBackpack().removeIngredients(new Bouquet(), 1);
            temp.getBackpack().addIngredients(new Bouquet(), 1);
            if (tempRelation.isMarriage()) {
                App.getGameState().getCurrentPlayer().addEnergy(50);
                temp.addEnergy(50);
            }
            tempRelation.changeXp(0);
            return new Response(true, "You gave the flower.");
        }

        return new Response(false, "you Cannot give the flower to tihs person");

    }

    public Response getAskMarrige(Request request) {
        String username = request.body.get("username");
        String ringString = request.body.get("ring");

        if (App.getGameState().getCurrentPlayer().getUsername().equals(username)) {
            return new Response(false, "You can not propse to yourself.");
        }

        Player temp = null;

        for (Player p : App.getGameState().getPlayers()) {
            if (p.getUsername().equals(username)) {
                temp = p;
                break;
            }
        }

        if (temp == null) {
            return new Response(false, "Player not found");
        }

        if (temp.equals(App.getGameState().getCurrentPlayer())) {
            return new Response(false, "you can't choose yourself");
        }

        int distanceSquare = (App.getGameState().getCurrentPlayer().getPosition().getX() - temp.getPosition().getX())
                * (App.getGameState().getCurrentPlayer().getPosition().getX() - temp.getPosition().getX())
                + (App.getGameState().getCurrentPlayer().getPosition().getY() - temp.getPosition().getY())
                        * (App.getGameState().getCurrentPlayer().getPosition().getY() - temp.getPosition().getY());

        if (distanceSquare > 2) {
            return new Response(false, "You are too far away");
        }

        if (temp.isMarried()) {
            return new Response(false, "Married and Committed");
        }

        RelationNetwork tempNetwork = App.getGameState().getRelationsBetweenPlayers();
        Set<Player> lookUpKey = new HashSet<>();
        lookUpKey.add(App.getGameState().getCurrentPlayer());
        lookUpKey.add(temp);

        PlayersRelation tempRelation = tempNetwork.relationNetwork.get(lookUpKey);
        if (!tempRelation.canRequestMarriage()) {
            return new Response(false, "you can't request marriage in this friendship level");
        }

        temp.addNotification(
                new MarriageRequest("Mishe ba man ezdevaj konii azizam", App.getGameState().getCurrentPlayer()));

        return new Response(true, "Marriage requested");
    }

    public Response getResspondMarige(Request request) {
        String username = request.body.get("username");
        String state = request.body.get("state");


        MarriageRequest temp = null;

        for (Notification n : App.getGameState().getCurrentPlayer().getNotifications()) {

            if (n instanceof MarriageRequest) {
                if (!n.isChecked() && n.getSender().getUsername().equals(username)) {
                    temp = (MarriageRequest) n;
                    break;
                }
            }
        }

        if (temp == null) {
            return new Response(false, "No such marriage request");
        }

        temp.setChecked(true);

        RelationNetwork tempNetwork = App.getGameState().getRelationsBetweenPlayers();
        Set<Player> lookUpKey = new HashSet<>();
        lookUpKey.add(App.getGameState().getCurrentPlayer());
        lookUpKey.add(temp.getSender());

        PlayersRelation tempRelation = tempNetwork.relationNetwork.get(lookUpKey);

        if (state.equals("accept")) {

            App.getGameState().getCurrentPlayer().setMarried(true);
            temp.getSender().setMarried(true);

            tempRelation.setMarriage();
            tempRelation.setFriendshipLevel(PlayersFriendshipLevel.LevelFour);

            return new Response(true, "You accepted the marriage request");

        } else {

            tempRelation.setFriendshipLevel(PlayersFriendshipLevel.LevelZero);

            temp.getSender().setRemainingDaysAfterMarigDenied(7);
            return new Response(true, "You rejected the marriage request");
        }

    }

    public Response foragingTreeInfo(Request request) {
        String treeName = request.body.get("treeName");
        ForagingTreeSource foragingTreeSource = ForagingTreeSource.getForagingTreeSourceByName(treeName);

        if (foragingTreeSource == null)
            return new Response(false, "Foraging <" + treeName + "> not found");

        String output =
                String.format("Name:   %s\n", foragingTreeSource.getName()) +
                        String.format("Season: %s\n", foragingTreeSource.getSeason());

        return new Response(true, output);
    }

    public Response animalProduces() {
        Player player = App.getGameState().getCurrentPlayer();
        ArrayList<Animal> animals = player.getBackpack().getAllAnimals();

        if (animals.isEmpty())
            return new Response(false, "No animals found!");

        StringBuilder output = new StringBuilder();
        output.append("Animals that their products haven't been collected yet:\n");

        for (Animal animal : animals) {
            if (animal.isReadyProduct())
                output.append(String.format("%-20s (%-9s ->   %-25s\n",
                        animal.getName(), animal.getType() + ")", animal.getType().getAnimalGoods()));
        }

        return new Response(true, output.toString());
    }
}
