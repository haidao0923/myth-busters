package gamefiles.rooms;

import controller.Controller;
import controller.GameLoop;
import gamefiles.Door;
import gamefiles.Inventory;
import gamefiles.characters.*;
import gamefiles.items.Item;
import gamefiles.items.ItemDatabase;
import gamefiles.weapons.Weapon;
import gamefiles.weapons.WeaponDatabase;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public abstract class Room {
    private Door[] doors = new Door[4];
    private int row;
    private int column;
    private int width;
    private int height;
    private Text roomInfo;

    private ArrayList<Monster> monsters = new ArrayList<>();

    private Group roomGroup;

    public Room(int width, int height, int row, int column) {
        this.width = width;
        this.height = height;
        this.row = row;
        this.column = column;
        roomInfo = new Text(410, 10, toString());
        roomInfo.setStyle("-fx-font-size: 30;");

        if (this instanceof BasicRoom) {
            //Add at least 1 monster
            Monster defaultMonster = new Soldier();
            defaultMonster.moveAbsolute(Math.random() * width, Math.random() * height);
            monsters.add(defaultMonster);

            //Randomly add more monsters
            for (int i = 0; i < (Math.random() * (2 + Controller.getDifficulty())) - 1; i++) {
                spawnSoldier();
            }
            for (int i = 0; i < (Math.random() * (2 + Controller.getDifficulty())) - 1; i++) {
                spawnMage();
            }
            for (int i = 0; i < (Math.random() * (2 + Controller.getDifficulty())) - 1; i++) {
                spawnTrapMonster();
            }
        }
        if (this instanceof BossRoom) {
            Monster boss = new Boss();
            boss.moveAbsolute(width / 2, height / 2);
            monsters.add(boss);
        }
    }

    public void giveBonusRewards() {
        ArrayList<Item> toAdd = new ArrayList<>();
        int newCoins = 0;
        int weaponKey = (int)(3 * Math.random());
        Weapon w = WeaponDatabase.getWeapon(weaponKey);
        if (!checkWeapon(w)) {
            Inventory.addToInventory(w);
        } else {
            newCoins += (int) (50 + Math.random() * 50);
        }
        for (int i = 0; i < 2 + (int)(3 * Math.random()); i++) {
            int itemKey = (int)(3 * Math.random());
            toAdd.add(ItemDatabase.getItem(itemKey));
        }
        newCoins += (int) (50 + Math.random() * 50);
        Controller.getPlayer().addCoins(newCoins);
        displayReward("You picked up " + newCoins + " coins, and a huge amount of treasure!\nCheck your inventory!");
        Controller.getPlayer().updateHotbar(null, toAdd);
        
    }

    public boolean checkWeapon(Weapon w) {
        if (w.equals(Controller.getPlayer().getWeapon())) {
            return true;
        }
        List<Item> inventory = Inventory.getInventory();
        for (Item i: inventory) {
            if ((i instanceof Weapon)) {
                Weapon w2 = (Weapon) (i);
                if (w.equals(w2)) {
                    return true;
                }
            }
        }
        return false;
    }

    public int getHeight() {
        return height;
    }
    
    public int getWidth() {
        return width;
    }

    public Door[] getDoors() {
        return doors;
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
        doors[0] = new Door(0, height / 2 - Door.getHeight() / 2, destination);
    }
    public void setTopDoor(Room destination) {
        doors[1] = new Door(width / 2 - Door.getWidth() / 2, 0, destination);
    }
    public void setRightDoor(Room destination) {
        doors[2] = new Door(width - Door.getWidth(), height / 2 - Door.getHeight() / 2, destination);
    }
    public void setBottomDoor(Room destination) {
        doors[3] = new Door(width / 2 - Door.getWidth() / 2, height - Door.getHeight(), destination);
    }

    public void unlockDoors() {
        for (int i = 0; i < 4; i++) {
            if (doors[i] != null) {
                doors[i].unlock();
            }
        }
    }

    public void lockDoors() {
        for (int i = 0; i < 4; i++) {
            if (doors[i] != null) {
                doors[i].lock();
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
        for (Monster monster : monsters) {
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

    public Soldier spawnSoldier() {
        Soldier soldier = new Soldier();
        soldier.moveAbsolute(Math.random() * width, Math.random() * height);
        monsters.add(soldier);
        GameLoop.getMonsters().add(soldier);
        return soldier;
    }
    public Mage spawnMage() {
        Mage mage = new Mage();
        mage.moveAbsolute(Math.random() * width, Math.random() * height);
        monsters.add(mage);
        GameLoop.getMonsters().add(mage);
        return mage;
    }
    public TrapMonster spawnTrapMonster() {
        TrapMonster trapMonster = new TrapMonster();
        trapMonster.moveAbsolute(Math.random() * width, Math.random() * height);
        monsters.add(trapMonster);
        GameLoop.getMonsters().add(trapMonster);
        return trapMonster;
    }

    public void displayReward(String text) {
        Label display = new Label(text);
        display.setPrefWidth(Controller.getW());
        display.setStyle("-fx-font-size: 30; -fx-font-weight: bold; -fx-text-fill: white;"
                + "-fx-alignment:CENTER;");
        display.setLayoutY(250);
        display.setId("dropNotificationDisplay");
        Controller.getGameScreen().getBoard().getChildren().add(display);

        new AnimationTimer() {
            private int timer = 180;
            @Override
            public void handle(long currentNanoTime) {
                timer--;
                if (timer <= 0) {
                    Controller.getGameScreen().getBoard().getChildren().remove(display);
                    this.stop();
                }
            }
        }.start();
    }

    // abstract methods for generating monsters and chests will be here.



}
