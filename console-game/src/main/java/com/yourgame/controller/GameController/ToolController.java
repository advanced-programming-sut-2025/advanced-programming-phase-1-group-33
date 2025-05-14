package com.yourgame.controller.GameController;

import com.yourgame.model.App;
import com.yourgame.model.IO.Request;
import com.yourgame.model.IO.Response;
import com.yourgame.model.Inventory.Tools.Tool;

public class ToolController {
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
}
