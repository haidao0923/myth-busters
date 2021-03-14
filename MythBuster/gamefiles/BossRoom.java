package gamefiles;

import javafx.scene.Group;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BossRoom extends Room {
    private Rectangle boss;
    private Text bossName;
    public BossRoom(int width, int height, int row, int column) {
        super(width, height, row, column);
        boss = new Rectangle(width / 2 + 150, height / 2, 130, 130);
        boss.setFill(Color.BLACK);
        bossName = new Text(width / 2 + 155, height / 2 + 50, "The Boss");
        bossName.setFill(Color.WHITE);
        bossName.setStyle("-fx-font-size: 30;");
    }
    //this class will have a method to generate a boss, as well as trigger a victory condition
    @Override
    public Group getRoomGroup() {
        Group roomGroup = super.getRoomGroup();
        roomGroup.getChildren().addAll(boss, bossName);
        return roomGroup;
    }
    public String toString() {
        return "Boss Room";
    }
}
