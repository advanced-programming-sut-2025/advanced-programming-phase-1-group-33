package com.yourgame.view;

import java.util.Scanner;

import com.yourgame.controller.AppController.LoginMenuController;
import com.yourgame.controller.GameController.*;
import com.yourgame.model.App;
import com.yourgame.model.IO.Request;
import com.yourgame.model.IO.Response;
import com.yourgame.model.enums.Commands.LoginMenuCommands;
import com.yourgame.model.enums.Commands.MenuTypes;
import com.yourgame.model.enums.Commands.GameViewCommands;

import com.yourgame.view.AppViews.AppMenu;

public class GameMenu implements AppMenu {
    private GameController controller;

    public Response handleMenu(String input, Scanner scanner) {

        if (controller == null) {
            // Initialize only when game state exists
            if (App.getCurrentUser() == null || App.getGameState() == null) {
                App.setCurrentMenu(MenuTypes.LoginMenu);
                return new Response(false, "Game not initialized. Please start a new game.");
            }
            controller = new GameController();
        }

        GameViewCommands command = GameViewCommands.parse(input);
        if (command == null) {
            return getInvalidCommand();
        }
        switch (command) {
            case NEXT_TURN:
                return getNextTurn();
            case TIME:
                return getTime();
            case DATE:
                return getDate();
            case DATETIME:
                return getDateTime();
            case DAY_OF_WEEK:
                return getDayOfWeek();
            case SEASON:
                return getSeason();
            case CHEAT_ADVANCE_DATE:
                return getAdvancedDate(input);
            case CHEAT_ADVANCE_TIME:
                return getAdvancedTime(input);
            case CHEAT_THOR:
                return getCheatThor(input);
            case WEATHER:
                return getWeather();
            case CHEAT_WEATHER_SET:
                return getCheatWeather(input);
            case WEATHER_FORECAST:
                return getWeatherForcast();
            case GREEN_HOUSE_BUILD:
                return getBuildGreenHouse();
            case SHOW_MY_POSITION:
                return getMyPOsition();
            case WALK:
                return getWalk(input);
            case WALK_FROM_HERE_AND_SHOW_MAP:
                return getWalkFromHereAndShowMap(input);
            case PRINT_MAP:
                return getPrintMap(input);
            case PRINT_Whole_MAP:
                return getPrintWholeMap(input);
            case Print_map_for_current_player:
                return getPrintMapForCurrentPlayer();
            case HELP_READING_MAP:
                return getHelpRedning();
            case ENERGY_SHOW:
                return getEnergyShow();
            case ENERGY_SET_VALUE:
                return getEnergySet(input);
            case ENERGY_UNLIMITED:
                return getEnergyUnlimited();
            case Go_Back:
                App.setCurrentMenu(MenuTypes.MainMenu);
                return new Response(true, "Going Back to MainMenu");
            case EXIT_MENU:
                return getExitMenu(input);
            case ENTER_MENU:
                return getEnterMenu(input);
            case SHOW_MENU:
                return getShowMenu(input);
            case INVENTORY_SHOW:
                return getShowInventoryResponse(input);
            case INVENTORY_TRASH:
                return getInventoryTrashResponse(input);
            case TOOLS_EQUIP:
                return getToolsEquipResponse(input);
            case TOOLS_SHOW_CURRENT:
                return getToolsShowResponse(input);
            case TOOLS_SHOW_AVAILABLE:
                return getToolsShowAvailable(input);
            case TOOLS_USE_DIRECTION:
                return getToolsUseDirection(input);
            case TOOLS_UPGRADE:
                return getToolUpgradeResponse(input);
            case CHEAT_ADD_DOLLARS:
                return getAddDollars(input);
            case CRAFT_INFO:
                return showCraftInfo(input);
            case Plant:
                return getPlant(input);
            case CRAFTING_SHOW_RECIPES:
                return craftShowRecipes(input);
            case CRAFTING_CRAFT:
                return getCraftingResponse(input);
            case CHEAT_ADD_ITEM:
                return getAddItemCheatResponse(input);
            case COOKING_REFRIGERATOR_PICK_PUT:
                return getCookingRefrigeratorResponse(input);
            case COOKING_SHOW_RECIPES:
                return getShowCookingRecipesResponse(input);
            case COOKING_PREPARE:
                return getCookingPrepareResponse(input);
            case EAT:
                return getEatResponse(input);
            case FERTILIZE:
                return getFirtilizeResponse(input);
            case SHOWPLANT:
                return getShowPlantResponse(input);
            case HowMuchWater:
                return getHowMuchWater();
            case BUILD:
                return getbuildResponse(input);
            case BUYANIMAL:
                return getBuyAnimalResponse(input);
            case CHEAT_SET_FRIENDSHIP:
                return getCheatSetFriendShip(input);
            case ANIMALS:
                return getAnimals();
            case SHEPHERD:
                return getShepherd(input);
            case FEED_HAY:
                return getFeedHey(input);
            case PRODUCES:
                return getProduces();
            case COLLECT_PRODUCE:
                return getCollectProduces(input);
            case SELL_ANIMAL:
                return getSellAnimal(input);
            case PET:
                return getPetAnimal(input);
            case ARTISAN_USE:
                return getArtistanUse(input);
            case ARTISAN_GET:
                return getArtistanGet(input);
            case StoreMenu:
                return getGoToStoreMenu(input);
            case SELL_PRODUCT:
                return getSellProduct(input);
            case FRIENDSHIPS:
                return getFriendShips();
            case TALK:
                return getTalk(input);
            case TALK_HISTORY:
                return getTalkHistory(input);
            case GIFT:
                return getGift(input);
            case GIFT_LIST:
                return getGiftList();
            case GIFT_RATE:
                return getGiftRate(input);
            case GIFT_HISTORY:
                return getGiftHistory(input);
            case HUG:
                return getHug(input);
            case FLOWER:
                return getFlower(input);
            case ASK_MARRIAGE:
                return getAskMarrige(input);
            case RESPOND_MARRIAGE:
                return getRsponsedMarrige(input);
            case TreeInfo:
                return getTreeInfo(input);
            case ForagingTreeInfo:
                return getForagingTreeInfo(input);
            case AnimalsProduces:
                return getAnimalProduces();
            default:
                return getInvalidCommand();
        }
    }

