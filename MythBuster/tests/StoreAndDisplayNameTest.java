package tests;

import controller.Controller;
import gamefiles.Player;


import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.GameModel;
import views.RoomOne;

import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;

import static org.junit.Assert.assertEquals;
import static org.testfx.api.FxAssert.verifyThat;

public class StoreAndDisplayNameTest extends ApplicationTest {
    Controller controller;
    Player player;

    @Override
    public void start(Stage primaryStage) throws Exception {
        controller = new Controller();
        controller.start(primaryStage);
        controller.goToConfigurationScreen();
    }

    /**
     * Make sure that when the user begin the game, the name is actually
     * stored into the Player object.
     */
    @Test
    public void playerNameStoredCorrectly() {
        String name = "Test";
        clickOn("#HeroNameTextField");
        write(name);
        clickOn("Begin!");
        player = controller.getPlayer();
        assertEquals("Test", player.getName());
    }

    /**
     * Make sure that when the user begin the game, the name displayed
     * is correct with the name of the player in the system.
     */
    @Test
    public void playerNameDisplayedCorrectly() {
        String name = "Test";
        clickOn("#HeroNameTextField");
        write(name);
        clickOn("Begin!");
        player = controller.getPlayer();
        RoomOne roomOne = controller.getRoomOne();
        assertEquals("Name: " + player.getName(), roomOne.getNameDisplay().getText());
    }}
