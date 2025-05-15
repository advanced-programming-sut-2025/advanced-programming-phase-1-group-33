package com.yourgame.model.Map;



// need a way from farms to npc village  .....
// doors are completed , but we need ways from each farm to npc village.


import com.yourgame.model.Item.ForagingMineral;
import com.yourgame.model.Item.Tree;
import com.yourgame.model.Npc.NPC;
import com.yourgame.model.Npc.NPCType;
import com.yourgame.model.Stores.*;
import com.yourgame.model.UserInfo.Player;
import com.yourgame.model.WeatherAndTime.Weather;
import com.yourgame.model.App;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Map {
    private final static Random rand = new Random();
    private Tile[][] tiles = new Tile[250][200];
    private ArrayList<Farm> farms = new ArrayList<>();
    private NpcVillage npcVillage;
    private final ArrayList<NpcHome> npcHomes = new ArrayList<>();

    public Map(ArrayList<Farm> farms) {
        this.farms= farms;
    }

    public Tile findTile(int x, int y) {
        for(int i=0; i<tiles.length; i++) {
            for(int j=0; j<tiles.length; j++) {
                if(x == i && y == j) {
                    return tiles[i][j];
                }
            }
        }
        return null;
    }

    public Tile findTile(Position position) {
        return findTile(position.getX(), position.getY());
    }

    public void buildMap(ArrayList<Player> players) {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                tiles[i][j] = new Tile(new Position(i, j));
            }
        }
        farms.clear();
        for (Player player : players) {
            player.getFarm().setTilesSymbol(tiles);
            farms.add(player.getFarm());
        }
        this.npcVillage = new NpcVillage(new Rectangle(100, 75 , 49 , 49 ) ,
                new Blacksmith(102 , 77 , 3 , 3) ,
                new CarpenterShop(106 , 81 , 3 , 3),
                new FishShop(110 , 85 , 3 ,3 ),
                new JojaMart(114 , 89 , 3, 3) ,
                new MarnieRanch(118 , 93 ,3 , 3 ) ,
                new PierreGeneralStore(122 , 97  ,3 , 3),
                new StardropSaloon(126 , 101 ,  3 ,3 ));

        NPC abigailNpc = new NPC(NPCType.Abigail);
        NPC sebastianNpc = new NPC(NPCType.Sebastian );
        NPC leahNpc = new NPC(NPCType.Leah );
        NPC robinNpc = new NPC(NPCType.Robin );
        NPC harveyNpc = new NPC(NPCType.Harvey );

        NpcHome abigailHome = new NpcHome(130 , 104 , 3 ,3 , abigailNpc);
        npcHomes.add(abigailHome);
        NpcHome harveyHome = new NpcHome(130 ,109 , 3,  3, harveyNpc);
        npcHomes.add(harveyHome);
        NpcHome robinHome = new NpcHome(130 ,114 , 3 ,3 , robinNpc);
        npcHomes.add(robinHome);
        NpcHome leahHome = new NpcHome(130 ,119 , 3 ,3 , leahNpc);
        npcHomes.add(leahHome);
        NpcHome sebastianHome = new NpcHome(130 ,99 , 3 ,3 , sebastianNpc);
        npcHomes.add(sebastianHome);



        for(Farm farm : farms) {

            for(Placeable placeable : farm.getPlaceables()) {

                for(int i=placeable.getBounds().x; i<placeable.getBounds().x+placeable.getBounds().width; i++) {
                    for(int j=placeable.getBounds().y; j<placeable.getBounds().y+placeable.getBounds().height; j++) {
                        tiles[i][j].setWalkable(false);

                        tiles[i][j].setSymbol(placeable.getSymbol());
                        tiles[i][j].setPlaceable(placeable);

                    }
                }
            }
        }
        for(int i=this.npcVillage.getRectangle().x ; i<this.npcVillage.getRectangle().x + this.npcVillage.getRectangle().width; i++) {
            for(int j = this.npcVillage.getRectangle().y ; j < this.npcVillage.getRectangle().y + this.npcVillage.getRectangle().height; j++) {
                tiles[i][j].setSymbol('.');
            }
        }
        for(Placeable p : this.npcVillage.getPlaceables()){
            for(int i=p.getBounds().x; i<p.getBounds().x+p.getBounds().width; i++) {
                for(int j=p.getBounds().y; j<p.getBounds().y+p.getBounds().height; j++) {
                    tiles[i][j].setWalkable(false);
                    tiles[i][j].setSymbol(p.getSymbol());
                    tiles[i][j].setPlaceable(p);
                }
            }
        }
        for(NpcHome h : npcHomes) {
            for(int i=h.getBounds().x; i<h.getBounds().x+h.getBounds().width; i++) {
                for(int j = h.getBounds().y; j<h.getBounds().y+h.getBounds().height; j++) {
                    tiles[i][j].setSymbol(h.getSymbol());
                    tiles[i][j].setPlaceable(h);
                    tiles[i][j].setWalkable(false);
                }
            }
        }

        setBorderFarmsAndNpcVillage();
        setDoorFarmsAndNpcVillage();
        for(Farm farm : farms) {
            setWalkableDoorTrue(farm.getDoor().getBounds().x , farm.getDoor().getBounds().y ,
                    farm.getDoor().getBounds().width , farm.getDoor().getBounds().height);
        }
        for(Door  d : npcVillage.getDoors()) {
            setWalkableDoorTrue(d.getBounds().x , d.getBounds().y , d.getBounds().width , d.getBounds().height);
        }




    }

    public ArrayList<NpcHome> getNpcHomes() {
        return npcHomes;
    }

    // this method is only for debug!!!
    public void printMap() {
        for (int y = 0; y < 200; y++) {
            for (int x = 0; x < 250; x++) {
                if(findTile(x, y).equals(findTile(App.getGameState().getCurrentPlayer().getPosition()))){
                    System.out.print('p');
                }
                else{
                    System.out.print(tiles[x][y].getSymbol());
                }
            }
            System.out.println();
        }
    }



    public void setBorderFarmsAndNpcVillage() {

        for(int i = 0 ; i < 200 ; i++){
            tiles[99][i].setSymbol('+');
            tiles[149][i].setSymbol('+');
        }
        for(int i =0 ; i<250 ; i++){
            tiles[i][74].setSymbol('+');
            tiles[i][124].setSymbol('+');
        }

    }
    public void setDoorFarmsAndNpcVillage() {
        farms.get(0).setDoor(new Door(99 , 37 , 1 , 3));

        farms.get(1).setDoor(new Door(149 , 37 , 1 , 3));
        //farms.get(2).setDoor(new Door(99 , 155 , 1 , 3));
        //farms.get(3).setDoor(new Door(149 , 155 , 1 , 3));
        npcVillage.addDoors(new Door(99 , 105 , 1 , 3));
        npcVillage.addDoors(new Door(149 , 105 , 1 , 3));
        npcVillage.addDoors(new Door(125 , 74 , 3 , 1));
        npcVillage.addDoors(new Door(125 , 124 , 3, 1));
    }













    public Tile[][] getTiles() {
        return tiles;
    }

    public ArrayList<Farm> getFarms() {
        return farms;

    }

    public NpcVillage getNpcVillage() {
        return npcVillage;
    }
    public void setWalkableDoorTrue(int x, int y , int width, int height) {
        for(int i=x ;i<x+ width ; i++){
            for(int j=y ;j<y+ height ;j++){
                tiles[i][j].setWalkable(true);
                tiles[i][j].setSymbol('d');
            }
        }
    }

    public void randomForagingMineralGenerator(){
        for(Farm farm : farms) {
            for(Quarry quarry : farm.getQuarries()) {
                int numberOfForagingMinerals = 2 ;
                int counter = 0;
                while(counter < numberOfForagingMinerals) {
                    ForagingMineral foragingMineral = ForagingMineral.values()[rand.nextInt(ForagingMineral.values().length)];
                    quarry.addForagingMineral(foragingMineral);
                    counter++;

                }
            }
        }
    }

    public Tile getTileByDirection(Tile currentTile, Direction direction) {
        return switch (direction) {
            case UP -> findTile(currentTile.getPosition().getX() , currentTile.getPosition().getY() -1);
            case DOWN -> findTile(currentTile.getPosition().getX(), currentTile.getPosition().getY() + 1);
            case LEFT -> findTile(currentTile.getPosition().getX() - 1, currentTile.getPosition().getY());
            case RIGHT -> findTile(currentTile.getPosition().getX() + 1, currentTile.getPosition().getY());
            case UP_LEFT -> findTile(currentTile.getPosition().getX() - 1, currentTile.getPosition().getY() - 1);
            case UP_RIGHT -> findTile(currentTile.getPosition().getX() + 1, currentTile.getPosition().getY() - 1);
            case DOWN_LEFT -> findTile(currentTile.getPosition().getX() - 1, currentTile.getPosition().getY() + 1);
            case DOWN_RIGHT -> findTile(currentTile.getPosition().getX() + 1, currentTile.getPosition().getY() + 1);
        };
    }
    // this method should call every day.
    public void GotThunderByStormyWeather(){
        if(App.getGameState().getGameTime().getWeather().equals(Weather.Stormy)){
            int x = rand.nextInt(250);
            int y = rand.nextInt(200);
            Tile tile = findTile(x, y);
            if(tile != null){
                if(tile.getPlaceable() instanceof Tree tree){
                    tile.setWalkable(true );
                    tile.setGotThunder(true);
                    tile.setPlaceable(null);
                    tile.setSymbol('.');
                }
            }
        }
    }
}