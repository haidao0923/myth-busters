package gamefiles.rooms;

import controller.Controller;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BossRoom extends Room {
    private Rectangle boss;
    private Text bossName;

    public BossRoom(int width, int height, int row, int column) {
        super(width, height, row, column);
    }

    @Override
    public Group getRoomGroup() {
        Group roomGroup = super.getRoomGroup();
        roomGroup.getChildren().addAll(boss, bossName);
        Controller.getGameScreen().changeBackgroundColor(Color.RED);
        return roomGroup;
    }
    public String toString() {
        return "Boss Room";
    }

    public Rectangle getBoss() {
        return boss;
    }
}