    private Response getAnimalProduces() {
        return controller.animalProduces();
    }

    private Response getForagingTreeInfo(String input) {
        Request request = new Request(input);
        request.body.put("treeName", GameViewCommands.ForagingTreeInfo.getGroup(input, "treeName"));
        return controller.foragingTreeInfo(request);
    }

    private Response getTreeInfo(String input) {
        Request request = new Request(input);
        request.body.put("treeName", GameViewCommands.TreeInfo.getGroup(input, "treeName"));
        return controller.treeInfo(request);
    }

    private Response getRsponsedMarrige(String input) {
        Request request = new Request(input);
        request.body.put("username", GameViewCommands.RESPOND_MARRIAGE.getGroup(input, "username"));
        request.body.put("state", GameViewCommands.RESPOND_MARRIAGE.getGroup(input, "state"));
        return controller.getResspondMarige(request);

    }

    private Response getGiftHistory(String input) {
        Request request = new Request(input);
        request.body.put("username", GameViewCommands.GIFT_HISTORY.getGroup(input, "username"));
        return controller.getGiftHistory(request);

    }

    private Response getAskMarrige(String input) {

        Request request = new Request(input);
        request.body.put("username", GameViewCommands.ASK_MARRIAGE.getGroup(input, "username"));
        request.body.put("ring", GameViewCommands.ASK_MARRIAGE.getGroup(input, "ring"));
        return controller.getAskMarrige(request);
    }

