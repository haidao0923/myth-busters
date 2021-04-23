package views;

import controller.Controller;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;


public class DeathScreen {
    private int width;
    private int height;
    private Label header;
    private Button restartButton;

    public DeathScreen(int width, int height) {
        this.width = width;
        this.height = height;

        header = new Label("YOU DIED");
        header.setStyle("-fx-font-size: 100; -fx-font-weight: bold;-fx-border-color:red;"
                + "-fx-alignment:center; -fx-text-fill: #DEB887; -fx-background-color:black");
        header.setPrefWidth(width);
        restartButton = new Button("Restart?");
    }

    public Scene getScene() {

        restartButton.setStyle("-fx-font-weight: bold; -fx-font-size: 30");

        VBox restartButtonVBox = new VBox(restartButton);
        restartButtonVBox.setAlignment(Pos.CENTER);

        Insets buttonInset = new Insets(600, 150, 550, 550);

        restartButtonVBox.setPadding(buttonInset);

        int minutes = Controller.getTimeElapsed() / 3600;
        int seconds = Controller.getTimeElapsed() / 60 % 60;
        String timeString = String.format("%02d:%02d", minutes, seconds);
        Text timer = new Text(50, 250, "Time Taken: " + timeString);
        Controller.setTimeElapsed(0);
        timer.setStyle("-fx-font-size: 50;");

        Group board = new Group();
        board.getChildren().addAll(header, timer, restartButtonVBox);
        Scene scene = new Scene(board, width, height, Color.CRIMSON);

        return scene;
    }

    public Button getRestartButton() {
        return restartButton;
    }
}
