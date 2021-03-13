package views;

import gamefiles.Player;
import gamefiles.Room;
import gamefiles.RoomLayout;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import javafx.stage.Stage;

public class GameScreen {
    private int width;
    private int height;
    private Player player;

    private Text nameDisplay;
    private Text weaponDisplay;
    private Text coinDisplay;

    private RoomLayout roomLayout;
    private Room currentRoom;
    private Group roomGroup;
    private Group board;


    private GameScreen() { }

    /**
     * Create Room One.
     * @param width width of room screen
     * @param height height of room screen
     * @param player player in room
     */
    public GameScreen(int width, int height, Player player, RoomLayout roomLayout) {
        this.player = player;
        this.width = width;
        this.height = height;
        this.board = new Group();
        this.roomLayout = roomLayout;
    }

    public void setCurrentRoom(Room room) {
        currentRoom = room;

    }

    /**
     * Get scene for Room One Screen.
     * @return room one scene
     */
    public Scene getScene() {
        nameDisplay = new Text(110, 10, "Name: " + player.getName());
        nameDisplay.setStyle("-fx-font-size: 30;");
        weaponDisplay = new Text(210, 10, "Weapon: " + player.getWeapon().getName());
        weaponDisplay.setStyle("-fx-font-size: 30;");
        weaponDisplay.setId("weaponDisplay");
        coinDisplay = new Text(310, 10, "Coins: " + player.getCoins());
        coinDisplay.setStyle("-fx-font-size: 30;");
        coinDisplay.setId("coinDisplay");

        HBox displays = new HBox(50, nameDisplay, weaponDisplay, coinDisplay);
        displays.setLayoutX(10);
        displays.setLayoutY(20);


        player.moveAbsolute(100, 100);

        setCurrentRoom(roomLayout.getRoom(RoomLayout.START_ROOM_ROW, RoomLayout.START_ROOM_COLUMN);

        board.getChildren().addAll(displays);
        board.getChildren().addAll(currentRoom.getRoomGroup(),
                player.getGroup());
        Scene scene = new Scene(board, width, height);
        scene.setFill(Color.PURPLE);

        player.movePlayer(scene);

        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                // game logic

                //If there is a left door and we are at it.
                if (currentRoom.getLeftDoor() != null && player.intersects(currentRoom.getLeftDoor())) {
                    setCurrentRoom(RoomLayout.rooms[currentRoom.getRow()][currentRoom.getColumn() - 1]);
                    board.getChildren().clear();
                    board.getChildren().addAll(currentRoom.getRoomGroup(), player.getGroup());
                    player.moveAbsolute(width / 2, height / 2);
                        //System.out.println(RoomLayout.rooms[currentRoom.getRow()][currentRoom.getColumn() - 1]);
                }

                //If there is a right door and we are at it.
                if (currentRoom.getRightDoor() != null && player.intersects(currentRoom.getRightDoor())) {
                    setCurrentRoom(RoomLayout.rooms[currentRoom.getRow()][currentRoom.getColumn() + 1]);
                    board.getChildren().clear();
                    board.getChildren().addAll(currentRoom.getRoomGroup(),
                            player.getGroup());
                    player.moveAbsolute(width / 2, height / 2);
                }
                
                //If there is a right door and we are at it.
                if (currentRoom.getTopDoor() != null && player.intersects(currentRoom.getTopDoor())) {
                    setCurrentRoom(RoomLayout.rooms[currentRoom.getRow() - 1][currentRoom.getColumn()]);
                    board.getChildren().clear();
                    board.getChildren().addAll(currentRoom.getRoomGroup(),
                            player.getGroup());
                    player.moveAbsolute(width / 2, height / 2);
                }

                //If there is a right door and we are at it.
                if (currentRoom.getBottomDoor() != null && player.intersects(currentRoom.getBottomDoor())) {
                    setCurrentRoom(RoomLayout.rooms[currentRoom.getRow() + 1][currentRoom.getColumn()]);
                    board.getChildren().clear();
                    board.getChildren().addAll(currentRoom.getRoomGroup(),
                            player.getGroup());
                    player.moveAbsolute(width / 2, height / 2);
                }
            }
        }.startF();


        return scene;
    }


    /**
     * Set necessary binding properties.  Currently placeholder.
     * @param stage stage to bind to
     */
    public void setBinds(Stage stage) {
        //PlaceHolder for binding widths.
    }

    /**
     * @return the weapon display
     */
    public Text getWeaponDisplay() {
        return weaponDisplay;
    }

    /**
     * @return the coin display
     */
    public Text getCoinDisplay() {
        return coinDisplay;
    }

    /**
     * @return the name display
     */
    public Text getNameDisplay() {
        return nameDisplay;
    }
}
