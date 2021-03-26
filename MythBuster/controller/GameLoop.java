package controller;

import gamefiles.characters.Monster;
import javafx.animation.AnimationTimer;

import java.util.ArrayList;

public class GameLoop {

    private static int numEntities = 0;
    public static ArrayList<Monster> monsters = new ArrayList<>();


    public static void gameLoop() {

        new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                for(int i = 0; i < monsters.size(); i++) {
                    monsters.get(i).update();
                }

            }
        }.start();


    }

}
