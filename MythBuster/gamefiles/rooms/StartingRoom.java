package gamefiles.rooms;

import gamefiles.rooms.Room;

public class StartingRoom extends Room {
    public StartingRoom(int width, int height, int row, int column) {
        super(width, height, row, column);
        this.setLeftDoor(null);
        this.setBottomDoor(null);
        this.setRightDoor(null);
        this.setTopDoor(null);


    }
    public String toString() {
        return "Starting Room";
    }



}
