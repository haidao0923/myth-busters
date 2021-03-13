package gamefiles;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Collections;
import java.io.FileWriter;

import controller.Controller;


public class RoomLayout {
    static final int totalRows = 7;
    static final int totalColumns = 7;
    static final int ROOM_WIDTH = Controller.getW();
    static final int ROOM_HEIGHT = Controller.getH();

    public static final int START_ROOM_ROW = 4;
    public static final int START_ROOM_COLUMN = 4;


    private Room[][] rooms;

    public RoomLayout() {
        rooms = new Room[totalRows][totalColumns];

        this.fillRooms();
        this.growingTreeAlgorithm();
    }

    public void fillRooms() {
        for (int row = 0; row < rooms.length; row++) {
            for(int column = 0; column < rooms[0].length; column++) {
                rooms[row][column] = new BasicRoom(ROOM_HEIGHT, ROOM_WIDTH, row, column);
            }
        }

        rooms[START_ROOM_ROW][START_ROOM_COLUMN] = new StartingRoom(ROOM_HEIGHT, ROOM_WIDTH, START_ROOM_ROW, 
            START_ROOM_COLUMN);
    }

    /**
     * Growing tree algorithm for generating the maze.
     * Takes a matrix of Rooms and generates "paths" between them according to the specifications.
     * Tracks a visited set and list of Rooms to choose from.
     */
    public void growingTreeAlgorithm() {
        HashSet<Room> visitedSet = new HashSet<Room>();
        ArrayList<Room> currentList = new ArrayList<Room>();
        ArrayList<Integer> directions = new ArrayList<Integer>();
        directions.add(0);
        directions.add(1);
        directions.add(2);
        directions.add(3);

        Room startingRoom = rooms[START_ROOM_ROW][START_ROOM_COLUMN];
        visitedSet.add(startingRoom);
        this.addStartingNeighbors(currentList, visitedSet, startingRoom);

        while(currentList.size() > 0) {
            Collections.shuffle(directions);
            int index = pickIndex(currentList);
            Room currRoom = currentList.get(index);
            Room newRoom = null;

            for (Integer direction : directions) {
                Room neighbor = null;
                if (direction == 0 && currRoom.getColumn() != 0) {
                    neighbor = rooms[currRoom.getRow()][currRoom.getColumn() - 1];
                } else if (direction == 1 && currRoom.getRow() != 0) {
                    neighbor = rooms[currRoom.getRow() - 1][currRoom.getColumn()];
                } else if (direction == 2 && currRoom.getColumn() != totalColumns) {
                    neighbor = rooms[currRoom.getRow()][currRoom.getColumn() + 1];
                } else if (direction == 3 && currRoom.getColumn() != totalRows) {
                    neighbor = rooms[currRoom.getRow() + 1][currRoom.getColumn()];
                }

                if (neighbor != null && !visitedSet.contains(neighbor)) {
                    // Valid nonvisited neighbor
                    if (direction == 0) {
                        currRoom.setLeftDoor(neighbor);
                    } else if (direction == 1) {
                        currRoom.setTopDoor(neighbor);
                    } else if (direction == 2) {
                        currRoom.setRightDoor(neighbor);
                    } else if (direction == 3) {
                        currRoom.setBottomDoor(neighbor);
                    }
                    visitedSet.add(neighbor);
                    currentList.add(neighbor);
                    newRoom = neighbor;
                    break;
                }
            }

            if (newRoom == null) {
                // No valid neighbors = pop off from currentList
                currentList.remove(index);
            }
        }
    }

    public static int pickIndex(ArrayList<Room> currentList) {
        // EDIT THESE NUMBERS TO REFLECT ROOM GENERATION NEEDS
        double newestPercentage = .30;
        double oldestPercentage = 0;
        double randomPercentage = .70;

        double newestThreshold = newestPercentage;
        double oldestThreshold = newestThreshold + oldestPercentage;

        double randomizer = Math.random();

        if (randomizer < newestThreshold) {
            return currentList.size() - 1;
        } else if (randomizer < oldestThreshold) {
            return 0;
        } else {
            return (int) Math.floor(Math.random() * currentList.size());
        }
    }

    public void addStartingNeighbors(ArrayList<Room> currentList, HashSet<Room> visitedSet, Room startingRoom) {
        // Left
        startingRoom.setLeftDoor(rooms[START_ROOM_ROW][START_ROOM_COLUMN - 1]);
        currentList.add(rooms[START_ROOM_ROW][START_ROOM_COLUMN - 1]);
        visitedSet.add(rooms[START_ROOM_ROW][START_ROOM_COLUMN - 1]);
        // Top
        startingRoom.setTopDoor(rooms[START_ROOM_ROW - 1][START_ROOM_COLUMN]);
        currentList.add(rooms[START_ROOM_ROW - 1][START_ROOM_COLUMN]);
        visitedSet.add(rooms[START_ROOM_ROW - 1][START_ROOM_COLUMN]);
        // Right
        startingRoom.setRightDoor(rooms[START_ROOM_ROW][START_ROOM_COLUMN + 1]);
        currentList.add(rooms[START_ROOM_ROW][START_ROOM_COLUMN + 1]);
        visitedSet.add(rooms[START_ROOM_ROW][START_ROOM_COLUMN + 1]);
        // Bottom
        startingRoom.setBottomDoor(rooms[START_ROOM_ROW + 1][START_ROOM_COLUMN]);
        currentList.add(rooms[START_ROOM_ROW + 1][START_ROOM_COLUMN]);
        visitedSet.add(rooms[START_ROOM_ROW + 1][START_ROOM_COLUMN]);
    }

    public void toFile() {
        FileWriter clear = new FileWriter("printables/RoomLayout.txt", false).close();

    }
}
