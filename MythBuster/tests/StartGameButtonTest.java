package tests;
import controller.Controller;

import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;

import org.testfx.matcher.control.TextMatchers;

import static org.testfx.api.FxAssert.verifyThat;



public class StartGameButtonTest extends ApplicationTest {

    private Controller controller;

    @Override
    public void start(Stage primaryStage) throws Exception {
        controller = new Controller();
        controller.start(primaryStage);
    }

    @Test
    public void testInvalidName() {
        clickOn("Start Game");
        verifyThat("Configuration Screen", NodeMatchers.isNotNull());
    }

    @Test
    public void testWeaponSelection() {
        clickOn("Start Game");
        clickOn("#HeroNameTextField");
        write("BOB");
        clickOn("#startingWeaponSelector");
        clickOn("SHIELD");
        clickOn("Begin!");
        verifyThat("#weaponDisplay", TextMatchers.hasText("Weapon: Shield"));
    }

}
