package com.yourgame.model.Map;

import com.yourgame.model.Item.Crop;
import com.yourgame.model.Item.CropType;
import com.yourgame.model.Item.Tree;
import com.yourgame.model.Item.TreeType;
import com.yourgame.model.Map.Water.Lake;
import com.yourgame.model.WeatherAndTime.TimeSystem;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

public class FarmFactory {
    private static final HashSet<Point> usedPositions = new HashSet<>();

    private static final Random rand = new Random();
    public static Farm makeFarm1(int x  ,int y) {
        markUsedArea(10 + x , 10 + y , 4 ,4 );
        markUsedArea(20 + x , 60 + y , 6 ,6 );
        markUsedArea(60 + x , 15 + y , 3 , 12);
        markUsedArea(60 +x ,45+y , 3 , 14);
        markUsedArea(50 + x , 65 + y , 5 , 7);

        return new Farm(
            new Cottage(10 + x, 10 + y, 4, 4),
            new GreenHouse(20 + x, 60 + y, 6, 6),
            new ArrayList<>(Arrays.asList(
                new Lake(60 + x, 15 + y, 3, 12),
                new Lake(60 + x, 45 + y, 3, 14)
            )),
            new ArrayList<>(Arrays.asList(
                new Quarry(50 + x, 65 + y, 5, 7)
            )),

            generateRandomTree( x, y ), // درخت‌ها اگه خاص باشن اینجا اضافه کن
            generateRandomStone(x , y),
            generateRandomCrop(x , y),// سنگ‌ها
            new Rectangle(x, y, 100, 75),
            1
        );
    }

    public static Farm makeFarm2(int x  ,int y) {
        Cottage cottage = new Cottage(5 + x, 10 + y, 4, 4);
        markUsedArea(5+ x, 10 + y, 4, 4);
        GreenHouse greenHouse = new GreenHouse(30 + x, 15 + y, 6, 6);
        markUsedArea(30 + x, 15 + y, 6, 6);
        ArrayList<Lake> lakes = new ArrayList<>();
        Lake lake1 = new Lake(20 + x , 50 +y , 3, 18);
        Lake lake2 = new Lake(70 + x , 50 +y , 3, 15);
        lakes.add(lake1);
        lakes.add(lake2);
        markUsedArea(70 + x, 50 +y , 3, 15);
        markUsedArea(20 + x, 50 +y , 3, 18);
        ArrayList<Quarry> quarries = new ArrayList<>();
        Quarry q1 = new Quarry(50 + x, 30 + y , 5 , 7);
        quarries.add(q1);
        markUsedArea(50 + x, 30 +y , 5, 7);
        return new Farm(
            cottage,
            greenHouse,
            lakes,
            quarries,
            generateRandomTree(x , y ),
            generateRandomStone(x , y),
            generateRandomCrop(x , y),
            new Rectangle( x , y , 100, 75),
            2
        );
    }

    public static Farm makeFarm3(int x , int y) {
        Cottage c = new Cottage(10 + x, 5 + y, 4, 4);
        markUsedArea(10 + x, 5 + y, 4, 4);
        GreenHouse g = new GreenHouse(70 + x, 5 + y, 6, 6);
        markUsedArea(70 + x, 5 + y, 6, 6);
        ArrayList<Lake> lakes = new ArrayList<>();
        Lake l1 = new Lake(20 + x , 40 +y , 3 , 18);
        Lake l2 = new Lake(90 + x , 40 +y , 3 ,17);
        lakes.add(l1);
        lakes.add(l2);
        markUsedArea(20 +x , 40 +y , 3 , 18);
        markUsedArea(90 +x , 40 +y , 3 ,17);
        ArrayList<Quarry> quarries = new ArrayList<>();
        Quarry q1 = new Quarry(40 + x, 15 + y , 5 ,7);
        Quarry q2 = new Quarry(65 + x , 15  +y , 5, 7);
        quarries.add(q1);
        quarries.add(q2);
        markUsedArea(40 +x , 15 +y , 5 ,7);
        markUsedArea(65 +x , 15 +y , 5, 7);

        return new Farm(
            c,
            g,
            lakes,
            quarries,
            generateRandomTree(x , y ),
            generateRandomStone(x , y),
            generateRandomCrop(x , y),
            new Rectangle(x, y, 100, 75),
            3
        );
    }

