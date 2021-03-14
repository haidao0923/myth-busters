package gamefiles;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Collections;
import java.io.FileWriter;

import controller.Controller;


public class RoomLayout {
    static final int TOTAL_ROWS = 6;
    static final int TOTAL_COLUMNS = 6;
    static final int ROOM_WIDTH = Controller.getW();
    static final int ROOM_HEIGHT = Controller.getH();

    private int startRoomRow;
    private int startRoomColumn;

    private int bossRoomRow;
    private int bossRoomColumn;

    private Room[][] rooms;

    public RoomLayout() {
        rooms = new Room[TOTAL_ROWS][TOTAL_COLUMNS];

        this.setStartingRoom();
        this.fillRooms();
        this.growingTreeAlgorithm();
        this.setBossRoom();
        this.toFile();
    }

    public void fillRooms() {
        for (int row = 0; row < rooms.length; row++) {
            for (int column = 0; column < rooms[0].length; column++) {
                rooms[row][column] = new BasicRoom(ROOM_WIDTH, ROOM_HEIGHT, row, column);
            }
        }

        rooms[startRoomRow][startRoomColumn] = new StartingRoom(ROOM_WIDTH,
                ROOM_HEIGHT, startRoomRow, startRoomColumn);
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

        Room startingRoom = rooms[startRoomRow][startRoomColumn];
        visitedSet.add(startingRoom);
        this.addStartingNeighbors(currentList, visitedSet, startingRoom);

        while (currentList.size() > 0) {
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
                } else if (direction == 2 && currRoom.getColumn() != TOTAL_COLUMNS - 1) {
                    neighbor = rooms[currRoom.getRow()][currRoom.getColumn() + 1];
                } else if (direction == 3 && currRoom.getRow() != TOTAL_ROWS - 1) {
                    neighbor = rooms[currRoom.getRow() + 1][currRoom.getColumn()];
                }

                if (neighbor != null && !visitedSet.contains(neighbor)) {
                    // Valid nonvisited neighbor
                    if (direction == 0) {
                        currRoom.setLeftDoor(neighbor);
                        neighbor.setRightDoor(currRoom);
                    } else if (direction == 1) {
                        currRoom.setTopDoor(neighbor);
                        neighbor.setBottomDoor(currRoom);
                    } else if (direction == 2) {
                        currRoom.setRightDoor(neighbor);
                        neighbor.setLeftDoor(currRoom);
                    } else if (direction == 3) {
                        currRoom.setBottomDoor(neighbor);
                        neighbor.setTopDoor(currRoom);
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

    public void addStartingNeighbors(ArrayList<Room> currentList,
                                     HashSet<Room> visitedSet, Room startingRoom) {
        // Left
        startingRoom.setLeftDoor(rooms[startRoomRow][startRoomColumn - 1]);
        rooms[startRoomRow][startRoomColumn - 1].setRightDoor(startingRoom);
        currentList.add(rooms[startRoomRow][startRoomColumn - 1]);
        visitedSet.add(rooms[startRoomRow][startRoomColumn - 1]);
        // Top
        startingRoom.setTopDoor(rooms[startRoomRow - 1][startRoomColumn]);
        rooms[startRoomRow - 1][startRoomColumn].setBottomDoor(startingRoom);
        currentList.add(rooms[startRoomRow - 1][startRoomColumn]);
        visitedSet.add(rooms[startRoomRow - 1][startRoomColumn]);
        // Right
        startingRoom.setRightDoor(rooms[startRoomRow][startRoomColumn + 1]);
        rooms[startRoomRow][startRoomColumn + 1].setLeftDoor(startingRoom);
        currentList.add(rooms[startRoomRow][startRoomColumn + 1]);
        visitedSet.add(rooms[startRoomRow][startRoomColumn + 1]);
        // Bottom
        startingRoom.setBottomDoor(rooms[startRoomRow + 1][startRoomColumn]);
        rooms[startRoomRow + 1][startRoomColumn].setTopDoor(startingRoom);
        currentList.add(rooms[startRoomRow + 1][startRoomColumn]);
        visitedSet.add(rooms[startRoomRow + 1][startRoomColumn]);
    }

    public void setStartingRoom() {
        this.startRoomRow = (int) Math.floor(Math.random() * (TOTAL_ROWS - 2)) + 1;
        this.startRoomColumn = (int) Math.floor(Math.random() * (TOTAL_COLUMNS - 2)) + 1;
    }

    public void setBossRoom() {
        int r;
        int c;
        if (startRoomRow < TOTAL_ROWS / 2) {
            r = TOTAL_ROWS - 1;
        } else {
            r = 0;
        }
        if (startRoomColumn < TOTAL_COLUMNS / 2) {
            c = TOTAL_COLUMNS - 1;
        } else {
            c = 0;
        }

        Room oldRoom = rooms[r][c];
        BossRoom bossRoom = new BossRoom(ROOM_WIDTH, ROOM_HEIGHT, r, c);

        if (oldRoom.getLeftDoor() != null) {
            bossRoom.setLeftDoor(oldRoom.getLeftDoor().getDestination());
            oldRoom.getLeftDoor().getDestination().setRightDoor(bossRoom);
        }
        if (oldRoom.getTopDoor() != null) {
            bossRoom.setTopDoor(oldRoom.getTopDoor().getDestination());
            oldRoom.getTopDoor().getDestination().setBottomDoor(bossRoom);
        }
        if (oldRoom.getRightDoor() != null) {
            bossRoom.setRightDoor(oldRoom.getRightDoor().getDestination());
            oldRoom.getRightDoor().getDestination().setLeftDoor(bossRoom);
        }
        if (oldRoom.getBottomDoor() != null) {
            bossRoom.setBottomDoor(oldRoom.getBottomDoor().getDestination());
            oldRoom.getBottomDoor().getDestination().setTopDoor(bossRoom);
        }

        rooms[r][c] = bossRoom;
        bossRoomRow = r;
        bossRoomColumn = c;
    }

    public void toFile() {
        try {
            String filepath = System.getProperty("user.dir")
                    + "/MythBuster/gamefiles/printables/RoomLayout.txt";
            new FileWriter(filepath, false).close();

            FileWriter writer = new FileWriter(filepath);

            for (int row = 0; row < rooms.length; row++) {
                String cap = "";
                String mid = "";
                for (int column = 0; column < rooms[0].length; column++) {
                    Room currRoom = rooms[row][column];
                    String roomUp = currRoom.getTopDoor() != null ? " " : "-";
                    cap += "+" + roomUp;
                    String roomLeft = currRoom.getLeftDoor() != null ? " " : "|";
                    String content;
                    if (currRoom instanceof StartingRoom) {
                        content = "S";
                    } else if (currRoom instanceof BossRoom) {
                        content = "B";
                    } else {
                        content = " ";
                    }
                    mid += roomLeft + content;
                }
                cap += "+\n";
                mid += "|\n";
                writer.write(cap);
                writer.write(mid);
            }

            String bot = "";
            for (int column = 0; column < rooms[0].length; column++) {
                bot += "+-";
            }
            bot += "+";
            writer.write(bot);
            writer.close();
        } catch (Exception e) {
            System.out.println("Exception: Writing to file printables/RoomLayout.java.");
            e.printStackTrace();
            System.out.println(e);
        }
    }

    public Room getRoom(int row, int column) {
        return rooms[row][column];
    }

    public int getStartRoomRow() {
        return startRoomRow;
    }

    public int getStartRoomColumn() {
        return startRoomColumn;
    }

    public int getBossRoomRow() {
        return bossRoomRow;
    }
    public int getBossRoomColumn() {
        return bossRoomColumn;
    }
}
