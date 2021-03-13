package gamefiles;

import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Door implements Touchable {
    private Room destination; //Placeholder, maybe not needed.


    private double positionX;
    private double positionY;
    private double width = 60;
    private double height = 80;

    private Group doorGroup;
    public Door(double x, double y, Room destination) {
        doorGroup = new Group();
        Rectangle door = new Rectangle(0, 0, width, height);
        Rectangle knob = new Rectangle(50, 40, 7, 7);
        door.setStroke(Color.BLACK);
        door.setFill(Color.BROWN);
        knob.setFill(Color.BLACK);
        doorGroup.getChildren().addAll(door, knob);
        relocate(x, y);
        this.destination = destination;
    }

    public Group getGroup() {
        return doorGroup;
    }
    public void relocate(double x, double y) {
        positionX = x;
        positionY = y;
        doorGroup.relocate(positionX, positionY);
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
    public Room getDestination() {
        return destination;
    }
}
