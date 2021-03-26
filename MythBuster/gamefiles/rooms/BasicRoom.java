package gamefiles.rooms;

public class BasicRoom extends Room {

    public BasicRoom(int width, int height, int row, int column) {
        super(width, height, row, column);
    }

    // This will later have methods to generate monsters, gold, chests, and the like.
    public String toString() {
        return "Regular Room";
    }
}
