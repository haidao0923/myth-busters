package gamefiles.rooms;

import controller.Controller;
import javafx.scene.Group;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ChallengeRoom extends Room {
    private Rectangle challengeSigil;
    private Text challengeText;

    public ChallengeRoom(int width, int height, int row, int column) {
        super(width, height, row, column);
        challengeSigil = new Rectangle(width / 2 - 50, height / 2 - 50, 100, 100);
        challengeSigil.setFill(Color.RED);
        challengeSigil.setId("Challenge");
        challengeText = new Text(width / 2 - 50, height / 2 - 50, "Challenge");
        challengeText.setFill(Color.WHITE);
        challengeText.setStyle("-fx-font-size: 30;");

    }

    @Override
    public Group getRoomGroup() {
        Group roomGroup = super.getRoomGroup();
        roomGroup.getChildren().addAll(challengeSigil, challengeText);
        Controller.getGameScreen().changeBackgroundColor(Color.BLACK);
        return roomGroup;
    }
    public String toString() {
        return "Challenge Room";
    }

    public Rectangle getSigil() {
        return challengeSigil;
    }
}
