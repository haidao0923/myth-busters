import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Game extends Application {

    private static final double W = 1200, H = 800;

    private Group board;

    int coins;

    public static void main(String[] args) {
        launch(args);
   }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setResizable(false);
        welcomeScreen(stage);
    }

    private void initializeStats() {
        coins = 10;
    }

    private void welcomeScreen(Stage stage) {
        Label header = new Label("MYTHBUSTERS");
        header.setStyle("-fx-font-size: 100; -fx-font-weight: bold;-fx-border-color:red;"
                + "-fx-alignment:center; -fx-text-fill: #DEB887; -fx-background-color:black");
        header.minWidthProperty().bind(stage.widthProperty());

        ImageView leftImageView = new ImageView("sprites/Medusa.png");
        leftImageView.setFitWidth(200);
        leftImageView.setFitHeight(200);
        Node leftImage = leftImageView;
        leftImage.setLayoutX(100);
        leftImage.setLayoutY(250);

        ImageView rightImageView = new ImageView("sprites/Medusa.png");
        rightImageView.setFitWidth(200);
        rightImageView.setFitHeight(200);
        Node rightImage = rightImageView;
        rightImage.setLayoutX(W - 300);
        rightImage.setLayoutY(250);

        Button startGameButton = new Button("Start Game");
        startGameButton.setStyle("-fx-font-weight: bold; -fx-font-size: 30");
        startGameButton.addEventHandler(ActionEvent.ACTION, (e) -> {
            configurationScreen(stage);
        });

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().add(startGameButton);
        vBox.minWidthProperty().bind(stage.widthProperty());
        vBox.setLayoutY(300);;

        board = new Group();
        board.getChildren().addAll(header, leftImage, rightImage, vBox);
        Scene scene = new Scene(board, W, H, Color.PURPLE);

        stage.setScene(scene);
        stage.show();
    }

    private void configurationScreen(Stage stage) {
        Label header = new Label("Configuration Screen");
        header.setStyle("-fx-font-size: 100; -fx-font-weight: bold;-fx-border-color:red;"
                + "-fx-alignment:center; -fx-text-fill: #DEB887; -fx-background-color:black");
        header.minWidthProperty().bind(stage.widthProperty());

        TextField heroNameTextField = new TextField();
        heroNameTextField.setPrefWidth(350);
        heroNameTextField.setFont(new Font(30));
        heroNameTextField.setPromptText("Name your hero");

        ComboBox<StartingWeapon> startingWeaponSelector = new ComboBox<StartingWeapon>();
        startingWeaponSelector.setPrefWidth(300);
        startingWeaponSelector.setStyle("-fx-font-size: 30");
        startingWeaponSelector.getItems().setAll(StartingWeapon.values());

        ComboBox<Difficulty> difficultySelector = new ComboBox<Difficulty>();
        difficultySelector.setPrefWidth(300);
        difficultySelector.setStyle("-fx-font-size: 30");
        difficultySelector.getItems().setAll(Difficulty.values());

        HBox hBox = new HBox(50);
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(heroNameTextField, startingWeaponSelector, difficultySelector);
        hBox.minWidthProperty().bind(stage.widthProperty());
        hBox.setLayoutY(300);

        Label heroNameDescription = new Label("Hero Name");
        heroNameDescription.layoutXProperty().bind(heroNameTextField.layoutXProperty());
        heroNameDescription.layoutYProperty().bind(hBox.layoutYProperty().subtract(50));
        heroNameDescription.setStyle("-fx-font-size: 30; -fx-alignment:center");
        heroNameDescription.setPrefWidth(350);

        Label startingWeaponDescription = new Label("Starting Weapon");
        startingWeaponDescription.layoutXProperty().bind(startingWeaponSelector.layoutXProperty());
        startingWeaponDescription.layoutYProperty().bind(hBox.layoutYProperty().subtract(50));
        startingWeaponDescription.setStyle("-fx-font-size: 30; -fx-alignment:center");
        startingWeaponDescription.setPrefWidth(300);

        Label difficultyDescription = new Label("Starting Weapon");
        difficultyDescription.layoutXProperty().bind(difficultySelector.layoutXProperty());
        difficultyDescription.layoutYProperty().bind(hBox.layoutYProperty().subtract(50));
        difficultyDescription.setStyle("-fx-font-size: 30; -fx-alignment:center");
        difficultyDescription.setPrefWidth(300);

        Group descriptionGroup = new Group(heroNameDescription, startingWeaponDescription, difficultyDescription);

        Button beginButton = new Button("Begin");
        beginButton.setStyle("-fx-font-weight: bold; -fx-font-size: 30");
        beginButton.addEventHandler(ActionEvent.ACTION, (e) -> {
            if (heroNameTextField.getText().length() < 1 || heroNameTextField.getText().trim().isEmpty()) {
                showAlert();
                return;
            }
            initializeStats();
            gameScreen(stage);
        });

        VBox beginButtonVBox = new VBox(beginButton);
        beginButtonVBox.setAlignment(Pos.CENTER);
        beginButtonVBox.minWidthProperty().bind(stage.widthProperty());
        beginButtonVBox.setLayoutY(700);;

        board = new Group();
        board.getChildren().addAll(header, hBox, descriptionGroup, beginButtonVBox);
        Scene scene = new Scene(board, W, H, Color.POWDERBLUE);
        stage.setScene(scene);
        stage.show();

    }

    public void gameScreen(Stage stage) {
        board = new Group();
        board.getChildren().addAll();
        Scene scene = new Scene(board, W, H, Color.PURPLE);
        stage.setScene(scene);
        stage.show();
    }

    public void showAlert() {
        Alert alert = new Alert(AlertType.WARNING, "Your hero name is invalid!");
        alert.showAndWait();
        return;
    }
}
