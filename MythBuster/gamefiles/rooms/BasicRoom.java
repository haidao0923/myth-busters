package gamefiles.rooms;

import controller.Controller;
import javafx.scene.Group;
import javafx.scene.paint.Color;

public class BasicRoom extends Room {

    public BasicRoom(int width, int height, int row, int column) {
        super(width, height, row, column);
    }


    @Override
    public Group getRoomGroup() {
        Group roomGroup = super.getRoomGroup();
        Controller.getGameScreen().changeBackgroundColor(Color.PURPLE);
        return roomGroup;
    }

    // This will later have methods to generate monsters, gold, chests, and the like.
    public String toString() {
        return "Regular Room";
    }
}
