package views;

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
    private Text death;
    private Button restartButton;

    public DeathScreen(int width, int height) {
        this.width = width;
        this.height = height;

        header = new Label("YOU DIED");
        header.setStyle("-fx-font-size: 100; -fx-font-weight: bold;-fx-border-color:red;"
                + "-fx-alignment:center; -fx-text-fill: #DEB887; -fx-background-color:black");
        restartButton = new Button("Restart?");
    }

    public Scene getScene() {
        death = new Text(width / 2 - 200, height / 2, "YOU DIED");
        death.setStyle("-fx-font-size: 100;");

        restartButton.setStyle("-fx-font-weight: bold; -fx-font-size: 30");

        VBox restartButtonVBox = new VBox(restartButton);
        restartButtonVBox.setAlignment(Pos.CENTER);

        Insets buttonInset = new Insets(450, 150, 550, 550);

        restartButtonVBox.setPadding(buttonInset);

        Group board = new Group();
        board.getChildren().addAll(header, death, restartButtonVBox);
        Scene scene = new Scene(board, width, height, Color.CRIMSON);

        return scene;
    }

    public Button getRestartButton() {
        return restartButton;
    }
}
