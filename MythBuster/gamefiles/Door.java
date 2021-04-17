package gamefiles;

import gamefiles.rooms.Room;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Door implements Touchable {

    private Room destination;

    private double positionX;
    private double positionY;
    private static double width = 60;
    private static double height = 80;

    private boolean locked;

    private ImageView imageView;


    private Group doorGroup;
    public Door(double x, double y, Room destination) {
        doorGroup = new Group();
        imageView = new ImageView("sprites/door.png");
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        doorGroup.getChildren().addAll(imageView);
        relocate(x, y);
        this.destination = destination;
        locked = true;
    }

    public Group getGroup() {
        if (isLocked()) {
            imageView.setImage(new Image("sprites/locked_door.png"));
        } else {
            imageView.setImage(new Image("sprites/door.png"));
        }
        return doorGroup;
    }
    public void relocate(double x, double y) {
        positionX = x;
        positionY = y;
        doorGroup.relocate(positionX, positionY);
    }

    public void unlock() {
        locked = false;
        imageView.setImage(new Image("sprites/door.png"));
    }

    public boolean isLocked() {
        return locked;
    }

    public Rectangle2D getBoundary() {
        return new Rectangle2D(positionX, positionY, width, height);
    }

    public boolean intersects(Touchable other) {
        return other.getBoundary().intersects(this.getBoundary());
    }

    public static double getWidth() {
        return width;
    }
    public static double getHeight() {
        return height;
    }
    public Room getDestination() {
        return destination;
    }

    public double getPositionX() {
        return positionX;
    }

    public double getPositionY() {
        return positionY;
    }
}
