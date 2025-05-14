package com.yourgame.controller.GameController;

import com.yourgame.model.App;
import com.yourgame.model.IO.Request;
import com.yourgame.model.IO.Response;
import com.yourgame.model.Item.CropType;
import com.yourgame.model.Item.ForagingCrop;

import java.util.ArrayList;
import java.util.Random;

public class ForagingController {

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
}
