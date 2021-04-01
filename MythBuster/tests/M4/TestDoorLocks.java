package tests.M4;

import controller.Controller;
import gamefiles.characters.Player;
import gamefiles.rooms.Room;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.assertEquals;

public class TestDoorLocks extends ApplicationTest {

    private Controller controller;
    private Player player;

    @Override
    public void start(Stage primaryStage) throws Exception {
        controller = new Controller();
        controller.start(primaryStage);
        Controller.goToConfigurationScreen();
    }

    @Test
    public void testDoorLocks() {
        String name = "Test";
        clickOn("#HeroNameTextField");
        write(name);
        clickOn("Begin!");
        player = controller.getPlayer();

        Room currRoom = controller.getCurrentRoom();

        int currentRoomRow = currRoom.getRow();
        double doorPos = currRoom.getTopDoor().getPositionY();
        while (player.getPositionY() > doorPos && currentRoomRow
                == controller.getCurrentRoom().getRow()) {
            push(KeyCode.W);
        }
        int newRoomRow = controller.getCurrentRoom().getRow();

        assertEquals(currentRoomRow, newRoomRow);
    }

    @Test
    public void testRoomUnlock() {
        String name = "Test";
        clickOn("#HeroNameTextField");
        write(name);
        clickOn("Begin!");
        player = controller.getPlayer();

        Room currRoom = controller.getCurrentRoom();
        currRoom.unlockDoors();

        int currentRoomRow = currRoom.getRow();
        double doorPos = currRoom.getTopDoor().getPositionY();
        while (player.getPositionY() > doorPos && currentRoomRow
                == controller.getCurrentRoom().getRow()) {
            push(KeyCode.W);
        }
        int newRoomRow = controller.getCurrentRoom().getRow();

        assertEquals(currentRoomRow - 1, newRoomRow);
    }
}
