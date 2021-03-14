package controller;
import gamefiles.*;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.layout.HBox;
import model.GameModel;
import views.ConfigurationScreen;
import views.GameScreen;
import views.WelcomeScreen;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import views.WinScreen;


public class Controller extends Application {
    private static Stage mainWindow;
    private static final int W = 1200;
    private static final int H = 800;
    private static Player player;
    private static GameScreen gameScreen;
    private static RoomLayout roomLayout;
    private static Room currentRoom;


    public void start(Stage primaryStage) throws Exception {
        roomLayout = new RoomLayout();
        mainWindow = primaryStage;
        mainWindow.setTitle("MythBusters!");
        player = new Player(0);
        WeaponDatabase.initialize();
        initWelcomeScreen();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void initWelcomeScreen() {
        WelcomeScreen welcomeScreen = new WelcomeScreen(W, H);
        Scene scene = welcomeScreen.getScene();
        Button startGameButton = welcomeScreen.getStartButton();

        startGameButton.setOnAction(e -> {
            goToConfigurationScreen();
        });
        welcomeScreen.setBinds(mainWindow);

        mainWindow.setScene(scene);
        mainWindow.show();
    }

    public static void goToConfigurationScreen() {
        ConfigurationScreen configScreen = new ConfigurationScreen(W, H);
        Button beginButton = configScreen.getBeginButton();
        TextField heroNameField = configScreen.getHeroNameField();
        ComboBox<StartingWeapon> startingWeaponSelector = configScreen.getStartingWeaponSelector();
        ComboBox<Difficulty> difficultySelector = configScreen.getDifficultySelector();

        beginButton.addEventHandler(ActionEvent.ACTION, (e) -> {
            if (heroNameField.getText().length() < 1
                    || heroNameField.getText().trim().isEmpty()) {
                showAlert("Your name cannot be empty or whitespace only!");
                return;
            }
            initializeStats(heroNameField.getText(),
                    startingWeaponSelector.getSelectionModel().getSelectedIndex(),
                    difficultySelector.getValue());
            goToStartingRoom();
        });
        Scene scene = configScreen.getScene();
        mainWindow.setScene(scene);
        configScreen.setBinds(mainWindow);

    }

    public static void goToStartingRoom() {
        //Initialize starting room.
        gameScreen = new GameScreen(W, H, player, roomLayout);
        currentRoom = roomLayout.getRoom(roomLayout.getStartRoomRow(), roomLayout.getStartRoomColumn());
        gameScreen.getBoard().getChildren().addAll(currentRoom.getRoomGroup(), player.getGroup());
        gameScreen.getDisplays().getChildren().add(currentRoom.getRoomInfo());
        player.moveAbsolute(W / 2, H / 2);

        Scene scene = gameScreen.getScene();
        mainWindow.setScene(scene);
        playGame();

    }

    public static void playGame() {

        player.movePlayer(gameScreen.getScene());

        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                // game logic
                Group board = gameScreen.getBoard();
                HBox displays = gameScreen.getDisplays();

                //If there is a left door and we are at it.
                if (currentRoom.getLeftDoor() != null && player.intersects(currentRoom.getLeftDoor())) {
                    displays.getChildren().remove(currentRoom.getRoomInfo());
                    currentRoom = roomLayout.getRoom(currentRoom.getRow(), currentRoom.getColumn() - 1);
                    gameScreen.updateBoard(currentRoom);
                    player.moveAbsolute(W / 2, H / 2);
                }

                //If there is a right door and we are at it.
                if (currentRoom.getRightDoor() != null && player.intersects(currentRoom.getRightDoor())) {
                    displays.getChildren().remove(currentRoom.getRoomInfo());
                    currentRoom = roomLayout.getRoom(currentRoom.getRow(), currentRoom.getColumn() + 1);
                    gameScreen.updateBoard(currentRoom);
                    player.moveAbsolute(W / 2, H / 2);
                }

                //If there is a top door and we are at it.
                if (currentRoom.getTopDoor() != null && player.intersects(currentRoom.getTopDoor())) {
                    displays.getChildren().remove(currentRoom.getRoomInfo());
                    currentRoom = roomLayout.getRoom(currentRoom.getRow() - 1, currentRoom.getColumn());
                    gameScreen.updateBoard(currentRoom);
                    player.moveAbsolute(W / 2, H / 2);
                }

                //If there is a bottom door and we are at it.
                if (currentRoom.getBottomDoor() != null && player.intersects(currentRoom.getBottomDoor())) {
                    displays.getChildren().remove(currentRoom.getRoomInfo());
                    currentRoom = roomLayout.getRoom(currentRoom.getRow() + 1, currentRoom.getColumn());
                    gameScreen.updateBoard(currentRoom);
                    player.moveAbsolute(W / 2, H / 2);
                }

            }
        }.start();

    }

    public static void goToWinScreen() {
        WinScreen winScreen = new WinScreen(W, H);
        Scene scene = winScreen.getScene();
        mainWindow.setScene(scene);
        mainWindow.show();
    }


    /**
     * Set initial parameters
     *
     * @param nameEntry           the name of the hero
     * @param startingWeaponIndex the index of the starting weapon
     * @param difficultyEntry     the difficulty
     */
    private static void initializeStats(String nameEntry,
                                 int startingWeaponIndex, Difficulty difficultyEntry) {
        player.setName(nameEntry);
        player.setWeapon(WeaponDatabase.getWeapon(startingWeaponIndex));
        Difficulty difficulty = difficultyEntry;
        switch (difficulty) {
        case EASY:
            player.setCoins(30);
            break;
        case MEDIUM:
            player.setCoins(20);
            break;
        case HARD:
            player.setCoins(10);
            break;
        default: // unnecessary because of type safety
        }
    }

    /**
     * Alert Method
     * @param message Message to display in alert.
     */
    public static void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING, message);
        alert.showAndWait();
        return;
    }

    /**
     * @return the player object
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Returns the current room
     * @return the current room
     */
    public Room getCurrentRoom() {
        return currentRoom;
    }
    /**
     * @return the first room
     */
    public GameScreen getRoomOne() {
        return gameScreen;
    }

    /**
     * Private testing method to return a String representation of the Label of the window.
     * @return the string representing the label of the window.
     */
    public String getWindowTitle() {
        return mainWindow.getTitle();
    }

    public static int getW() {
        return W;
    }
    public static int getH() {
        return H;
    }
}