    private Response getFlower(String input) {
        Request request = new Request(input);
        request.body.put("username", GameViewCommands.FLOWER.getGroup(input, "username"));

        return controller.getFlower(request);

    }

    private Response getHug(String input) {

        Request request = new Request(input);
        request.body.put("username", GameViewCommands.HUG.getGroup(input, "username"));

        return controller.getHug(request);
    }

    private Response getGiftRate(String input) {
        Request request = new Request(input);
        request.body.put("giftNumber", GameViewCommands.GIFT_RATE.getGroup(input, "giftNumber"));
        request.body.put("rate", GameViewCommands.GIFT_RATE.getGroup(input, "rate"));

        return controller.getGiftRate(request);
    }

    private Response getGiftList() {
        return controller.getGiftList();

    }

    private Response getGift(String input) {

        Request request = new Request(input);
        request.body.put("name", GameViewCommands.GIFT.getGroup(input, "username"));
        request.body.put("item", GameViewCommands.GIFT.getGroup(input, "item"));
        request.body.put("amount", GameViewCommands.GIFT.getGroup(input, "amount"));
        return controller.GiftToUSer(request);

    }

    private Response getTalkHistory(String input) {
        Request request = new Request(input);
        request.body.put("name", GameViewCommands.TALK_HISTORY.getGroup(input, "username"));
        return controller.talkHistory(request);
    }

    private Response getTalk(String input) {

        Request request = new Request(input);
        request.body.put("name", GameViewCommands.TALK.getGroup(input, "username"));
        request.body.put("message", GameViewCommands.TALK.getGroup(input, "message"));
        return controller.getTalk(request);
    }

    private Response getFriendShips() {
        return controller.getFriendShips();
    }

    private Response getSellProduct(String input) {
        Request request = new Request(input);
        request.body.put("name", GameViewCommands.SELL_PRODUCT.getGroup(input, "productName"));
        request.body.put("count", GameViewCommands.SELL_PRODUCT.getGroup(input, "count"));
        return controller.getSellProduct(request);
    }

    private Response getGoToStoreMenu(String input) {
        return controller.goToStoreMenu();
    }

    private Response getArtistanGet(String input) {
        Request request = new Request(input);
        request.body.put("name", GameViewCommands.ARTISAN_GET.getGroup(input, "artisanName"));
        return controller.getArtistanGet(request);
    }

    private Response getArtistanUse(String input) {
        Request request = new Request(input);
        request.body.put("name", GameViewCommands.ARTISAN_USE.getGroup(input, "artisanName"));
        request.body.put("item1Name", GameViewCommands.ARTISAN_USE.getGroup(input, "item1Name"));
        return controller.getArtistanUse(request);
    }

    private Response getPetAnimal(String input) {
        Request request = new Request(input);
        request.body.put("name", GameViewCommands.PET.getGroup(input, "name"));
        return controller.getPetAnimal(request);

    }

    private Response getSellAnimal(String input) {
        Request request = new Request(input);
        request.body.put("name", GameViewCommands.SELL_ANIMAL.getGroup(input, "name"));
        return controller.getSellAnimal(request);
    }

    private Response getCollectProduces(String input) {
        Request request = new Request(input);
        request.body.put("name", GameViewCommands.COLLECT_PRODUCE.getGroup(input, "name"));
        return controller.getCollectProduces(request);

    }

    private Response getProduces() {
        return controller.getProduces();
    }

    private Response getFeedHey(String input) {
        Request request = new Request(input);
        request.body.put("name", GameViewCommands.FEED_HAY.getGroup(input, "animalName"));
        return controller.feedHey(request);
    }

    private Response getShepherd(String input) {
        Request request = new Request(input);
        request.body.put("name", GameViewCommands.SHEPHERD.getGroup(input, "animalName"));
        request.body.put("x", GameViewCommands.SHEPHERD.getGroup(input, "x"));
        request.body.put("y", GameViewCommands.SHEPHERD.getGroup(input, "y"));
        return controller.getAnimalShepherd(request);
    }

