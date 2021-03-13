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

    private double positionX;
    private double positionY;
    private double width;
    private double height;

    private Group imageGroup;

    public Player(int coins) {
        coins = 0;
        width = 100;
        height = 100;

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
                if (input.contains("A") && input.contains("W") && positionX > 0 && positionY > 0) {
                    moveRelative(-7, -7);
                } else if (input.contains("A") && input.contains("S") && positionX > 0
                        && positionY + height < scene.getHeight()) {
                    moveRelative(-7, 7);
                } else if (input.contains("D") && input.contains("S")
                        && positionX + width < scene.getWidth()
                        && positionY + height < scene.getHeight()) {
                    moveRelative(7, 7);
                } else if (input.contains("D") && input.contains("W")
                        && positionX + width < scene.getWidth() && positionY > 0) {
                    moveRelative(7, -7);
                } else if ((input.contains("LEFT") || input.contains("A")) && positionX > 0) {
                    moveRelative(-10, 0);
                } else if ((input.contains("RIGHT") || input.contains("D"))
                        && positionX + width < scene.getWidth()) {
                    moveRelative(10, 0);
                } else if ((input.contains("UP") || input.contains("W")) && positionY > 0) {
                    moveRelative(0, -10);
                } else if ((input.contains("DOWN") || input.contains("S"))
                        && positionY + height < scene.getHeight()) {
                    moveRelative(0, 10);
                }
            }
        }.start();
    }


    public void moveAbsolute(int x, int y) {
        positionX = x;
        positionY = y;
        imageGroup.relocate(positionX, positionY);
    }

    public void moveRelative(int x, int y) {
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

    public String toString() {
        return positionX + ";" + positionY + ";" + width + ";" + height + ";";
    }
}
