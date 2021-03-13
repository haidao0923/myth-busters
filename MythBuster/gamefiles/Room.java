package gamefiles;

import javafx.scene.Group;

public abstract class Room {
    private Door[] doors = new Door[4];
    private int row;
    private int column;
    private int width;
    private int height;

    public Room(int width, int height, int row, int column) {
        this.width = width;
        this.height = height;
        this.row = row;
        this.column = column;
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
        doors[0] = new Door(100, height / 2, destination);
    }
    public void setTopDoor(Room destination) {
        doors[1] = new Door(width / 2, 100, destination);
    }
    public void setRightDoor(Room destination) {
        doors[2] = new Door(width - 100, height / 2, destination);
    }
    public void setBottomDoor(Room destination) {
        doors[3] = new Door(width / 2, height - 100, destination);
    }

    public Group getRoomGroup() {
        Group roomGroup = new Group();
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

        return roomGroup;
    }

    public int getRow() {
        return row;
    }
    public int getColumn() {
        return column;
    }

    // abstract methods for generating monsters and chests will be here.



}