    private Response getAnimals() {
        return controller.getAnimals();
    }

    private Response getCheatSetFriendShip(String input) {
        Request request = new Request(input);
        request.body.put("name", GameViewCommands.CHEAT_SET_FRIENDSHIP.getGroup(input, "name"));
        request.body.put("amount", GameViewCommands.CHEAT_SET_FRIENDSHIP.getGroup(input, "amount"));
        return controller.getCheatSetFriendShip(request);
    }

    private Response getHowMuchWater() {
        return controller.getHowMuchWater();

    }

    private Response getPrintMapForCurrentPlayer() {
        return controller.getCurrentPlayerMapPosition();
    }

    private Response getBuyAnimalResponse(String input) {
        Request request = new Request(input);
        request.body.put("animal", GameViewCommands.BUYANIMAL.getGroup(input, "animal"));
        request.body.put("name", GameViewCommands.BUYANIMAL.getGroup(input, "name"));
        return controller.handleBuyAnimal(request);
    }

    private Response getbuildResponse(String input) {
        Request request = new Request(input);
        request.body.put("buildingName", GameViewCommands.BUILD.getGroup(input, "buildingName"));
        request.body.put("X", GameViewCommands.BUILD.getGroup(input, "X"));
        request.body.put("Y", GameViewCommands.BUILD.getGroup(input, "Y"));
        return controller.build(request);
    }

    private Response getShowPlantResponse(String input) {
        Response response;
        Request request = new Request(input);
        request.body.put("x", GameViewCommands.SHOWPLANT.getGroup(input, "X"));
        request.body.put("y", GameViewCommands.SHOWPLANT.getGroup(input, "Y"));
        response = controller.handleShowPlant(request);
        return response;
    }

    private Response getFirtilizeResponse(String input) {
        Response response;
        Request request = new Request(input);
        request.body.put("fertilizer", GameViewCommands.FERTILIZE.getGroup(input, "fertilizer"));
        request.body.put("direction", GameViewCommands.FERTILIZE.getGroup(input, "direction"));
        response = controller.handleFertilization(request);
        return response;
    }

    private Response getPlant(String input) {
        Request request = new Request(input);
        request.body.put("seed", GameViewCommands.Plant.getGroup(input, "seed"));
        request.body.put("direction", GameViewCommands.Plant.getGroup(input, "direction"));
        return controller.Plant(request);
    }

    private Response getWalkFromHereAndShowMap(String input) {
        Request request = new Request(input);
        request.body.put("x", GameViewCommands.WALK_FROM_HERE_AND_SHOW_MAP.getGroup(input, "x"));
        request.body.put("y", GameViewCommands.WALK_FROM_HERE_AND_SHOW_MAP.getGroup(input, "y"));
        return controller.getWalk_FromHereAndShowMap(request);
    }

    private Response getToolsUseDirection(String input) {
        Request request = new Request(input);
        request.body.put("direction", GameViewCommands.TOOLS_USE_DIRECTION.getGroup(input, "direction"));

        return controller.getUseTool(request);
    }

    private Response getAddDollars(String input) {
        Request request = new Request(input);
        request.body.put("amount", GameViewCommands.CHEAT_ADD_DOLLARS.getGroup(input, "amount"));

        return controller.getAddDollars(request);
    }

    private Response getToolUpgradeResponse(String input) {
        Request request = new Request(input);
        request.body.put("name", GameViewCommands.TOOLS_UPGRADE.getGroup(input, "name"));

        return controller.getToolUpgrade(request);
    }

    private Response getMyPOsition() {
        return controller.showMyPostion();
    }

    private Response getEatResponse(String input) {
        Response response;
        Request request = new Request(input);
        request.body.put("foodName", GameViewCommands.EAT.getGroup(input, "foodName"));
        response = controller.handleEating(request);
        return response;
    }

