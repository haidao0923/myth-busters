package controller;

import gamefiles.characters.Monster;
import javafx.animation.AnimationTimer;

import java.util.ArrayList;

public class GameLoop {

    public static ArrayList<Monster> monsters = new ArrayList<>();
    public static AnimationTimer monsterLoop;


    public static void gameLoop() {

        monsterLoop = new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                for(int i = 0; i < monsters.size(); i++) {
                    monsters.get(i).update();
                }

            }
        };
        
        monsterLoop.start();
    }

}
