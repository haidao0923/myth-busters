package gamefiles.characters;

import java.awt.*;
import java.util.ArrayList;

import controller.Controller;
import gamefiles.Heart;
import controller.GameLoop;
import controller.SpriteAnimation;
import gamefiles.Inventory;
import gamefiles.Touchable;
import gamefiles.items.Consumable;
import gamefiles.items.Item;
import gamefiles.items.ItemDatabase;
import gamefiles.weapons.Bow;
import gamefiles.weapons.Spear;
import gamefiles.weapons.Sword;
import gamefiles.weapons.Weapon;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Player implements Touchable {
    private String name;
    private int coins;
    private Weapon weapon;
    private double speed;
    private int numHearts;
    private double maxHealth;
    private double currentHealth;
    private double attackCD = 0;
    private double moveCD = 0;
    private double damage = 10; //TEMPORARY CHANGE FOR TESTING PURPOSES
    private double damageCooldown;
    private Image swordSprite = new Image("sprites/Player/swordPlayer.png");
    private Image spearSprite = new Image("sprites/Player/spearPlayer.png");
    private Image bowSprite = new Image("sprites/Player/bowPlayer.png");
    private ImageView imageView;
    private int direction = 0; //left = 0, right = 1

    private double positionX;
    private double positionY;
    private double width;
    private double height;

    private final double heartsPadding = 10;
    private final double heartsDimensions = 50;
    private final double hotbarPadding = 10;

    private int spriteWidth = 63;
    private int spriteHeight = 55;
    private int spriteX = 0;
    private int spriteY = 585;

    private Group imageGroup;
    private ArrayList<Heart> hearts;
    private HBox heartsBox;
    private Item[] hotbar;
    private HBox hotbarBox;

    private AnimationTimer playerLogic;
    private AnimationTimer playerHpUpdate;
    private AnimationTimer itemLoop;



    public Player(int coins, Weapon weapon) {
        this.coins = coins;
        this.weapon = weapon;
        width = 100;
        height = 100;
        damage = weapon != null ? weapon.getDamage() * damage : 0;
        imageView = new ImageView();
        if (weapon instanceof Spear) {
            imageView.setImage(spearSprite);
        } else if (weapon instanceof Sword) {
            imageView.setImage(swordSprite);
        } else if (weapon instanceof Bow) {
            imageView.setImage(bowSprite);
        }
        Rectangle2D viewport = new Rectangle2D(spriteX, spriteY, spriteWidth, spriteHeight);
        imageView.setViewport(viewport);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        imageView.setLayoutX(100);
        imageView.setLayoutY(250);
        imageGroup = new Group();
        imageGroup.getChildren().add(imageView);

        // hp
        currentHealth = 300;
        maxHealth = 300;
        numHearts = (int) Math.floor(maxHealth / Heart.HEALTH_PER_HEART);
        updatePlayerMaxHp();

        initializeHotbar();
    }

    public int attack(Scene scene) {
        Animation animation = null;
        int currX = spriteX;
        int currY = spriteY;
        double duration = 0;
        if (weapon instanceof Spear) {
            spriteY = spriteY - 256; //go to the attack frames

            duration = 500;
            animation = new SpriteAnimation(imageView, Duration.millis(duration),
                    8, 8, spriteY, spriteWidth, spriteHeight);
            animation.setCycleCount(1);
            animation.play();
        } else if (weapon instanceof Sword) {
            spriteY = spriteY + 1025;
            duration = 500;
            //spriteX = spriteX + 45;
            animation = new SpriteAnimation(imageView, Duration.millis(duration), 5, 5, spriteY, 105, spriteHeight);
            animation.setCycleCount(1);
            animation.play();
        } else if (weapon instanceof Bow) {
            spriteY += 510;
            duration = 500;
            System.out.println("Bow");
            animation = new SpriteAnimation(imageView, Duration.millis(duration), 12, 12, spriteY, spriteWidth,
                    spriteHeight);
            animation.setCycleCount(1);
            animation.play();
            ((Bow) weapon).fireArrow(direction, positionX, positionY + width / 2, damage);
        }
        animation.setOnFinished(actionEvent -> {
            spriteX = currX;
            spriteY = currY;
            Rectangle2D viewpoint = new Rectangle2D(spriteX, spriteY, spriteWidth, spriteHeight);
            imageView.setViewport(viewpoint);
        });
        return (int) ((duration / 1000) * 60);
    }

    public void play(Scene scene) {
        ArrayList<String> input = new ArrayList<>();

        scene.setOnKeyPressed(
            e -> {
                String code = e.getCode().toString();
                if (!input.contains(code)) {
                    input.add(code);
                }
            });

        scene.setOnKeyReleased(
            e -> {
                String code = e.getCode().toString();
                input.remove(code);
            });




        // PLAYER LOGIC
        this.playerLogic = new AnimationTimer() {
            private int invisibilityCd = 0;
            private int damageWindow = 0;

            //For checking if 'I' is pressed and released.
            boolean containedI = false;

            public void handle(long now) {
                // game logic
                // 60 now's = 1 second!!!
                if (attackCD > 0) {
                    attackCD--;
                }
                if (input.contains("J") && attackCD <= 0) {
                    speed = 0;
                    attackCD = 60;
                    int temp = attack(scene);
                    damageWindow = temp;
                    moveCD = temp;
                } else if (input.size() > 1) {
                    speed = 7;
                } else {
                    speed = 10;
                }
                if (damageWindow > 0) {
                    for (Monster monster : GameLoop.getMonsters()) {
                        if (Controller.getPlayer().intersects(monster)) {
                            monster.takeDamage(damage);
                            damageWindow = 0;
                        }
                    }
                    damageWindow--;
                }
                if (damageCooldown > 0 && damageCooldown % 15 == 0) { // got hit
                    invisibilityCd = 5; // set invis frames
                }
                damageCooldown--; // so dmgcd triggers for first frame (60 % 15)

                if (moveCD > 0) {
                    moveCD--;
                } else {
                    if (input.contains("A") && positionX > 0) {
                        imageView.setScaleX(1);
                        moveRelative(-speed, 0);
                        direction = 0;
                    }
                    if (input.contains("D") && positionX + width < scene.getWidth()) {
                        imageView.setScaleX(-1);
                        moveRelative(speed, 0);
                        direction = 1;
                    }
                    if (input.contains("W") && positionY > 0) {
                        moveRelative(0, -speed);
                    }
                    if (input.contains("S") && positionY + height < scene.getHeight()) {
                        moveRelative(0, speed);
                    }
                }

                if (invisibilityCd > 0) { // Overwrite setScale if invis frames
                    imageView.setScaleX(0);
                } else if (imageView.getScaleX() == 0) { // If player didn't move
                    if (direction == 0) { // same direction
                        imageView.setScaleX(1);
                    } else if (direction == 1) {
                        imageView.setScaleX(-1);
                    }
                }
                invisibilityCd--;


                //Keyboard transitions to screens
                if(input.contains("I")) {
                    containedI = true;
                }
                if(containedI && !input.contains("I")) { //Go to inventory if I was released.
                    Controller.goToInventory();
                    input.remove("I");
                    containedI = false;
                }

            }
        };

        // ITEM LOOP
        this.itemLoop = new AnimationTimer() {
            ArrayList<Integer> toDelete = new ArrayList<Integer>();
            int itemCD = 0;

            public void handle(long currentNanoTime) {
                Item[] currHotbar = Inventory.getHotbar();

                // some triggers for onscreen hotbar / consumables
                for (int i = 0; i < Inventory.getMAXHOTBARSIZE(); i++) { // max hotbar size of 5
                    Item item = currHotbar[i];
                    if(item != null) {
                        if (itemCD <= 0 && input.contains("DIGIT" + Integer.toString(i + 1))) {
                            System.out.println("Pressed " + (i + 1));
                            item.setActive(true);
                            itemCD = 30;
                            if (item instanceof Consumable) {
                                toDelete.add(i);
                                System.out.println("Trying to delete at index " + i);
                            }
                        }
                    }
                    if (item instanceof Consumable && item.isActive()) {
                        ((Consumable)item).effect(currentNanoTime);
                    }
                }
                updateHotbar(toDelete, null);
                itemCD--;
            }
        };
    }

    public void moveAbsolute(double x, double y) {
        positionX = x;
        positionY = y;
        imageGroup.relocate(positionX, positionY);
    }

    public void moveRelative(double x, double y) {
        positionX += x;
        positionY += y;
        imageGroup.relocate(positionX, positionY);
    }


    public void updateHotbar(ArrayList<Integer> toDelete, ArrayList<Item> toAdd) {
        boolean update = false;
        Item[] currHotbar = Inventory.getHotbar();
        if (toDelete != null && toDelete.size() > 0) {
            update = true;
            for (int i = toDelete.size() - 1; i >= 0; i--) {
                currHotbar[toDelete.remove(i).intValue()] = null;
                Inventory.setHotbarSize(Inventory.getHotbarSize() - 1);
            }
        }
        if (toAdd != null && toAdd.size() > 0) {

            update = true;
            for (Item item : toAdd) {
                if(Inventory.getHotbarSize() < Inventory.getMAXHOTBARSIZE()) {
                    currHotbar[Inventory.getHotbarSize()] = item;
                } else {
                    Inventory.addToInventory(item);
                }

            }

        }

        if (update) {
            // hotbarBox
            updateHotbarImages();
        }
    }

    public void initializeHotbar() {
        ItemDatabase.resetQuantities();

        this.hotbar = Inventory.getHotbar(); // UPDATE IF WEAPON IS AN ITEM
        this.hotbarBox = new HBox(hotbarPadding);
        for (int i = 0; i < Inventory.getMAXHOTBARSIZE(); i++) {
            Group hotbarSlot = new Group();
            hotbarSlot.getChildren().add(new Rectangle(50, 50, Color.YELLOW));
            hotbarBox.getChildren().add(hotbarSlot);

            ImageView imageView = null;
            if (hotbar[i] != null) {
                imageView = new ImageView(hotbar[i].getImage());
                imageView.setFitWidth(50);
                imageView.setFitHeight(50);
                hotbarSlot.getChildren().add(imageView);
            }

            hotbarBox.setLayoutX(700);
            hotbarBox.setLayoutY(hotbarPadding);
        }
    }


    public void updateHotbarImages() {
            for (int j = 0; j < Inventory.getMAXHOTBARSIZE(); j++) {
                if (hotbar[j] != null) {
                    ImageView imageView = new ImageView(hotbar[j].getImage());
                    imageView.setFitWidth(hotbar[j].getWidth());
                    imageView.setFitHeight(hotbar[j].getHeight());
                    Group hotbarSlot = (Group) hotbarBox.getChildren().get(j);
                    hotbarSlot.getChildren().clear();
                    hotbarSlot.getChildren().add(new Rectangle(50, 50, Color.YELLOW));
                    hotbarSlot.getChildren().add(imageView);
                } else {
                    Group hotbarSlot = (Group) hotbarBox.getChildren().get(j);
                    hotbarSlot.getChildren().clear();
                    hotbarSlot.getChildren().add(new Rectangle(50, 50, Color.YELLOW));
                }
            }

    }






    public void updatePlayerHp() {
        this.playerHpUpdate = new AnimationTimer() {
            private double oldHealth = getCurrentHealth();
            public void handle(long currentNanoTime) {
                double currentHealth = getCurrentHealth();

                if (currentHealth != oldHealth) {
                    //System.out.println("Updating Damage!");

                    if (currentHealth <= 25) { // higher number b/c some glitch
                        Controller.goToDeathScreen();
                    }

                    updateHearts(currentHealth);
                }

                oldHealth = currentHealth;
            }
        };
    }

    public void updateHearts(double currentHealth) {
        int emptyThreshold = (int) Math.floor(currentHealth / Heart.HEALTH_PER_HEART);
        for (int i = 0; i < hearts.size(); i++) {
            if (i >= emptyThreshold) {
                hearts.get(i).setEmpty();
            } else {
                hearts.get(i).setFull();
            }
        }

        ArrayList<ImageView> heartsImages = new ArrayList<ImageView>(hearts.size());
        for (int j = 0; j < hearts.size(); j++) {
            heartsImages.add(hearts.get(j).getImageView());
        }
        heartsBox.getChildren().setAll(heartsImages);
    }

    public void updatePlayerMaxHp() {
        this.hearts = new ArrayList<Heart>(numHearts);
        this.heartsBox = new HBox(heartsPadding);
        for (int i = 0; i < numHearts; i++) {
            Heart heart = new Heart(heartsDimensions, heartsDimensions, true);
            hearts.add(heart);
            heartsBox.getChildren().add(heart.getImageView());
        }
        heartsBox.setLayoutX(heartsPadding);
        heartsBox.setLayoutY(800 - heartsDimensions - heartsPadding);
        if (Controller.getGameScreen() != null) {
            Controller.getGameScreen().getBoard().getChildren().remove(heartsBox);
            Controller.getGameScreen().getBoard().getChildren().add(heartsBox);
        }
    }

    public Group getGroup() {
        return imageGroup;
    }

    public ArrayList<Heart> getHearts() {
        return hearts;
    }

    public HBox getHeartsBox() {
        return heartsBox;
    }

    public Rectangle2D getBoundary() {
        return new Rectangle2D(positionX, positionY, width, height);
    }

    public boolean intersects(Touchable other) {
        if (other == null) {
            System.out.println("Null Other!");
            return false;
        }
        boolean intersected = other.getBoundary().intersects(this.getBoundary());

        return intersected;
    }

    public int getCoins() {
        return coins;
    }
    public void setCoins(int amount) {
        coins = amount;
        if (Controller.getGameScreen() != null) {
            Controller.getGameScreen().getCoinDisplay().setText("Coins: " + getCoins());
        }
    }
    public void addCoins(int amount) {
        setCoins(coins + amount);
    }
    public void subtractCoins(int amount) {
        setCoins(coins - amount);
    }
    public void setWeapon(Weapon w) {
        weapon = w;
        if (weapon instanceof Spear) {
            imageView.setImage(spearSprite);
        } else if (weapon instanceof Sword) {
            imageView.setImage(swordSprite);
        } else if (weapon instanceof Bow) {
            imageView.setImage(bowSprite);
        }
    }
    public Weapon getWeapon() {
        return weapon;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public double getPositionX() {
        return positionX;
    }
    public double getPositionY() {
        return positionY;
    }
    public double getSpeed() {
        return speed;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    // DAMAGE || HP
    public void addDamageStat(double value) {
        this.damage += value;
    }

    public void subtractDamageStat(double value) {
        addDamageStat(-value);
    }

    public double getDamageStat() {
        return this.damage;
    }

    public double getDamageCooldown() {
        return damageCooldown;
    }

    public void setHealth(double value) {
        this.currentHealth = value;
    }
    public void addHealth(double value) {
        this.currentHealth += value;
    }
    public void takeDamage(double damage) {
        if (damageCooldown <= 0) {
            addHealth(-damage);
            damageCooldown = 60;
        }
        System.out.println(currentHealth);
    }

    public void addMaximumHealth(double value) {
        this.maxHealth += value;
        this.currentHealth += value;
        this.numHearts += (int) Math.floor(value / Heart.HEALTH_PER_HEART);
        updatePlayerMaxHp();
    }

    public void subtractMaximumHealth(double value) {
        addMaximumHealth(-value);
    }

    public double getMaximumHealth() {
        return this.maxHealth;
    }

    public double getCurrentHealth() {
        return currentHealth;
    }


    public HBox gethotbarBox() {
        return this.hotbarBox;
    }

    public AnimationTimer getPlayerLogicTimer() {
        return playerLogic;
    }

    public AnimationTimer getPlayerHpUpdateTimer() {
        return playerHpUpdate;
    }

    public AnimationTimer getItemLoop() {
        return itemLoop;
    }

    public String toString() {
        return positionX + ";" + positionY + ";" + width + ";" + height + ";";
    }
}