    private Response getCookingPrepareResponse(String input) {
        Request request = new Request(input);
        request.body.put("itemName", GameViewCommands.COOKING_PREPARE.getGroup(input, "itemName"));
        return controller.handleCookingFood(request);
    }

    private Response getShowCookingRecipesResponse(String input) {
        Response response;
        Request request = new Request(input);
        response = controller.handleShowCookingRecipes(request);
        return response;
    }

    private Response getCookingRefrigeratorResponse(String input) {
        Response response;
        Request request = new Request(input);
        request.body.put("item", GameViewCommands.COOKING_REFRIGERATOR_PICK_PUT.getGroup(input, "item"));
        request.body.put("action", GameViewCommands.COOKING_REFRIGERATOR_PICK_PUT.getGroup(input, "action"));
        response = controller.cookingRefrigerator(request);
        return response;
    }

    private Response getAddItemCheatResponse(String input) {
        Response response;
        Request request = new Request(input);
        request.body.put("itemName", GameViewCommands.CHEAT_ADD_ITEM.getGroup(input, "itemName"));
        request.body.put("count", GameViewCommands.CHEAT_ADD_ITEM.getGroup(input, "count"));
        response = controller.handleAddItemCheat(request);
        return response;
    }

    private Response getCraftingResponse(String input) {
        Response response;
        Request request = new Request(input);
        request.body.put("itemName", GameViewCommands.CRAFTING_CRAFT.getGroup(input, "itemName"));
        response = controller.handleItemCrafting(request);
        return response;
    }

    private Response craftShowRecipes(String input) {
        Response response;
        Request request = new Request(input);
        response = controller.craftingShowRecipes(request);
        return response;
    }

    private Response showCraftInfo(String input) {
        Response response;
        Request request = new Request(input);
        request.body.put("craftName", GameViewCommands.CRAFT_INFO.getGroup(input, "craftName"));
        response = controller.craftInfo(request);
        return response;
    }

    // private Response getToolUpgradeResponse(String input) {
    // Response response;
    // Request request = new Request(input);
    // request.body.put("name", GameViewCommands.TOOLS_EQUIP.getGroup(input,
    // "name"));
    // response = controller.handleToolsUpgrade(request);
    // return response;
    // }

    private Response getHelpRedning() {
        return controller.getHelpRedning();
    }

    private Response getPrintMap(String input) {
        Request request = new Request(input);
        request.body.put("x", GameViewCommands.PRINT_MAP.getGroup(input, "x"));
        request.body.put("y", GameViewCommands.PRINT_MAP.getGroup(input, "y"));
        request.body.put("size", GameViewCommands.PRINT_MAP.getGroup(input, "size"));
        return controller.printMap(request);
    }

    private Response getPrintWholeMap(String input) {
        return controller.printWholeMap();
    }

    private Response getWalk(String input) {
        // TODO Auto-generated method stub
        Request request = new Request(input);
        request.body.put("x", GameViewCommands.WALK.getGroup(input, "x"));
        request.body.put("y", GameViewCommands.WALK.getGroup(input, "y"));
        return controller.getWalk(request);
    }

    // private Response getToolsUseDirectionResponse(String input) {
    // Response response;
    // Request request = new Request(input);
    // response = controller.handleToolsUseDirectionResponse(request);
    // return response;
    // }

    private Response getToolsShowAvailable(String input) {
        Response response;
        Request request = new Request(input);
        response = controller.handleToolsShowAvailable(request);
        return response;
    }

    //
    private Response getToolsShowResponse(String input) {
        Response response;
        Request request = new Request(input);
        response = controller.handleToolsShow(request);
        return response;
    }

    private Response getToolsEquipResponse(String input) {
        Response response;
        Request request = new Request(input);
        request.body.put("toolName", GameViewCommands.TOOLS_EQUIP.getGroup(input, "toolName"));
        response = controller.handleToolsEquip(request);
        return response;
    }

