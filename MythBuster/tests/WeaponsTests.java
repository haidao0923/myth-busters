package tests;

import controller.Controller;
import gamefiles.Player;
import views.RoomOne;

import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.assertEquals;

public class WeaponsTests extends ApplicationTest {

    Controller controller;
    Player player;
    RoomOne roomOne;

    @Override
    public void start(Stage primaryStage) throws Exception {
        controller = new Controller();
        controller.start(primaryStage);
        controller.goToConfigurationScreen();
    }

    @Test
    public void testSwordPlayerAndDisplay() {
        clickOn("#HeroNameTextField");
        write("Test");

        String startingWeapon = "SWORD";

        clickOn("#startingWeaponSelector");
        clickOn(startingWeapon);
        clickOn("Begin!");

        player = controller.getPlayer();
        roomOne = controller.getRoomOne();

        assertEquals("Sword", player.getWeapon().getName());
        assertEquals("Weapon: Sword" , roomOne.getWeaponDisplay().getText());
    }
}
