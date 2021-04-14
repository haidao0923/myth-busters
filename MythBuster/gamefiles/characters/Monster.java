package gamefiles.characters;

import controller.Controller;
import controller.GameLoop;
import gamefiles.Inventory;
import gamefiles.Touchable;
import gamefiles.items.Item;
import gamefiles.items.ItemDatabase;
import gamefiles.weapons.*;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Map;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class Monster implements Touchable {
    protected String name;
    protected double maxHealth;
    protected double currentHealth;
    protected double percentageHealth;
    protected boolean isDead;
    protected double movementSpeed;
    protected double positionX;
    protected double positionY;
    protected double width;
    protected double height;
    protected ImageView imageView;

    protected Node image;
    protected Rectangle healthBar;
    protected Rectangle healthBarBacking;
    protected double healthBarWidth;

    private Map<Integer, Integer> lootTable = new HashMap<>();

    protected Group monsterGroup;
    public Monster(String name, double health, double movementSpeed, String spritePath,
        double width, double height) {

        this.name = name;
        this.maxHealth = health;
        currentHealth = maxHealth;
        //Somewhat randomize movespeed to prevent stacking.
        this.movementSpeed = ((Math.random() * 0.5) + 0.5) * movementSpeed;
        this.width = width;
        this.height = height;
        this.healthBarWidth = width;

        isDead = false;

        initLootTable();

        imageView = new ImageView(spritePath);
        imageView.setFitWidth(this.width);
        imageView.setFitHeight(this.height);
        image = imageView;
        healthBar = new Rectangle(positionX, positionY - 30, healthBarWidth, 10);
        healthBar.setFill(Color.GREEN);
        healthBarBacking = new Rectangle(positionX, positionY - 30, healthBarWidth, 10);
        healthBarBacking.setFill(Color.RED);
        monsterGroup = new Group();
        monsterGroup.getChildren().addAll(image, healthBarBacking, healthBar);

    }

    private void initLootTable() {
        int total = 100;
        for (int i = 0; i <= 2; i++) {
            int probability = (int)(6 * Math.random() + 19);
            lootTable.put(i, probability);
            total = total - probability;
        }
        for (int i = 100; i <= 102; i++) {
            int probability = (int)(3 * Math.random() + 3);
            lootTable.put(i, probability);
            total = total - probability;
        }
        lootTable.put(-1, total);
    }


    public abstract void update();

    public Group getGroup() {
        return monsterGroup;
    }

    public void redrawHealthBar() {
        percentageHealth = currentHealth / maxHealth;
        healthBar.setWidth(healthBarWidth * percentageHealth);
    }

    public void checkDeath() {
        if (currentHealth <= 0) {
            isDead = true;
            Platform.runLater(() -> {
                Controller.getGameScreen().getBoard().getChildren().remove(this.getGroup());
                Controller.getCurrentRoom().getMonsters().remove(this);
                monsterGroup.getChildren().removeAll(image, healthBar, healthBarBacking);
            });
            if (this instanceof Trap) {
                Trap.decrementTrapCount(1);
            }
            GameLoop.getMonsters().remove(this);
            if (!(this instanceof Trap) && !(this instanceof Fireball)) {
                addItems();
            }
        }
    }

    public boolean addItems() {
        ArrayList<Item> toAdd = new ArrayList<>();
        int prob = (int)(Math.random() * 100);
        int total = -1;
        Set<Integer> keySet = lootTable.keySet();
        for (int key: keySet) {
            total = total + lootTable.get(key);
            if (prob < total) {
                if (key >= 100) {
                    Weapon w = WeaponDatabase.getWeapon(key % 100);
                    if(!checkWeapon(w)) {
                        toAdd.add(w);
                        displayReward("You picked up a " + w.getName());
                        Inventory.addToInventory(w);
                        return true;
                    } else {
                        int newCoins = (int)(5 + Math.random() * 5);
                        displayReward("You picked up " + newCoins + " coins");
                        Controller.getPlayer().addCoins(newCoins);
                        return true;
                    }
                } else if (key >= 0) {
                    displayReward("You picked up a " + ItemDatabase.getItem(key).getName());
                    toAdd.add(ItemDatabase.getItem(key));
                    Controller.getPlayer().updateHotbar(null, toAdd);
                    return true;
                } else {
                    int newCoins = (int)(5 + Math.random() * 5);
                    displayReward("You picked up " + newCoins + " coins");
                    Controller.getPlayer().addCoins(newCoins);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkWeapon(Weapon w) {
        List<Item> inventory = Inventory.getInventory();
        for (Item i: inventory) {
            if ((i instanceof Weapon)) {
                Weapon w2 = (Weapon)(i);
                if (w.equals(w2)) {
                    return true;
                }
            }
        }
        return false;
    }


    public void addHealth(double value) {
        this.currentHealth += value;
    }
    public void takeDamage(double value) {
        addHealth(-value);
    }
    public void loseAllHealth() {
        takeDamage(maxHealth);
        checkDeath();
    }

    public void moveAbsolute(double x, double y) {
        positionX = x;
        positionY = y;
        monsterGroup.relocate(positionX, positionY);
    }

    public void moveRelative(double x, double y) {
        positionX += x;
        positionY += y;
        monsterGroup.relocate(positionX, positionY);
    }



    public Rectangle2D getBoundary() {
        return new Rectangle2D(positionX, positionY, width, height);
    }
    public boolean intersects(Touchable other) {
        return other.getBoundary().intersects(this.getBoundary());
    }

    public double getWidth() {
        return width;
    }
    public double getHeight() {
        return height;
    }

    public double getPositionX() {
        return positionX;
    }
    public double getPositionY() {
        return positionY;
    }

    public String getName() {
        return this.name;
    }

    public double getCurrentHealth() {
        return currentHealth;
    }
    public double getMaxHealth() {
        return maxHealth;
    }

    public void displayReward(String text) {
        Label display = new Label(text);
        display.setPrefWidth(Controller.getW());
        display.setStyle("-fx-font-size: 30; -fx-font-weight: bold; -fx-text-fill: white;"
                + "-fx-alignment:CENTER;");
        display.setLayoutY(200);
        Controller.getGameScreen().getBoard().getChildren().add(display);

        new AnimationTimer() {
            int timer = 60;
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

}
