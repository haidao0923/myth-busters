package views;

import controller.Controller;
import gamefiles.Difficulty;
import gamefiles.TreasureChest;
import gamefiles.characters.Monster;
import gamefiles.characters.Player;
import gamefiles.rooms.ChallengeRoom;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
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
        restartButton.addEventHandler(ActionEvent.ACTION, (e) -> {
            Controller.goToConfigurationScreen();
        });
        Button exitButton = new Button("Exit");
        exitButton.setLayoutX(width - 150);
        exitButton.setLayoutY(40);
        exitButton.setStyle("-fx-font-weight: bold; -fx-font-size: 30");
        exitButton.setOnAction(e -> {Controller.getMainWindow().close();});

        int minutes = Controller.getTimeElapsed() / 3600;
        int seconds = Controller.getTimeElapsed() / 60 % 60;
        String timeString = String.format("%02d:%02d", minutes, seconds);
        Text timer = new Text(50, 250, "Time Taken: " + timeString);
        Text monstersKilled = new Text(width / 2 + 100, 250, "Monsters Killed: " + Monster.getMonstersKilled());
        Text greedyIndex = new Text(50, 350, "Greedy Index: " + Player.getGreedyIndex());
        Text potionsUsed = new Text(width / 2 + 100, 350, "Potions Used: " + Player.getPotionsUsed());
        Text challengesAttempted = new Text(50, 450, "Challenges Attempted: " + ChallengeRoom.getChallengesAttempted());
        Text chestsOpened = new Text(width / 2 + 100, 450, "Chests Opened: " + TreasureChest.getChestsOpened());
        Controller.setTimeElapsed(0);
        Monster.setMonstersKilled(0);
        Player.setGreedyIndex(0);
        Player.setPotionsUsed(0);
        ChallengeRoom.setChallengesAttempted(0);
        TreasureChest.setChestsOpened(0);
        timer.setFont(new Font(50));
        monstersKilled.setFont(new Font(50));
        greedyIndex.setFont(new Font(50));
        potionsUsed.setFont(new Font(50));
        challengesAttempted.setFont(new Font(50));
        chestsOpened.setFont(new Font(50));

        Text consoleMessage = new Text(50, height - 80, "Time Taken: " + timeString);
        consoleMessage.setWrappingWidth(width - 100);
        consoleMessage.setTextAlignment(TextAlignment.CENTER);
        consoleMessage.prefHeight(80);
        consoleMessage.setStyle("-fx-font-size: 35; -fx-font-style: italic;-fx-alignment:center;");
        switch(Controller.getDifficulty()) {
        case 0:
            consoleMessage.setText("Hahaha! How did you even lose on easy mode???");
            break;
        case 1:
            consoleMessage.setText("So you lost on normal. Eh.");
            break;
        case 2:
            consoleMessage.setText("Congratulations! You can now tell everyone that you lost on hard mode.");
            break;
        }

        Group board = new Group();
        board.getChildren().addAll(header, timer, chestsOpened, greedyIndex, monstersKilled,
            challengesAttempted, potionsUsed, restartButtonVBox, exitButton, consoleMessage);
        Scene scene = new Scene(board, width, height, Color.CRIMSON);

        return scene;
    }

    public Button getRestartButton() {
        return restartButton;
    }
}
