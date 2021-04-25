package tests.M6;

import controller.Controller;
import gamefiles.Door;
import gamefiles.characters.Boss;
import gamefiles.rooms.BasicRoom;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.service.query.EmptyNodeQueryException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.testfx.api.FxAssert.verifyThat;

public class BossTest extends ApplicationTest {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Controller controller = new Controller();
        controller.start(primaryStage);
        Controller.goToWinScreen();
    }

    @Test
    public void testBossHealth() {
        clickOn("Start Game");
        clickOn("#HeroNameTextField");
        write("test123");
        clickOn("#startingWeaponSelector");
        clickOn("SPEAR");
        clickOn("#startingWeaponSelector");
        clickOn("HARD");
        clickOn("Begin!");

        Boss b = new Boss();
        assertEquals(1000, b.getCurrentHealth(), 0.01);
    }
    
}
