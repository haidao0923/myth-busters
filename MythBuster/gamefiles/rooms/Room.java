package gamefiles.rooms;

import controller.Controller;
import gamefiles.Door;
import gamefiles.characters.Mage;
import gamefiles.characters.Monster;
import gamefiles.characters.Soldier;
import gamefiles.characters.TrapMonster;
import javafx.scene.Group;
import javafx.scene.text.Text;

import java.util.ArrayList;

public abstract class Room {
    private Door[] doors = new Door[4];
    private int row;
    private int column;
    private int width;
    private int height;
    private Text roomInfo;

    ArrayList<Monster> monsters = new ArrayList<>();

    private Group roomGroup;

    public Room(int width, int height, int row, int column) {
        this.width = width;
        this.height = height;
        this.row = row;
        this.column = column;
        roomInfo = new Text(410, 10, toString());
        roomInfo.setStyle("-fx-font-size: 30;");

        //Add at least 1 monster
        Monster defaultMonster = new Soldier();
        defaultMonster.moveAbsolute(Math.random() * width, Math.random() * height);
        monsters.add(defaultMonster);

        //Randomly add more monsters
        for(int i = 0; i < (Math.random() * (2 + Controller.getDifficulty())) - 1; i++) {
            spawnSoldier();
        }
        for(int i = 0; i < (Math.random() * (2 + Controller.getDifficulty())) - 1; i++) {
            spawnMage();
        }
        for(int i = 0; i < (Math.random() * (2 + Controller.getDifficulty())) - 1; i++) {
            spawnTrapMonster();
        }

    }
    public Door getLeftDoor() {
        return doors[0];
    }
    public Door getTopDoor() {
        return doors[1];
    }
    public Door getRightDoor() {
        return doors[2];
    }
    public Door getBottomDoor() {
        return doors[3];
    }
    public void setLeftDoor(Room destination) {
        doors[0] = new Door(100, height / 2, destination);
    }
    public void setTopDoor(Room destination) {
        doors[1] = new Door(width / 2, 100, destination);
    }
    public void setRightDoor(Room destination) {
        doors[2] = new Door(width - 100, height / 2, destination);
    }
    public void setBottomDoor(Room destination) {
        doors[3] = new Door(width / 2, height - 100, destination);
    }

    public void unlockDoors() {
        for (int i = 0; i < 4; i++) {
            if (doors[i] != null) {
                doors[i].unlock();
            }
        }
    }

    public Group getRoomGroup() {
        roomGroup = new Group();
        if (doors[0] != null) {
            roomGroup.getChildren().add(doors[0].getGroup());
        }
        if (doors[1] != null) {
            roomGroup.getChildren().add(doors[1].getGroup());
        }
        if (doors[2] != null) {
            roomGroup.getChildren().add(doors[2].getGroup());
        }
        if (doors[3] != null) {
            roomGroup.getChildren().add(doors[3].getGroup());
        }
        for(Monster monster : monsters){
            roomGroup.getChildren().add(monster.getGroup());
        }


        System.out.println("Row:" + row + " Col: " + column);
        return roomGroup;
    }

    public ArrayList<Monster> getMonsters() {
        return monsters;
    }

    public Text getRoomInfo() {

        return roomInfo;
    }

    public int getRow() {
        return row;
    }
    public int getColumn() {
        return column;
    }

    public void spawnSoldier() {
        Soldier soldier = new Soldier();
        soldier.moveAbsolute(Math.random() * width, Math.random() * height);
        monsters.add(soldier);
    }
    public void spawnMage() {
        Mage mage = new Mage();
        mage.moveAbsolute(Math.random() * width, Math.random() * height);
        monsters.add(mage);
    }
    public void spawnTrapMonster() {
        TrapMonster trapMonster = new TrapMonster();
        trapMonster.moveAbsolute(Math.random() * width, Math.random() * height);
        monsters.add(trapMonster);
    }
    // abstract methods for generating monsters and chests will be here.



}
