package gamefiles.rooms;

import java.util.ArrayList;

import controller.Controller;
import gamefiles.BlueTreasureChest;
import gamefiles.GreenTreasureChest;
import gamefiles.RedTreasureChest;
import gamefiles.YellowTreasureChest;
import gamefiles.TreasureChest;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import views.GameScreen;

public class TreasureRoom extends Room {

    private int treasureCount = (int) (Math.random() * 3) + 1;
    private ArrayList<TreasureChest> treasureChests = new ArrayList<>();
    private static AnimationTimer animationTimer;

    private Group treasureGroup;
    public TreasureRoom(int width, int height, int row, int column) {
        super(width, height, row, column);
        switch(treasureCount) {
        case 1:
            addRandomChest(width/2 - 50, height/2 - 50);
            break;
        case 2:
            addRandomChest(width/2 - 150, height/2 - 50);
            addRandomChest(width/2 + 50, height/2 - 50);
            break;
        case 3:
            addRandomChest(width/2 - 200, height/2 - 50);
            addRandomChest(width/2 - 50, height/2 - 50);
            addRandomChest(width/2 + 100, height/2 - 50);
            break;
        }
    }

    public void addRandomChest(double positionX, double positionY, int cost) {
        int random = (int)(Math.random() * 4);
        switch (random) {
        case 0:
            treasureChests.add(new RedTreasureChest(positionX, positionY, cost));
            break;
        case 1:
            treasureChests.add(new BlueTreasureChest(positionX, positionY, cost));
            break;
        case 2:
            treasureChests.add(new YellowTreasureChest(positionX, positionY, cost));
            break;
        case 3:
            treasureChests.add(new GreenTreasureChest(positionX, positionY, cost));
            break;
        }
    }
    public void addRandomChest(double positionX, double positionY) {
        addRandomChest(positionX, positionY, (int) (Math.random() * 5 + 2) * 5);
    }


    @Override
    public Group getRoomGroup() {
        Group roomGroup = super.getRoomGroup();
        treasureGroup = new Group();
        for (int i = 0; i < treasureChests.size(); i++) {
            if (!treasureChests.get(i).isOpened()) {
                treasureGroup.getChildren().add(treasureChests.get(i).getGroup());
            }
        }

        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                for (int i = 0; i < treasureChests.size(); i++) {
                    if (treasureChests.get(i).canOpen()) {
                        treasureChests.get(i).open();
                        treasureGroup.getChildren().remove(treasureChests.get(i).getGroup());
                    }
                }
            }
        };
        animationTimer.start();

        roomGroup.getChildren().add(treasureGroup);
        Controller.getGameScreen().changeBackgroundColor("#6e6e65");
        return roomGroup;
    }
    public String toString() {
        return "Treasure Room";
    }
    public ArrayList<TreasureChest> getTreasureChests() {
        return treasureChests;
    }
    public static AnimationTimer getAnimationTimer() {
        return animationTimer;
    }
}
