package gamefiles;

import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;

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
    private double damageCooldown;


    private double positionX;
    private double positionY;
    private double width;
    private double height;

    private Group imageGroup;

    public Player(int coins) {
        coins = 0;
        width = 50;
        height = 50;

        ImageView imageView = new ImageView("sprites/Medusa.png");
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        Node image = imageView;
        image.setLayoutX(100);
        image.setLayoutY(250);
        imageGroup = new Group();
        imageGroup.getChildren().add(image);
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
        if (other instanceof Monster) {
            if (damageCooldown > 0) {
                return false;
            }
            damageCooldown += 10;
        }
        return other.getBoundary().intersects(this.getBoundary());
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

    public String toString() {
        return positionX + ";" + positionY + ";" + width + ";" + height + ";";
    }
}
