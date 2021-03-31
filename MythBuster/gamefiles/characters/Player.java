package gamefiles.characters;

import controller.GameLoop;
import controller.SpriteAnimation;
import gamefiles.Touchable;
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
import javafx.util.Duration;

import java.util.ArrayList;

public class Player implements Touchable {
    private String name;
    private int coins;
    private Weapon weapon;
    private double speed;
    private boolean shooting;
    private double maxHealth;
    private double currentHealth;
    private double percentageHealth;
    private double attackCD = 0;
    private double moveCD = 0;
    private double damage = 10;
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

    private int spriteWidth = 63;
    private int spriteHeight = 55;
    private int spriteX = 0;
    private int spriteY = 585;

    private Group imageGroup;

    public Player(int coins, Weapon weapon) {
        this.coins = coins;
        this.weapon = weapon;
        width = 100;
        height = 100;
        damage = weapon.getDamage() * damage;
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

        currentHealth = 9999;
        maxHealth = 9999;
    }

    public void attack(Scene scene) {
        Animation animation;
        int currX = spriteX;
        int currY = spriteY;
        if (weapon instanceof Spear) {
            spriteY = spriteY - 256; //go to the attack frames
            animation = new SpriteAnimation(imageView, Duration.millis(500), 8, 8, 0, spriteY, spriteWidth,
                    spriteHeight);
            animation.setCycleCount(1);
            animation.play();
            for (Monster monster : GameLoop.monsters) {
                if (this.intersects(monster)) {
                    System.out.println("player attacked" + monster.getName());
                    monster.takeDamage(damage);
                }
            }
        }
        spriteX = currX;
        spriteY = currY;
        Rectangle2D viewpoint = new Rectangle2D(spriteX, spriteY, spriteWidth, spriteHeight);
        imageView.setViewport(viewpoint);
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

        new AnimationTimer() {
            public void handle(long now) {
                // game logic
                if (attackCD > 0) {
                    attackCD--;
                }
                if (input.contains("J") && attackCD <= 0) {
                    speed = 0;
                    attackCD = 60;
                    attack(scene);
                    moveCD = 30;
                } else if (input.size() > 1) {
                    speed = 7;
                } else {
                    speed = 10;
                }
                if (moveCD > 0) {
                    moveCD--;
                } else {
                    if (input.contains("A") && positionX > 0) {
                        imageView.setScaleX(1);
                        moveRelative(-speed, 0);
                    }
                    if (input.contains("D") && positionX + width < scene.getWidth()) {
                        imageView.setScaleX(-1);
                        moveRelative(speed, 0);
                    }
                    if (input.contains("W") && positionY > 0) {
                        moveRelative(0, -speed);
                    }
                    if (input.contains("S") && positionY + height < scene.getHeight()) {
                        moveRelative(0, speed);
                    }
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

    public Group getGroup() {
        return imageGroup;
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
        System.out.println(currentHealth);
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public double getCurrentHealth() {
        return currentHealth;
    }

    public String toString() {
        return positionX + ";" + positionY + ";" + width + ";" + height + ";";
    }
}