    public static Farm makeFarm4(int x  ,int y) {
        Cottage cottage = new Cottage(5 + x, 10 + y, 4, 4);
        markUsedArea(10 + x, 10 + y, 4, 4);
        ArrayList<Lake> lakes = new ArrayList<>();
        Lake l1 = new Lake(20 + x, 35 + y, 3, 20);
        lakes.add(l1);
        markUsedArea(20 + x, 35 + y, 3, 20);
        ArrayList<Quarry> quarries = new ArrayList<>();
        Quarry q1 = new Quarry(10 + x  ,15 + y  , 5  ,7);
        quarries.add(q1);
        markUsedArea(10 + x, 15 + y, 5, 7);
        GreenHouse greenHouse = new GreenHouse(50 + x, 5 + y, 6, 6);
        markUsedArea(50 + x, 5 + y, 6, 6);

        return new Farm(
            cottage,
            greenHouse,
            lakes,
            quarries,
            generateRandomTree(x , y),
            generateRandomStone(x , y),
            generateRandomCrop(x , y),
            new Rectangle(x, y, 100, 75),
            4
        );
    }
    public static ArrayList<Tree> generateRandomTree(int x, int y) {
        ArrayList<Tree> trees = new ArrayList<>();
        int numberOfTrees = rand.nextInt(11) + 30;

        while (trees.size() < numberOfTrees) {
            int randomX = rand.nextInt(100) + x;
            int randomY = rand.nextInt(75) + y;
            Point p = new Point(randomX, randomY);

            if (usedPositions.contains(p)) continue;

            usedPositions.add(p);
            //Tree t = new Tree(getRandomTreeType(), new TimeSystem(), null, randomX, randomY, 1, 1);
            //trees.add(t);
        }
        return trees;
    }

    public static ArrayList<Stone> generateRandomStone(int x, int y) {
        ArrayList<Stone> stones = new ArrayList<>();
        int numberOfStones = rand.nextInt(11) + 35;

        while (stones.size() < numberOfStones) {
            int randomX = rand.nextInt(100) + x;
            int randomY = rand.nextInt(75) + y;
            Point p = new Point(randomX, randomY);

            if (usedPositions.contains(p)) continue;

            usedPositions.add(p);
            stones.add(new Stone(randomX, randomY));
        }
        return stones;
    }

    public static ArrayList<Crop> generateRandomCrop(int x, int y) {
        ArrayList<Crop> crops = new ArrayList<>();
        int numberOfCrops = rand.nextInt(11) + 30;

        while (crops.size() < numberOfCrops) {
            int randomX = rand.nextInt(100) + x;
            int randomY = rand.nextInt(75) + y;
            Point p = new Point(randomX, randomY);

            if (usedPositions.contains(p)) continue;

            usedPositions.add(p);
            Crop c = new Crop(getRandomCropType(), new TimeSystem(), null, randomX, randomY);
            crops.add(c);
        }
        return crops;
    }

    public static CropType getRandomCropType() {
        Random rand = new Random();
        CropType[] cropTypes = CropType.values();
        return cropTypes[rand.nextInt(cropTypes.length)];
    }
    public static TreeType getRandomTreeType() {
        Random rand = new Random();
        TreeType[] types = TreeType.values();
        return types[rand.nextInt(types.length)];
    }
    public static void markUsedArea(int x , int y , int width , int height) {
        for(int i = x ; i < x + width ; i++) {
            for(int j = y ; j < y + height ; j++) {
                usedPositions.add(new Point(i , j));
            }
        }
    }

}
