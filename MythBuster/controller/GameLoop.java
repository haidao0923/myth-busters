package controller;

import gamefiles.characters.Player;
import gamefiles.characters.Monster;
import javafx.animation.AnimationTimer;
import views.GameScreen;

import java.util.ArrayList;

public class GameLoop {

    private static ArrayList<Monster> monsters = new ArrayList<>();
    private static AnimationTimer monsterLoop;


    public static void gameLoop() {

        monsterLoop = new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                for (int i = 0; i < monsters.size(); i++) {
                    monsters.get(i).update();
                }

            }
        };
    }

    public static void initializeAllAnimationTimers(Player player, GameScreen gameScreen) {
        GameLoop.gameLoop();
        player.updatePlayerHp();
        player.play(gameScreen.getScene());
    }

    public static void startAllAnimationTimers(AnimationTimer... timers) {
        for (AnimationTimer timer : timers) {
            timer.start();
        }
    }

    public static void stopAllAnimationTimers(AnimationTimer... timers) {
        for (AnimationTimer timer : timers) {
            timer.stop();
        }
    }

    public static ArrayList<Monster> getMonsters() {
        return monsters;
    }

    public static void setMonsters(ArrayList<Monster> newMonsters) {
        monsters = newMonsters;
    }

    public static AnimationTimer getMonsterLoop() {
        return monsterLoop;
    }
}
