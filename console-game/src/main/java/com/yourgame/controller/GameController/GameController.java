package com.yourgame.controller.GameController;

import com.yourgame.model.Animals.AnimalGood;
import com.yourgame.model.Animals.Fish;
import com.yourgame.model.GameState;
import com.yourgame.model.Inventory.Tools.Tool;
import com.yourgame.model.Item.*;
import com.yourgame.model.ManuFactor.ArtisanMachine;
import com.yourgame.model.ManuFactor.Ingredient;
import com.yourgame.model.Map.Map;
import com.yourgame.model.Map.Position;
import com.yourgame.model.Recipes.CookingRecipe;
import com.yourgame.model.Recipes.CraftingRecipes;
import com.yourgame.model.UserInfo.Player;
import com.yourgame.model.UserInfo.User;
import com.yourgame.model.IO.Request;
import com.yourgame.model.IO.Response;
import com.yourgame.model.Map.Tile;
import com.yourgame.model.WeatherAndTime.Weather;
import com.yourgame.view.ConsoleView;

import java.util.*;

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
        // TODO Auto-generated method stub
        gameState.getCurrentPlayer().setUnlimitedEnergy(true);
        return new Response(true, "Your energy is unlimited");
    }

    public Response NextTurn() {
        gameState.nextPlayerTurn();
        return new Response(true, "You are  playing as " + gameState.getCurrentPlayer().getUsername());

    }

    public Response getBuildGreenHouse() {
        // TODO Auto-generated method stub
        return new Response(false, "To Do We need to create Map after that we can have green house");
    }


    public Response getWalk(Request request) {
        // Obtain current player and destination.
        Player currentPlayer = gameState.getCurrentPlayer();
        int x = Integer.parseInt(request.body.get("x"));
        int y = Integer.parseInt(request.body.get("y"));

        List<Position> positions = new LinkedList<Position>();
        Response response = findPath(x  ,y , positions);
        System.out.println(response.getMessage());
        if(response.getSuccessful()){
                return walk(x , y , positions);
        }
        return new Response(false, "");
    }
    public Response walk(int endX , int endY , List<Position> positions){
        int energy = calculateEnergyBasedOnShortestDistance(positions);
        if(energy > App.getGameState().getCurrentPlayer().getEnergy()){
            App.getGameState().getCurrentPlayer().faint();
            return new Response(false ,"you are fainted");
        }
        App.getGameState().getCurrentPlayer().getPosition().setX(endX);
        App.getGameState().getCurrentPlayer().getPosition().setY(endY);
        App.getGameState().getCurrentPlayer().consumeEnergy(energy);
        return new Response(true , String.format("you have teleported successfully to " + endX + " " + endY ));
    }

    public int calculateEnergyBasedOnShortestDistance(List<Position> shortestPath){
        int energy = 0;
        for(Position p : shortestPath){
            energy++;
        }
        return  energy / 20;
    }

    // Helper method: findPath using BFS with 8–directions.
    public Response findPath(int endX, int endY, List<Position> positions){
        Tile destination = App.getGameState().getMap().findTile(endX, endY);


        if(App.getGameState().getMap().findTile(endX , endY).getPlaceable() != null){
            return new Response(false ,"Wrong place , Wrong Time");
        }
        for(Player p : App.getGameState().getPlayers()){
            if(App.getGameState().getCurrentPlayer().equals(p)){
                continue;
            }
            if(p.getFarm().getRectangle().contains(endX, endY)){
                return new Response(false ,"this position is not in your farm");
            }
        }

        List<Position> path = findShortestPath(
                App.getGameState().getMap(),
                App.getGameState().getCurrentPlayer().getPosition().getX(),
                App.getGameState().getCurrentPlayer().getPosition().getY(),
                endX, endY
        );

        if(path == null){
            return new Response(false ,"No path found");
        }

        positions.clear();
        positions.addAll(path);

        int energy = calculateEnergyBasedOnShortestDistance(positions);
        return new Response(true, String.format("energy needed: %d", energy));
    }

    public List<Position> findShortestPath(Map map , int startX , int startY , int endX , int endY){
        int[] dx= {1, -1 , 0 , 0};
        int[] dy = {0 , 0 , 1 , -1};
        boolean[][] visited = new boolean[250][200];
        Position[][] prev = new Position[250][200];
        Queue<Position> q = new LinkedList<>();
        q.add(new Position(startX, startY));
        visited[startX][startY] = true;
        while(!q.isEmpty()){
            Position p = q.poll();
            if(p.getX() == endX && p.getY() == endY){
                break;
            }
            for(int  i = 0 ; i < 4 ; i++){
                int nx = p.getX() + dx[i];
                int ny = p.getY() + dy[i];

                if(nx >= 0 && nx < 250 && ny >= 0 && ny < 200 && !visited[nx][ny] && map.getTiles()[nx][ny].isWalkable()){
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
        if (at == null) return null; // مسیر پیدا نشد
        shortestPath.add(new Position(startX, startY));
        Collections.reverse(shortestPath);
        return shortestPath;
    }

    // Check if a coordinate is within the bounds of the map.

    public Response handleShowInventory(Request request) {
        StringBuilder sb = new StringBuilder();
        for (java.util.Map.Entry<Ingredient, Integer> entry : App.getGameState().getCurrentPlayer().getBackpack()
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
                    return new Response(true, String.format("%s removed from backpack", entry.getKey()
                            .getClass().getSimpleName()));
                } else {
                    int quantity = entry.getValue();
                    App.getGameState().getCurrentPlayer().getBackpack().removeIngredients(entry.getKey(), quantity);
                    return new Response(true, String.format("%s removed from backpack", entry.getKey()
                            .getClass().getSimpleName()));
                }

            }
        }
        return new Response(true, String.format("%s not found", name));
    }


    public Response getTime() {
        return new Response(true, String.format("current time is: %d", gameState.getGameTime().getHour()));
    }

    public Response getDate() {
        return new Response(true, String.format("current season is %s, in %d ", gameState.getGameTime().getSeason().name(), gameState.getGameTime().getDay()));
    }

    public Response getDateTime() {
        return new Response(true, String.format("Season : %s , Day : %d , Hour : %d", gameState.getGameTime()
                .getSeason().name(), gameState.getGameTime().getDay(), gameState.getGameTime().getHour()));
    }

    public Response getDayOfWeek() {
        // TODO Auto-generated method stub
        return new Response(true, String.format("Day : %s ", gameState.getGameTime().getDayOfWeek().name()));

    }

    public Response getSeason() {
        // TODO Auto-generated method stub
        return new Response(true, "Current Season is " + gameState.getGameTime().getSeason());
    }

    public Response getAdvancedDate(Request request) {
        // TODO Auto-generated method stub

        int amountOfDays = Integer.parseInt(request.body.get("amount"));

        gameState.getGameTime().advancedDay(amountOfDays);

        return new Response(true, "Time Traveling... (" + amountOfDays + "Days)");
    }

    public Response getAdvancedTime(Request request) {
        int amountOfHours = Integer.parseInt(request.body.get("amount"));

        gameState.getGameTime().advancedHour(amountOfHours);

        return new Response(true, "Time Traveling... (" + amountOfHours + " hours)");
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
        // TODO Auto-generated method stub
        return new Response(false, "To Do We need to create the Thor After Map ");
    }

    public Response getWeatherForecast() {
        return new Response(true, "Tomorrow Weather is " + gameState.getGameTime().getWeather().getName());
    }

    public Response handleToolsEquip(Request request) {
        String input = request.body.get("toolName");
        for (Tool t : App.getGameState().getCurrentPlayer().getBackpack().getTools()) {
            if (t.getClass().getSimpleName().equals(input)) {
                App.getGameState().getCurrentPlayer().setCurrentTool(t);
                return new Response(true, "Tool equipped");
            }
        }
        return new Response(false, "Tool not found");
    }

    //
    public Response handleToolsShow(Request request) {
        return new Response(true, "Current Tool : " + App.getGameState().getCurrentPlayer()
                .getCurrentTool().getClass().getSimpleName());
    }

    public Response handleToolsShowAvailable(Request request) {
        StringBuilder sb = new StringBuilder();
        for (Tool t : App.getGameState().getCurrentPlayer().getBackpack().getTools()) {
            sb.append(t.getClass().getSimpleName());
            sb.append("\n");

        }
        return new Response(true, "Available Tools : " + sb);
    }

//    public Response handleToolsUpgrade(Request request) {
//          //TODO
//    }

//    public Response handleToolsUseDirectionResponse(Request request) {
//        // TODO
//        return handleShowInventory(request);
//    }

    public Response craftInfo(Request request) {
        String craftName = request.body.get("craftName");
        CropType crop = CropType.getCropTypeByName(craftName);

        if (crop == null)
            return new Response(false, "Craft <" + craftName + "> not found");

        String output = String.format("Name: %s\n", crop.getName()) +
                String.format("Source: %s\n", crop.getSource()) +
                String.format("Stages: %s\n", crop.getStages()) +
                String.format("Total Harvest Time: %d\n", crop.getTotalHarvestTime()) +
                String.format("One Time: %s\n", crop.isOneTime()) +
                String.format("Regrowth Time: %d\n", crop.getRegrowthTime()) +
                String.format("Base Sell Price: %d\n", crop.getBaseSellPrice()) +
                String.format("Is Edible: %s\n", crop.isEdible()) +
                String.format("Base Energy: %d\n", crop.getEnergy()) +
                String.format("Season: %s\n", crop.getSeasons()) +
                String.format("Can Become Giant: %s", crop.CanBecomeGiant());

        return new Response(true, output);
    }
    //    public Response treeInfo(String treeName) {
//        TreeType tree = TreeType.getTreeTypeByName(treeName);
//
//        if (tree == null)
//            return new Response(false, "Tree <" + treeName + "> not found");
//        String output = String.format("Name: %s\n", tree.getName()) +
//                String.format("Source: %s\n", tree.getSource()) +
//                String.format("Stages: %s\n", tree.getStages()) +
//                String.format("Total Harvest Time: %d\n", tree.getTotalHarvestTime()) +
//                String.format("Fruit: %s\n", tree.getFruit()) +
//                String.format("HarvestCycle: %d\n", tree.getHarvestCycle()) +
//                String.format("FruitBaseSellPrice: %d\n", tree.getFruitBaseSellPrice()) +
//                String.format("IsFruitEdible: %s\n", tree.isFruitEdible()) +
//                String.format("FruitEnergy: %d\n", tree.getFruitEnergy()) +
//                String.format("Season: %s", tree.getSeason());
//
//        return new Response(true, output);
//    }
//
//    public Response foragingCropInfo(String cropName) {
//        ForagingCrop foragingCrop = ForagingCrop.getForagingCropByName(cropName);
//
//        if (foragingCrop == null)
//            return new Response(false, "Foraging <" + cropName + "> not found");
//
//        String output = String.format("Name: %s\n", foragingCrop.getName()) +
//                String.format("Season: %s\n", foragingCrop.getSeason()) +
//                String.format("BaseSellPrice: %d\n", foragingCrop.getBaseSellPrice()) +
//                String.format("Energy: %d", foragingCrop.getEnergy());
//
//        return new Response(true, output);
//    }
//
//    public Response foragingTreeInfo(String treeName) {
//        ForagingTreeSource foragingTreeSource = ForagingTreeSource.getForagingTreeSourceByName(treeName);
//
//        if (foragingTreeSource == null)
//            return new Response(false, "Foraging <" + treeName + "> not found");
//
//        String output = String.format("Name: %s\n", foragingTreeSource.getName()) +
//                String.format("Season: %s", foragingTreeSource.getSeason());
//
//        return new Response(true, output);
//    }


//public Response harvestWithScythe(Growable plant, Tile targetTile) {
//    Player player = App.getGameState().getCurrentPlayer();
//
//    if (!plant.isComplete())
//        return new Response(false, "Plant hasn't grown completely!");
//
//    if (plant.canGrowAgain()) {
//        if (plant.harvest()) {
//            if (plant instanceof Crop crop)
//                player.getBackpack().addIngredients(crop, 1);
//            else if (plant instanceof Tree tree)
//                player.getBackpack().addIngredients(tree.getType().getFruit(), 1);
//
//            player.getAbility().increaseFarmingRate(5);
//
//            return new Response(true,
//                    String.format("You picked up %s\nThis plant can grow again!", plant.getNameOfProduct()));
//        }
//        else {
//            return new Response(false, "The plant hasn't grown again completely!");
//        }
//    }
//    else {
//        player.getBackpack().addIngredients(((Crop) plant), 1);
//        player.getFarm().getCrops().remove(plant);
//        player.getFarm().getPlaceables().remove(plant);
//        targetTile.setPlaceable(null);
//        targetTile.setWalkable(true);
//        targetTile.setSymbol('.');
//        targetTile.setPlowed(false);
//        player.getAbility().increaseFarmingRate(5);
//        return new Result(true,
//                String.format("You picked up %s\nThis plant cannot grow again!", plant.getNameOfProduct()));
//    }
//}

    public void foragingRandom() {
        //this method should call everyday
        //TODO find an empty tile
        if (Math.random() <= 0.01) {
            ArrayList<ForagingCrop> foragingCrops = ForagingCrop.getCropsBySeason(App.getGameState().getGameTime().getSeason());
            ForagingCrop foragingCrop = foragingCrops.get(new Random().nextInt(foragingCrops.size()));
            //TODO put it in backpack and in map
        }
    }


//public Response plant(String seedName, String directionName) {
//    Direction direction = Direction.getDirectionByInput(directionName);
//    Player player = App.getGame().getCurrentPlayingPlayer();
//
//    if (direction == null) {
//        return new Result(false, "Direction <" + directionName + "> not found");
//    }
//    Tile myTile = App.getGame().getMap().findTile(player.getPosition());
//    Tile targetTile = App.getGame().getMap().getTileByDirection(myTile, direction);
//
//    if (targetTile == null) {
//        return new Result(false, "Tile in direction <" + directionName + "> not found!");
//    }
//    if (!targetTile.isPlowed()) {
//        return new Result(false, "Tile is not plowed!");
//    }
//
//    Seeds seed = Seeds.getSeedByName(seedName);
//    TreeSource treeSource = TreeSource.getTreeSourceByName(seedName);
//
//    if (seed != null) {
//
//        if (!seed.getSeason().equals(App.getGame().getTime().getSeason()))
//            return new Result(false, "You can't plant this seed in this season!");
//
//        CropType cropType = plantCrop(seed);
//        Crop crop = new Crop(cropType, App.getGame().getTime(), targetTile.getFertilizer(),
//                targetTile.getPosition().getX(), targetTile.getPosition().getY());
//        player.getFarm().getCrops().add(crop);
//        player.getFarm().getPlaceables().add(crop);
//        targetTile.setPlaceable(crop);
//        targetTile.setWalkable(false);
//        targetTile.setSymbol(crop.getSymbol());
//        player.getAbility().increaseFarmingRate(5);
//        return new Result(true, "You successfully plant.");
//
//    }
//    else if (treeSource != null) {
//
//        if (!treeSource.getTreeType().getSeason().equals(App.getGame().getTime().getSeason()))
//            return new Result(false, "You can't plant this tree in this season!");
//
//        Tree tree = new Tree(treeSource.getTreeType(), App.getGame().getTime(), targetTile.getFertilizer(),
//                targetTile.getPosition().getX(), targetTile.getPosition().getY(), 1, 1);
//        player.getFarm().getTrees().add(tree);
//        player.getFarm().getPlaceables().add(tree);
//        targetTile.setPlaceable(tree);
//        targetTile.setWalkable(false);
//        targetTile.setSymbol(tree.getSymbol());
//        player.getAbility().increaseFarmingRate(5);
//        return new Result(true, "You successfully plant.");
//
//    }
//    else {
//        return new Result(false, "Tree_source/seed not found");
//    }
//}

//private CropType plantCrop(Seeds seed) {
//    CropType cropType;
//
//    if (seed.equals(Seeds.MixedSeeds)) {
//        ArrayList<CropType> possibleCrops = MixedSeeds.getSeasonCrops(App.getGameState().getGameTime().getSeason());
//        cropType = possibleCrops.get(new Random().nextInt(possibleCrops.size()));
//    }
//    else
//        cropType = seed.getCrop();
//
//    return cropType;
//}
//
//public Result waterPlantWithUseTool(Growable plant) {
//    plant.watering();
//    return new Result(true, "You water this plant successfully!");
//}
//
//public Result fertilize(String fertilizerName, String directionName) {
//    Direction direction = Direction.getDirectionByInput(directionName);
//    Fertilizer fertilizer = Fertilizer.getFertilizerByName(fertilizerName);
//    Player player = App.getGame().getCurrentPlayingPlayer();
//    Tile myTile = App.getGame().getMap().findTile(player.getPosition());
//    Tile targetTile = App.getGame().getMap().getTileByDirection(myTile, direction);
//
//    if (!targetTile.isPlowed())
//        return new Result(false, "This tile hasn't plowed yet!");
//
//    if (!player.getBackpack().getIngredientQuantity().containsKey(fertilizer)) {
//        return new Result(false, "You don't have this fertilizer in the backpack!");
//    }
//
//    player.getBackpack().removeIngredients(fertilizer, 1);
//    targetTile.setFertilizer(fertilizer);
//    return new Result(true, "You fertilize this tile successfully!");
//}

    //public Response showPlant(int x, int y) {
//    Tile tile = App.getGameState().getMap().findTile(x, y);
//
//    if (tile == null)
//        return new Result(false, "Tile not found!");
//
//    Placeable content = tile.getPlaceable();
//    Growable plant;
//    if (!(content instanceof Crop || content instanceof Tree)) {
//        return new Result(false, "Here is no Plant!");
//    }
//    else
//        plant = ((Growable) content);
//
//    return new Result(true,
//            String.format("Name: %s\n", plant.getName()) +
//                    String.format("Days to complete: %d\n", plant.getNumberOfDaysToComplete()) +
//                    String.format("Current stage: %d\n", plant.getCurrentStage()) +
//                    String.format("Has watered Today: %s\n", plant.hasWateredToday()) +
//                    String.format("Has Fertilized: %s\n", plant.hasFertilized()) +
//                    String.format("Fertilizer: %s", plant.getFertilizer()));
//}

    public Response craftingShowRecipes(Request resquest) {
        Player player = App.getGameState().getCurrentPlayer();
        ArrayList<CraftingRecipes> recipes = player.getBackpack().getCraftingRecipes();
        StringBuilder output = new StringBuilder();
        output.append("Crafting Recipes: \n");
        for (int i = 0; i < recipes.size(); i++) {
            output.append(i + 1).append(". ").append(recipes.get(i).toString()).append("\n");
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
        if (player.getBackpack().hasCapacity())
            return new Response(false, "You don't have enough space in backpack!");

        HashMap<Ingredient, Integer> ingredients = recipe.getIngredients();
        for (Ingredient ingredient : ingredients.keySet()) {
            if (!(player.getBackpack().getIngredientQuantity().containsKey(ingredient) &&
                    player.getBackpack().getIngredientQuantity().get(ingredient) >= ingredients.get(ingredient))) {
                return new Response(false, "You don't have enough <" + ingredient + "> in your backpack!");
            }
            player.getBackpack().removeIngredients(ingredient, ingredients.get(ingredient));
        }

        ArtisanMachine artisanMachine = ArtisanMachine.getArtisanMachineByRecipe(recipe);
        if (artisanMachine != null)
            player.getBackpack().addArtisanMachine(artisanMachine);
        if (recipe.equals(CraftingRecipes.MysticTreeSeed))
            player.getBackpack().addIngredients(TreeSource.MysticTreeSeeds, 1);
        //TODO else

        player.consumeEnergy(2);

        return new Response(true, "You craft <" + itemName + "> successfully!");
    }

    public Response handleAddItemCheat(Request request) {
        String count = request.body.get("count");
        int quantity = Integer.parseInt(count);
        String ItemName = request.body.get("itemName");
        Player player = App.getGameState().getCurrentPlayer();

        if (quantity <= 0)
            return new Response(false, "The quantity must be greater than zero!");
        if (!player.getBackpack().hasCapacity(quantity))
            return new Response(false, "You don't have enough space in backpack!");
        CraftingRecipes craftingRecipe = CraftingRecipes.getRecipeByName(ItemName);
        ArtisanMachine machine;
        if (craftingRecipe != null) {
            if ((machine = ArtisanMachine.getArtisanMachineByRecipe(craftingRecipe)) != null) {
                player.getBackpack().addArtisanMachine(machine);
            } else if (craftingRecipe.equals(CraftingRecipes.MysticTreeSeed)) {
                player.getBackpack().addIngredients(TreeSource.MysticTreeSeeds, quantity);
            }
            //TODO else
            return new Response(true, "You add <" + ItemName + "> successfully!");
        }
        CookingRecipe cookingRecipe = CookingRecipe.getRecipeByName(ItemName);
        if (cookingRecipe != null) {
            Food food = Food.getFoodByName(ItemName);
            player.getBackpack().addIngredients(food, quantity);
            return new Response(true, "You add <" + ItemName + "> successfully!");
        }
        //TODO else item
        return new Response(false, "There is no such Item!");
    }

    public Response cookingRefrigerator(Request request) {
        String action = request.body.get("action");
        String itemName = request.body.get("itemName");
        Food food = Food.getFoodByName(itemName);
        Player player = App.getGameState().getCurrentPlayer();
        Refrigerator refrigerator = player.getBackpack().getRefrigerator();

        if (food == null)
            return new Response(false, "Food <" + itemName + "> not found!");

        if (action.equals("put")) {
            if (!player.getBackpack().getIngredientQuantity().containsKey(food))
                return new Response(false, "You don't have this food in the backpack!");
            player.getBackpack().removeIngredients(food, 1);
            refrigerator.addItem(food, 1);
            return new Response(true, "You put <" + itemName + "> successfully in refrigerator!");
        }
        else {
            if (!(player.getBackpack().getRefrigerator().getQuantity(food) == 0))
                return new Response(false, "You don't have this food in the Refrigerator!");
            player.getBackpack().getRefrigerator().removeItem(food, 1);
            player.getBackpack().addIngredients(food, 1);
            return new Response(true, "You pickUp <" + itemName + "> successfully!");
        }
    }

    public Response handleShowCookingRecipes(Request request) {
        Player player = App.getGameState().getCurrentPlayer();
        ArrayList<CookingRecipe> recipes = player.getBackpack().getCookingRecipes();
        StringBuilder output = new StringBuilder();
        output.append("Cooking Recipes: \n");
        for (int i = 0; i < recipes.size(); i++) {
            output.append(i+1).append(". ").append(recipes.get(i).toString()).append("\n");
        }
        return new Response(true, output.toString());
    }

    public Response handleCookingFood(Request request) {
        String ItemName = request.body.get("itemName");
        CookingRecipe recipe = CookingRecipe.getRecipeByName(ItemName);
        Player player = App.getGameState().getCurrentPlayer();

        if (recipe == null)
            return new Response(false, "Recipe <" + ItemName + "> not found!");
        if (!player.getBackpack().containRecipe(recipe))
            return new Response(false, "You don't have <" + ItemName + "> CookingRecipe in your backpack!");
        if (player.getBackpack().hasCapacity())
            return new Response(false, "You don't have enough space in backpack!");

        HashMap<Ingredient, Integer> requiredIngredients = recipe.getIngredients();

        for (Ingredient requiredIngredient : requiredIngredients.keySet()) {

            Ingredient ingredientInBackpack = getIngredient(requiredIngredient, player);

            if (ingredientInBackpack == null)
                return new Response(false, "You don't have any <" + requiredIngredient + "> in your backpack!");

            if (player.getBackpack().getIngredientQuantity().get(ingredientInBackpack) < requiredIngredients.get(ingredientInBackpack)) {
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
            if ((myIngredient instanceof Crop crop && crop.getType().equals(requiredIngredient)) ||
                    (myIngredient instanceof AnimalGood animalGood && animalGood.getType().equals(requiredIngredient)) ||
                    (myIngredient instanceof Fish fish && fish.getType().equals(requiredIngredient)) ||
                    (myIngredient.equals(requiredIngredient))) {
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
        }
        else
            return new Response(false, "You don't have Food <" + food + "> space in backpack!");
    }

}