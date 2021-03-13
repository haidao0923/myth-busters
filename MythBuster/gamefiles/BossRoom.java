package gamefiles;

public class BossRoom extends Room {
    public BossRoom(int width, int height, int row, int column) {
        super(width, height, row, column);
    }
    //this class will have a method to generate a boss, as well as trigger a victory condition
    public String toString() {
        return "Boss Room";
    }
}
