package gamefiles.characters;

import java.util.ArrayList;

import controller.Controller;
import gamefiles.Heart;
import gamefiles.Touchable;
import gamefiles.Weapon;
import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class Player implements Touchable {
    private String name;
    private int coins;
    private Weapon weapon;
    private double speed;
    private boolean shooting;
    private int numHearts;
    private double maxHealth;
    private double currentHealth;
    // private double oldHealth;
    private double percentageHealth;
    private double damageCooldown;


    private double positionX;
    private double positionY;
    private double width;
    private double height;

    private final double heartsPadding = 10;
    private final double heartsDimensions = 50;

    private Group imageGroup;
    private ArrayList<Heart> hearts;
    private HBox heartsBox;

    public Player(int coins) {
        coins = 0;
        // hitbox
        width = 100;
        height = 100;

        ImageView imageView = new ImageView("sprites/Medusa.png");
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        imageView.setLayoutX(100);
        imageView.setLayoutY(250);
        imageGroup = new Group();
        imageGroup.getChildren().add(imageView);

        // hp
        currentHealth = 250;
        // oldHealth = currentHealth;
        maxHealth = 250;
        numHearts = (int) Math.floor(maxHealth / Heart.HEALTH_PER_HEART);
        
        updatePlayerMaxHp();
    }

    public Player(Player player, int x, int y) {
        this(player.coins);

        moveAbsolute(x, y);
    }

    public void movePlayer(Scene scene) {
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

        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                // game logic
                damageCooldown -= 1;

                // movement
                if (input.size() > 1) {
                    speed = 7;
                } else {
                    speed = 10;
                }
                if (input.contains("A") && positionX > 0) {
                    moveRelative(-speed, 0);
                }
                if (input.contains("D") && positionX + width < scene.getWidth()) {
                    moveRelative(speed, 0);
                }
                if (input.contains("W") && positionY > 0) {
                    moveRelative(0, -speed);
                }
                if (input.contains("S") && positionY + height < scene.getHeight()) {
                    moveRelative(0, speed);
                }
            }
        }.start();
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

    public void updatePlayerHp() {
        new AnimationTimer() {
            double oldHealth = getCurrentHealth();
            public void handle(long currentNanoTime) {
                double currentHealth = getCurrentHealth();
                
                if (currentHealth != oldHealth) {
                    System.out.println("Updating Damage!");

                    if (currentHealth <= 0) {
                        Controller.goToDeathScreen();
                        this.stop();
                    }

                    for (int i = (int) Math.floor(currentHealth / Heart.HEALTH_PER_HEART); i >= 0 && i < hearts.size(); i++) {
                        hearts.get(i).setEmpty();
                    }
                    ArrayList<ImageView> heartsImages = new ArrayList<ImageView>(hearts.size());
                    for (int j = 0; j < hearts.size(); j++) {  
                        heartsImages.add(hearts.get(j).getImageView());
                    }
                    heartsBox.getChildren().setAll(heartsImages);
                }
                
                oldHealth = currentHealth;
            }
        }.start();
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
    }

    public Group getGroup() {
        return imageGroup;
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
        coins = coins + amount;
    }

    public void setWeapon(Weapon w) {
        weapon = w;
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
    public double getDamageCooldown() {
        return damageCooldown;
    }

    public void addHealth(double value) {
        this.currentHealth += value;
    }
    public void takeDamage(double damage) {
        addHealth(-damage);
    }

    public void addMaximumHealth(double value) {
        this.maxHealth += value;
        this.numHearts += (int) Math.floor(value / Heart.HEALTH_PER_HEART);
        updatePlayerMaxHp();
    }

    public void subtractMaximumHealth(double value) {
        addMaximumHealth(-value);
    }

    public double getCurrentHealth() {
        return currentHealth;
    }

    public String toString() {
        return positionX + ";" + positionY + ";" + width + ";" + height + ";";
    }
}
