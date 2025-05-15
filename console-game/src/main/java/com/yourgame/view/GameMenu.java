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
            case WALK:
                return getWalk(input);
            case PRINT_MAP:
                return getPrintMap(input);
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
//          case TOOLS_USE_DIRECTION:
//            return getToolsUseDirectionResponse(input);
//          case TOOLS_UPGRADE:
//                return getToolUpgradeResponse(input);
            case CRAFT_INFO:
                return showCraftInfo(input);
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
            default:
                return getInvalidCommand();
        }
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

//    private Response getToolUpgradeResponse(String input) {
//        Response response;
//        Request request = new Request(input);
//        request.body.put("name", GameViewCommands.TOOLS_EQUIP.getGroup(input, "name"));
//        response = controller.handleToolsUpgrade(request);
//        return response;
//    }

    private Response getHelpRedning() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getHelpRedning'");
    }


    private Response getPrintMap(String input) {
        App.getGameState().getMap().printMap();
        return new Response(true,"");
    }


    private Response getWalk(String input) {
        // TODO Auto-generated method stub
        Request request = new Request(input);
        request.body.put("x", GameViewCommands.WALK.getGroup(input, "x"));
        request.body.put("y", GameViewCommands.WALK.getGroup(input, "y"));

        return controller.getWalk(request);
    }


//    private Response getToolsUseDirectionResponse(String input) {
//        Response response;
//        Request request = new Request(input);
//        response = controller.handleToolsUseDirectionResponse(request);
//        return response;
//    }

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
        // TODO Auto-generated method stub
        return controller.getWeather();
    }

    private Response getCheatThor(String input) {
        Request request = new Request(input);
        request.body.put("X", GameViewCommands.CHEAT_THOR.getGroup(input, "X"));
        request.body.put("Y", GameViewCommands.CHEAT_THOR.getGroup(input, "Y"));
        // TODO Auto-generated methd stub
        // return controller.getAdvancedTime(request);
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
}