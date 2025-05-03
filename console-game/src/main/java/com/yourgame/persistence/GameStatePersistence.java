//package com.yourgame.persistence;
//
//import com.google.gson.Gson;
//import com.yourgame.model.GameState;
//import java.io.FileWriter;
//import java.io.FileReader;
//import java.io.IOException;
//
//public class GameStatePersistence {
//    private static final Gson gson = new Gson();
//
//    public static void saveGameState(GameState gameState, String filePath) {
//        try (FileWriter writer = new FileWriter(filePath)) {
//            gson.toJson(gameState, writer);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static GameState loadGameState(String filePath) {
//        try (FileReader reader = new FileReader(filePath)) {
//            return gson.fromJson(reader, GameState.class);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//}