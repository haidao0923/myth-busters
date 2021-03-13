package gamefiles;

import controller.Controller;


public class RoomLayout {
    static final int totalRows = 5;
    static final int totalColumns = 15;
    static final int ROOM_WIDTH = Controller.getW();
    static final int ROOM_HEIGHT = Controller.getH();

    public static final int START_ROOM_ROW = 3;
    public static final int START_ROOM_COLUMN = 5;

    public static Room[][] rooms;
    static {
        rooms = new Room[totalRows][totalColumns];
        rooms[START_ROOM_ROW][START_ROOM_COLUMN] = new Room(ROOM_WIDTH, ROOM_HEIGHT, START_ROOM_ROW, START_ROOM_COLUMN);

    }

    public static void generateRoom(int row, int column) {
        rooms[row][column] = new Room(ROOM_WIDTH, ROOM_HEIGHT, row, column);
        if (column == totalColumns - 1) {
            //This means we are at the right border.  Don't generate a door to the right.
        }
        else if (column < totalColumns - 1 && rooms[row][column + 1] != null && rooms[row][column + 1].getLeftDoor() != null) {
            //This means there is a room to the right with a door to this room.  So generate a door to that room.
            rooms[row][column].setRightDoor(null);
        } else if (column < totalColumns - 1 && rooms[row][column + 1] != null && rooms[row][column + 1].getLeftDoor() == null) {
            //This means there is a room to the right, but it does not have a door to this room.  So, don't generate a door to that room.
        } else {
            //This means there is no room to the right.  Use randomizer.
        }

        //Someone please finish this by writing the stuff for the other three directions, and thinking if there are any edge cases.  -Peter


    }

}
