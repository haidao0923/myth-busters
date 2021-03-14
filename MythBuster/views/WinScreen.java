package views;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class WinScreen {
    private int width;
    private int height;
    private Label header;
    private Text win;

    public WinScreen(int width, int height) {
        this.width = width;
        this.height = height;

        header = new Label("Congratulations!");
        header.setStyle("-fx-font-size: 100; -fx-font-weight: bold;-fx-border-color:red;"
                + "-fx-alignment:center; -fx-text-fill: #DEB887; -fx-background-color:black");
    }

    public Scene getScene() {
        win = new Text(width / 2 - 200, height / 2, "You Win!");
        win.setStyle("-fx-font-size: 100;");

        Group board = new Group();
        board.getChildren().addAll(header, win);
        Scene scene = new Scene(board, width, height, Color.POWDERBLUE);
        return scene;
    }
}
