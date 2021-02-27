package views;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import gamefiles.Difficulty;
import gamefiles.StartingWeapon;
import javafx.stage.Stage;

public class ConfigurationScreen {
    private int width;
    private int height;
    private Label header;
    private Button beginButton;
    private ComboBox<StartingWeapon> startingWeaponSelector;
    private ComboBox<Difficulty> difficultySelector;
    private TextField heroNameTextField;

    private ConfigurationScreen() { }

    /**
     * Create Configuration Screen.
     * @param width width of screen
     * @param height height of screen
     */
    public ConfigurationScreen(int width, int height) {
        this.width = width;
        this.height = height;
        beginButton = new Button("Begin!");
        heroNameTextField = new TextField();
        startingWeaponSelector = new ComboBox<StartingWeapon>();
        difficultySelector = new ComboBox<Difficulty>();

        header = new Label("Configuration Screen");
        header.setStyle("-fx-font-size: 100; -fx-font-weight: bold;-fx-border-color:red;"
                + "-fx-alignment:center; -fx-text-fill: #DEB887; -fx-background-color:black");

    }

    /**
     * Get Configuration Screen Scene.
     * @return configuration screen scene
     */
    public Scene getScene() {

        heroNameTextField.setPrefWidth(350);
        heroNameTextField.setFont(new Font(30));
        heroNameTextField.setPromptText("Name your hero");

        startingWeaponSelector.setPrefWidth(300);
        startingWeaponSelector.setStyle("-fx-font-size: 30");
        startingWeaponSelector.getItems().setAll(StartingWeapon.values());
        startingWeaponSelector.getSelectionModel().selectFirst();

        difficultySelector.setPrefWidth(300);
        difficultySelector.setStyle("-fx-font-size: 30");
        difficultySelector.getItems().setAll(Difficulty.values());
        difficultySelector.getSelectionModel().selectFirst();

        HBox hBox = new HBox(50);
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(heroNameTextField, startingWeaponSelector, difficultySelector);
        hBox.setMinWidth(width);
        hBox.setLayoutY(300);

        Label heroNameDescription = new Label("Hero Name");
        heroNameDescription.layoutXProperty().bind(heroNameTextField.layoutXProperty());
        heroNameDescription.layoutYProperty().bind(hBox.layoutYProperty().subtract(50));
        heroNameDescription.setStyle("-fx-font-size: 30; "
                + "-fx-font-weight: bold; -fx-alignment:center");

        heroNameDescription.setPrefWidth(350);

        Label startingWeaponDescription = new Label("Starting GameFiles.Weapon");
        startingWeaponDescription.layoutXProperty()
                .bind(startingWeaponSelector.layoutXProperty());
        startingWeaponDescription.layoutYProperty().bind(hBox.layoutYProperty().subtract(50));
        startingWeaponDescription.setStyle("-fx-font-size: 30; "
                + "-fx-font-weight: bold; -fx-alignment:center");
        startingWeaponDescription.setPrefWidth(300);

        Label difficultyDescription = new Label("Difficulty");
        difficultyDescription.layoutXProperty().bind(difficultySelector.layoutXProperty());
        difficultyDescription.layoutYProperty().bind(hBox.layoutYProperty().subtract(50));
        difficultyDescription.setStyle("-fx-font-size: 30; "
                + "-fx-font-weight: bold; -fx-alignment:center");
        difficultyDescription.setPrefWidth(300);

        Group descriptionGroup = new Group(heroNameDescription,
                startingWeaponDescription, difficultyDescription);

        beginButton.setStyle("-fx-font-weight: bold; -fx-font-size: 30");

        VBox beginButtonVBox = new VBox(beginButton);
        beginButtonVBox.setAlignment(Pos.CENTER);
        beginButtonVBox.setMinWidth(width);
        beginButtonVBox.setLayoutY(700);

        Group board = new Group();
        board.getChildren().addAll(header, hBox, descriptionGroup, beginButtonVBox);
        Scene scene = new Scene(board, width, height, Color.POWDERBLUE);
        return scene;
    }

    /**
     * Get button to begin game.
     * @return button to begin game
     */
    public Button getBeginButton() {
        return beginButton;
    }

    /**
     * Get Hero Name TextField
     * @return hero name TextField
     */
    public TextField getHeroNameField() {
        return heroNameTextField;
    }

    /**
     * Get Weapon Selector Box.
     * @return weapon selector box
     */
    public ComboBox<StartingWeapon> getStartingWeaponSelector() {
        return startingWeaponSelector;
    }

    /**
     * Get Difficulty selector box.
     * @return difficulty selector
     */

    public ComboBox<Difficulty> getDifficultySelector() {
        return difficultySelector;
    }

    /**
     * Set necessary bindings properties.
     * @param stage stage to bind to
     */
    public void setBinds(Stage stage) {
        header.minWidthProperty().bind(stage.widthProperty());
    }
}
