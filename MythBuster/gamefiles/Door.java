package gamefiles;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Door {
    private Group doorGroup;
    public Door(int x, int y) {
        doorGroup = new Group();
        Rectangle door = new Rectangle(0, 0, 60, 80);
        Rectangle knob = new Rectangle(50, 40, 7, 7);
        door.setStroke(Color.BLACK);
        door.setFill(Color.BROWN);
        knob.setFill(Color.BLACK);
        doorGroup.getChildren().addAll(door, knob);
        doorGroup.relocate(x, y);
    }

    public Group getGroup() {
        return doorGroup;
    }
    public void relocate(int x, int y) {
        doorGroup.relocate(x, y);
    }
    public int getWidth() {
        return 100;
    }
    public int getHeight() {
        return 100;
    }
}