    //
    //
    private Response getInventoryTrashResponse(String input) {
        Response response;
        Request request = new Request(input);
        request.body.put("itemName", GameViewCommands.INVENTORY_TRASH.getGroup(input, "itemName"));
        request.body.put("number", GameViewCommands.INVENTORY_TRASH.getGroup(input, "number"));
        response = controller.handleInventoryTrashing(request);
        return response;
    }

    private Response getShowInventoryResponse(String input) {
        Response response;
        Request request = new Request(input);
        response = controller.handleShowInventory(request);
        return response;
    }

    private Response getBuildGreenHouse() {
        return controller.getBuildGreenHouse();
    }

    private Response getWeatherForcast() {
        return controller.getWeatherForecast();
    }

    private Response getCheatWeather(String input) {
        Request request = new Request(input);
        request.body.put("Type", GameViewCommands.CHEAT_WEATHER_SET.getGroup(input, "Type"));
        // return controller.getAdvancedTime(request);
        return controller.cheatWeather(request);
    }

    private Response getWeather() {
        return controller.getWeather();
    }

    private Response getCheatThor(String input) {
        Request request = new Request(input);
        request.body.put("X", GameViewCommands.CHEAT_THOR.getGroup(input, "X"));
        request.body.put("Y", GameViewCommands.CHEAT_THOR.getGroup(input, "Y"));

        return controller.cheatThor(request);
    }

    private Response getAdvancedTime(String input) {
        Request request = new Request(input);
        request.body.put("amount", GameViewCommands.CHEAT_ADVANCE_TIME.getGroup(input, "amount"));
        return controller.getAdvancedTime(request);
    }

    private Response getAdvancedDate(String input) {
        Request request = new Request(input);
        request.body.put("amount", GameViewCommands.CHEAT_ADVANCE_DATE.getGroup(input, "amount"));
        return controller.getAdvancedDate(request);
    }

    private Response getSeason() {
        // TODO Auto-generated method stub
        return controller.getSeason();
    }

    private Response getDayOfWeek() {
        // TODO Auto-generated method stub
        return controller.getDayOfWeek();
    }

    private Response getDateTime() {
        // TODO Auto-generated method stub
        return controller.getDateTime();
    }

    private Response getDate() {
        return controller.getDate();
    }

    private Response getTime() {
        return controller.getTime();
    }

    private Response getNextTurn() {
        return controller.NextTurn();
    }

    private Response getExitFromGame(String input, Scanner scanner) {
        App.setCurrentMenu(MenuTypes.ExitMenu);
        return new Response(true, "Exit from the Game Until We make some menu for it");
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method
        // 'getExitFromGame'");
    }

    private Response getEnergyUnlimited() {
        return controller.setCurrentPlayerEnergyUnlimited();
    }

    private Response getEnergySet(String input) {
        Request request = new Request(input);
        request.body.put("value", GameViewCommands.ENERGY_SET_VALUE.getGroup(input, "value"));
        return controller.setCurrentPlayerEnergy(request);
    }

    private Response getEnergyShow() {
        return controller.getEnergy();
    }

    private static Response getExitMenu(String input) {
        Request request = new Request(input);
        Response response = LoginMenuController.handleExitMenu(request);
        return response;
    }

    private static Response getEnterMenu(String input) {
        Request request = new Request(input);
        request.body.put("menuName", LoginMenuCommands.ENTER_MENU.getGroup(input, "menuName"));
        Response response = LoginMenuController.handleEnterMenu(request);
        return response;
    }

    private static Response getShowMenu(String input) {
        Request request = new Request(input);
        Response response = LoginMenuController.handleShowMenu(request);
        return response;
    }

    public void doNights() {
        System.out.println(controller.walkPlayersToHome());
    }
}
